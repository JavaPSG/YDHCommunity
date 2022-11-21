package com.github.javapsg.post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.github.javapsg.user.User;
import com.github.javapsg.user.UserDataManager;
import com.github.javapsg.utils.JDBCUtil;

public class PostDataManager {

	private final Map<UUID, Post> postMap = Collections.synchronizedMap(new HashMap<>());
	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	private static class InnerInstanceClazz {
		private static final PostDataManager instance = new PostDataManager();
	}

	public static PostDataManager getInstance() {
		return InnerInstanceClazz.instance;
	}

	private UUID createUUID() {
		UUID uuid = UUID.randomUUID();
		if (postMap.containsKey(uuid)) {
			return createUUID();
		} else {
			return uuid;
		}
	}

	public void init() {
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Post post = null;
		postMap.clear();

		try {
			pstmt = conn.prepareStatement(
					"select writer, title, content, recommanders, write_time, uuid from post order by write_time");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				post = new Post(rs.getString("writer"), rs.getString("title"), rs.getString("content"),
						getRecs(rs.getString("recommanders")), rs.getString("write_time"),
						UUID.fromString(rs.getString("uuid")));
				addPost(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
	}
	
	private Collection<String> getRecs(String str){
		if (str == null || (str != null && str.isEmpty())) {
			return Arrays.asList();
		}
		return Arrays.asList(str.split(":"));
	}
	
	public int insertPost(Post post) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		UUID uuid = createUUID();
		User user = UserDataManager.getInstance().getUser(post.getWriter());
		String sql = "insert into post(writer, title, content, recommanders, write_time, uuid) values(?,?,?,?,?,?)";
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, post.getWriter());
			pstmt.setString(2, post.getTitle());
			pstmt.setString(3, post.getContent());
			pstmt.setString(4, String.join(":", post.getRec()));
			pstmt.setString(5, format.format(new Date()));
			pstmt.setString(6, uuid.toString());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		post.setUuid(uuid);
		user.addPost(uuid);
		UserDataManager.getInstance().updateMember(user);
		addPost(post);
		return result;
	}

	public int updatePost(Post post) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update post set title = ?, content = ?, recommanders = ? where uuid = ?";
		try {
			conn = JDBCUtil.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, post.getTitle());
			pstmt.setString(2, post.getContent());
			pstmt.setString(3, String.join(":", post.getRec()));
			pstmt.setString(4, post.getUuid().toString());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		addPost(post);
		return result;
	}

	private Post addPost(Post post) {
		return postMap.put(post.getUuid(), post);
	}

	private boolean removePost(UUID uuid) {
		if (postMap.containsKey(uuid)) {
			Post post = postMap.get(uuid);
			// 삭제하는 SQL
			postMap.remove(uuid);
			return true;
		}
		return false;
	}

	public Post getPost(UUID uuid) {
		return postMap.get(uuid);
	}

	public Collection<Post> getPosts() {
		return postMap.values();
	}

	public Collection<UUID> getUUIDs() {
		return postMap.keySet();
	}
}
