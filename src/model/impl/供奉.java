package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.DocUtil;

public class 供奉 extends 乐斗项目 {

	public static String thing = "还魂丹1"; // 供奉物品+次数

	public 供奉(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	static int count = 0;
	
	public void 一键供奉() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "今日活跃度");
//			if (doc.text().contains("12.[1/1]")) {
//				message.put("供奉情况", "今日已经供奉过了！");
//				return;
//			}
			thing = "".equals(thing) ? "还魂丹1" : thing;
			//拆分物品与次数
			Object[] thingAndNum = getThingAndNum(thing);
			thing = (String) thingAndNum[0];
			int num = (int) thingAndNum[1];
			doc = DocUtil.clickTextUrl(userKey, doc, "供奉");
			do {
				String docString = doc.toString();
				if (doc.toString().contains(thing)) {
					Document 供奉结果;
					int i;
					for(i = 0; i< num; i++) {
						供奉结果 = DocUtil.clickTextUrl(userKey, Jsoup.parse(docString
								.substring(docString.indexOf(thing))), "供奉");
						if(供奉结果.text().contains("每天最多供奉5次")) break;
					}
					message.put("供奉情况"+count, "供奉成功"+i+"次！");
					return;
				} else if (doc.text().contains("下页")) {
					doc = DocUtil.clickTextUrl(userKey, doc, "下页");
				}
				//背包没有该供奉物品，选择候选物品供奉。
//				if (!doc.text().contains("下页")) {
//					String[] 候选物品  = {"神来拳套","经验药水", "还魂丹"};
//					message.put("供奉情况"+count, "背包里没有(" + thing + ")，自动选择候选供奉物品("+候选物品[count]+")！");
//					thing = 候选物品[count++];
//					System.out.println(thing);
//					if(count > 候选物品.length) {
//						message.put("供奉情况"+count, "供奉失败！");
//						return;
//					}
//					一键供奉();
//				}
				if (!doc.text().contains("下页")) {
					message.put("供奉情况"+count, "供奉失败！");
					return;
				}
			} while (doc.text().contains("下页"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//拆分物品和数量
	public Object[] getThingAndNum(String thing) {
		int i;
		Object[] thingAndNum = new Object[2];
		for(i=0; i<thing.length(); i++) {
			int tmp = (int)thing.charAt(i);
			// '0' = 48, '9' = 57
			if(tmp >= 48 && tmp <= 57) break;
		}
		thingAndNum[0] = thing.substring(0, i);
		try {
			thingAndNum[1] = Integer.valueOf(thing.substring(i));
		}catch(NumberFormatException e) {
			thingAndNum[1] = 1;
		}
		return thingAndNum;
	}
}
