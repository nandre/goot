var successURL = 'http://goot.outsidethecircle.eu/facebookUser/syncFacebookUser';

function postToChromeExtension(JSESSIONID) {
	var currentUrl = window.location.href;
	console.log('location : ' + window.location);

	if (currentUrl.length > successURL.length) {
		var params = currentUrl.split('?')[1];

		console.log(params);
		//post message to savetoken.js
		window.postMessage({
			type : "FROM_PAGE",
			accessToken : params, 
			JSESSIONID : JSESSIONID
		}, "*");
	}
}
