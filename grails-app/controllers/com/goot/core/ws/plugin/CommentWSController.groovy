package com.goot.core.ws.plugin

import com.goot.LinkSuggestion
import com.goot.User;
import com.goot.core.GlobalController
import com.goot.utils.CommentType;
import com.goot.data.Comment;
import com.goot.data.Link
import grails.converters.JSON

/**
 * CommentWSController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class CommentWSController extends GlobalController {

	static scaffold = true

	def springSecurityService;
	def commentService;
	
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
	
	
	
	/**
	 * Send a comment to a friend
	 * @return
	 */
	def sendToFriend(){
		
		try {
			def user = springSecurityService.getCurrentUser() as User;
			
			def receiverMail = request.JSON.receiverMail
			def commentId = request.JSON.commentId
			
			// get link or add it to favorites if it's not already in
			def comment = Comment.get(commentId);
			
			def receiver = User.findByEmailOrUsername(receiverMail, receiverMail);			
			
			def suggestion = new LinkSuggestion(sender : user,
								receiver : receiver,
								link : Link.findByComment(comment.identity).list().get(0),
								comment : comment)
	
			suggestion.save();
			
			return getSuccess();
			
		} catch(Exception e){
			log.error "error", e
			
			return getError();
		}
	}
	
	
	/**
	 * Get comments from url for the connected user (and so comments from his friends)
	 * @return
	 */
	def getFriendsCommentsFromUrl(){
		
		try {
		
			def user = springSecurityService.getCurrentUser() as User;
			
			def link = Link.findByUrl(request.JSON.tabUrl);
			
			def friends = user.friends as ArrayList<User>; 
			
			def linkCommentIds = (link.comments)*.id as Collection;
			def userCommentIds;
					
			def comments = new ArrayList<Comment>();
			for(f in friends){
				try {
					userCommentIds = (f.comments)*.id as Collection;
					comments.add(Comment.executeQuery(
								"SELECT c FROM Comment c " +
								"WHERE c.id in (:linkCommentIds) " +
								"AND c.id in (:userCommentIds) " +
								"ORDER BY c.creationDate",
								[linkCommentIds: linkCommentIds, userCommentIds: userCommentIds]));
				}catch(e){}
			}
			
			log.info "return from getMyCommentsFromUrl : " + ([status : getSuccess(), content : [comments : comments]] as JSON)
			
			render ([status : getSuccess(), content : [comments : comments]] as JSON);
			return;
		}catch(Exception e){
			log.error "error", e
			render ([status : getError()] as JSON);
			return;
		
		}
		
	}
	
	
	
	def getMyCommentsFromUrl(){
		
		try {
		
			def user = springSecurityService.getCurrentUser() as User;
			
			def link = Link.findByUrl(request.JSON.tabUrl);
						
			def linkCommentIds = (link.comments)*.id as Collection;
			def userCommentIds = (user.comments)*.id as Collection;

					
			def comments = new ArrayList<Comment>();
				try {
					comments.add(Comment.executeQuery(
								"SELECT c FROM Comment c " +
								"WHERE c.id in (:linkCommentIds) " +
								"AND c.id in (:userCommentIds) " +
								"ORDER BY c.creationDate",
								[linkCommentIds: linkCommentIds, userCommentIds: userCommentIds]));
				}catch(e){}
			
			log.info "return from getMyCommentsFromUrl : " + ([status : getSuccess(), content : [comments : comments]] as JSON)
				
			render ([status : getSuccess(), content : [comments : comments]] as JSON);
			return;
		}catch(Exception e){
			
			log.error "error", e
		
			render ([status : getError()] as JSON);
			return;
		
		}
		
	}
	

	
}
