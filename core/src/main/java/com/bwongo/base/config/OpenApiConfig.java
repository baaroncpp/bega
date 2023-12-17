package com.bwongo.base.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 12/15/23
 **/
@Configuration
@OpenAPIDefinition(info = @Info(title = "Bega Core API",
        description = "List APIs for operations that can be performed on web ui", version = "v1"),
        security = @SecurityRequirement(name = "Bearer Authentication"))
@SecurityScheme(name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "jwt",
        paramName = "Authorization",
        scheme = "bearer")
        //flows = @OAuthFlows(password = @OAuthFlow(
        //        tokenUrl = "${springdoc.oAuthFlow.password.tokenUrl}", scopes = {})))
public class OpenApiConfig {
}
