package server;

import java.io.Serializable;

public class ChatRoomVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String clients; // 접속한 유저목록
	//private StringBuffer roomBuffer; // 채팅로그 만들경우 사용

	public ChatRoomVO(String clients) {
		this.clients = clients;
	}

	public String getClients() {
		return clients;
	}

	public void setClients(String clients) {
		this.clients = clients;
	}

//	public StringBuffer getRoomBuffer() {
//		return roomBuffer;
//	}
//
//	public void setRoomBuffer(StringBuffer roomBuffer) {
//		this.roomBuffer = roomBuffer;
//	}
//	
}
