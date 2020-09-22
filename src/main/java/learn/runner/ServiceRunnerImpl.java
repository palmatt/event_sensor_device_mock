package learn.runner;

import java.util.List;

import learn.exception.ServiceException;
import learn.service.MyService;

public class ServiceRunnerImpl implements ServiceRunner {

	private List<MyService> services;
	private static ServiceRunnerImpl instance;

	private ServiceRunnerImpl() {

	}

	public static ServiceRunnerImpl getInstance() {
		if (instance == null) {
			instance = new ServiceRunnerImpl();
		}
		return instance;
	}

	@Override
	public boolean startServices() {
		boolean executed = false;

		try {
			services.forEach(s -> s.startService());
			executed = true;
		} catch (ServiceException serviceException) {
			serviceException.printStackTrace();
		}
		return executed;
	}

}
