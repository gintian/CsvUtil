package com.hp.csv;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;



public class GenJavaFile {
	/**
	 * @param xmlFilePath 配置文件file.xml路径
	 * @param projectPath csv所在项目路径 csv文件夹必须与src同级
	 * */
	public void gen(String xmlFilePath, String projectPath) throws Exception{
		File xmlFile = new File(xmlFilePath);
		File project = new File(projectPath);
		if(xmlFile.exists() && xmlFile.isFile() && project.exists() && project.isDirectory()){
			//解析xml
			AnalysisManager manager = new AnalysisManager();
			List<CsvTableVo> tables = manager.analysisXml(xmlFile);
			for(CsvTableVo vo : tables){
				if(!Utils.checkVoExits(vo)){
					String voPath = Utils.getVoPath(vo, project.getAbsolutePath());
					File voFile = new File(voPath);
					if(!voFile.exists()){
						String csvPath = Utils.getCsvPath(vo.getFileName(), project.getAbsolutePath());
						File csvFile = new File(csvPath);
						List<Attr> attrs = manager.getCsvAttrs(csvFile);
						vo.getAttrs().clear();
						vo.addAttrs(attrs);
						genFile(vo, voFile);
					}
				}
			}
		}
	}
	
	/**
	 * 创建文件
	 * */
	private void genFile(CsvTableVo vo, File voFile) {
		try {
			voFile.createNewFile();
			try(	FileOutputStream fos = new FileOutputStream(voFile)){
				String packageInfo = javaFileContent(vo);
				fos.write(packageInfo.getBytes("UTF-8"));
				fos.flush();
			}
			System.out.println("生成文件" + voFile.getName() + "成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("生成文件" + voFile.getName() + "失败");
		}
	}
	
	/**
	 * 生成类的内容
	 * */
	private String javaFileContent(CsvTableVo vo){
		StringBuilder result = new StringBuilder();
		//package名
		String packageInfo = "package " + vo.getTargetPackage() + ";\n\n";
		result.append(packageInfo);
		//输入类名
		String clsBeginInfo = "public class " + vo.getModelName() + " {\n";
		result.append(clsBeginInfo);
		//输入属性名
		for(int i = 0; i < vo.getAttrs().size(); ++ i){
			Attr attr = vo.getAttrs().get(i);
			String attrInfo = "	private " + Utils.parseType(attr.getType()) + " " + attr.getName() + ";\n";
			result.append(attrInfo);
		}
		result.append("\n");
		//set get方法
		for(int i = 0; i < vo.getAttrs().size(); ++ i){
			Attr attr = vo.getAttrs().get(i);
			String attrGetMethod = "	public " + Utils.parseType(attr.getType()) + ("boolean".equals(attr.getType()) ? " is" : " get") + Utils.changeAttrName(attr.getName()) + "(){\n"
					+ "		return this." + attr.getName() + ";\n"
					+ "	}\n";
			result.append(attrGetMethod);
			String attrSetMethod = "	public void set" + Utils.changeAttrName(attr.getName()) + "(" + Utils.parseType(attr.getType()) + " " + attr.getName() + "){\n"
					+ "		this." + attr.getName() + " = " + attr.getName() + ";\n"
					+ "	}\n";
			result.append(attrSetMethod);
		}
		//输入类结尾名
		String clsEndInfo = "}";
		result.append(clsEndInfo);
		return result.toString();
	}
	
	
	
}
