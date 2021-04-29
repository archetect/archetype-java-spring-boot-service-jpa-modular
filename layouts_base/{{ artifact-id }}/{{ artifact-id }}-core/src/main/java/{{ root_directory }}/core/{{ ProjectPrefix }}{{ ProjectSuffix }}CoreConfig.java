package {{ root_package }}.core;

import {{ root_package }}.persistence.{{ ProjectPrefix }}{{ ProjectSuffix }}PersistenceConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.Set;

@Configuration
@Import({
        {{ ProjectPrefix }}{{ ProjectSuffix }}PersistenceConfig.class,
})
@ComponentScan
public class {{ ProjectPrefix }}{{ ProjectSuffix }}CoreConfig {

    @Bean
    @Qualifier("{{ projectPrefix }}")
    public ConversionService {{ projectPrefix }}ConversionService(Set<Converter<?,?>> converters) {
        ConfigurableConversionService conversionService = new DefaultConversionService();
        converters.forEach(conversionService::addConverter);
        return conversionService;
    }
}