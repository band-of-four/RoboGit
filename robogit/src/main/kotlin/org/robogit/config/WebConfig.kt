package org.robogit.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
open class WebConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry?) {
        registry!!.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
    }
}