package com.goot.core.ws.plugin

import grails.plugins.springsecurity.SpringSecurityService;

import org.springframework.dao.DataIntegrityViolationException

import com.goot.User;
import com.goot.core.GlobalController
import com.goot.validation.NewAccountRegisterUserInstance

/**
 * UserController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class UserWSController extends GlobalController {
	
	def userRegistrationService;
	
	def springSecurityService;

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	/**
	 * Add a friend from his mail
	 * @return
	 */
	def addFriend(){
		
		try { 
			def user = springSecurityService.getCurrentUser();
			
			def email = request.JSON.email
			
			def friend = User.findByEmailOrUsername(email);
			
			if(user && friend){
				user.addToFriends(friend);	
			}
			
			user.save();
			
			render getSuccess();
		}catch(Exception e){
			log.error "error", e
		
			render getError();
			
		}
	}
	
	
	/**
	 * Remove from user's friends
	 * @return
	 */
	def removeFriend(){
		try { 
			def user = springSecurityService.getCurrentUser();
			
			def email = request.JSON.email
			
			def friend = User.findByEmailOrUsername(email);
			
			if(user && friend){
				user.removeFromFriends(friend);
			}
			
			user.save();
			
			render getSuccess();
		} catch(Exception e){
			log.error "error", e
			
			render getError();
		
		}
	}
	
	
	
}
