package com.hp.csv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Utils {
	protected static String parseType(String type) {
		String result = null;
		switch (type.toLowerCase()) {
		case "string":
			result = "String";
			break;
		default:
			result = type;
			break;
		}
		return result;
	}

	/**
	 * 检测csv对应的数据VO类是否存在
	 * */
	protected static boolean checkVoExits(CsvTableVo vo) {
		try {
			String clsName = Class.forName(
					vo.getTargetPackage() + "." + vo.getModelName()).getName();
			return clsName != null && !"".equals(clsName);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 第一个字母变大写
	 * */
	protected static String changeAttrName(String attrName) {
		char firstLetter = attrName.charAt(0);
		char changedLetter = (char) (firstLetter >= 'A' && firstLetter <= 'Z' ? firstLetter
				: firstLetter - 32);
		return attrName.replaceFirst(Character.toString(firstLetter),
				Character.toString(changedLetter));
	}
	
	/**
	 * 去除非法字符
	 * */
	protected static String filtForbiddenChar(String s){
		Pattern pattern = Pattern.compile("[^0-9A-Za-z]");
		Matcher matcher = pattern.matcher(s);
		return matcher.replaceAll("");
	}

	protected static Object getAttrValue(String value, Attr attr) {
		Object result = null;
		switch (parseType(attr.getType())) {
		case "int":
			result = Integer.parseInt(value);
			break;
		case "boolean":
			result = Boolean.parseBoolean(value);
			break;
		case "short":
			result = Short.parseShort(value);
			break;
		case "byte":
			result = Byte.parseByte(value);
			break;
		case "long":
			result = Long.parseLong(value);
			break;
		case "float":
			result = Float.parseFloat(value);
			break;
		case "double":
			result = Double.parseDouble(value);
			break;
		case "char":
			result = value.charAt(0);
			break;
		case "String":
			result = value;
			break;
		default:
			break;
		}
		return result;
	}

	protected static Class<?> getType(String type) throws Exception {
		Class<?> result = null;
		switch (type) {
		case "int":
			result = int.class;
			break;
		case "boolean":
			result = boolean.class;
			break;
		case "short":
			result = short.class;
			break;
		case "byte":
			result = byte.class;
			break;
		case "long":
			result = long.class;
			break;
		case "float":
			result = float.class;
			break;
		case "double":
			result = double.class;
			break;
		case "char":
			result = char.class;
			break;
		case "String":
			result = String.class;
			break;
		default:
			result = Class.forName(type);
			break;
		}
		return result;
	}

	protected static List<String> getList(String str, String splitStr) {
		List<String> list = new ArrayList<String>();
		if (str == null || "".equals(str)) {
			return list;
		}
		String[] arr = str.split(splitStr);
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		return list;
	}

	protected static String getCsvPath(String csvName, String projectPath) {
		StringBuilder builder = new StringBuilder();
		builder.append(projectPath).append(File.separator)
				.append("csv").append(File.separator).append(csvName);
		return builder.toString();
	}

	protected static String getVoPath(CsvTableVo vo, String projectPath) {
		StringBuilder builder = new StringBuilder();
		builder.append(projectPath).append(File.separator)
				.append("src").append(File.separator)
				.append(vo.getTargetPackage().replace(".", File.separator))
				.append(File.separator).append(vo.getModelName())
				.append(".java");
		return builder.toString();
	}
}
