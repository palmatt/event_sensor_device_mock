package learn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import learn.exception.ServiceException;
import learn.service.messenger.Messenger;
import learn.service.messenger.Topic;
import learn.service.messenger.listener.Listener;

public class MessengerImpl implements Messenger {

	private static MessengerImpl instance;
	private ConcurrentHashMap<Topic, List<Listener>> topicListenerPair;

	private MessengerImpl() {
		topicListenerPair = new ConcurrentHashMap<>();
	}

	public static MessengerImpl getInstance() {
		if (instance == null) {
			instance = new MessengerImpl();
		}
		return instance;
	}

	@Override
	public void startService() throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void subscribe(Topic topic, Listener listener) {
		if (!topicListenerPair.containsKey(topic)) {
			List<Listener> listeners = new ArrayList<>(List.of(listener));
			topicListenerPair.put(topic, listeners);
		} else {
			List<Listener> listeners = topicListenerPair.get(topic);
			if (!listeners.contains(listener)) {
				listeners.add(listener);
			}
		}
	}

	@Override
	public void publish(Topic topic, Object message) {
		List<Listener> listeners = topicListenerPair.get(topic);
		if (listeners != null) {
			listeners.forEach(s -> s.onMessage(message));
		}

	}

}
