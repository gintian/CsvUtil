package com.hp.csv;

class Attr {
	private String name;// 变量名 
	private String type;// 类型
	public Attr() {
		// TODO Auto-generated constructor stub
	}
	public Attr(String name, String type) {
		this.name = Utils.filtForbiddenChar(name);
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
