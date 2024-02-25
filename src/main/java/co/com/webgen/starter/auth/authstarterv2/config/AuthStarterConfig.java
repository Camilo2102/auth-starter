package co.com.webgen.starter.auth.authstarterv2.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("co.com.webgen.web.sql.starter.config")
@ComponentScan("co.com.webgen.web.starter.service")
public class AuthStarterConfig {
}
