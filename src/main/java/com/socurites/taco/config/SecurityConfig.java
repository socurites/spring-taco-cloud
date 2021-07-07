package com.socurites.taco.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//  private final DataSource dataSource;

  private final UserDetailsService userDetailsService;

//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.inMemoryAuthentication()
//      .withUser("user1")
//      .password("{noop}password1")
//      .authorities("ROLE_USER")
//      .and()
//      .withUser("user2")
//      .password("{noop}password2")
//      .authorities("ROLE_USER");
//  }


//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth
//      .jdbcAuthentication()
//      .dataSource(dataSource);
//  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .antMatchers("/design", "orders")
          .hasRole("USER")
        .antMatchers("/", "/**")
          .permitAll()
      .and()
        .formLogin()
          .loginPage("/login")
        .loginProcessingUrl("/login")
          .usernameParameter("username")
          .passwordParameter("password")
          .defaultSuccessUrl("/", true)
        .failureUrl("/login-fail")
      .and()
        .logout()
          .logoutUrl("/logout")
            .logoutSuccessUrl("/");
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
      .ignoring()
      .antMatchers("/h2-console/**");
  }
}
