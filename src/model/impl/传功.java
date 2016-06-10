package model.impl;

import java.io.IOException;
import java.util.Map;

import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 传功 extends 乐斗项目 {

	public 传功(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void doit() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "今日活跃度");
			if (doc.text().contains("15.[1/1]")) {
				message.put("传功情况", "今日已完成传功12次！");
				return;
			}
			doc = DocUtil.clickTextUrl(userKey, doc, "传功12次");
			if(doc.text().contains("关闭")) //关闭二次确认
				DocUtil.clickTextUrl(userKey, doc, "关闭");
			for(int i=0; i<12; i++) {
				doc = DocUtil.clickTextUrl(userKey, doc, "传功");
				if(doc.text().contains("传功符不足！")) {
					message.put("传功情况", "传功符不足！");
					return;
				}
				if(doc.text().contains("位置已满")) {
					DocUtil.clickTextUrl(userKey, DocUtil.clickTextUrl(userKey, DocUtil.clickTextUrl(userKey, doc, "一键拾取"),"丹田"),"一键合成");
					doc = DocUtil.clickTextUrl(userKey, doc, "传功");
				}
			}
			message.put("传功情况", "成功传功了12次！");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
