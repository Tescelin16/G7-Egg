package turismoreceptivo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import turismoreceptivo.web.service.AgenciaService;
import turismoreceptivo.web.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private AgenciaService agenciaService;
    
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Autowired 
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth
                    .userDetailsService(usuarioService)
                    .passwordEncoder(encoder)
                .and()
                    .userDetailsService(agenciaService)
                    .passwordEncoder(encoder);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        
        http
                .authorizeRequests()
                   .antMatchers("/static/**").permitAll()
                   .antMatchers("/**").permitAll()
                .and()
                    .formLogin()
                        .loginPage("/login-usuario")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                .and()
                    .formLogin()
                        .loginPage("/login-agencia")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                .and()
                    .logout()
                        .logoutUrl("/salir")
                        .logoutSuccessUrl("/index")
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                .and()
                    .exceptionHandling().accessDeniedPage("/error-403")
                .and()
                    .csrf().disable();
    }
}
