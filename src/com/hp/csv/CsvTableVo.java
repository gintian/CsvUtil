package com.hp.csv;
import java.util.ArrayList;
import java.util.List;


class CsvTableVo {
	private String fileName;//文件名
	private String targetPackage;//生成VO类目录
	private String modelName;//生成VO类名
	private List<Attr> attrs = new ArrayList<>();//对应的属性
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTargetPackage() {
		return targetPackage;
	}
	public void setTargetPackage(String targetPackage) {
		this.targetPackage = targetPackage;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public List<Attr> getAttrs() {
		return this.attrs;
	}
	/**
	 * @param clsName 类型名
	 * @param attrName 变量名称
	 * */
	public void addAttr(String clsName, String attrName){
		this.attrs.add(new Attr(attrName, clsName));
	}
	public void addAttrs(List<Attr> attrs) {
		this.attrs.addAll(attrs);
	}
}
