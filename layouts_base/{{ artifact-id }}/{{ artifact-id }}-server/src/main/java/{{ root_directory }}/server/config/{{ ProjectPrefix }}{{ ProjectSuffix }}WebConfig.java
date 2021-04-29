package {{ root_package }}.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class {{ ProjectPrefix }}{{ ProjectSuffix }}WebConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        {% if events_feature %}converters.add(new MessageHttpMessageConverter());{% endif %}
    }
}
