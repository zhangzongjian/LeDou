package model.impl;

import java.io.IOException;

import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.DocUtil;

public class 探险 extends 乐斗项目 {

	public 探险(Document mainURL) {
		super(mainURL);
	}

	public void doit() {
//		try {
//			Document doc = DocUtil.clickTextUrl(mainDoc, "今日活跃度");
//			if(doc.text().contains("12.[1/1]")) {
//				message.put("供奉情况", "今日已经供奉过了！");
//				return;
//			}
//			thing = "".equals(thing) ? "还魂丹":thing;
//			doc = DocUtil.clickTextUrl(doc, "供奉");
//			do {
//				String docString = doc.toString();
//				if(doc.toString().contains(thing)) {
//					DocUtil.clickTextUrl(Jsoup.parse(docString.substring(docString.indexOf(thing))), "供奉");
//					message.put("供奉情况", "供奉成功！");
//					return;
//				} else if (doc.text().contains("下页")) {
//					doc = DocUtil.clickTextUrl(doc, "下页");
//				}
//				if (!doc.text().contains("下页")) {
//					message.put("供奉情况", "供奉失败，背包里木有"+thing+"！");
//					return;
//				}
//			} while (doc.text().contains("下页"));
//		} catch (IOException e) {
//			message.put("消息", "连接超时，请重试！");
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public static void main(String[] args) {
		System.out.println("eee");
	}
}
