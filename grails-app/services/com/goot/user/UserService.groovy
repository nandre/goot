package com.goot.user

import com.goot.FacebookUser;
import com.goot.User;
/**
 * UserService
 * A service class encapsulates the core business logic of a Grails application
 */
class UserService {

    static transactional = true

	def springSecurityService;
	def sessionRegistry;
	
	/**
	 * Find User by his accessToken 
	 * @param tokenString
	 * @return facebook user or normal user 
	 */
    def findUserByAccessToken(String tokenString) {
		
		def connectedUser = FacebookUser.findByAccessToken(tokenString) as FacebookUser;
		
		def user = connectedUser.user;
		
		return user; 
		
    }
}
