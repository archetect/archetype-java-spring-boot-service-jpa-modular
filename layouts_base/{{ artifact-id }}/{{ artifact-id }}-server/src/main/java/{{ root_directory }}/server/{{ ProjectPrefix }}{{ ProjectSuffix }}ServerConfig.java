package {{ root_package }}.server;

import {{ root_package }}.core.{{ ProjectPrefix }}{{ ProjectSuffix }}CoreConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        {{ ProjectPrefix }}{{ ProjectSuffix }}CoreConfig.class,
})
public class {{ ProjectPrefix }}{{ ProjectSuffix }}ServerConfig {

}
