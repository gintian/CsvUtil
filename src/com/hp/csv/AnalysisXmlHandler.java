package com.hp.csv;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



/**
 * 解析配置文件
 * */
class AnalysisXmlHandler extends DefaultHandler{
	private List<CsvTableVo> tables;

	@Override
	public void startDocument() throws SAXException {
		tables = new ArrayList<>();
		super.startDocument();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if("javaModel".equals(qName)){
			CsvTableVo table = new CsvTableVo();
			table.setFileName(attributes.getValue("csvfile"));
			table.setModelName(attributes.getValue("modelName"));
			table.setTargetPackage(attributes.getValue("targetPackage"));
			tables.add(table);
		}
	}

	public List<CsvTableVo> getTables() {
		return tables;
	}
}
