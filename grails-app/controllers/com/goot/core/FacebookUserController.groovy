package com.goot.core

import org.codehaus.groovy.grails.web.json.JSONObject
import org.springframework.dao.DataIntegrityViolationException

import com.goot.utils.RequestTools;
import com.goot.FacebookUser;
import com.goot.User;
import com.goot.Role;
import com.goot.UserRole;

import com.goot.utils.ToolsService;
/**
 * FacebookUserController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class FacebookUserController {

	def facebookGraphService 
	
	def toolsService
	
	def customAuthenticationService
	
	def springSecurityService
	
	def userRegistrationService
	
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [facebookUserInstanceList: FacebookUser.list(params), facebookUserInstanceTotal: FacebookUser.count()]
    }

    def create() {
        [facebookUserInstance: new FacebookUser(params)]
    }

    def save() {
        def facebookUserInstance = new FacebookUser(params)
        if (!facebookUserInstance.save(flush: true)) {
            render(view: "create", model: [facebookUserInstance: facebookUserInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'facebookUser.label', default: 'FacebookUser'), facebookUserInstance.id])
        redirect(action: "show", id: facebookUserInstance.id)
    }

    def show() {
        def facebookUserInstance = FacebookUser.get(params.id)
        if (!facebookUserInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'facebookUser.label', default: 'FacebookUser'), params.id])
            redirect(action: "list")
            return
        }

        [facebookUserInstance: facebookUserInstance]
    }

    def edit() {
        def facebookUserInstance = FacebookUser.get(params.id)
        if (!facebookUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'facebookUser.label', default: 'FacebookUser'), params.id])
            redirect(action: "list")
            return
        }

        [facebookUserInstance: facebookUserInstance]
    }

    def update() {
        def facebookUserInstance = FacebookUser.get(params.id)
        if (!facebookUserInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'facebookUser.label', default: 'FacebookUser'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (facebookUserInstance.version > version) {
                facebookUserInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'facebookUser.label', default: 'FacebookUser')] as Object[],
                          "Another user has updated this FacebookUser while you were editing")
                render(view: "edit", model: [facebookUserInstance: facebookUserInstance])
                return
            }
        }

        facebookUserInstance.properties = params

        if (!facebookUserInstance.save(flush: true)) {
            render(view: "edit", model: [facebookUserInstance: facebookUserInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'facebookUser.label', default: 'FacebookUser'), facebookUserInstance.id])
        redirect(action: "show", id: facebookUserInstance.id)
    }

    def delete() {
        def facebookUserInstance = FacebookUser.get(params.id)
        if (!facebookUserInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'facebookUser.label', default: 'FacebookUser'), params.id])
            redirect(action: "list")
            return
        }

        try {
            facebookUserInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'facebookUser.label', default: 'FacebookUser'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'facebookUser.label', default: 'FacebookUser'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	
	
	
	def syncFacebookUser() { 
		
		def accessToken = params.access_token;
		Date accessTokenExpires;
		JSONObject fbUserData;
		
		try { 
			def expiresIn = params.expires_in as Long; // in seconds
		
			def now = new Date().getTime();
			def accessTokenExpiresTimestamp = now + (expiresIn  * 1000); //in milliseconds
		
			accessTokenExpires = new Date(accessTokenExpiresTimestamp);
		} catch(Exception e){ 
			//no matter if we don't have the expiration date
		}
		
		
		if(!accessToken){
			log.debug "No fb token received"
			return [isLoggedWithFacebook : false]
		}
		
		try { 
			def url = facebookGraphService.DOMAIN_MAP.graph + 'me?access_token=' + accessToken
			
			log.debug("Getting facebook user infos from url : " + url)
			
			//Requesting facebook for data
			def fbUserDataString = RequestTools.makeRequest(url,[method : 'GET']) as String
			if(fbUserDataString){ 
				fbUserData = new JSONObject(fbUserDataString);
			}
			
			if(fbUserData){
				log.debug "Fb data received : " + fbUserData
				
				/* Add facebook uid to newly created session */
				if(session.facebook == null){ 
					log.debug "Create new object 'facebook' in session"
					session.facebook = [:];
				}
				try { 
					session.facebook.uid = fbUserData.id;
				} catch(Exception e){ 
					log.debug "Impossible to add uid to facebook session : " + e.getMessage();
				}
				
				/* Check if user already exists in the DB */
				def existingUser = FacebookUser.findAllByUid(fbUserData.id)[0];
				if(existingUser){
					
					/* connect user */
					
					log.debug "User found, connecting user..."
					customAuthenticationService.authenticate(((User)existingUser.user).username)
					
					/* and update accessToken and accessTokenExpiration date */ 
					existingUser.accessToken = accessToken;
					existingUser.accessTokenExpires = accessTokenExpires;
					
					existingUser.save();
				}
				else { 
					log.debug "Creating New facebook User"
					/* When we create a facebook user, we use the email as username */
					userRegistrationService.createFacebookUser(fbUserData, accessToken, accessTokenExpires)
										
				}
				
			} else { 
				log.debug "No facebook data received"
				return [isLoggedWithFacebook : false]
			}
		} catch(Exception e){ 
			log.warn "Creation of facebook user failed : " + e.getMessage()
			return [isLoggedWithFacebook : false]
		}
		
				
		return [isLoggedWithFacebook : true]; 

	}
	
	/**
	 * Welcome to new Facebook Users
	 */
	def welcomePage() { 
		
	}
	
}
