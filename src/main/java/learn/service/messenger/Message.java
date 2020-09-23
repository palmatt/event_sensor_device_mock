package learn.service.messenger;

import learn.service.messenger.listener.Listener;

public class Message {
	private Topic topic;
	private Listener listener;
	private Object messageContent;

	public Message(Topic topic, Listener listener, Object messageContent) {
		this.topic = topic;
		this.listener = listener;
		this.messageContent = messageContent;
	}

	/**
	 * @return the topic
	 */
	public Topic getTopic() {
		return topic;
	}

	/**
	 * @return the listener
	 */
	public Listener getListener() {
		return listener;
	}

	/**
	 * @return the messageContent
	 */
	public Object getMessageContent() {
		return messageContent;
	}
}
