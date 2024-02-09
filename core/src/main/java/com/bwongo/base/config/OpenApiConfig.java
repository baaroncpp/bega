package com.bwongo.base.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 12/15/23
 **/
/*@Configuration
@OpenAPIDefinition(info = @Info(title = "Bega Core API",
        description = "List APIs for operations that can be performed on web ui",
        version = "1.0"),
        security = @SecurityRequirement(name = "Bearer Authentication"))
@SecurityScheme(name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "jwt",
        paramName = "Authorization",
        scheme = "bearer")*/
public class OpenApiConfig {
}
