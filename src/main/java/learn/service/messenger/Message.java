package learn.service.messenger;

import learn.service.messenger.listener.Listener;

public class Message {
	private Topic topic;
	private Listener listener;
	private Object messageContent;

	public Message() {

	}

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
	 * @param topic the topic to set
	 */
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	/**
	 * @return the listener
	 */
	public Listener getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	/**
	 * @return the messageContent
	 */
	public Object getMessageContent() {
		return messageContent;
	}

	/**
	 * @param messageContent the messageContent to set
	 */
	public void setMessageContent(Object messageContent) {
		this.messageContent = messageContent;
	}

}
