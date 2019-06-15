package it.lucius.customers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import it.lucius.customers.security.AuthenticationFailureHandler;
import it.lucius.customers.security.AuthenticationSuccessHandler; 
import it.lucius.customers.security.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableRedisHttpSession
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Bean
//	public FilterRegistrationBean instagramFilterRegistration() {
//
//	    FilterRegistrationBean registration = new FilterRegistrationBean();
//	    registration.setFilter(instagramFilter());
//	    registration.addUrlPatterns("/pr/inst/*");
//	    //registration.addInitParameter("paramName", "paramValue");
//	    registration.setName("instagramFilter");
//	    registration.setOrder(1);
//	    return registration;
//	} 
//
//	public Filter instagramFilter() {
//	    return new InstagramFilter();
//	}
	
//	@Bean
//	public LettuceConnectionFactory connectionFactory() {
//		return new LettuceConnectionFactory(); 
//	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public TokenAuthenticationFilter jwtAuthenticationTokenFilter() throws Exception {
		return new TokenAuthenticationFilter();
	}

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private UserDetailsService userDetails;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				// .exceptionHandling().authenticationEntryPoint( restAuthenticationEntryPoint
				// ).and()
				// .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS
				// ).and()
				.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				// .antMatchers("/product/image/**").permitAll().antMatchers(HttpMethod.GET,
				// "/product/**").permitAll()
				// .antMatchers(HttpMethod.GET,
				// "/group/**").permitAll().antMatchers("/cart/**").permitAll()
				// .antMatchers("/v2/**").permitAll().antMatchers("/swagger-ui.html").permitAll()
				// .antMatchers("/webjars/**").permitAll().antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/pb/**").permitAll()
				.antMatchers("/img").permitAll()
				.antMatchers("/callBackInstagram").permitAll()
				.antMatchers("/**").permitAll()
				.anyRequest().authenticated()
				.antMatchers("/user/**").hasAnyRole("user", "admin")
				.antMatchers("/admin/**").hasRole("admin")
				.antMatchers("/pr/**").hasAnyRole("user", "admin")
				// .anyRequest().hasRole("admin") << Works with ROLE entities while we have
				// SimpleGrantedAuthority...
				// .anyRequest().hasAuthority("user")

				// .httpBasic().disable();
				//.and().successHandler(authenticationSuccessHandler)
				//.failureHandler(authenticationFailureHandler)

				// From https://github.com/bfwg/springboot-jwt-starter
				.and().csrf().disable();
	}
}