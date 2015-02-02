package com.goot.login
import grails.converters.JSON

import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.codehaus.groovy.grails.web.json.JSONObject

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

import com.goot.FacebookUser
import com.goot.User

class LoginController {

	/**
	 * Dependency injection for the authenticationTrustResolver.
	 */
	def authenticationTrustResolver

	/**
	 * Dependency injection for the springSecurityService.
	 */
	def springSecurityService
	
	def customAuthenticationService

	def facebookGraphService
	
	def userRegistrationService
	
	/**
	 * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
	 */
	def index = {
		if (springSecurityService.isLoggedIn()) {
			if(params.newFacebookUser){
				log.debug "Redirect to welcome page"
				redirect controller : 'facebookUser', action : 'welcomePage'
			}
			else { 
				redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
			}
		}
		else {
			log.debug "User not logged, redirect to auth page"
			redirect action: 'auth', params : [username : params.username] 
		}
	}

	/**
	 * Show the login page.
	 */
	def auth = {
		
		def config = SpringSecurityUtils.securityConfig

		if (springSecurityService.isLoggedIn()) {
			redirect uri: config.successHandler.defaultTargetUrl
			return
		}
		
		log.debug "User not logged, show the login page"

		String view = 'auth'
		String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
		render view: view, model: [postUrl: postUrl,
		                           rememberMeParameter: config.rememberMe.parameter]
	}
	
	/**
	 * Log the user through facebook
	 */
	def facebookLogin = { 
		
		log.debug "Connecting user with facebook account"
		/*
		 * This function is called after a successful facebook login.
		 * The facebook authentication filter has already created a session.facebook 
		 * object containing the uid of the facebook user. 
		 */
		
		if (springSecurityService.isLoggedIn()) {
			redirect uri: config.successHandler.defaultTargetUrl
			return
		}
		
		def uid = session.facebook.uid;
		def fbUser;
		
		log.debug "uid passed : " + uid;
		
		if(uid)
			fbUser = FacebookUser.findByUid(uid);
		
		def user;
		
		if(fbUser){
			log.debug "Facebook user found" 
			user = fbUser.user as User
			
			if(user){
				customAuthenticationService.authenticate(user?.username)
				log.debug "Facebook connection done"
			}
			
			/* anyway, go back to main page */
			redirect(action : "index")
			return;
			
		} else if (uid){ 
			log.debug "Facebook user doesn't exist"
			
			def fbUserData = facebookGraphService.getFacebookProfile() as JSONObject
			
			log.info "fbUserData : " + fbUserData; 
			
			userRegistrationService.createFacebookUser(fbUserData)
			
			params.newFacebookUser = true;
	
			redirect(action : "index", params : params)
			return;
		} else { 
				
			log.debug("No uid passed so nothing can be done..., redirect to index");
			
			redirect(action : "index")
			return;
		
		}
		
		
	}

	/**
	 * The redirect action for Ajax requests.
	 */
	def authAjax = {
		response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
		response.sendError HttpServletResponse.SC_UNAUTHORIZED
	}

	/**
	 * Show denied page.
	 */
	def denied = {
		if (springSecurityService.isLoggedIn() &&
				authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
			// have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
			redirect action: 'full', params: params
		}
	}

	/**
	 * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
	 */
	def full = {
		def config = SpringSecurityUtils.securityConfig
		render view: 'auth', params: params,
			model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
			        postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
	}

	/**
	 * Callback after a failed login. Redirects to the auth page with a warning message.
	 */
	def authfail = {

		def username = session[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
		String msg = ''
		def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
		if (exception) {
			if (exception instanceof AccountExpiredException) {
				msg = g.message(code: "springSecurity.errors.login.expired")
			}
			else if (exception instanceof CredentialsExpiredException) {
				msg = g.message(code: "springSecurity.errors.login.passwordExpired")
			}
			else if (exception instanceof DisabledException) {
				msg = g.message(code: "springSecurity.errors.login.disabled")
			}
			else if (exception instanceof LockedException) {
				msg = g.message(code: "springSecurity.errors.login.locked")
			}
			else {
				msg = g.message(code: "springSecurity.errors.login.fail")
			}
		}

		if (springSecurityService.isAjax(request)) {
			render([error: msg] as JSON)
		}
		else {
			flash.message = msg
			redirect action: 'auth', params: params
		}
	}

	/**
	 * The Ajax success redirect url.
	 */
	def ajaxSuccess = {
		render([success: true, username: springSecurityService.authentication.name] as JSON)
	}

	/**
	 * The Ajax denied redirect url.
	 */
	def ajaxDenied = {
		render([error: 'access denied'] as JSON)
	}
}
