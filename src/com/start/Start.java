package com.start;

import java.util.List;

import com.hp.csv.CheckDataAttr;
import com.hp.csv.GameDataReader;
import com.vo.CardData;
import com.vo.ItemData;


public class Start {
	public static void main(String[] args) throws Exception{
		System.out.println(System.getProperty("user.home"));
		List<CardData> list = GameDataReader.genBean(CardData.class, "C:\\cocos2d-x\\java\\csvUtil\\src\\dbConfig\\file.xml", "C:\\cocos2d-x\\java\\csvUtil");
		for(CardData data : list){
			if(data.getName() == null){
				System.out.println(data.getId());
			}
		}
		System.out.println(list.size());
		List<ItemData> list2 = GameDataReader.genBean(ItemData.class, "C:\\cocos2d-x\\java\\csvUtil\\src\\dbConfig\\file.xml", "C:\\cocos2d-x\\java\\csvUtil");
		System.out.println(list2.size());
		
		CheckDataAttr.checkChanges("C:\\cocos2d-x\\java\\csvUtil\\src\\dbConfig\\file.xml", "C:\\cocos2d-x\\java\\csvUtil");
	}
}
