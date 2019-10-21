package com.springBoot.utility;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
@Service
public class TokenGeneration {
	  public  final String TOKEN_SECRET = "sd5745FAHFW";
	
	  //token generation
	public  String createToken(Long id)  
	{
        try {
        		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
     
        		String token = JWT.create()
        						.withClaim("userId", id)
        						.sign(algorithm);
        		return token;
        	} 
        catch (JWTCreationException exception) 
        {
        		exception.printStackTrace();
         
        } 
        catch (IllegalArgumentException e)
        {
				e.printStackTrace();
		} 
        catch (UnsupportedEncodingException e) 
        {
				e.printStackTrace();
		}
        return null;
	  }
	 
	 //token decode
	public Long decodeToken(String token)
	  {
		  		Long userid;
	          
	            Verification verification = null;
				try 
				{
					verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
				} 
				catch (IllegalArgumentException | UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
				
	            JWTVerifier jwtverifier=verification.build();
	           
	            DecodedJWT decodedjwt=jwtverifier.verify(token);
	
	            Claim claim=decodedjwt.getClaim("userId");
	            userid=claim.asLong();    
	            return userid;
      
	  }
}
    
