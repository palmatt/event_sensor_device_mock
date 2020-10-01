package learn.service.sensor.temperature;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import learn.exception.ServiceException;
import learn.service.MyService;
import learn.service.messenger.Messenger;
import learn.service.messenger.MessengerImpl;
import learn.service.messenger.message.Reading;
import learn.service.messenger.topic.Topic;
import learn.service.sensor.Sensor;

public class TemperatureServiceMock implements MyService, Runnable, Sensor {
	private static final Integer PROBABILITY = 70;
	private AtomicInteger temperature = new AtomicInteger(-1);
	private Messenger messenger;
	private Random random;
	private volatile boolean running = false;
	private String name;

	public TemperatureServiceMock(String name) {
		messenger = MessengerImpl.getInstance();
		random = new Random();
		this.name = name;
	}

	public TemperatureServiceMock() {
		messenger = MessengerImpl.getInstance();
		random = new Random();
		name = "Temperature";
	}

	@Override
	public void run() {
		while (running) {
			try {
				sendMessageAndWait();
				updateValue();
				correctValue();
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread()
					.interrupt();
			}
		}
	}

	@Override
	public void startService() {
		try {
			running = true;
			Thread thread = new Thread(this);
			thread.start();
			temperature.set(23);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void stopService() {
		running = false;
	}

	private void sendMessageAndWait() throws InterruptedException {
		Reading reading = generateMessage();
		messenger.publish(Topic.TEMPERATURE, reading);
		Thread.sleep(1000);
	}

	private Reading generateMessage() {
		LocalDateTime now = LocalDateTime.now();
		
		return new Reading(now, name, String.valueOf(temperature.get()));
	}

	private void updateValue() {
		if (shouldChange()) {
			int offset = calculateOffset();
			temperature.getAndAdd(offset);
		}
	}

	private boolean shouldChange() {
		boolean change = false;

		int chance = random.nextInt(100);
		if (chance > PROBABILITY) {
			change = true;
		}
		return change;
	}

	private int calculateOffset() {
		int randomNumber = random.nextInt(2);
		return randomNumber - random.nextInt(2);
	}

	private void correctValue() {
		int value = temperature.get();
		if (value > 40) {
			temperature.set(value - 10);
		} else if (value < -25) {
			temperature.set(value + 10);
		}
	}
}
