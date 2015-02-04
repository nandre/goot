class UrlMappings {

	static mappings = {
		
		/* 
		 * Pages without controller 
		 */
//		"/"				(view:"/index")
		"/about"		(view:"/siteinfo/about")
		"/blog"			(view:"/siteinfo/blog")
		"/systeminfo"	(view:"/siteinfo/systeminfo")
		"/contact"		(view:"/siteinfo/contact")
		"/terms"		(view:"/siteinfo/terms")
		"/imprint"		(view:"/siteinfo/imprint")
		"/nextSteps"	(view:"/home/nextSteps")
		
		"/facebookUser/syncFacebookUser.gsp"	(view :"/facebookUser/syncFacebookUser")
		
		/* 
		 * Pages with controller
		 * WARN: No domain/controller should be named "api" or "mobile" or "web"!
		 */
        "/"	{
			controller	= 'home'
			action		= { 'index' }
            view		= { 'index' }
        }
		
		
		// CHROME EXTENSION URLS 
		
		//connect user to chrome extension
		"/plugin/connect"{ 
			controller  =  "pluginLogin"
			action 		=  "connect"
			view 		=  "autoClose"
		}
		
		//add a link to user history
		"/plugin/link/add"{
			controller  =  "link"
			action 		=  "add"
		}
		
		//add a image comment to link and user
		"/plugin/comment/image"{
			controller  =  "comment"
			action 		=  "addImage"
		}
		
		//add a text comment to link and user
		"/plugin/comment/text"{
			controller  =  "comment"
			action 		=  "addText"
		}
		
		
		"/auth/$action" {
			controller  = 'login'
		}
		"/$controller/$action?/$id?"{
			constraints {
				controller(matches:/^((?!(api|mobile|web)).*)$/)
		  	}
		}
		
		/* 
		 * System Pages without controller 
		 */
		"403"	(view:'/_errors/403')
		"404"	(view:'/_errors/404')
		"500"	(view:'/_errors/error')
		"503"	(view:'/_errors/503')
	}
}
