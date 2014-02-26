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

	def authenticate(username,password) throws Exception {
		
				def config = SpringSecurityUtils.securityConfig
				boolean connexion = false;
				
				try {
					def auth = new UsernamePasswordAuthenticationToken(username, password)
					def authtoken = daoAuthenticationProvider.authenticate(auth)
					SCH.context.authentication = authtoken
					
					//if successful authentication
					def userInstance = (User)springSecurityService.getCurrentUser()
		
					if(springSecurityService.isLoggedIn() == true && userInstance.username == username){
						connexion = true
					}
				} catch(Exception e){ 
					connexion = false
				}
		
			return connexion;	
			
	}
	
	
	
	
	def authenticate(username) throws Exception { 
		
		springSecurityService.reauthenticate username;
	
	}
	
	
}
