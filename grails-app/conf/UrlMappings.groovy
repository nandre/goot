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
		
		// see a link from its token (url format is /share?me=[token])
		"/share" { 
			controller = "link"
			action = "redirectFromToken"
		}
		
		
		// CHROME EXTENSION URLS 
		
		//connect user to chrome extension
		"/plugin/connect"{ 
			controller  =  "pluginLogin"
			action 		=  "ajaxConnect"
		}
		
		//add a link to user history
		"/plugin/link/add"{
			controller  =  "linkWS"
			action 		=  "add"
		}
		
		//add a link to user history
		"/plugin/link/send"{
			controller  =  "linkWS"
			action 		=  "sendToFriend"
		}
		
		
		//add a image comment to link and user
		"/plugin/comment/image"{
			controller  =  "commentWS"
			action 		=  "addImage"
		}
		
		//add a text comment to link and user
		"/plugin/comment/text"{
			controller  =  "commentWS"
			action 		=  "addText"
		}
		
		//add a text comment to link and user
		"/plugin/comment/send"{
			controller  =  "commentWS"
			action 		=  "sendToFriend"
		}
		
		"/plugin/comment/getFromUrlAndFriends"{
			controller = "commentWS"
			action 	   = "getFriendsCommentsFromUrl"
		}
		
		"/plugin/comment/getFromUrl"{
			controller = "commentWS"
			action 	   = "getMyCommentsFromUrl"
		}
		
		
		"/auth/$action" {
			controller  = 'login'
		}
		
		
		
		"/test/data" {
			controller = "test"
			action = "createData"
		}
		
		"/test/query" {
			controller = "test"
			action = "test"
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
