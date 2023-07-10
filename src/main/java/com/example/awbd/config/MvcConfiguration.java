package com.example.awbd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    // creare si configurare bean SimpleMappingExceptionResolver
    @Bean(name="simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

        // setare eroare default: "defaultException".
        resolver.setDefaultErrorView("defaultException");

        //seteaza numele objectului in "ex"
        resolver.setExceptionAttribute("ex");

        // configurare mapare exceptii pt diferite view-uri
        Properties mappings = new Properties();
        mappings.setProperty("NumberFormatException", "numberFormatException");
        resolver.setExceptionMappings(mappings);

        // configurare coduri de stare
        Properties statusCodes = new Properties();
        statusCodes.setProperty("NumberFormatException", "400");
        resolver.setStatusCodes(statusCodes);

        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // creare resource handler pt toate "/webjars/**" si mapare la "/webjars/".
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");
    }
}
