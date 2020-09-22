package learn.service.sensor;

import java.util.Random;

import learn.exception.ServiceException;
import learn.service.MyService;
import learn.service.messenger.Messenger;

public class TemperatureServiceMock implements MyService, Runnable {
	private static final Integer PROBABILITY = 70;
	private int temperature = -1;
	private Messenger messenger;

	public void TemperatureServiceMock() {

	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			if (shouldChange()) {
				int offset = calculateOffset();
				temperature += offset;
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void startService() throws ServiceException {
		try {
			run();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	private boolean shouldChange() {
		boolean change = false;

		Random random = new Random();
		int chance = random.nextInt(100);
		if (chance > PROBABILITY) {
			change = true;
		}
		return change;
	}

	private int calculateOffset() {
		return 0;
	}

}
