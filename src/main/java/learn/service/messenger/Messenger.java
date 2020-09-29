package learn.service.messenger;

import org.springframework.stereotype.Component;

import learn.service.messenger.listener.Listener;
import learn.service.messenger.topic.Topic;

@Component
public interface Messenger {

	public void subscribe(Topic topic, Listener listener);

	public void unSubscribe(Topic topic, Listener listener);

	public void publish(Topic topic, Object message);

}
