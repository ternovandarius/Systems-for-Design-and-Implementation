package lab9.lab.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"lab9.lab.repository", "lab9.lab.service", "lab9.lab.ui"})
public class RepoConfig {
}
