package com.goot.data

import com.goot.User;

/**
 * Link
 * A domain class describes the data object and it's mapping to the database
 */
class Link {

	String url;

	/* no belongsTo relation cause we want to keep the links even if one of the owners is deleted */ 
			
    static mapping = {
    }
    
	static constraints = {
    }
	
	/*
	 * Methods of the Domain Class
	 */
//	@Override	// Override toString for a nicer / more descriptive UI 
//	public String toString() {
//		return "${name}";
//	}
}
