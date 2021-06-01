package {{root_package}}.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Payment Gateway Archetype
 */

@EnableSwagger2
@Configuration
@ComponentScan(basePackages = "{{root_package}}.api")
public class {{ ProjectPrefix }}{{ ProjectSuffix }}SwaggerConfig {
    /**
     * Load the swagger definition
     *
     * @return SwaggerResourcesProvider swagger resource definition
     */
    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            SwaggerResource wsResource = new SwaggerResource();
            wsResource.setName("{{ artifact-id }}");
            wsResource.setSwaggerVersion("2.0");
            wsResource.setLocation("/openapi/{{ artifact-id }}.yaml");
            List<SwaggerResource> resources = new ArrayList<>();
            resources.add(wsResource);
            return resources;
        };
    }

}
