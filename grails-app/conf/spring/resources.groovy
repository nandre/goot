// Place your Spring DSL code here
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

beans = {
	customPropertyEditorRegistrar(CustomDateEditorRegistrar)
	
	userDetailsService(com.gliiim.logging.MyUserDetailsService) { 
		grailsApplication = ref('grailsApplication') 
	}
	
	saltSource(com.gliiim.logging.MySaltSource) {
		//userPropertyToUse = application.config.grails.plugins.springsecurity.dao.reflectionSaltSourceProperty
		userPropertyToUse = CH.config.grails.plugins.springsecurity.dao.reflectionSaltSourceProperty
    }
		
}
