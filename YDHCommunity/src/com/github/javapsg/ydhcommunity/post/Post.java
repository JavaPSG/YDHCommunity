package com.github.javapsg.ydhcommunity.post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.github.javapsg.ydhcommunity.user.User;

public class Post implements Comparable<Post>{

	private UUID uuid;

	private String title;

	private String writer;

	private String content;

	// 값은 이메일 문자열
	private Set<String> recommanders = Collections.synchronizedSet(new HashSet());

	private Calendar writeTime;

	public Post(String writer, String title, String content, Collection<String> recommanders, String writeTime,
			UUID uuid) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.recommanders.clear();
		this.recommanders.addAll(recommanders);
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(writeTime));
		} catch (ParseException e) {
			calendar.setTime(new Date());
		}
		this.uuid = uuid;
	}

	// insertPost 전의 쓰이는 생성자
	public Post(String writer, String title, String content, Collection<String> recommanders, String writeTime) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.recommanders.clear();
		this.recommanders.addAll(recommanders);
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(writeTime));
		} catch (ParseException e) {
			calendar.setTime(new Date());
		}
	}

	public void addRec(String email) {
		recommanders.add(email);
	}

	public void removeRec(String email) {
		recommanders.remove(email);
	}

	public Set<String> getRec() {
		return recommanders;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(Calendar writeTime) {
		this.writeTime = writeTime;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	@Override
    public int compareTo(Post post) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		int v1 = Integer.valueOf(format.format(post.getWriteTime().getTime()));
		int v2 = Integer.valueOf(format.format(writeTime.getTime()));
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
