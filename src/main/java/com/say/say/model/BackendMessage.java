package com.say.say.model;

import java.io.Serializable;

/**
 * Class used to send information from backend to frontend
 * @author gavrilo
 *
 */
public class BackendMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	String text;
	
	MessageType messageType;
	
	public BackendMessage(String text, MessageType messageType) {
		this.text = text;
		this.messageType = messageType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageType == null) ? 0 : messageType.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BackendMessage other = (BackendMessage) obj;
		if (messageType != other.messageType)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
	
	
}
