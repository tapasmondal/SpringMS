package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;


/**
 *  @author tapasmondal
 *  
 *  This is a AuthorizacionServerConfiguration class for configuration of authentication manager
 * 
 */


@Configuration
@EnableAuthorizationServer
public class AuthorizacionServerConfiguration extends AuthorizationServerConfigurerAdapter {
	
	 
	 @Value("${config.oauth2.clientid}")
	 private String clientId;
	
	 @Value("${config.oauth2.clientSecret}")
	 private String clientSecret;
	 
	 @Value("${config.oauth2.privateKey}")
	 private String privateKey;
	 
	 @Value("${config.oauth2.publicKey}")
	 private String publicKey;
	
	 @Autowired
	 @Qualifier ("authenticationManagerBean")
	 private AuthenticationManager authenticationManager;
	   
	 @Autowired
	 private TokenStore tokenStore;
	   
	 @Override
	 public void configure (ClientDetailsServiceConfigurer clients) throws Exception {
	 clients.inMemory ()
	     .withClient (clientId)
	     .secret (passwordEncoder (). encode (clientSecret))
	     .authorizedGrantTypes ("password", "authorization_code", "refresh_token", "implicit")
         .authorities ("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT", "USER")
         .scopes ("read", "write")
         .autoApprove (true)
         .accessTokenValiditySeconds(5000)
         .refreshTokenValiditySeconds(500000);          
	 }
	 
	 @Override
	  public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	    endpoints
	    .tokenStore(tokenStore())
	    .authenticationManager(authenticationManager)
	    .accessTokenConverter(defaultAccessTokenConverter());
	  }
	 
	 
	 /*
	  * Need to Know Why this is required ? impact of it 
	  */
	 @Override
	    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
	        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	    }
	 
	 @Bean
	     public PasswordEncoder passwordEncoder () {
	         return new BCryptPasswordEncoder ();
	     }

	 
	 @Bean
	  public TokenStore tokenStore(){
	    return new JwtTokenStore(defaultAccessTokenConverter());
	  }
	 
	 
	  @Bean
	  public JwtAccessTokenConverter defaultAccessTokenConverter() {
	    JwtAccessTokenConverter converter = new CustomTokenEnhancer();
	    converter.setSigningKey(privateKey);
	    converter.setVerifierKey(publicKey);
	    return converter;
	  }
	  
	  
	  
	  @Bean
	  @Primary
	  public DefaultTokenServices tokenServices() {
	      DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	      defaultTokenServices.setTokenStore(tokenStore());
	      defaultTokenServices.setSupportRefreshToken(true);
	      return defaultTokenServices;
	  }
	  
	  
	/* @Override
	  public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	      endpoints
	              .authenticationManager (authenticationManager)        
	              .tokenStore (tokenStore);
	  }
	   
	 @Bean
	   public TokenStore tokenStore () {
	       return new InMemoryTokenStore ();
	  }
	 
	 */
	 }
