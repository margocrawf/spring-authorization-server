package sample;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.security.util.InMemoryResource;

import java.io.IOException;
import java.util.List;

public class Oauth2AuthorizationServerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		String config = System.getenv("CLIENT_CONFIG");
		Resource resource = new InMemoryResource(config);
		YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();
		List<PropertySource<?>> propertySources = null;
		try {
			propertySources = sourceLoader.load("simpleConfig", resource);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		propertySources.forEach(propertySource -> applicationContext.getEnvironment().getPropertySources().addFirst(propertySource));
	}
}
