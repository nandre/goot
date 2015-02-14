import com.gliiim.Role

class BootStrap {

    def init = { servletContext ->
		
		/* Create some default roles if they don't exist */
		
		def roleAdmin = null
		if(!(roleAdmin = Role.findByAuthority("ROLE_ADMIN"))){
			roleAdmin = new Role(authority: 'ROLE_ADMIN').save()
		}

		def roleUser = null
		if(!(roleUser = Role.findByAuthority("ROLE_USER"))){
			roleUser = new Role(authority: 'ROLE_USER').save()
		}
		
    }
    def destroy = {
    }
}
