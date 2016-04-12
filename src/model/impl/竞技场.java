package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 竞技场 extends 乐斗项目 {

	public 竞技场(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 挑战() {
		try {
			if (!mainDoc.text().contains("竞技场")) {
				message.put("挑战情况", "未开启竞技场功能");
				return;
			}
			Document doc = DocUtil.clickTextUrl(mainDoc, "竞技场");
			if (doc.text().contains("赛季中")) {
				int num = 0;
				while (!doc.text().contains("免费挑战次数已用完")) {
					if(doc.text().contains("开始挑战")) {
						doc = DocUtil.clickTextUrl(doc, "开始挑战");
					} else {
						doc = DocUtil.clickTextUrl(doc, "免费挑战");
					}
					message.put("挑战情况" + num,
							DocUtil.substring(doc.text(), "竞技点商店", 5, "赛季状态"));
					num++;
				}
				if (DocUtil.isHref(doc, "领取奖励")) {
					DocUtil.clickTextUrl(doc, "领取奖励");
					message.put("领奖情况", "已领取奖励！");
				}
			} else {
				message.put("挑战情况", "非赛季时间!");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
