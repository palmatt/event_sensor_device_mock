package learn.service.dummy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import learn.exception.ServiceException;
import learn.service.MyService;
import learn.service.messenger.Messenger;
import learn.service.messenger.MessengerImpl;
import learn.service.messenger.listener.Listener;
import learn.service.messenger.topic.Topic;

public class DummyService implements MyService {
	private Logger logger;
	private Messenger messenger;
	private volatile boolean running = false;
	private Listener listener;
	private List<Topic> subscribedTopics;

	public DummyService(Topic... topics) {
		logger = Logger.getAnonymousLogger();
		messenger = MessengerImpl.getInstance();
		subscribedTopics = new ArrayList<>(List.of(topics));
	}

	@Override
	public void startService() throws ServiceException {
		running = true;
		listener = new DummyServiceListener();
		subscribedTopics.forEach(topic -> messenger.subscribe(topic, listener));
	}

	@Override
	public void stopService() {
		running = false;
		subscribedTopics.forEach(topic -> messenger.unSubscribe(topic, listener));
	}

	protected void consumeInfo(String message) {
		logger.info(message);
	}

	public boolean isRunning() {
		return running;
	}
}
