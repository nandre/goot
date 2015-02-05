package com.goot.core

/**
 * GlobalController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class GlobalController {

	/*
	 * Define standard global functions
	 * Should also define exception handling
	 */
	
	static scaffold = true
	def grailsApplication;
	
	String getSuccess(){
		return grailsApplication.config.successMessage;
	}
	
	String getError(){
		return grailsApplication.config.errorMessage;
	}
}
