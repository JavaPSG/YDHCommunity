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
		System.out.println("[Session] 货肺款 技记 积己凳");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("[Session] 技记 力芭吝...");
		HttpSession session = event.getSession();
		UUID uuid = (UUID) session.getAttribute("account");
		if (uuid != null) {
			System.out.println("[Session] 技记俊辑 掘篮 肺弊牢 蜡历 单捞磐 UUID 沥府");
			UserDataManager.getInstance().logout(uuid);
		}
	}
}