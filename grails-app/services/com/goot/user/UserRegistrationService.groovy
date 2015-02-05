package com.goot.user

import com.goot.FacebookUser
import com.goot.User
import com.goot.Role
import com.goot.UserRole

import org.codehaus.groovy.grails.web.json.JSONObject
import org.junit.internal.runners.statements.FailOnTimeout;

/**
 * UserRegistrationService
 * A service class encapsulates the core business logic of a Grails application
 */
class UserRegistrationService {

    static transactional = true

	def toolsService
	
    def createFacebookUser(JSONObject fbUserData, String accessToken = null, Date accessTokenExpires = null) {
		
		log.debug "Creating facebook user in service"
		
		def user = new User(firstName : fbUserData.first_name, lastName : fbUserData.last_name,
			email : fbUserData.email, username : fbUserData.email, password : toolsService.keyGenerator())
		
		def fbUser = new FacebookUser(uid : fbUserData.id, accessToken : accessToken, accessTokenExpires : accessTokenExpires,  user : user)
	
		
		if(user.validate() && fbUser.validate()) {
			user.save()
			fbUser.save()
			
			def userRole = Role.findByAuthority('ROLE_USER');
			UserRole.create(user, userRole)
			
			log.debug("New FB user correctly registered")
			return true;

		}
		else {
			log.debug "Errors during user validation : "
			
			user.errors.allErrors.each {
				log.debug it
			}
			fbUser.errors.allErrors.each {
				log.debug it
			}
			
			return false;
		}

    }
	
	
	
	
	
	
	
	def createUser(String email, String firstName, String lastName,
				   String password, String secretQuestion, String secretAnswer){ 
		
				   
	    log.debug "Service createUser called for email : " + email; 
				  
		def done = false;
		def user = new User(username : email,
							password : password, 
							firstName : firstName, 
							lastName : lastName,
							email : email, 
							secretQuestion : secretQuestion, 
							secretAnswer : secretAnswer, 
							enabled : true)
		try { 
			if(!user.validate()){ 
				log.debug "Errors while creating user : "
				user.errors.allErrors.each {
					log.debug it
				}
			} else {
				log.debug "user is correct";
				user.save();
					
				def userRole = Role.findByAuthority('ROLE_USER');
				UserRole.create(user, userRole);
					
				done = true;
			}
		} catch(Exception e){
		    log.debug "Error while creating user : " + e.getMessage() 
			return done;
		}		   
		
		return done;		   
				   
	}
	
	
	
	
	
}
