package com.goot.test

import com.goot.User
import com.goot.core.GlobalController
import com.goot.data.Comment
import com.goot.data.Link
import com.goot.utils.CommentType
import grails.converters.JSON

/**
 * TestController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class TestController extends GlobalController {

	def createData(){
		def user = new User(username : params.email, 
							email: params.email,
							firstName: "nico",
							lastName: "nico", 
							password: "naenae00",
							enabled: true);
						
		user.save();
		
		def link = new Link(url : params.tabUrl, 
						reporter : user);
					
		link.save();
		
		def comment = new Comment(type: CommentType.TEXT.getCode(), 
									content: "text comment");
								
		comment.save();
		
		user.addToFavorites(link);
		user.addToComments(comment);
		user.save();
		
		link.addToComments(comment);
		link.save();
		
		render "ok"
	}
	
	
	def test(){
		
		try {
		
			def user = User.findByEmail(params.email);
			def link = Link.findByUrl(params.tabUrl);
			
			def friends = user.friends as ArrayList<User>; 
			friends.add(user);
			
			def linkCommentIds = (link.comments)*.id as Collection;
			def userCommentIds;
					
			def comments = new ArrayList<Comment>();
			for(f in friends){
				try {
					userCommentIds = (user.comments)*.id as Collection;
					comments.add(Comment.executeQuery(
								"SELECT c FROM Comment c, User u " +
								"WHERE c.id in (:linkCommentIds) " +
								"AND c.id in (:userCommentIds) " +
								"ORDER BY c.creationDate",
								[linkCommentIds: linkCommentIds, userCommentIds: userCommentIds]));
				}catch(e){}
			}
			
			render ([status : getSuccess(), content : [comments : comments]] as JSON);
			return;
		}catch(Exception e){
			log.error "error", e
			render ([status : getError()] as JSON);
			return;
			
		
		}
		
	}
}
