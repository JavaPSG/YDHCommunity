package com.github.javapsg.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class User {

	private String name;
	private String email;
	private String password;
	private boolean isWhiteTheme;
	private Calendar lastConnectTime;

	
	public User(String name, String email, String password, boolean isWhiteTheme, String lastConnectTime) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.isWhiteTheme = isWhiteTheme;

		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(lastConnectTime));
		} catch (ParseException e) {
			calendar.setTime(new Date());
		}
	}

	public boolean isWithdraw() {
		return name.equals("") && password.equals("");
	}

	public void withdraw() {
		name = "";
		password = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isWhiteTheme() {
		return isWhiteTheme;
	}

	public void setWhiteTheme(boolean isWhiteTheme) {
		this.isWhiteTheme = isWhiteTheme;
	}

	public Calendar getLastConnectTime() {
		return lastConnectTime;
	}

	public void setLastConnectTime(Calendar lastConnectTime) {
		this.lastConnectTime = lastConnectTime;
	}

}
