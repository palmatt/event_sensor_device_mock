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

@RestController
@RequestMapping(value = { "temperature", "temperature/" })
public class TemperatureController {
	private Messenger messenger;
	private Listener listener = new TemperatureListener();
	private AtomicReference<String> atomicTemperature = new AtomicReference<>("-1");

	public TemperatureController(@Autowired Messenger messenger) {
		this.messenger = messenger;
		messenger.subscribe(Topic.TEMPERATURE, listener);
	}

	@GetMapping
	public String getTemperature() {
		return atomicTemperature.get();
	}

	private class TemperatureListener implements Listener {
		@Override
		public void onMessage(Object message) throws ListenerException {
			atomicTemperature.compareAndSet(atomicTemperature.get(), message.toString());
		}
	}
}
