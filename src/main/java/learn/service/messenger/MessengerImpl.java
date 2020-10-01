package learn.service.messenger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import learn.exception.ListenerException;
import learn.exception.ServiceException;
import learn.service.MyService;
import learn.service.messenger.listener.Listener;
import learn.service.messenger.message.Message;
import learn.service.messenger.topic.Topic;

public class MessengerImpl implements MyService, Messenger, Runnable {

	private static MessengerImpl instance;
	private ConcurrentHashMap<Topic, List<Listener>> topicListenerPair;
	private ConcurrentLinkedQueue<Message> messages;
	private volatile boolean running = false;

	private MessengerImpl() {
		topicListenerPair = new ConcurrentHashMap<>();
		messages = new ConcurrentLinkedQueue<>();
	}

	public static MessengerImpl getInstance() {
		if (instance == null) {
			instance = new MessengerImpl();
		}
		return instance;
	}

	@Override
	public void startService() {
		running = true;
		try {
			Thread thread = new Thread(this);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
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
		if (listeners != null && !listeners.isEmpty()) {
			listeners.forEach(listener -> messages.add(new Message(topic, listener, message)));
		}
	}

	@Override
	public void stopService() {
		running = false;
	}

	@Override
	public void run() {
		while (running) {
			if (!messages.isEmpty()) {
				Message message = messages.poll();
				try {
					message.getListener()
						.onMessage(message.getMessageContent());
				} catch (ListenerException e) {
					e.printStackTrace();
					messages.add(message);
					throw new ListenerException();
				}
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread()
						.interrupt();
				}
			}
		}
	}

	@Override
	public void unSubscribe(Topic topic, Listener listener) {
		List<Listener> listenerList = topicListenerPair.get(topic);
		if (listenerList.contains(listener)) {
			listenerList.remove(listener);
		}
	}
}
