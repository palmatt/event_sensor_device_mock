package learn.service.messenger;

import learn.service.messenger.listener.Listener;
import learn.service.messenger.topic.Topic;

public interface Messenger{

	public void subscribe(Topic topic, Listener listener);

	public void unSubscribe(Topic topic, Listener listener);

	public void publish(Topic topic, Object message);

}
