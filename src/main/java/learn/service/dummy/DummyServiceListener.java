package learn.service.dummy;

import java.util.logging.Logger;

import learn.exception.ListenerException;
import learn.service.messenger.listener.Listener;

public class DummyServiceListener implements Listener {
	private Logger logger = Logger.getAnonymousLogger();
	private DummyService service;

	protected DummyServiceListener() {

	}

	protected DummyServiceListener(DummyService ownerService) {
		this.service = ownerService;
	}

	@Override
	public void onMessage(Object message) throws ListenerException {
		if (!(message instanceof String)) {
			if (service != null) {
				service.consumeInfo(message.toString());
			} else {
				logger.info(message.toString());
			}
		}
	}
}