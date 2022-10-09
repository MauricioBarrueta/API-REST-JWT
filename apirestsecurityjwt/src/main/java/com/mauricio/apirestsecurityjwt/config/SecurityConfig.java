package com.mauricio.apirestsecurityjwt.config;

import com.mauricio.apirestsecurityjwt.security.CustomUserDetailsService;
import com.mauricio.apirestsecurityjwt.security.JwtAuthenticationEntryPoint;
import com.mauricio.apirestsecurityjwt.security.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//! @Configuration: Indica que la clase es una configuración en sí misma y tendrá métodos para crear instancias y configurar las dependencias.
@Configuration 

/** @EnableWebSecurity: 
 *! Se agrega a una clase @Configuration para tener la configuración Spring Security definida en cualquier WebSecurityConfigurer 
 *! o más probablemente extendiendo la clase base WebSecurityConfigurerAdapter y reemplazando métodos individuales.*/ 
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //* Se inyecta el servicio que obtiene el usuario y su rol
    @Autowired
    private CustomUserDetailsService userDetailsService;

    //* Se inyecta la clase que maneja los errores de autorización
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    //* Se instancia la clase que valida el token
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    } 

    @Bean
    //! Codificar contraseña
    PasswordEncoder passwordEncoder() {
        //* BCryptPasswordEncoder se basa en el algoritmo BCrypt (función de hashing de passwords)
        return new BCryptPasswordEncoder();
    }

    //* Método de configuración de las autenticaciones al hacer peticiones
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //* Se desactiva 'Cross-site request forgery', ya que Spring ya cuenta con protección
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint) /* Se agrega el manejo de excepciones de la clase */
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) /* STATELESS = sin estado */
            .and()
            .authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").permitAll() /* Permite las petiiones 'GET' para cualquiera */        
            .antMatchers("/api/auth/**").permitAll()          
            .anyRequest().authenticated(); /* Para cualquier otra petición tendrá que ser autenticada */

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);            
    }

    //! @Bean: Funciona con @Configuration que tendrá métodos para crear instancias y configurar dependencias. Dichos métodos serán anotados con @Bean

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //* Se implementa el metodo autogenerado (auth)
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {       
        return super.authenticationManagerBean();
    }

    //* Para crear usuarios desde el código
    /* UserDetailService es el método donde se crean los usuarios y los almacena en la memoria
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails usuario = User.builder().username("mauricio").password(passwordEncoder() // passwordEncoder para codificar la contraseña
                    .encode("password")).roles("USER").build(); // Se le asigna su rol

        UserDetails administrador = User.builder().username("admin").password(passwordEncoder()
                    .encode("admin")).roles("ADMIN").build();
        // InMemoryUserDetailsManager: Se guardan en la memoria para poder usarlos
        return new InMemoryUserDetailsManager(usuario, administrador);
    }*/
}
