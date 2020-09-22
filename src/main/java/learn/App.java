package learn;

import learn.runner.ServiceRunner;
import learn.runner.ServiceRunnerImpl;

public class App {

	public static void main(String... args) {
		ServiceRunner runner = ServiceRunnerImpl.getInstance();
		runner.startServices();
	}

}
