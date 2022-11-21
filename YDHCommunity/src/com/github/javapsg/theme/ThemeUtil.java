package com.github.javapsg.theme;

import java.util.UUID;

import javax.servlet.http.Cookie;

import com.github.javapsg.user.User;
import com.github.javapsg.user.UserDataManager;

public class ThemeUtil {

	private static final UserDataManager manager = UserDataManager.getInstance();

	public static final Theme getTheme(Cookie[] cookies) {
		if (cookies != null) {
			String uuidStr = manager.getAccountData(cookies);
			System.out.println("G1 " + uuidStr);
			if (uuidStr != null) {
				UUID uuid = UUID.fromString(uuidStr);
				System.out.println("G2 " + uuid);
				if (uuid != null) {
					User user = manager.getUser(manager.getEmail(uuid));
					System.out.println("G3 " + user);
					return toTheme(user.isWhiteTheme());
				}
			} else {
				for (int i = 0; i < cookies.length; i++) {
					Cookie c = cookies[i];
					if (c.getName().equalsIgnoreCase("ydhcommunity_theme")) {
						return toTheme(c.getValue());
					}
				}
			}

		}
		return new Theme("change-white-theme", "&#xe51c", "테마: 다크");
	}

	private static final Theme toTheme(String value) {
		if (value.equalsIgnoreCase("dark")) {
			return new Theme("change-dark-theme", "&#xe518", "테마: 라이트");
		} else {
			return new Theme("change-white-theme", "&#xe51c", "테마: 다크");
		}
	}

	private static final Theme toTheme(boolean value) {
		if (value) {
			return new Theme("change-dark-theme", "&#xe518", "테마: 라이트");
		} else {
			return new Theme("change-white-theme", "&#xe51c", "테마: 다크");
		}
	}
}
