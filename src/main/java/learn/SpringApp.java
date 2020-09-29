package learn;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import learn.config.ApiConfig;

public class SpringApp extends AbstractAnnotationConfigDispatcherServletInitializer {

	public SpringApp() {
		App localApp = new App();
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { ApiConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
