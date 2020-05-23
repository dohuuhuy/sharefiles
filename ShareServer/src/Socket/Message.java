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
}
