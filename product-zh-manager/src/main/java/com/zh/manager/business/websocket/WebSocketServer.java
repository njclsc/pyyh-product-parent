package com.zh.manager.business.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/endpoint")
public class WebSocketServer {
	@OnOpen
	public void onOpen(Session session){
		
	}
	@OnMessage
	public void onMessage(){}
	@OnClose
	public void onClose(){}
	@OnError
	public void onError(){}
}
