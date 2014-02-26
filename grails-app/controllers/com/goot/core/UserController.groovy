package com.goot.core

import org.springframework.dao.DataIntegrityViolationException

import com.goot.User;

/**
 * UserController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class UserController {
	
	def userRegistrationService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def create() {
        [userInstance: new User(params)]
    }

    def save() {
        def userInstance = new User(params)
        if (!userInstance.save(flush: true)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def show() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def edit() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def update() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'user.label', default: 'User')] as Object[],
                          "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
        }

        userInstance.properties = params

        if (!userInstance.save(flush: true)) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def delete() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
            return
        }

        try {
            userInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	
	
	
	def createUser = { NewAccountRegisterUserInstance userInst ->
		
		def creationOK = false;
		
		if(!request.post){ 
			return;
		}
		
		if(userInst.hasErrors()){ 
			return [userInst: userInst]
		} else { 
			creationOK = userRegistrationService.createUser(userInst.email, 
				userInst.firstName, userInst.lastName,
				userInst.password, userInst.secretQuestion, 
				userInst.secretAnswer)
		}
		
		if(creationOK){ 
			log.debug "User creation done : " + userInst.email;
			return []	
		} else { 
		
		}
		
		
		
	}
	
	
	
	
	
	class NewAccountRegisterUserInstance {
		
			String password = ""
			String password2 = ""
			String firstName =""
			String lastName =""
			String email = ""
			
			String secretQuestion
			String secretAnswer
			
			
			static constraints = {
				password blank : false, nullable : false
				password2 blank : false, nullable : false
				firstName blank : false, nullable : false
				lastName blank : false, nullable : false
				email blank: false, nullable: false, email : true, validator: { String email ->
					if (email && User.countByEmail(email)) {
						return 'email.error.unique'
					}
				}
				password blank: false, minSize: 8, maxSize: 64, validator: { password, userInst ->
	
					if (password && password.length() >= 8 && password.length() <= 64 &&
							(!password.matches('^.*\\p{Alpha}.*$') ||
							!password.matches('^.*\\p{Digit}.*$')
							//||!password.matches('^.*[!@#$%^&].*$')
							)) {
						return 'password.error.strength'
					}
				}
				password2 validator: { password2, userInst ->
					if (userInst.password != password2) {
						return 'passsord.error.mismatch'
					}
				}
				secretQuestion blank: false, nullable: false
				secretAnswer blank : false, nullable : false
			}
		}
	
	
	
	
}
