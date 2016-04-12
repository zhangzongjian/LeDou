package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import util.DocUtil;

public class 掠夺 extends 乐斗项目 {

	public 掠夺(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 领奖() {
		if (!mainDoc.text().contains("掠夺")) {
			message.put("领奖情况", "未开启抢粮功能！");
			return;
		}
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "掠夺");
			if(super.day == 2) {
				Document doc1 = DocUtil.clickTextUrl(doc, "掠夺");
				if(!DocUtil.isHref(doc1, "领奖")) {
					doc1 = DocUtil.clickTextUrl(doc1, "掠夺", -1);
				}
				doc1 = DocUtil.clickTextUrl(doc1, "领奖");
				message.put("挑战奖励", "挑战奖励："+DocUtil.substring(doc1.text(), "返回", 2, "生命"));
			}
			Element element = doc.getElementsContainingOwnText("领取胜负奖励").get(0);
			if (element.hasAttr("href")) {
				doc = DocUtil.clickURL(element.attr("href"));
				message.put("领奖情况",
						"胜负奖励："+DocUtil.substring(doc.text(), "规则", 2, "领取胜负奖励"));
			} else {
				message.put("领奖情况", "胜负奖励：已经领取过奖励了！");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
