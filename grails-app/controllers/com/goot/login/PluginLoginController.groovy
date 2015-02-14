package com.goot.login

import com.goot.User
import com.goot.core.GlobalController
import grails.converters.JSON

/**
 * PluginLoginController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class PluginLoginController extends GlobalController {
	
	def customAuthenticationService
	def springSecurityService
	
	/**
	 * Connect from plugin (i.e. chrome extension). 
	 * Returns true or false as a string
	 */
	def ajaxConnect = {
		
		try { 
			def username = request.JSON.username
			def password = request.JSON.password
			
			def res = customAuthenticationService.authenticate(username,password)
			
			def user = springSecurityService.getCurrentUser() as User;
			
			
			if(res){
				log.debug "JSESSIONID : " + session.getId()
				render ([status : getSuccess(), content : [sessionId : session.getId(), firstName : user.firstName]] as JSON);
				return;
			} else { 
				throw new Exception('Error while logging in');
			}
		} catch(Exception e){
			render ([status : getError()] as JSON);
			return;
		}
		
	}
	
}
