package com.goot.login

/**
 * PluginLoginController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class PluginLoginController {
	
	def customAuthenticationService
	
	/**
	 * Connect from plugin (i.e. chrome extension). 
	 * Returns true or false as a string
	 */
	def ajaxConnect = {
		
		def username = request.JSON.username
		def password = request.JSON.password
		
		def res = customAuthenticationService.authenticate(username,password)
		
		
		if(res){
			log.debug "JSESSIONID : " + session.getId()
			render session.getId();
		} else { 
			render "error"
		}
		
	}
	
}
