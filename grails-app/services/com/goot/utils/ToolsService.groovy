package com.goot.utils

import java.io.File;
import java.math.BigDecimal;
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.SecureRandom

import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import java.security.MessageDigest;
import java.text.DecimalFormat
import java.util.UUID;
import java.util.regex.Matcher
import java.util.regex.Pattern
import org.slf4j.Logger
import org.slf4j.LoggerFactory;
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

import com.goot.User;

class ToolsService {

    static transactional = true

	/**
	* Dependency injection for the springSecurityService.
	*/
	def springSecurityService
	

	/**
	* Generates a random controlNumber
	* @return
	*/
   static String generateRandomControlNumber() {
	   String generatedNum = "";
	   generatedNum+= (1..10).inject("") { a, b -> a += ('a'..'z')[new Random().nextFloat() * 26 as int] }.capitalize()
	   return generatedNum.toLowerCase();
   }
   
   /**
	* Generates a uuid for user
	* @return uuid
	*/
   
   static String uuidGenerator() {
	   	   
	   UUID userkey = UUID.randomUUID();
	   return userkey.toString()
   }
   
   static String uniqueUsernameGenerator(){
	   def uuid = uuidGenerator()
	   if(!User.findByUsername(uuid)){
		   return uuid
	   }
	   else{ return uniqueUsernameGenerator() }
   }
   
   static String uniqueFakeUsernameGenerator(String email){
	   try {
			int i = 0
			int j = email.length()
			def chars = []
			int k=0;
			['a'..'z','A'..'Z','0'..'9'].each{chars += it}
			for(i; i< email.length();i++){
				if (i%2 == 0) {
				    k = j
				} else {
				   k = chars.size() - j 
				}
				email = replaceCharAt(email, i,(char)((List)chars).get(k))
				j--;
			}
			def sbStr2 = this.stringToHexa(email);
			def sbStr1 = email.toLowerCase();
			def sbStr = new String();
			for(int l = 0; l< Math.min(sbStr2.length(),sbStr1.length());l++){
				if(l%2 == 0){
					sbStr += sbStr1.charAt(l);
				}else {
					sbStr += sbStr2.charAt(l);
				}
			}
			while(sbStr.length() < 35){
				sbStr = sbStr.concat(sbStr);
			}
			
			sbStr = this.replaceCharAt(sbStr,  8 , (char)'-');
			sbStr = this.replaceCharAt(sbStr, 13 , (char)'-');
			sbStr = this.replaceCharAt(sbStr, 18 , (char)'-');
			sbStr = this.replaceCharAt(sbStr, 23 , (char)'-');


			return sbStr.substring(0,34);
	   }
	   catch(Exception e){
	   		e.printStackTrace()
	   		return uuidGenerator();
	   }
		
   }
   
   public static String replaceCharAt(String s, int pos, char c){
	   return s.substring(0,pos) + c + s.substring(pos+1);
   }
   

   public static String stringToHexa(String texte) {
	   int c;//int's equivalent to char
	   
	   StringBuffer buff = new StringBuffer(texte.length());
	   for (int i = 0; i < texte.length(); i++) {
		   c=texte.charAt(i);
		   buff.append(Integer.toHexString(c))
	   }
	   return buff.toString();
   }

   
   static String keyGenerator() {
	   try {

      SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

      //generate a random number
      String randomNum = new Integer( prng.nextInt() ).toString();

      MessageDigest sha = MessageDigest.getInstance("SHA-1");
      byte[] result =  sha.digest( randomNum.getBytes() );

      return hexEncode(result);
    }
    catch (Exception ex ) {
	  ex.printStackTrace()
      return;
    }
   }
   
   
   static private String hexEncode( byte[] aInput){
	   StringBuilder result = new StringBuilder();
	   char[] digits = ['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f']
	   for (int idx = 0; idx < aInput.length; ++idx) {
		 byte b = aInput[idx];
		 result.append( digits[ (b&0xf0) >> 4 ] );
		 result.append( digits[ b&0x0f] );
	   }
	   return result.toString();
   }
   
   
   def hashPIN(PIN) {
	   
		  MessageDigest md = MessageDigest.getInstance("SHA-256");
		  md.update(PIN.getBytes());
   
		  byte[] byteData = md.digest();
   
		  //convert the byte to hex format
		  StringBuffer sb = new StringBuffer();
		  for (int i = 0; i < byteData.length; i++) {
		   sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		  }
   
		  return sb.toString()
   }
   

   
   
  public static boolean isAnEmail(String email){
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);

		boolean isMail = m.matches();
		StringTokenizer st = new StringTokenizer(email, ".");
		String lastToken = null;
		while (st.hasMoreTokens()) {
		lastToken = st.nextToken();
		}
		if (isMail && lastToken.length() >= 2
		&& email.length() - 1 != lastToken.length()) {
		return true;
		}
		else return false;
		}
 
	   
	  
	  public Double formatAmount(BigDecimal amount){
		  DecimalFormat decimalFormat = new DecimalFormat("0.00");
		  return Double.parseDouble((decimalFormat.format(amount)).replace(',','.'));
	  }
}