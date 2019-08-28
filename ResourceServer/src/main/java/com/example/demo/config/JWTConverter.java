package com.example.demo.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import com.example.demo.model.AccessTokenMapper;

@Component
public class JWTConverter extends DefaultAccessTokenConverter implements JwtAccessTokenConverterConfigurer{

	@Override
	public void configure(JwtAccessTokenConverter converter) {
		
		converter.setAccessTokenConverter(this);
	}
	
	@Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication auth = super.extractAuthentication(map);
        AccessTokenMapper mapper= new AccessTokenMapper();
       
        if(map.get("client_id")!=null) 
        mapper.setId((String)map.get("client_id"));
        
        if(map.get("user_name")!=null) 
            mapper.setUserName((String)map.get("user_name"));
        
        if(map.get("provider")!=null) 
            mapper.setId((String)map.get("provider"));
        
        auth.setDetails(mapper);
        return auth;
    }

}
