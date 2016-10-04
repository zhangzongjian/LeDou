package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.DocUtil;

public class 供奉 extends 乐斗项目 {

	public static String thing = "还魂丹"; // 供奉物品

	public 供奉(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	static int count = 0;
	
	public void 一键供奉() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "今日活跃度");
			if (doc.text().contains("12.[1/1]")) {
				message.put("供奉情况", "今日已经供奉过了！");
				return;
			}
			thing = "".equals(thing) ? "还魂丹" : thing;
			doc = DocUtil.clickTextUrl(userKey, doc, "供奉");
			do {
				String docString = doc.toString();
				if (doc.toString().contains(thing)) {
					DocUtil.clickTextUrl(userKey, Jsoup.parse(docString
							.substring(docString.indexOf(thing))), "供奉");
					message.put("供奉情况"+count, "供奉成功！");
					return;
				} else if (doc.text().contains("下页")) {
					doc = DocUtil.clickTextUrl(userKey, doc, "下页");
				}
				if (!doc.text().contains("下页")) {
					String[] 候选物品  = {"神来拳套","经验药水","孙子兵法","大力丸"};
					message.put("供奉情况"+count, "背包里没有(" + thing + ")，自动选择候选供奉物品("+候选物品[count]+")！");
					thing = 候选物品[count++];
					System.out.println(thing);
					if(count > 候选物品.length) {
						message.put("供奉情况"+count, "供奉失败！");
						return;
					}
					一键供奉();
				}
			} while (doc.text().contains("下页"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
