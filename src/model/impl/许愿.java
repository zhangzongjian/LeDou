package model.impl;

import java.io.IOException;

import model.乐斗项目;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 许愿 extends 乐斗项目 {

	public 许愿(Document mainURL) {
		super(mainURL);
	}

	public void xuYuan() {
		try {
			Elements elements = mainDoc.getElementsByAttributeValueMatching(
					"href", "wish");
			Document doc = DocUtil.clickURL(elements.get(0).attr("href"));
			if (doc.text().contains("明天领取")) {
				message.put("许愿情况", "今日已许过愿了！");
				return;
			}
			if (doc.text().contains("领取许愿奖励")) {
				doc = DocUtil.clickTextUrl(doc, "领取许愿奖励");
				message.put("每日许愿奖励",
						DocUtil.substring(doc.text(), "【每日许愿】", 6, "。"));
			}
			if (doc.text().contains("首胜后才能许愿")) {
				message.put("许愿情况", "首胜后才能许愿！");
				return;
			}
			DocUtil.clickTextUrl(
					DocUtil.clickURL(doc
							.getElementsByAttributeValueMatching("href",
									"sub=4").get(0).attr("href")), "向月敏上香许愿");
			int num = Integer.parseInt(doc.text().charAt(
					doc.text().indexOf("连续许愿3天") + 7)
					+ "");
			if (num == 3) {
				DocUtil.clickTextUrl(doc, "领取");
				message.put("连续3天许愿奖励", "获得魂珠碎片宝箱*1");
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
