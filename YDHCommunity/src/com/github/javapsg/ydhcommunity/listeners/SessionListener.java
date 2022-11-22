package com.github.javapsg.ydhcommunity.listeners;

import java.util.UUID;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.github.javapsg.ydhcommunity.user.UserDataManager;

@WebListener
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("[Session] 새로운 세션을 생성함");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("[Session] 세션 제거중...");
		HttpSession session = event.getSession();
		UUID uuid = (UUID) session.getAttribute("account");
		if (uuid != null) {
			System.out.println("[Session] 세션과 그 세션의 유저 계정 UUID 값을 함께 제거");
			UserDataManager.getInstance().logout(uuid);
		}
	}
}