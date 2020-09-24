package learn.runner;

import java.util.ArrayList;
import java.util.List;

import learn.exception.ServiceException;
import learn.service.MyService;
import learn.service.dummy.DummyService;
import learn.service.messenger.MessengerImpl;
import learn.service.sensor.temperature.TemperatureServiceMock;

public class ServiceRunnerImpl implements ServiceRunner {

	private List<MyService> services;
	private static ServiceRunnerImpl instance;

	private ServiceRunnerImpl() {
		services = new ArrayList<>();
		retrieveServiceList();
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

	private void retrieveServiceList() {
		services.add(MessengerImpl.getInstance());
		services.add(new TemperatureServiceMock());
		services.add(new DummyService());
	}

}
