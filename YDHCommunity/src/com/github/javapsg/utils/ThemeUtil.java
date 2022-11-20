package com.github.javapsg.utils;

import javax.servlet.http.Cookie;

public class ThemeUtil {

	public static final Theme getTheme(Cookie[] cookies) {
		if (cookies != null) {

			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (c.getName().equalsIgnoreCase("ydhcommunity_theme")) {
					return toTheme(c.getValue());
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

}
