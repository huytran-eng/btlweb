package D20PTIT.hoidap.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    // để mã hóa password
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> // cho phép người dùng được sử dụng các chức năng tùy theo vị trí
                        authorize.requestMatchers("/tag/add").hasRole("admin")
                                .requestMatchers("/user").hasAuthority("ADMIN")
                                .requestMatchers("/question/add").hasAnyRole("admin", "member")
                                .requestMatchers("/tag/add").hasRole("admin")
                                .requestMatchers("/answers/**").hasAnyRole("admin", "member")
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("/tag/**").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/public/**").permitAll()
                                .requestMatchers("/error/**").permitAll()
                                .requestMatchers("/webjars/**").permitAll()
                                .requestMatchers("/test").permitAll()
                                .requestMatchers("/question/**").permitAll()
                                .requestMatchers("/image/**").permitAll()
                                .requestMatchers("/users").hasAuthority("ADMIN")
                                .requestMatchers("/user/**").permitAll()
                                .requestMatchers("/update/**").permitAll()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


}



