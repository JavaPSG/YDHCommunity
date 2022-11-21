package com.github.javapsg.post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Post {

	private String title;

	private String writer;

	private String content;

	// 값은 이메일 문자열
	private Set<String> recommanders = Collections.synchronizedSet(new HashSet());

	private Calendar writeTime;

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

}
