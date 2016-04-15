package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 乐斗boss extends 乐斗项目 {

	public 乐斗boss(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 一键挑战() {
		try {
			// 好友boss
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "好友");
			// 若当前页面不是首页，则跳到首页
			if (doc.text().contains("首页 末页")) {
				doc = DocUtil.clickTextUrl(userKey, doc, "首页");
			}
			// 挑战前先吞贡献药水
			// 贡献药水效果剩余次数
			int num = Integer.parseInt(DocUtil.substring(doc.text(), "贡献药水 速购 使用 剩", "贡献药水 速购 使用 剩".length(), "次 大力丸 速购 使用"));
			double drinkNum = Math.ceil((20 - num) / 5.0);
			for (int i = 0; i < drinkNum; i++) {
				DocUtil.clickTextUrl(userKey, doc, "使用", 5);
				message.put("使用贡献药水", "自动使用贡献药水"+ (int)drinkNum +"瓶！");
			}

			Document temp = Jsoup.parse(DocUtil.substring(doc.toString(), "侠：",
					2, "1："));
			Elements boss = temp.getElementsContainingOwnText("乐斗");
			// 移除包含乐斗字样的干扰链接和文本，只要乐斗链接
			boss.removeAll(temp.getElementsByAttributeValueMatching("href",
					"totalinfo"));
			boss.removeAll(temp.getElementsContainingOwnText("已乐斗"));
			boss.removeAll(temp.getElementsByTag("body"));
			int size = boss.size();
			if (size == 0)
				message.put("好友boss乐斗情况", "所有好友boss已斗！");
			for (int i = 0; i < size; i++) {
				Document d = DocUtil.clickURL(userKey, boss.get(i).attr("href"));
				if (d.text().contains("侠侣") && d.text().contains(" (恢复")) {
					message.put("乐斗结果" + i,
							DocUtil.substring(d.text(), "侠侣", 2, " (恢复"));
				} else {
					message.put("乐斗结果" + i,  d.text().substring(7));
				}
			}
			// 帮派boss
			Document doc1 = DocUtil.clickTextUrl(userKey, mainDoc, "帮友");
			if (doc1.text().contains("尚未加入任何帮派")) {
				message.put("乐斗结果", "尚未加入任何帮派");
			} else {
				Document temp1 = Jsoup.parse(DocUtil.substring(doc1.toString(),
						"侠：", 2, "帮："));
				Elements boss1 = temp1.getElementsContainingOwnText("乐斗");
				boss1.removeAll(temp1.getElementsByAttributeValueMatching(
						"href", "totalinfo"));
				boss1.removeAll(temp1.getElementsContainingOwnText("已乐斗"));
				boss1.removeAll(temp1.getElementsByTag("body"));
				int size1 = boss1.size();
				if (size1 == 0)
					message.put("帮派boss乐斗情况", "所有帮派boss已斗！");
				for (int i = 0; i < size1; i++) {
					Document d = DocUtil.clickURL(userKey, boss1.get(i).attr("href"));
					if (d.text().contains("侠侣") && d.text().contains(" (恢复")) {
						message.put("乐斗结果_" + i,
								DocUtil.substring(d.text(), "侠侣", 2, " (恢复"));
					} else {
						message.put("乐斗结果_" + i, d.text().substring(7));
					}
				}
			}
			// 侠侣boss
			Document doc2 = DocUtil.clickTextUrl(userKey, mainDoc, "侠侣");
			String subStringResult = doc2.toString().substring(
					doc2.toString().indexOf("侠：") + 2,
					doc2.toString().indexOf("19级") + 231);
			Document temp2 = Jsoup.parse(subStringResult);
			Elements boss2 = temp2.getElementsContainingOwnText("乐斗");
			boss2.removeAll(temp2.getElementsByAttributeValueMatching("href",
					"totalinfo"));
			boss2.removeAll(temp2.getElementsContainingOwnText("已乐斗"));
			boss2.removeAll(temp2.getElementsByTag("body"));
			int size2 = boss2.size();
			if (size2 == 0)
				message.put("侠侣boss乐斗情况", "所有侠侣boss已斗！");
			for (int i = 0; i < size2; i++) {
				Document d = DocUtil.clickURL(userKey, boss2.get(i).attr("href"));
				if (d.text().contains("侠侣") && d.text().contains(" (恢复")) {
					message.put("乐斗结果-" + i,
							DocUtil.substring(d.text(), "侠侣", 2, " (恢复"));
				} else if(d.text().contains("您还没有结婚")) {
					message.put("乐斗结果-" + i, "您还没有结婚，不能打该boss！");
				} else {
					message.put("乐斗结果-" + i, d.text().substring(7));
				}
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
