package com.github.javapsg.ydhcommunity.post;

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

import com.github.javapsg.ydhcommunity.user.User;
import com.github.javapsg.ydhcommunity.user.UserDataManager;
import com.github.javapsg.ydhcommunity.utils.JDBCUtil;

public class PostDataManager {

	// 모든 게시물 데이터 맵
	private final Map<UUID, Post> postMap = Collections.synchronizedMap(new HashMap<>());
	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	// 싱글톤: 내부 클래스 인스턴스
	private static class InnerInstanceClazz {
		private static final PostDataManager instance = new PostDataManager();
	}

	/***
	 * 싱글톤 패턴을 이용한 여러 위치에서 같은 인스턴스를 사용하기 위한 메소드
	 * 
	 * @return 아 클래스의 싱글톤 인스턴스
	 */
	public static PostDataManager getInstance() {
		return InnerInstanceClazz.instance;
	}

	/***
	 * 중복되지 않은 UUID 발급
	 * 
	 * @return 랜덤 UUID
	 */
	private UUID createUUID() {
		UUID uuid = UUID.randomUUID();
		if (postMap.containsKey(uuid)) {
			return createUUID();
		} else {
			return uuid;
		}
	}

	/***
	 * 게시물 데이터가 비어있는 경우에 DB로부터 데이터를 받아와서 저장함
	 */
	public void init() {
		if (!postMap.isEmpty()) {
			return;
		}
		postMap.clear();
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

	private Collection<String> getRecs(String str) {
		if (str == null || (str != null && str.isEmpty())) {
			return Arrays.asList();
		}
		return Arrays.asList(str.split(":"));
	}

	
	/***
	 * DB와 맵에 게시물 데이터를 저장
	 * 
	 * @param post 게시물
	 * @return SQL 문을 실행한 결과
	 */
	public UUID insertPost(Post post) {
		UUID uuid = createUUID();
		insertPost(post, uuid);
		return uuid;
	}

	/***
	 * DB와 맵에 게시물 데이터를 저장
	 * 
	 * @param post 게시물
	 * @return SQL 문을 실행한 결과
	 */
	private int insertPost(Post post, UUID uuid) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
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

	/***
	 * DB와 맵에 기존 게시물 데이터를 갱신
	 * 
	 * @param post 게시물
	 * @return SQL 문을 실행한 결과
	 */
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
