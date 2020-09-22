package learn.service.messenger;

import learn.service.MyService;
import learn.service.messenger.listener.Listener;

public interface Messenger extends MyService {

	public void subscribe(Topic topic, Listener listener);

	public void publish(Topic topic, Object message);

}
