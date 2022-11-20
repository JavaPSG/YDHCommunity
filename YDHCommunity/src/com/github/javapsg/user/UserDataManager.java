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

import com.github.javapsg.utils.JDBCUtil;

public class UserDataManager {
	
	private static class InnerInstanceClazz {
        private static final UserDataManager instance = new UserDataManager();
    }

    public static UserDataManager getInstance() {
        return InnerInstanceClazz.instance;
    }

	private Map<String, User> userMap = Collections.synchronizedMap(new HashMap<>());


	public void init() {
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		
		userMap.clear();
		
		try {
			pstmt = conn.prepareStatement("select name, email, password, white_theme, last_connect_time from member order by userid");
			
			while(rs.next()) {
				user = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getBoolean("white_theme"), rs.getDate("last_connect_time"));
				userMap.put(rs.getString("email"), user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
	}

	private User addUser(User user) {
		return userMap.put(user.getEmail(), user);
	}

	private boolean removeUser(String email) {
		if (userMap.containsKey(email)) {
			User user = userMap.get(email);
			user.withdraw();
			userMap.put(email, user);
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
