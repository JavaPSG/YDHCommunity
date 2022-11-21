package com.github.javapsg.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class User implements Comparable<User>{

	private String name;
	private String email;
	private String password;
	private boolean isWhiteTheme = false;
	private Calendar lastConnectTime;
	private List<UUID> posts = Collections.synchronizedList(new ArrayList());

	public User(String name, String email, String password, boolean isWhiteTheme, String lastConnectTime,
			Collection<UUID> posts) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.isWhiteTheme = isWhiteTheme;
		this.posts.clear();
		this.posts.addAll(posts);
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

	public boolean setWhiteTheme(boolean isWhiteTheme) {
		this.isWhiteTheme = isWhiteTheme;
		return this.isWhiteTheme;
	}

	public Calendar getLastConnectTime() {
		return lastConnectTime;
	}

	public void setLastConnectTime(Calendar lastConnectTime) {
		this.lastConnectTime = lastConnectTime;
	}

	public void addPost(UUID uuid) {
		posts.add(uuid);
	}

	public void removePost(UUID uuid) {
		posts.remove(uuid);
	}

	public List<UUID> getPosts() {
		return posts;
	}
	
	@Override
    public int compareTo(User user) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		int v1 = Integer.valueOf(format.format(user.lastConnectTime.getTime()));
		int v2 = Integer.valueOf(format.format(lastConnectTime.getTime()));
		System.out.println(v1);
		System.out.println(v2);
        if (v1 < v2){
            return 1;
        } else if (v1 > v2) {
            return -1;
        }
        return 0;
    }

}
