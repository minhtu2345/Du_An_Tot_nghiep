package com.example.demo.config;

import com.example.demo.entity.KhachHang;
import com.example.demo.repository.KhachHangDao;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter  {

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().cors().disable();
        http.authorizeRequests()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/admin/login").permitAll()
                .antMatchers("/cart/view").hasRole("USER")
                .antMatchers("/checkout").hasRole("USER")
                .antMatchers("/cart/add").hasRole("USER");
        http.formLogin()
                .loginPage("/login") // Trang đăng nhập mặc định cho khách hàng
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/trangchu", false)
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();
        http.logout()
                .logoutUrl("/logout")
                .permitAll();
        http.oauth2Login().loginPage("/login")
                .defaultSuccessUrl("/oauth2/login/success",true)
                .failureUrl("/login?error=true")
                .authorizationEndpoint()
//
                .baseUri("/oauth2/authorization");
    }

}
