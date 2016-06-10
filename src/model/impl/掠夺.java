package model.impl;

import java.io.IOException;
import java.util.Map;

import model.乐斗项目;

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
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "掠夺");
			if(super.day == 2) {
				Document doc1 = DocUtil.clickTextUrl(userKey, doc, "掠夺");
				if(!DocUtil.isHref(doc1, "领奖")) {
					doc1 = DocUtil.clickTextUrl(userKey, doc1, "掠夺", -1);
				}
				doc1 = DocUtil.clickTextUrl(userKey, doc1, "领奖");
				message.put("挑战奖励", "挑战奖励："+DocUtil.substring(doc1.text(), "返回", 2, "生命"));
			}
			Element element = doc.getElementsContainingOwnText("领取胜负奖励").get(0);
			if (element.hasAttr("href")) {
				doc = DocUtil.clickURL(userKey, element.attr("href"));
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
	
	
//	public void 领奖() {
//		if (!mainDoc.text().contains("掠夺")) {
//			message.put("领奖情况", "未开启抢粮功能！");
//			return;
//		}
//		try {
//			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "掠夺");
//			if(super.day == 2) {
//				Document doc1 = DocUtil.clickTextUrl(userKey, doc, "掠夺");
//				int i = 0;
//				int index = 0;
//				H:
//				while (index++ <= 12) {
//					while (!doc1.text().contains("已占领 " + (index + 1))) {
//						Elements e = doc1.getElementsByAttributeValueMatching(
//								"href", "subtype=4&gra_id=[0123]+" + index);
//						if(e.attr("href") == "") continue H;
//						doc1 = DocUtil.clickURL(userKey, e.attr("href"));
//						if (doc1.text().contains("战斗冷却中"))
//							continue;
//						message.put("挑战奖励结果" + i++,
//								DocUtil.substring(doc1.text(), "返回", 2, "生命"));
//
//						System.out.println(DocUtil.substring(doc1.text(), "返回",
//								2, "生命"));
//
//						if (doc1.text().contains("你已经没有足够的复活次数！ "))
//							return;
//					}
//					if (doc1.text().contains("已占领 " + (index + 1))) {
//						message.put("挑战奖励结果" + i++, "该粮仓已占领！");
//						continue;
//					}
//				}
//			}
//		} catch (IOException e) {
//			message.put("消息", "连接超时，请重试！");
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
