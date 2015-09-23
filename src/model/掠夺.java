package model;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import util.DocUtil;
import util.乐斗项目;

public class 掠夺 extends 乐斗项目 {

	public 掠夺(Document mainURL) {
		super(mainURL);
	}

	public void 领奖() {
		if (!mainDoc.text().contains("掠夺")) {
			message.put("领奖情况", "未开启抢粮功能！");
			return;
		}
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "掠夺");
			Element element = doc.getElementsContainingOwnText("领取胜负奖励").get(0);
			if (element.hasAttr("href")) {
				doc = DocUtil.clickURL(element.attr("href"));
				message.put("领奖情况",
						DocUtil.substring(doc.text(), "规则", 2, "领取胜负奖励"));
			} else {
				message.put("领奖情况", "已经领取过奖励了！");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}
}
