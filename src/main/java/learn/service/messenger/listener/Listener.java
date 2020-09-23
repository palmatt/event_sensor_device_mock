package learn.service.messenger.listener;

import learn.exception.ListenerException;

public interface Listener {
	public void onMessage(Object message) throws ListenerException;
}
