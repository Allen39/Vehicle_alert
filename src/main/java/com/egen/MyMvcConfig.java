package com.egen;

import com.amazonaws.services.sns.AmazonSNS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Qualifier("MyMvcConfig")
public class MyMvcConfig {
//
//    @Bean
//    public WebMvcConfigurer webMvcConfig()
//    {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry)
//            {
//                registry.addMapping("/**").allowedMethods("GET", "POST", "DELETE","PUT", "PATCH").allowedHeaders("*");
//
//            }
//        };
//    }

    @Bean
    public NotificationMessagingTemplate notificationMessagingTemplate(AmazonSNS amazonSNS)
    {
        return new NotificationMessagingTemplate(amazonSNS);
    }
}

