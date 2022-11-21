package com.github.javapsg.post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import com.github.javapsg.utils.JDBCUtil;

public class PostDataManager {

	private final Map<String, Post> postMap = Collections.synchronizedMap(new HashMap<>());

	private static class InnerInstanceClazz {
		private static final PostDataManager instance = new PostDataManager();
	}

	public static PostDataManager getInstance() {
		return InnerInstanceClazz.instance;
	}

	public void init() {
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Post post = null;

		postMap.clear();

		try {
			pstmt = conn.prepareStatement(
					"select name, email, password, white_theme, last_connect_time from member order by last_connect_time");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User(rs.getString("name"), rs.getString("email"), rs.getString("password"),
						rs.getBoolean("white_theme"), rs.getDate("last_connect_time"));
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
			pstmt.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
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
			pstmt.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
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
