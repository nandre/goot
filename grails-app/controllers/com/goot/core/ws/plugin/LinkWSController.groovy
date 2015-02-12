package com.goot.core.ws.plugin

import org.springframework.dao.DataIntegrityViolationException

import com.goot.LinkSuggestion
import com.goot.User
import com.goot.core.GlobalController;
import com.goot.data.Link;
import grails.converters.JSON

/**
 * LinkController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class LinkWSController extends GlobalController {

	def springSecurityService;
	
	def linkService;
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  
	
	
	
	/**
	 * Create link if doesn't exist and save it into user history 
	 * @return
	 */
	def add(){ 
		//JSESSIONID is passed in cookie so user can be gotten from the session
		try { 
			def user = springSecurityService.getCurrentUser() as User;	
			def tabUrl = request.JSON.tabUrl;
			
			log.debug "logged user : " + user?.username;
			log.debug "tabUrl : " + tabUrl;
			
			linkService.addToFavorites(user, tabUrl);
						
			render getSuccess();

		}catch(Exception ex){
			log.error "error", ex
			log.debug "error message : " + getError();
			render getError();
		}
	
	}
	
	
	
	/**
	 * Send a link to a friend
	 * @return
	 */
	def sendToFriend(){
		
		try {
			def user = springSecurityService.getCurrentUser() as User;
			def tabUrl = request.JSON.tabUrl;
			def receiverMail = request.JSON.receiverMail
			
			// get link or add it to favorites if it's not already in
			def link = linkService.addToFavorites(user, tabUrl);
			
			def receiver = User.findByEmailOrUsername(receiverMail);
			
			def suggestion = new LinkSuggestion(sender : user, 
								receiver : receiver, 
								link : linkService.findOrCreate(tabUrl, user))
	
			suggestion.save();
			
			return getSuccess();
		} catch(Exception e){
			log.error "error", e
			
			return getError();
		}						
		
	}
	
	def getMyLinks(){
		try {
			def user = springSecurityService.getCurrentUser() as User;
		
			def links = user.favorites
			
			render ([status : getSuccess(), content : [links : links]] as JSON)
			return;
		} catch(Exception e){
			log.error "error", e
			
			render ([status : getError()] as JSON)
			return;
		}
	}
}
