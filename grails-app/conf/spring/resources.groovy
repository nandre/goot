// Place your Spring DSL code here
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH

beans = {
	customPropertyEditorRegistrar(CustomDateEditorRegistrar)
	
	userDetailsService(com.goot.logging.MyUserDetailsService) { 
		grailsApplication = ref('grailsApplication') 
	}
	
	saltSource(com.goot.logging.MySaltSource) {
		//userPropertyToUse = application.config.grails.plugins.springsecurity.dao.reflectionSaltSourceProperty
		userPropertyToUse = CH.config.grails.plugins.springsecurity.dao.reflectionSaltSourceProperty
    }
		
}
