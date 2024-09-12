package co.com.webgen.starter.auth.authstarterv2.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("cloud.webgen.web.starter.service")
@ComponentScan("cloud.webgen.web.starter.handler")
public class AuthStarterConfig {
}
