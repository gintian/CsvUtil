package com.vo;

public class CharmData {
	private int id;
	private String name;
	private int type;
	private int level;
	private String attr;

	public int getId(){
		return this.id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public int getType(){
		return this.type;
	}
	public void setType(int type){
		this.type = type;
	}
	public int getLevel(){
		return this.level;
	}
	public void setLevel(int level){
		this.level = level;
	}
	public String getAttr(){
		return this.attr;
	}
	public void setAttr(String attr){
		this.attr = attr;
	}
}