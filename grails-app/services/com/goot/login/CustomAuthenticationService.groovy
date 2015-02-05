package com.goot.login

import javax.servlet.*
import javax.servlet.http.*

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder as SCH

import com.goot.User

/**
 * CustomAuthenticationService
 * A service class encapsulates the core business logic of a Grails application
 */
class CustomAuthenticationService {

    static transactional = true

	/** Dependency injection for daoAuthenticationProvider. */
	def daoAuthenticationProvider
		
	def springSecurityService

	/**
	 * Authenticate user using username and password
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	def authenticate(username,password) throws Exception {
		
				def config = SpringSecurityUtils.securityConfig
				boolean connexion = false;
				
				def user = User.findByUsername(username)
				try {
					
					if(user.password == springSecurityService.encodePassword(password, user.salt)){
						// this block has to be changed to fit connexion with salt 					
						def auth = new UsernamePasswordAuthenticationToken(username, password)
						def authtoken = daoAuthenticationProvider.authenticate(auth)
						SCH.context.authentication = authtoken
						//authenticate(username);
					}
					
					//if successful authentication
					def userInstance = (User)springSecurityService.getCurrentUser()
		
					if(springSecurityService.isLoggedIn() && userInstance.username == username){
						connexion = true
					}
				} catch(Exception e){
				 	log.error "error", e
					connexion = false
				}
		
			return connexion;	
			
	}
	
	
	
	/**
	 * Authenticate only from username.
	 * Full authentication must be done by another service (might typically be facebook)
	 * @param username
	 * @return
	 * @throws Exception
	 */
	def authenticate(username) throws Exception { 
		
		springSecurityService.reauthenticate username;
	
	}
	
	
}
