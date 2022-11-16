package com.github.javapsg.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.github.javapsg.utils.JDBC;

public class UserDataManager {

	private Map<UUID, User> userMap = Collections.synchronizedMap(new HashMap<>());

	private UUID createUUID() {
		UUID uuid = UUID.randomUUID();
		if (userMap.containsKey(uuid)) {
			return createUUID();
		} else {
			return uuid;
		}

	}

	public void init() {
		Connection conn = JDBC.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		
		userMap.clear();
		
		try {
			pstmt = conn.prepareStatement("select name, email, password, white_theme, last_connect_time from member order by userid");
			
			while(rs.next()) {
				user = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getBoolean("white_theme"), rs.getDate("last_connect_time"));
				userMap.put(UUID.fromString(rs.getString("uuid")), user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBC.close(conn, pstmt, rs);
		}
	}

	private User addUser(User user) {
		return userMap.put(createUUID(), user);
	}

	private boolean removeUser(UUID uuid) {
		if (userMap.containsKey(uuid)) {
			User user = userMap.get(uuid);
			user.withdraw();
			userMap.put(uuid, user);
			return true;
		}
		return false;
	}

	public User getUser(UUID uuid) {
		return userMap.get(uuid);
	}

	public Collection<User> getUsers() {
		return userMap.values();
	}
}
