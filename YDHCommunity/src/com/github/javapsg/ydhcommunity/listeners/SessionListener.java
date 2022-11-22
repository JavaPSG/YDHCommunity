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
		System.out.println("[Session] ���ο� ���� ������");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("[Session] ���� ������...");
		HttpSession session = event.getSession();
		UUID uuid = (UUID) session.getAttribute("account");
		if (uuid != null) {
			System.out.println("[Session] ���ǿ��� ���� �α��� ���� ������ UUID ����");
			UserDataManager.getInstance().logout(uuid);
		}
	}
}