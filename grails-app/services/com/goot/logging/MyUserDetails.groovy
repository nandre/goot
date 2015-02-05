package com.goot.logging


import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser

class MyUserDetails extends GrailsUser {
	public final String salt

	MyUserDetails(GrailsUser base, String salt) {
		super(
			base.username,
			base.password,
			base.enabled,
			base.accountNonExpired,
			base.credentialsNonExpired,
			base.accountNonLocked,
			base.authorities,
			base.id
		)

		this.salt = salt;
	}
}