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
@RequestMapping(value = { "humidity", "humidity/" })
public class HumidityController {
	private Messenger messenger;
	private Listener listener = new HumidityListener();
	private AtomicReference<String> atomicHumidity = new AtomicReference<>("-1");

	public HumidityController(@Autowired Messenger messenger) {
		this.messenger = messenger;
		messenger.subscribe(Topic.HUMIDITY, listener);
	}

	@GetMapping
	public String getHumidity() {
		return atomicHumidity.get();
	}

	private class HumidityListener implements Listener {
		@Override
		public void onMessage(Object message) throws ListenerException {
			atomicHumidity.compareAndSet(atomicHumidity.get(), message.toString());
		}
	}
}
