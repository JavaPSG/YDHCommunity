package com.github.javapsg.theme;

public class Theme {

	private String name;
	private String icon;
	private String id;

	public Theme(String id, String icon, String name) {
		this.id = id;
		this.icon = icon;
		this.name = name;
	}

	public String getThemeClass() {
		if (!this.id.split("-")[1].equals("white")) {
			return "white-theme";
		}
		return "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
