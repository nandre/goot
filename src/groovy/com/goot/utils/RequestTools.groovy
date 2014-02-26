package com.goot.utils

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import groovyx.net.http.ContentType
import groovyx.net.http.EncoderRegistry
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject

class RequestTools {

	/**
	* Makes an HTTP request.
	*
	* @param urlAsString the URL to make the request to
	* @param params the parameters to use for the POST body
	* @return String the response text
	*/
   private static def makeRequest(urlAsString, params) {
	   def resp
	   def encodedParams = ""
	   URL url
	   Writer writer
	   URLConnection connection
	   
	   // Encoding the params...
	   if (params) {
		   params.each{k,v ->
			   if(k != 'method' && k != 'id') {
				   encodedParams += k.encodeAsURL() + '=' + v.encodeAsURL() + '&'
			   }
		   }
		   if(encodedParams.size() > 2)
		   		encodedParams = encodedParams[0..-2]
	   }
	   
	   try {
		   // Making the request
		   switch(params.method) {
			   case "GET":
				   if(encodedParams) urlAsString += '?' + encodedParams
				   url = new URL(urlAsString)
				   resp = url.text
			   break;
			   case "POST":
				   url = new URL(urlAsString)
				   connection = url.openConnection()
				   connection.setRequestMethod("POST")
				   connection.doOutput = true
				   
				   writer = new OutputStreamWriter(connection.outputStream)
				   writer.write(encodedParams)
				   writer.flush()
				   writer.close()
				   connection.connect()
				   
				   if (connection.responseCode == 200 || connection.responseCode == 201)
					   resp = connection.content.text
   
			   break;
		   }
	   } catch(Exception e) {
		   // resp will be null, nothing to do...
	   }

	   return resp
   }
   
   
   private static JSONObject postRequestAndGetJSON(urlAsString, params){
	   def ret;
	   
	   // REQUEST
	   try {
		   def jsonPostContent = params as JSON
		   def http = new HTTPBuilder(urlAsString)
		   http.setHeaders()
		   http.encoderRegistry = new EncoderRegistry(charset: 'utf-8')
		   http.request(groovyx.net.http.Method.POST) { req ->
			   req.getParams().setParameter("http.connection.timeout", new Integer(5000));
			   req.getParams().setParameter("http.socket.timeout", new Integer(5000));

			   uri.path = url
			   requestContentType = ContentType.JSON
			   body = jsonPostContent.toString()

			   response.success = {resp, json ->
				   ret = json
			   }

			   response.failure = {resp, json ->
				   ret = json
			   }
		   }
	   }
	   catch (Exception e) {
		   System.out.println "\n\n"
		   System.out.println e.getClass().toString()
		   System.out.println "    message: "		+ e.getMessage()
		   System.out.println "    url: "			+ urlAsString
		   System.out.println "    params : "	+ params
		   System.out.println "\n\n"
		   
		   throw e
	   }
	   // End of request



	   // CONVERSION
	   String strRet = ret.toString()
	   JSONObject jsonRet = JSON.parse(strRet)
	   
	   return jsonRet
	   
   }

}
