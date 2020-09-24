package learn.service.dummy;

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

	public DummyService() {
		logger = Logger.getAnonymousLogger();
		messenger = MessengerImpl.getInstance();
	}

	@Override
	public void startService() throws ServiceException {
		running = true;
		listener = new DummyServiceListener();
		messenger.subscribe(Topic.TEMPERATURE, listener);
	}

	@Override
	public void stopService() {
		running = false;
		messenger.unSubscribe(Topic.TEMPERATURE, listener);

	}

	protected void consumeInfo(String message) {
		logger.info(message);
	}

	public boolean isRunning() {
		return running;
	}
}
