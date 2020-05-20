package Socket;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	public String type, sender, title, content, document, recipient;

	public Message(String type, String sender, String title, String content, String document, String recipient) {
		this.type = type;
		this.sender = sender;
		this.title = title;
		this.content = content;
		this.document = document;		
		this.recipient = recipient;
	}

	@Override
	public String toString() {
		return "{type='" + type + "', sender='" + sender + "', title = '" + title + "' ,content='" + content
				+ "',document='" + document + "', recipient='" + recipient + "'}";
	}
}
