package learn.controller;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learn.exception.ListenerException;
import learn.service.messenger.Messenger;
import learn.service.messenger.listener.Listener;
import learn.service.messenger.topic.Topic;

//TODO split in 3 controllers, refactor messenger in order to use json instead of plain text

@RestController
@RequestMapping(value = "/")
public class ApiController {
	private AtomicReference<String> atomicTemperature = new AtomicReference<>("-1");
	private AtomicReference<String> atomicHumidity = new AtomicReference<>("-1");

	private Messenger messenger;

	public ApiController(@Autowired Messenger messenger) {
		this.messenger = messenger;
		this.messenger.subscribe(Topic.TEMPERATURE, new TemperatureListener());
		this.messenger.subscribe(Topic.HUMIDITY, new HumidityListener());
	}

	@GetMapping(value = { "temperature", "temperature/" })
	public String getTemperature() {
		return atomicTemperature.get();
	}

	@GetMapping(value = { "humidity", "humidity/" })
	public String getHumidity() {
		return atomicHumidity.get();
	}

	private class TemperatureListener implements Listener {

		@Override
		public void onMessage(Object message) throws ListenerException {
			atomicTemperature.compareAndSet(atomicTemperature.get(), (String) message);
		}
	}

	private class HumidityListener implements Listener {

		@Override
		public void onMessage(Object message) throws ListenerException {
			atomicHumidity.compareAndSet(atomicHumidity.get(), (String) message);
		}
	}
}