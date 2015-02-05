package com.goot.validation

import com.goot.User;
import grails.validation.Validateable;

@Validateable
class NewAccountRegisterUserInstance {

	String password = ""
	String password2 = ""
	String firstName =""
	String lastName =""
	String email = ""

	String secretQuestion
	String secretAnswer


	static constraints = {
		password blank : false, nullable : false
		password2 blank : false, nullable : false
		firstName blank : false, nullable : false
		lastName blank : false, nullable : false
		email blank: false, nullable: false, email : true, validator: { String email ->
			if (email && User.countByEmail(email)) {
				return 'email.error.unique'
			}
		}
		password blank: false, minSize: 8, maxSize: 64, validator: { password, userInst ->

			if (password && password.length() >= 8 && password.length() <= 64 &&
			(!password.matches('^.*\\p{Alpha}.*$') ||
			!password.matches('^.*\\p{Digit}.*$')
			//||!password.matches('^.*[!@#$%^&].*$')
			)) {
				return 'password.error.strength'
			}
		}
		password2 validator: { password2, userInst ->
			if (userInst.password != password2) {
				return 'passsord.error.mismatch'
			}
		}
		secretQuestion blank: false, nullable: false
		secretAnswer blank : false, nullable : false
	}
}