package com.goot

/**
 * FacebookUser
 */
class FacebookUser {

  long uid
  String accessToken
  Date accessTokenExpires
  
  static belongsTo = [user: User] //connected to main Spring Security domain

  static constraints = {
    uid unique: true
	accessToken nullable : true
	accessTokenExpires nullable : true
  }
  
}
