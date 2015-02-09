package com.goot.data

import com.goot.User;

/**
 * Comment
 * A domain class describes the data object and it's mapping to the database
 */
class Comment {

	int type //from CommentType enum class
	
	String content;
	
	//here no belongsTo a user because we want to keep the comment if user is deleted
	
//	static belongsTo	= []	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
//	static hasOne		= []	// tells GORM to associate another domain object as an owner in a 1-1 mapping
//	static hasMany		= []	// tells GORM to associate other domain objects for a 1-n or n-m mapping
//	static mappedBy		= []	// specifies which property should be used in a mapping 
	
	//Comments can get commented too
	static hasMany = [comments : Comment]
	
    static mapping = {
    }
    
	static constraints = {
		type nullable : false;
		content nullable : false, maxSize: 50000;
		comments : nullable : true;
    }
	
	
	
	/*
	 * Methods of the Domain Class
	 */
//	@Override	// Override toString for a nicer / more descriptive UI 
//	public String toString() {
//		return "${name}";
//	}
}
