package com.ugc.gameserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Created by fanjl on 2017/4/27.
 */
@Configuration
@EnableWebMvc
public class ApplicationConfig extends WebMvcConfigurerAdapter{
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreUnknownPathExtensions(true).defaultContentType(MediaType.APPLICATION_JSON_UTF8);
    }
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setPrettyPrint(true);
//        MappingJackson2XmlView xmlView = new MappingJackson2XmlView();
//        xmlView.setPrettyPrint(true);
        registry.enableContentNegotiation(jsonView);
//        registry.jsp("/WEB-INF/view/", ".jsp");
    }
}
