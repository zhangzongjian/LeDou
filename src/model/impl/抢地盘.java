package model.impl;

import java.io.IOException;

import org.jsoup.nodes.Document;

import util.DocUtil;

import java.util.Map;import model.乐斗项目;

public class 抢地盘 extends 乐斗项目 {

	public 抢地盘(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void doit(){
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "抢地盘");
			Document 抢地盘记录 = DocUtil.clickTextUrl(userKey, doc, "抢地盘记录");
			if(抢地盘记录.text().contains("【今天")) {
				message.put("抢地盘情况", "挑战失败！每天仅抢占一次！");
				return;
			}
			doc = DocUtil.clickTextUrl(userKey, doc, "无限制区");
			if (DocUtil.clickTextUrl(userKey, doc, "攻占").text()
					.contains("您的挑战书数量不足")) {
				message.put("抢地盘情况", "您的挑战书数量不足");
				return;
			}
			message.put("抢地盘情况", "成功抢了1次地盘！");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
