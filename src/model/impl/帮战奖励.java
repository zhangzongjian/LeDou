package model.impl;

import java.io.IOException;
import java.util.Map;

import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 帮战奖励 extends 乐斗项目 {

	public 帮战奖励(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 领奖() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "帮战");
			doc = DocUtil.clickTextUrl(userKey, doc, "领取奖励");
			String[] result = DocUtil.substring(doc.text(), "报名状态", 0, "查看上届各赛区").split(" ");
			message.put("报名情况", result[0]);
			message.put("领奖情况", "领奖情况："+result[1]);
			
			doc = DocUtil.clickTextUrl(userKey, doc, "激活祝福");
			doc = DocUtil.clickTextUrl(userKey, doc, "激活");
			message.put("祝福情况", "激活祝福："+DocUtil.substring(doc.text(), "报名状态", 0, "查看上届各赛区").split(" ")[1]);
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
