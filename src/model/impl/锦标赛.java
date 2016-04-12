package model.impl;

import java.io.IOException;
import java.util.Random;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 锦标赛 extends 乐斗项目 {

	public 锦标赛(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	// 随机赞助
	public void 赞助() {
		if (!mainDoc.text().contains("锦标赛")) {
			message.put("赞助情况", "未开启锦标赛功能！");
			return;
		}
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "锦标赛");
			if (doc.text().contains("免费赞助：0")) {
				message.put("赞助情况",
						DocUtil.substring(doc.text(), "=本届已赞助=", 0, "积分排行"));
				return;
			}
			if (doc.text().contains("领取奖励")) {
				message.put("领奖情况",
						DocUtil.substring(doc.text(), "【百米锦标赛】", 7, "领取奖励"));
				doc = DocUtil.clickTextUrl(doc, "领取奖励");
			}
			int size = 5;
			Random random = new Random();
			while(true) {
				doc = DocUtil.clickTextUrl(doc, "赞助", random.nextInt(size));
				if(doc.text().contains("没有足够的赞助券")) break;
			}
			message.put("赞助情况",
					DocUtil.substring(doc.text(), "=本届已赞助=", 0, "积分排行"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
