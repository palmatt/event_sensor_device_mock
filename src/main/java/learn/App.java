package learn;

import learn.runner.ServiceRunner;
import learn.runner.ServiceRunnerImpl;

public class App {

	public App() {
		ServiceRunner runner = ServiceRunnerImpl.getInstance();
		runner.startServices();
	}
}
