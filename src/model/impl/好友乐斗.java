package model.impl;

import java.io.IOException;

import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 好友乐斗 extends 乐斗项目 {

	public 好友乐斗(Document mainURL) {
		super(mainURL);
	}

	public void doit() {
		try {
			Document doc = DocUtil.clickURL(DocUtil.getTextUrl(mainDoc, "侠侣"));
			String subStringResult = doc.toString().substring(doc.toString().indexOf("19级") + 231,doc.toString().indexOf("【快速购买】"));
			doc = Jsoup.parse(subStringResult);
			Elements friends = doc.getElementsContainingOwnText("乐斗");
			friends.removeAll(doc.getElementsByAttributeValueMatching("href",
					"totalinfo"));
			friends.removeAll(doc.getElementsContainingOwnText("已乐斗"));
			friends.removeAll(doc.getElementsByTag("body"));
			int size = friends.size();
			if (size == 0)
				message.put("侠侣好友乐斗情况", "所有侠侣好友已斗！");
			for (int i = 0; i < size; i++) {
				Document d = DocUtil.clickURL(friends.get(i).attr("href"));
				if (d.text().contains("侠侣") && d.text().contains("查看乐斗过程")) {
					message.put("乐斗结果." + i,
							DocUtil.substring(d.text(), "侠侣", 2, "查看乐斗过程"));
				} else if(d.text().contains("体力值不足")){
					message.put("乐斗结果." + i, "体力值不足！");
					break;
				} else {
					message.put("乐斗结果." + i, "未找到结果！");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}
}
