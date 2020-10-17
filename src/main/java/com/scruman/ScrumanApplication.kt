package com.scruman

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.web.servlet.DispatcherServlet

@SpringBootApplication
open class ScrumanApplication : SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(ScrumanApplication::class.java)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val ctx = SpringApplication.run(ScrumanApplication::class.java, *args)
            val dispatcherServlet = ctx.getBean("dispatcherServlet") as DispatcherServlet
            dispatcherServlet.setThrowExceptionIfNoHandlerFound(true)
        }
    }
}