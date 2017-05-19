package com.hp.csv;

import java.io.File;
import java.util.Collections;
import java.util.List;



public class GameDataReader{
	
	/**
	 * for web
	 * */
	public static <T> List<T> genBean(Class<T> cls) throws Exception{
		String separator = null;
		String osInfo = System.getProperties().getProperty("os.name");
		if (osInfo.contains("Windows")) {
			separator = "\\";
		}
		if (osInfo.contains("Linux")) {
			separator = "/";
		}
		String path = GameDataReader.class.getResource("/").getPath();
		File file = new File(path);
		path = file.getParentFile().getParent();
		String packagePath = path;
		String xmlFilePath = path + separator + "csvConfig" + separator + "file.xml";
		return genBean(cls, xmlFilePath, packagePath);
	} 
	
	public static <T> List<T> genBean(Class<T> cls, String xmlFilePath, String csvPackagePath) throws Exception{
		AnalysisManager manager = new AnalysisManager();
		File xmlFile = new File(xmlFilePath);
		List<CsvTableVo> list = manager.analysisXml(xmlFile);
		for(CsvTableVo vo : list){
			if(vo.getModelName().equals(cls.getSimpleName())){
				String csvPath = Utils.getCsvPath(vo.getFileName(), csvPackagePath);
				File csvFile = new File(csvPath);
				List<T> result = manager.getCsvContent(csvFile, cls);
				return result;
			}
		}
		return Collections.emptyList();
	}
	
}
