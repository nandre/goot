var successURL = 'http://goot.cloudfoundry.com/facebookUser/syncFacebookUser';

window.onload = function(){
				var currentUrl = window.location.href;
				console.log('location : ' + window.location);
				
                if (currentUrl.length > successURL.length) {
                    var params = currentUrl.split('?')[1];
                    
                    console.log(params);
					//post message to savetoken.js
					window.postMessage({ type: "FROM_PAGE", accessToken: params}, "*");	
                }
	

	
};