package org.robogit.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
open class ExternResourceConfig : WebMvcConfigurer {
  override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
    registry!!.addResourceHandler("/external/**")
        .addResourceLocations("file:external/")
  }
}
