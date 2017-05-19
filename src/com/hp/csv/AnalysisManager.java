package com.hp.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;



class AnalysisManager{
	
	public List<CsvTableVo> analysisXml(File xmlFile) throws Exception{
		if(!xmlFile.exists()){
			return Collections.emptyList();
		}
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		AnalysisXmlHandler handler = new AnalysisXmlHandler();
		try(	FileInputStream fis = new FileInputStream(xmlFile);){
			parser.parse(fis, handler);
		}
		return handler.getTables();
	}
	
	public List<Attr> getCsvAttrs(File csvFile) throws Exception{
		List<Attr> attrs = new ArrayList<>();
		if(csvFile.exists()){
			try(	BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "GBK"));){
				String attrNames = reader.readLine();
				String typeNames = reader.readLine();
				if(attrNames != null && !"".equals(attrNames) && typeNames != null && !"".equals(typeNames)){
					List<String> attrlist = Utils.getList(attrNames, ",");
					List<String> typelist = Utils.getList(typeNames, ",");
					int len = Math.min(attrlist.size(), typelist.size());
					for(int i = 0; i < len; ++ i){
						attrs.add(new Attr(attrlist.get(i), typelist.get(i)));
					}
				}
			}
		}
		return attrs;
	}
	
	/**
	 * 获取xml配置文件中申明的vo封装类
	 * */
	public ArrayList<Class<?>> getAllClass(File xmlFile) throws Exception{
		ArrayList<Class<?>> result = new ArrayList<>();
		List<CsvTableVo> vos = analysisXml(xmlFile);
		for(CsvTableVo vo : vos){
			try {
				Class<?> cls = Class.forName(vo.getTargetPackage() + "." + vo.getModelName());
				result.add(cls);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return result;
	}
	
	public <T> List<T> getCsvContent(File csvFile, Class<T> cls) throws Exception{
		List<T> result = new ArrayList<>();
		if(csvFile.exists()){
			try(	BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "GBK"));){
				List<Attr> attrs = getCsvAttrs(csvFile);
				String content = null;
				int cols = 0;//已读到第几行数据
				MethodHandles.Lookup lookup = MethodHandles.lookup();
				while((content = reader.readLine()) != null){
					++ cols;
					if(cols >= 4){
						List<String> contentlist = Utils.getList(content, ",");
						T t = cls.newInstance();
						for(int i = 0; i < attrs.size(); ++ i){
							Attr attr = attrs.get(i);
							Class<?> typeCls = Utils.getType(Utils.parseType(attr.getType()));
							try {
								Method method = cls.getDeclaredMethod("set" + Utils.changeAttrName(attr.getName()), typeCls);
								MethodHandle handle = lookup.unreflect(method);
								handle.invoke(t, Utils.getAttrValue(contentlist.get(i), attr));
							} catch (Throwable e) {
								continue;
							}
						}
						result.add(t);
					}
				}
			}
		}
		return result;
	}
	
	
}
