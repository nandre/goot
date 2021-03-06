// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.gorm.failOnError = true
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

errorMessage = "error";
successMessage = "success";

environments {
    development {
        grails.logging.jul.usebridge = true
		grails.serverURL = "http://localhost:8080/Gliiim"
    }
    production {
        grails.logging.jul.usebridge = true
        grails.serverURL = "http://gliiim.outsidethecircle.eu"
    }
}

// log4j configuration
log4j = {
//	appenders {
//		environments {
//			development {
//				console name: 'stdout', layout: pattern(conversionPattern: '%-5p %d{HH:mm:ss,SSS} %c{2} %m%n')
//			}
//	
//			production {
//				appender new org.apache.log4j.DailyRollingFileAppender(
//						name: 'file',
//						datePattern: "'.'yyyy-MM-dd",
//						file: System.properties['catalina.base'] + "/logs/mylog-web.log",
//						layout: pattern(conversionPattern: '%d [%t] %-5p %c{2} %x - %m%n')
//				)
//				rollingFile name: "stacktrace", maxFileSize: 1024 * 10,
//						file: System.properties['catalina.base'] + "/logs/mylog-web-stacktrace.log"
//			}
//		}
//	}
	
	error 'org.codehaus.groovy.grails.web.servlet', // controllers
		  'org.codehaus.groovy.grails.web.pages', // GSP
		  'org.codehaus.groovy.grails.web.sitemesh', // layouts
		  'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
		  'org.codehaus.groovy.grails.web.mapping', // URL mapping
		  'org.codehaus.groovy.grails.commons', // core / classloading
		  'org.codehaus.groovy.grails.plugins', // plugins
		  'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
		  'org.springframework',
		  'org.hibernate',
		  'net.sf.ehcache.hibernate'
			
	info "grails.app"
	
	debug "grails.app.controllers"
	debug "grails.app.services"
	
	error "grails.app"
}



// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.gliiim.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.gliiim.UserRole'
grails.plugins.springsecurity.authority.className = 'com.gliiim.Role'

grails.plugins.springsecurity.dao.reflectionSaltSourceProperty = 'salt'


facebook.applicationSecret='3a9d58f3d9de363206f5c41afe72024e'
facebook.applicationId='430736073677176'

//grails.plugins.springsecurity.facebook.domain.classname='com.gliiim.FacebookUser'
//grails.plugins.springsecurity.facebook.appId='430736073677176'
//grails.plugins.springsecurity.facebook.secret='3a9d58f3d9de363206f5c41afe72024e'

grails.config.defaults.locations = [KickstartResources]