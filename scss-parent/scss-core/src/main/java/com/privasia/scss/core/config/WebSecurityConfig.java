/**
 * 
 */
package com.privasia.scss.core.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.privasia.scss.core.security.entrypoint.RestAuthenticationEntryPoint;
import com.privasia.scss.core.security.filter.JwtTokenAuthenticationProcessingFilter;
import com.privasia.scss.core.security.filter.LoginProcessingFilter;
import com.privasia.scss.core.security.jwt.SkipPathRequestMatcher;
import com.privasia.scss.core.security.jwt.extractor.TokenExtractor;
import com.privasia.scss.core.security.provider.JwtAuthenticationProvider;
import com.privasia.scss.core.security.provider.SCSSAuthenticationProvider;
import com.privasia.scss.core.security.util.MD5PasswordEncoder;

/**
 * @author Janaka
 *
 */

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
  public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/public/login";
  public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/**";
  public static final String TOKEN_REFRESH_ENTRY_POINT = "/public/refreshtoken";

  @Autowired
  private RestAuthenticationEntryPoint authenticationEntryPoint;
  @Autowired
  private AuthenticationSuccessHandler successHandler;
  @Autowired
  private AuthenticationFailureHandler failureHandler;
  @Autowired
  private SCSSAuthenticationProvider scssAuthenticationProvider;
  @Autowired
  private JwtAuthenticationProvider jwtAuthenticationProvider;

  @Autowired
  private TokenExtractor tokenExtractor;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private ObjectMapper objectMapper;

  @Bean
  protected LoginProcessingFilter buildLoginProcessingFilter() throws Exception {
    LoginProcessingFilter filter =
        new LoginProcessingFilter(FORM_BASED_LOGIN_ENTRY_POINT, successHandler, failureHandler, objectMapper);
    filter.setAuthenticationManager(this.authenticationManager);
    return filter;
  }

  @Bean
  protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
    List<String> pathsToSkip = Arrays.asList(TOKEN_REFRESH_ENTRY_POINT, FORM_BASED_LOGIN_ENTRY_POINT);
    SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
    JwtTokenAuthenticationProcessingFilter filter =
        new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
    filter.setAuthenticationManager(this.authenticationManager);
    return filter;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(scssAuthenticationProvider);
    auth.authenticationProvider(jwtAuthenticationProvider);
  }

  @Bean
  protected PasswordEncoder passwordEncoder() {
    return new MD5PasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable() // We don't need CSRF for JWT based authentication
        .exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)

        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and().authorizeRequests().antMatchers(FORM_BASED_LOGIN_ENTRY_POINT).permitAll() // Login
                                                                                         // end-point
        .antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll() // Token
                                                            // refresh
                                                            // end-point
        // .antMatchers("/scss/scancard/**").permitAll()
        .antMatchers("/console").permitAll() // H2 Console Dash-board - only for testing
        .and().authorizeRequests().antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated() // Protected
                                                                                             // API
                                                                                             // End-points
        .and().addFilterBefore(buildLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
  }

}
