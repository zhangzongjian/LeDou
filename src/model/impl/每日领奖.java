package model.impl;

import java.io.IOException;

import java.util.List;
import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 每日领奖 extends 乐斗项目 {

	public 每日领奖(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 领取() {
		try {
			Document doc = mainDoc;
			
			//dailygift
			Document 每日奖励 = DocUtil.clickURL(userKey, mainDoc.getElementsByAttributeValueMatching("href", "cmd=dailygift").attr("href"));
			List<Element> list = DocUtil.getTextUrlElementList(每日奖励, "领取");
			for(Element e : list) {
				DocUtil.clickURL(userKey, e.attr("href"));
			}
			message.put("领取每日奖励", "每日奖励：已领取");
			
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
