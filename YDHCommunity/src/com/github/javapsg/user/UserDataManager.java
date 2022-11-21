package com.github.javapsg.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import com.github.javapsg.utils.JDBCUtil;

public class UserDataManager {

	private final Map<String, User> userMap = Collections.synchronizedMap(new HashMap<>());
	private final Map<UUID, String> accountMap = Collections.synchronizedMap(new HashMap<>());
	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	private static class InnerInstanceClazz {
		private static final UserDataManager instance = new UserDataManager();
	}

	public static UserDataManager getInstance() {
		return InnerInstanceClazz.instance;
	}

	private UUID createUUID() {
		UUID uuid = UUID.randomUUID();
		if (userMap.containsKey(uuid)) {
			return createUUID();
		} else {
			return uuid;
		}

	}

	public UUID login(String email) {
		UUID uuid = createUUID();
		accountMap.put(uuid, email);
		return uuid;
	}

	public void logout(UUID uuid) {
		accountMap.remove(uuid);
	}

	public void init() {
		if (!userMap.isEmpty()) {
			return;
		}
		userMap.clear();
		
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;

		try {
			pstmt = conn.prepareStatement(
					"select name, email, password, white_theme, last_connect_time from member order by last_connect_time");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"),
						rs.getBoolean("white_theme"), rs.getString("last_connect_time"));
				userMap.put(rs.getString("email"), user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
	}

	public int insertMember(User user) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into member(name, email, password, white_theme, last_connect_time) values(?,?,?,?,?)";
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			System.out.println(user);

			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getEmail());
			pstmt.setString(3, user.getPassword());
			pstmt.setInt(4, user.isWhiteTheme() ? 1 : 0);
			pstmt.setString(5, format.format(new Date()));
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		userMap.put(user.getEmail(), user);
		System.out.println("A " + UserDataManager.getInstance().getUsers().stream().map(value -> value.getPassword())
				.collect(Collectors.toList()));
		System.out.println("A " + UserDataManager.getInstance().getEmails());
		return result;
	}

	public int updateMember(User user) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update member set name = ?, password = ?, white_theme = ?, last_connect_time = ? where email = ?";
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setInt(3, user.isWhiteTheme() ? 1 : 0);
			pstmt.setString(4, format.format(new Date()));
			pstmt.setString(5, user.getEmail());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		userMap.put(user.getEmail(), user);
		System.out.println(UserDataManager.getInstance().getUsers());
		return result;
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

	public User getUser(String email) {
		return userMap.get(email);
	}

	public String getEmail(UUID uuid) {
		return accountMap.get(uuid);
	}

	public Collection<User> getUsers() {
		return userMap.values();
	}

	public Collection<String> getEmails() {
		return userMap.keySet();
	}

	public String getAccountData(Cookie[] cookies) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie c = cookies[i];
			if (c.getName().equalsIgnoreCase("ydhcommunity_account")) {
				return c.getValue();
			}
		}
		return null;
	}
}
