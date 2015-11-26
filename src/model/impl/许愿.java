package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 许愿 extends 乐斗项目 {

	public 许愿(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void xuYuan() {
		try {
			Elements elements = mainDoc.getElementsByAttributeValueMatching(
					"href", "wish");
			Document doc = DocUtil.clickURL(userKey, elements.get(0).attr("href"));
			if (doc.text().contains("明天领取")) {
				message.put("许愿情况", "今日已许过愿了！");
				return;
			}
			if (doc.text().contains("领取许愿奖励")) {
				doc = DocUtil.clickTextUrl(userKey, doc, "领取许愿奖励");
				message.put("每日许愿奖励",
						DocUtil.substring(doc.text(), "【每日许愿】", 6, "。"));
			}
			if (doc.text().contains("首胜后才能许愿")) {
				message.put("许愿情况", "首胜后才能许愿！");
				return;
			}
			doc = DocUtil.clickTextUrl(userKey, 
					DocUtil.clickURL(userKey, doc
							.getElementsByAttributeValueMatching("href",
									"sub=4").get(0).attr("href")), "向月敏上香许愿");
			if(doc.text().contains("（3/3）")) {
				DocUtil.clickTextUrl(userKey, doc, "领取");
				message.put("连续3天许愿奖励", "连续3天许愿，获得魂珠碎片宝箱*1");
			}
			message.put("许愿情况", "许愿成功！");
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
