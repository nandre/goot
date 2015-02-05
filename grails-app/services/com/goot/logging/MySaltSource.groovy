package com.goot.logging

import org.springframework.security.authentication.dao.ReflectionSaltSource
import org.springframework.security.core.userdetails.UserDetails

class MySaltSource extends ReflectionSaltSource {
    Object getSalt(UserDetails user) {
        user[userPropertyToUse]
    }
}