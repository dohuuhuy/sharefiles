package Socket;

import java.io.Serializable;

import javafx.scene.control.CheckBox;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	public String type, sender, title, content, document, recipient;
	//private CheckBox select;
	public Message(String type, String sender, String title, String content, String document, String recipient) {
		this.type = type;
		this.sender = sender;
		this.title = title;
		this.content = content;
		this.document = document;
		this.recipient = recipient;
		//this.select = new CheckBox();
	}
	

	@Override
	public String toString() {
		return "{type='" + type + "', sender='" + sender + "', title = '" + title + "' ,content='" + content
				+ "',document='" + document + "', recipient='" + recipient + "'}";
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSender() {

		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {

		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDocument() {

		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getRecipient() {

		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}


	public Object getBytes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * public CheckBox getSelect() {
	 * 
	 * return select; }
	 * 
	 * public void setSelect(CheckBox select) { this.select = select; }
	 */

}
