package com.example.demo.auth;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class CustomTokenEnhancer extends JwtAccessTokenConverter{

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		//we add out customer information in JWT
		Map<String,Object> info= new LinkedHashMap<>();
		info.put("provider","Tapas");
		
		DefaultOAuth2AccessToken customAccessToken= new DefaultOAuth2AccessToken(accessToken);
		customAccessToken.setAdditionalInformation(info);
		
		return super.enhance(customAccessToken, authentication);
	}
	
	

}
