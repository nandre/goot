package com.goot.data

import com.goot.User;
import java.security.SecureRandom

/**
 * Link
 * A domain class describes the data object and it's mapping to the database
 */
class Link {

	String url;
	
	// first person who reported the link;
	User reporter;
	
	// used to send a goot link referencing a link
	String token;
	
	
	static hasMany = [comments : Comment]
	
	/* no belongsTo relation cause we want to keep the links even if one of the owners is deleted */ 
			
    static mapping = {
    }
    
	static constraints = {
		reporter nullable : false
		comments nullable : true
		token nullable : true
    }
	
	
	def beforeInsert() {
		if (!token) {
		   token = generateLinkToken();
		}
	 }
	
	
	def generateLinkToken(){
		SecureRandom random = new SecureRandom();
		def token = new BigInteger(130, random).toString(8);
		
		if(Link.findByToken(token)){
			token = generateLinkToken();
		}
		
		return token;
	}
	
	static namedQueries = {
		findByComment {
			 commentId ->
			   comments {
				   eq 'id', commentId
		   }
		 }
	   }
		
	
	/*
	 * Methods of the Domain Class
	 */
//	@Override	// Override toString for a nicer / more descriptive UI 
//	public String toString() {
//		return "${name}";
//	}
}
