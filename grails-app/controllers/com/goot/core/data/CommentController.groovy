package com.goot.core.data

import org.springframework.dao.DataIntegrityViolationException

import com.goot.User;
import com.goot.core.GlobalController
import com.goot.data.Comment;
import com.goot.data.Link;

import com.goot.utils.CommentType;

/**
 * CommentController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class CommentController extends GlobalController {

	def springSecurityService;
	def commentService;
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [commentInstanceList: Comment.list(params), commentInstanceTotal: Comment.count()]
    }
	
	//Not used for the moment
	def listFromLink() {
		def link = Link.get(params.linkId);
		
		def comments = link.comments as Set<Comment>;
		def commentList = new ArrayList<Comment>();
		
		commentList.addAll(comments);

		render(view: "list", model: [commentInstanceList: commentList, commentInstanceTotal: commentList.size()])
	}

    def create() {
        [commentInstance: new Comment(params)]
    }

    def save() {
        def commentInstance = new Comment(params)
        if (!commentInstance.save(flush: true)) {
            render(view: "create", model: [commentInstance: commentInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'comment.label', default: 'Comment'), commentInstance.id])
        redirect(action: "show", id: commentInstance.id)
    }

    def show() {
        def commentInstance = Comment.get(params.id)
        if (!commentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
            redirect(action: "list")
            return
        }

        [commentInstance: commentInstance]
    }

    def edit() {
        def commentInstance = Comment.get(params.id)
        if (!commentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
            redirect(action: "list")
            return
        }

        [commentInstance: commentInstance]
    }

    def update() {
        def commentInstance = Comment.get(params.id)
        if (!commentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (commentInstance.version > version) {
                commentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'comment.label', default: 'Comment')] as Object[],
                          "Another user has updated this Comment while you were editing")
                render(view: "edit", model: [commentInstance: commentInstance])
                return
            }
        }

        commentInstance.properties = params

        if (!commentInstance.save(flush: true)) {
            render(view: "edit", model: [commentInstance: commentInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'comment.label', default: 'Comment'), commentInstance.id])
        redirect(action: "show", id: commentInstance.id)
    }

    def delete() {
        def commentInstance = Comment.get(params.id)
        if (!commentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
            redirect(action: "list")
            return
        }

        try {
            commentInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'comment.label', default: 'Comment'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	
	/**
	 * Add a comment as an image to the given url
	 * Can also pass a comment id which will be the parent of the current comment (as a thread)
	 * @return
	 */
	def addImage(){
		
		try {
			def user = springSecurityService.getCurrentUser() as User;
			
			int type = CommentType.IMAGE.getCode(); //from CommentType enum class
		
			String content = request.JSON.image;
			String url = request.JSON.tabUrl;
			String sParentCommentId = request.JSON.parentCommentId;
			long parentCommentId = Long.parseLong(!(sParentCommentId in [null, "null", "undefined"]) ? sParentCommentId : "0");
			
			log.debug "url : " + url;
			
			commentService.addComment(user, url, content, type, parentCommentId);
			
			render getSuccess();
			
		}catch(Exception ex){
			log.error 'error', ex
			render getError();
		}
	}
	
	
	
	
	
	
	/**
	 * Add a text comment to given url 
	 * Can also pass a comment id which will be the parent of the current comment (as a thread)
	 * @return
	 */
	def addText(){
		
		try {
			def user = springSecurityService.getCurrentUser() as User;
			
			int type = CommentType.TEXT.getCode(); //from CommentType enum class
		
			String content = request.JSON.text;
			String url = request.JSON.tabUrl;
			String sParentCommentId = request.JSON.parentCommentId;
			long parentCommentId = Long.parseLong(!(sParentCommentId in [null, "null", "undefined"]) ? sParentCommentId : "0");

			log.debug "url : " + url;
			
			commentService.addComment(user, url, content, type, parentCommentId);
			
			render getSuccess();
			
		}catch(Exception ex){
			log.error 'error', ex
			render getError();
		}
	}
	
}
