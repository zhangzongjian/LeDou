package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 每日领奖 extends 乐斗项目 {

	public 每日领奖(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 领取() {
		try {
			Document doc = mainDoc;
			if (doc.text().contains("领取达人礼包")) {
				Document doc1 = DocUtil.clickTextUrl(userKey, doc, "领取达人礼包");
				if (doc1.text().contains("您还不是达人"))
					message.put("领取达人礼包", "达人礼包：您还不是达人");
				else
					message.put("领取达人礼包", "达人礼包：领取成功！");
			} else
				message.put("领取达人礼包", "达人礼包：已领取");
			if (doc.text().contains("领取每日奖励")) {
				DocUtil.clickTextUrl(userKey, doc, "领取每日奖励");
				message.put("领取每日奖励", "领取成功！");
			} else
				message.put("领取每日奖励", "每日奖励：已领取");
			if (doc.text().contains("领取每日双倍奖励")) {
				DocUtil.clickTextUrl(userKey, doc, "领取每日双倍奖励");
				message.put("领取每日奖励", "领取成功！");
			} else
				message.put("领取每日奖励", "每日奖励：已领取");
			if (doc.text().contains("领取徒弟经验")) {
				Document doc3 = DocUtil.clickTextUrl(userKey, doc, "领取徒弟经验");
				message.put(
						"领取徒弟经验",
						"徒弟经验："
								+ DocUtil.substring(doc3.text(), "其中", 2,
										"多跟徒弟"));
			} else
				message.put("领取徒弟经验", "徒弟经验：已领取");
			if (doc.text().contains("无字天书")) {
				Document doc4 = DocUtil.clickTextUrl(userKey, doc, "无字天书");
				if (doc4.text().contains("达人玩家才能领取")) {
					message.put("领取无字天书", "无字天书：达人玩家才能领取！");
				} else {
					message.put("领取无字天书", "无字天书：领取成功！");
				}
			} else
				message.put("领取无字天书", "无字天书：已领取");

			// 每日领取传功符
			Document 静脉 = DocUtil.clickTextUrl(userKey, doc, "经脉");
			if (静脉.text().contains("领取传功符")) {
				DocUtil.clickTextUrl(userKey, 静脉, "领取传功符");
				message.put("领取传功符", "每日传功符：领取成功！");
			} else {
				message.put("领取传功符", "每日传功符：已领取");
			}
			//每日宝箱
			Document 每日宝箱 = DocUtil.clickTextUrl(userKey, doc, "每日宝箱");
			int i = 0;
			while(true) {
				Document temp = DocUtil.clickTextUrl(userKey, 每日宝箱, "打开");
				if(temp != null && temp.text().contains("恭喜您")) {
					message.put("每日宝箱"+(i++), "每日宝箱："+DocUtil.substring(temp.text(), "恭喜您", 0, "铜质宝箱"));
				}
				else {
					break;
				}
				if(i>50) break;
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
