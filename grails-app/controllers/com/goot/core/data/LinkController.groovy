package com.goot.core.data

import org.springframework.dao.DataIntegrityViolationException

import com.goot.User
import com.goot.core.GlobalController;
import com.goot.data.Link;

/**
 * LinkController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class LinkController extends GlobalController {

	def springSecurityService;
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
		
		def user = springSecurityService.getCurrentUser() as User;
		log.debug "logged user : " + user?.username;
		
		def links = user.links;
		
        //params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [linkInstanceList: links, linkInstanceTotal: links.size()]
    }

    def create() {
        [linkInstance: new Link(params)]
    }

    def save() {
        def linkInstance = new Link(params)
        if (!linkInstance.save(flush: true)) {
            render(view: "create", model: [linkInstance: linkInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'link.label', default: 'Link'), linkInstance.id])
        redirect(action: "show", id: linkInstance.id)
    }

    def show() {
        def linkInstance = Link.get(params.id)
        if (!linkInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'link.label', default: 'Link'), params.id])
            redirect(action: "list")
            return
        }

        [linkInstance: linkInstance]
    }

    def edit() {
        def linkInstance = Link.get(params.id)
        if (!linkInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'link.label', default: 'Link'), params.id])
            redirect(action: "list")
            return
        }

        [linkInstance: linkInstance]
    }

    def update() {
        def linkInstance = Link.get(params.id)
        if (!linkInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'link.label', default: 'Link'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (linkInstance.version > version) {
                linkInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'link.label', default: 'Link')] as Object[],
                          "Another user has updated this Link while you were editing")
                render(view: "edit", model: [linkInstance: linkInstance])
                return
            }
        }

        linkInstance.properties = params

        if (!linkInstance.save(flush: true)) {
            render(view: "edit", model: [linkInstance: linkInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'link.label', default: 'Link'), linkInstance.id])
        redirect(action: "show", id: linkInstance.id)
    }

    def delete() {
        def linkInstance = Link.get(params.id)
        if (!linkInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'link.label', default: 'Link'), params.id])
            redirect(action: "list")
            return
        }

        try {
            linkInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'link.label', default: 'Link'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'link.label', default: 'Link'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	
	
	
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
			
			def link = Link.findByUrl(tabUrl);
		
			if(!link){
				//link doesn't exist, create it and save user as reporter
				link = new Link();
				link.url = tabUrl;
				link.reporter = user;
				link.save();	
			}
			
			// add to user link favorites
			user.addToLinks(link);
			
			user.save()
			
			log.debug "link added : " + link.url;
			
			render getSuccess();

		}catch(Exception ex){
			log.error "error", ex
			log.debug "error message : " + getError();
			render getError();
		}
	
	}
}
