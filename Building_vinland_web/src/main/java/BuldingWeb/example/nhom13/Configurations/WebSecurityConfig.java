package BuldingWeb.example.nhom13.Configurations;

import BuldingWeb.example.nhom13.Filters.JwtTokenFliter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtTokenFliter jwtTokenFliter;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(jwtTokenFliter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .requestMatchers("/upload/**").permitAll()
                            .requestMatchers(

                                    String.format("/%s/dangnhap", apiPrefix),
                                    String.format("/%s/dangKy", apiPrefix),
                                    String.format("/%s/search", apiPrefix)

                            )
                            .permitAll()

                            .requestMatchers(HttpMethod.GET, String.format("/%s/admin/tintuc/duyettin/tintucs", apiPrefix))
                            .hasAnyRole("ADMIN", "NHANVIEN")


                            .requestMatchers(HttpMethod.GET, String.format("/%s/admin/tintuc/tintucs", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("/%s/admin/tintuc/**", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("/%s/admin/bds", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("/%s/admin/bds/chitiet/**", apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.GET, String.format("/%s/moigioi", apiPrefix)).permitAll()
                            .requestMatchers(HttpMethod.GET, String.format("/%s/moigioi/*", apiPrefix)).permitAll()

                            .requestMatchers(HttpMethod.POST, String.format("/%s/moigioi", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, String.format("/%s/moigioi/*", apiPrefix)).hasRole("ADMIN")

                            .requestMatchers(HttpMethod.PUT, String.format("/%s/admin/bds/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, String.format("/%s/admin/bds/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, String.format("/%s/admin/bds/**", apiPrefix)).hasRole("ADMIN")

                            .requestMatchers(HttpMethod.GET,String.format("/%s/admin/duyettin/**", apiPrefix)).hasAnyRole("ADMIN","NHANVIEN")
                            .requestMatchers(HttpMethod.PUT,String.format("/%s/admin/duyettin/**", apiPrefix)).hasAnyRole("ADMIN","NHANVIEN")

                            .requestMatchers(HttpMethod.POST, String.format("/%s/admin/dangtin/**", apiPrefix))
                            .hasAnyRole("ADMIN","NHANVIEN","AGENT")

                            .requestMatchers(HttpMethod.GET, String.format("/%s/admin/user/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, String.format("/%s/admin/user", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST,String.format("/%s/admin/user/**", apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT,String.format("/%s/admin/user/**", apiPrefix)).hasRole("ADMIN")

                            .requestMatchers(HttpMethod.POST, String.format("/%s/admin/tintuc/dangtin", apiPrefix))
                            .hasAnyRole("ADMIN", "NHANVIEN", "AGENT")

                            .requestMatchers(HttpMethod.GET, String.format("/%s/admin/tintuc/duyettin/**", apiPrefix))
                            .hasAnyRole("ADMIN", "NHANVIEN")

                            .requestMatchers(HttpMethod.PUT, String.format("/%s/admin/tintuc/duyettin/**", apiPrefix))
                            .hasAnyRole("ADMIN", "NHANVIEN")

                            .requestMatchers(HttpMethod.DELETE, String.format("/%s/admin/tintuc/*", apiPrefix)).hasRole("ADMIN")

                            .anyRequest().authenticated();



                })

                .exceptionHandling(exceptions ->
                        exceptions

                                .authenticationEntryPoint((request, response, authException) ->
                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Chưa xác thực")
                                )

                                .accessDeniedHandler((request, response, accessDeniedException) ->
                                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Không có quyền truy cập")
                                )
                );

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "http://localhost:5500"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}