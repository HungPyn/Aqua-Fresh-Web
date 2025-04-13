    package com.quafresh.web.aquafreshweb._config;

    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.servlet.config.annotation.CorsRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            CorsConfiguration configuration = new CorsConfiguration();
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:5173", "http://localhost:5175", "http://localhost:5176")  // Hoặc chỉ định URL cụ thể
                    .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        }
    }