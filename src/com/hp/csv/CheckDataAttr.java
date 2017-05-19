package com.hp.csv;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 检测csv对应的vo类属性是否发生变动
 * */
public class CheckDataAttr {
	
	public static void checkChanges(String xmlFilePath, String csvPackagePath) throws Exception{
		AnalysisManager manager = new AnalysisManager();
		File xmlFile = new File(xmlFilePath);
		ArrayList<Class<?>> cls = manager.getAllClass(xmlFile);
		for(Class<?> c : cls){
			checkChanges(c, xmlFilePath, csvPackagePath);
		}
	}
	
	private static void checkChanges(Class<?> voCls, String xmlFilePath, String csvPackagePath) throws Exception{
		File xmlFile = new File(xmlFilePath);
		AnalysisManager manager = new AnalysisManager();
		List<CsvTableVo> vos = manager.analysisXml(xmlFile);
		for(CsvTableVo vo : vos){
			if(voCls.getName().equals(vo.getTargetPackage() + "." + vo.getModelName())){
				List<Attr> attrs = manager.getCsvAttrs(new File(Utils.getCsvPath(vo.getFileName(), csvPackagePath)));
				for(Method method : voCls.getMethods()){
					try{
						Object.class.getMethod(method.getName(), null);
						continue;
					} catch (Exception e) {
						if(method.getName().startsWith("get")){//从vo类的get方法获取对应属性的名称与类型
							String attrName = method.getName().substring(3);
							String attrCls = method.getReturnType().getSimpleName();
							Attr attr = getAttr(attrName, attrs);
							if(attr == null){
								System.out.println("className : " + voCls.getName() + " attrName : " + attrName + " attrClass : " + attrCls + " is not exsit.");
							} else {
								if(!attrCls.toLowerCase().equals(attr.getType().toLowerCase())){
									System.out.println("className : " + voCls.getName() + " attrName : " + attrName + " attrClass : " + attrCls + " is not match with csvFile : " + vo.getFileName());
								}
							}
						}
					}
				}
				break;
			}
		}
	}
	
	private static Attr getAttr(String attrName, List<Attr> attrs){
		for(Attr attr : attrs){
			if(attr.getName().toLowerCase().equals(attrName.toLowerCase())){
				return attr;
			}
		}
		return null;
	}
	
}
