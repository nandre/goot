package com.goot.core.services

import com.goot.User;
import com.goot.data.Comment
import com.goot.data.Link

/**
 * CommentService
 * A service class encapsulates the core business logic of a Grails application
 */
class CommentService {

    static transactional = true

	/**
	 * Add comment to given url
	 * Please note that adding a comment to a link automatically adds the link to your favorites
	 * @param user
	 * @param url
	 * @param content
	 * @param type
	 */
	public void addComment(User user, String url, String content, Integer type, long parentCommentId){
		
		def comment = new Comment(type : type, content : content);
		comment.save();
		
		user.addToComments(comment);
		
		def link = Link.findByUrl(url);
		if(link){
			link.addToComments(comment);
			link.save();
			
			if(!user.favorites.contains(link)){
				user.addToFavorites(link)
			}
		} else {
			link = new Link(url : url, reporter : user);
			link.addToComments(comment);
			link.save();
			
			user.addToFavorites(link)
		}
		
		user.save();
		
		//save the parent comment to keep the comment into its thread 
		if(parentCommentId != null && parentCommentId != 0l){
			def parentComment;
			parentComment = Comment.get(parentCommentId);
			parentComment.addToComments(comment);
			parentComment.save();
		}
	}
	
	
}
