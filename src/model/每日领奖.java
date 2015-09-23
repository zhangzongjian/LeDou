package model;

import java.io.IOException;

import org.jsoup.nodes.Document;

import util.DocUtil;
import util.乐斗项目;

public class 每日领奖 extends 乐斗项目 {

	public 每日领奖(Document mainURL) {
		super(mainURL);
	}

	public void 领取() {
		try {
			Document doc = mainDoc;
			if (doc.text().contains("领取达人礼包")) {
				Document doc1 = DocUtil.clickTextUrl(doc, "领取达人礼包");
				if (doc1.text().contains("您还不是达人"))
					message.put("领取达人礼包", "达人礼包：您还不是达人");
				else
					message.put("领取达人礼包", "领取成功！");
			} else
				message.put("领取达人礼包", "达人礼包：已领取");
			if (doc.text().contains("领取每日奖励")) {
				DocUtil.clickTextUrl(doc, "领取每日奖励");
				message.put("领取每日奖励", "领取成功！");
			} else
				message.put("领取每日奖励", "每日奖励：已领取");
			if (doc.text().contains("领取每日双倍奖励")) {
				DocUtil.clickTextUrl(doc, "领取每日双倍奖励");
				message.put("领取每日奖励", "领取成功！");
			} else
				message.put("领取每日奖励", "每日奖励：已领取");
			if (doc.text().contains("领取徒弟经验")) {
				Document doc3 = DocUtil.clickTextUrl(doc, "领取徒弟经验");
				message.put(
						"领取徒弟经验",
						"徒弟经验："
								+ DocUtil.substring(doc3.text(), "其中", 2,
										"多跟徒弟"));
			} else
				message.put("领取徒弟经验", "徒弟经验：已领取");
			if (doc.text().contains("无字天书")) {
				Document doc4 = DocUtil.clickTextUrl(doc, "无字天书");
				if (doc4.text().contains("达人玩家才能领取")) {
					message.put("领取无字天书", "无字天书：达人玩家才能领取！");
				} else {
					message.put("领取无字天书", "无字天书：领取成功！");
				}
			} else
				message.put("领取无字天书", "无字天书：已领取");

			// 每日领取传功符
			doc = DocUtil.clickTextUrl(doc, "经脉");
			if (doc.text().contains("领取传功符")) {
				DocUtil.clickTextUrl(doc, "领取传功符");
				message.put("领取传功符", "每日传功符：领取成功！");
			} else
				message.put("领取传功符", "每日传功符：已领取");
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}
}
