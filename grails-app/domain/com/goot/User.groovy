package com.goot

import java.security.SecureRandom;
import com.goot.data.Link;
import com.goot.data.Comment;

class User {

	transient springSecurityService

	String username
	String password
	String salt
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	String firstName
	String lastName
	String email

	//"favorites" corresponds to the link user added to his goot or commented
	//"comments" corresponds to the commentes he created 
	static hasMany = [favorites: Link,
					comments: Comment, 
					friends: User];
	
	static constraints = {
		username blank: false, unique: true
		password blank: false
		email blank: false, unique: true
		salt maxSize: 64
		favorites nullable : true
		comments nullable : true
		friends nullable : true
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	protected encodePassword() {
		password = springSecurityService.encodePassword(password, salt) // update
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')){ //if password changed, then, reencode it with salt before updating
			encodePassword()
		}
	}

	String getSalt() {
		if (!this.salt) {
			def rnd = new byte[48];
			new SecureRandom().nextBytes(rnd)
			this.salt = rnd.encodeBase64()
		}
		this.salt
	}
}
