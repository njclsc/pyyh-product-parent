package com.zh.manager.business.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.zh.manager.util.ContainerUtil;

@Component
@ServerEndpoint("/endpoint")
public class WebSocketServer {
	@OnOpen
	public void onOpen(Session session){
		Map<String, List<String>> parameters = session.getRequestParameterMap();
		List<String> parameter = parameters.get("unitIndex");
		String sid = session.getId();
		String unitIndex = parameter.get(0);
		if(unitIndex != null){
			ContainerUtil.getEndpointSession().put(unitIndex + "_" + sid, session);
			ContainerUtil.getAlarmIds().put(unitIndex + "_" + sid, new ArrayList<String>());
		}
		System.out.println(ContainerUtil.getEndpointSession().size() + "  open-->");
	}
	@OnMessage
	public void onMessage(String message, Session session){
		
	}
	@OnClose
	public void onClose(Session session){
		Map<String, List<String>> parameters = session.getRequestParameterMap();
		List<String> parameter = parameters.get("unitIndex");
		String sid = session.getId();
		String unitIndex = parameter.get(0);
		if(unitIndex != null){
			ContainerUtil.getEndpointSession().remove(unitIndex + "_" + sid);
			ContainerUtil.getAlarmIds().remove(unitIndex + "_" + sid);
		}
		System.out.println(ContainerUtil.getEndpointSession().size() + "  close-->");
		System.out.println(ContainerUtil.getAlarmIds().size() + "  close-->");
	}
	@OnError
	public void onError(Throwable t){}
}
