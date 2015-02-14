package com.goot.core.ws.plugin

import grails.converters.JSON
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
			
			def friend = User.findByEmailOrUsername(email, email);
			
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
			
			def friend = User.findByEmailOrUsername(email, email);
			
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
	
	/**
	 * Get connected user's friends
	 */
	def getFriends(){ 
		try { 
			def user = springSecurityService.getCurrentUser() as User;
			
			def friends = user.friends;
			
			render ([status : getSuccess(), content : [friends : friends]] as JSON);
			return;
		} catch(Exception e){
			log.error "error", e
			
			render ([status : getError()] as JSON);
			return;
		
		}
	}
	
	
	
}
