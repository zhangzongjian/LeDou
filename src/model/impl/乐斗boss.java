package model.impl;

import java.io.IOException;

import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 乐斗boss extends 乐斗项目 {

	public 乐斗boss(Document mainURL) {
		super(mainURL);
	}

	public void 一键挑战() {
		// 好友boss
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "好友");
			// 若当前页面不是首页，则跳到首页
			if (doc.text().contains("首页 末页")) {
				doc = DocUtil.clickTextUrl(doc, "首页");
			}
			// 第二个才是使用贡献药水的链接
			Elements elements = doc.getElementsByAttributeValueMatching("href",
					"3038");
			Document temp = Jsoup.parse(DocUtil.substring(doc.toString(), "侠：",
					2, "1："));
			Elements boss = temp.getElementsContainingOwnText("乐斗");
			// 移除包含乐斗字样的干扰链接和文本，只要乐斗链接
			boss.removeAll(temp.getElementsByAttributeValueMatching("href",
					"totalinfo"));
			boss.removeAll(temp.getElementsContainingOwnText("已乐斗"));
			boss.removeAll(temp.getElementsByTag("body"));
			int size = boss.size();
			// 挑战前先吞贡献药水
			if (doc.text().contains("贡献药水 速购 使用 剩0次"))
				for (int i = 0; i < 4; i++) {
					DocUtil.clickURL(elements.get(1).attr("href"));
					message.put("使用贡献药水", "自动使用贡献药水4瓶！");
				}
			if (size == 0)
				message.put("好友boss乐斗情况", "所有好友boss已斗！");
			for (int i = 0; i < size; i++) {
				Document d = DocUtil.clickURL(boss.get(i).attr("href"));
				if (d.text().contains("侠侣") && d.text().contains("查看乐斗过程")) {
					message.put("乐斗结果" + i,
							DocUtil.substring(d.text(), "侠侣", 2, "查看乐斗过程"));
				} else {
					message.put("乐斗结果" + i, "未找到结果！");
				}
			}
			// 帮派boss
			Document doc1 = DocUtil.clickTextUrl(mainDoc, "帮友");
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
					Document d = DocUtil.clickURL(boss1.get(i).attr("href"));
					if (d.text().contains("侠侣") && d.text().contains("查看乐斗过程")) {
						message.put("乐斗结果_" + i,
								DocUtil.substring(d.text(), "侠侣", 2, "查看乐斗过程"));
					} else {
						message.put("乐斗结果_" + i, "未找到结果！");
					}
				}
			}
			// 侠侣boss
			Document doc2 = DocUtil.clickTextUrl(mainDoc, "侠侣");
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
				Document d = DocUtil.clickURL(boss2.get(i).attr("href"));
				if (d.text().contains("侠侣") && d.text().contains("查看乐斗过程")) {
					message.put("乐斗结果-" + i,
							DocUtil.substring(d.text(), "侠侣", 2, "查看乐斗过程"));
				} else {
					message.put("乐斗结果-" + i, "未找到结果！");
				}
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}
}
