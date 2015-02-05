package com.goot.logging


import org.codehaus.groovy.grails.plugins.springsecurity.GormUserDetailsService
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class MyUserDetailsService extends GormUserDetailsService {

    protected UserDetails createUserDetails(user, Collection authorities) {
        new MyUserDetails((GrailsUser) super.createUserDetails(user, authorities),
            user.salt
        )
    }
}
