package learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import learn.service.messenger.Messenger;
import learn.service.messenger.MessengerImpl;

@Configuration
@ComponentScan(basePackages = { "learn.controller" })
public class ApiConfig {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/view/");
		internalResourceViewResolver.setSuffix(".jsp");

		return internalResourceViewResolver;
	}

	@Bean
	public Messenger getMessenger() {
		return MessengerImpl.getInstance();
	}
}
