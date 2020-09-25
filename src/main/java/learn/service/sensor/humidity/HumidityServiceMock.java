package learn.service.sensor.humidity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import learn.exception.ServiceException;
import learn.service.MyService;
import learn.service.messenger.Messenger;
import learn.service.messenger.MessengerImpl;
import learn.service.messenger.topic.Topic;
import learn.service.sensor.Sensor;

public class HumidityServiceMock implements MyService, Runnable, Sensor {
	private static final Integer PROBABILITY = 70;
	private AtomicInteger humidity = new AtomicInteger(-1);
	private Messenger messenger;
	private Random random;
	private volatile boolean running = false;
	private String name;

	public HumidityServiceMock(String name) {
		messenger = MessengerImpl.getInstance();
		random = new Random();
		this.name = name;
	}

	public HumidityServiceMock() {
		messenger = MessengerImpl.getInstance();
		random = new Random();
		name = "Humidity";
	}

	@Override
	public void run() {
		while (running) {
			try {
				sendMessageAndWait();
				updateValue();
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
			humidity.set(70);
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
		String message = generateMessage();
		messenger.publish(Topic.HUMIDITY, message);
		Thread.sleep(1000);
	}

	private String generateMessage() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss ");
		LocalDateTime now = LocalDateTime.now();

		return dateTimeFormatter.format(now) + name + ": " + humidity;
	}

	private void updateValue() {
		if (shouldChange()) {
			int offset = calculateOffset();
			humidity.getAndAdd(offset);
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

}
