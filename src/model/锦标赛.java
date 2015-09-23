package model;

import java.io.IOException;
import java.util.Random;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.DocUtil;
import util.乐斗项目;

public class 锦标赛 extends 乐斗项目 {

	public 锦标赛(Document mainURL) {
		super(mainURL);
	}

	// 随机赞助
	public void 赞助() {
		if (!mainDoc.text().contains("锦标赛")) {
			message.put("赞助情况", "未开启锦标赛功能！");
			return;
		}
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "锦标赛");
			if (doc.text().contains("领取奖励")) {
				message.put("领奖情况",
						DocUtil.substring(doc.text(), "【百米锦标赛】", 7, "领取奖励"));
				doc = DocUtil.clickTextUrl(doc, "领取奖励");
			}
			Elements elements = doc.getElementsContainingOwnText("赞助");
			for (int i = 0; i < elements.size(); i++) {
				if (!elements.get(i).hasAttr("href")) // 去掉非超链接元素
					elements.remove(i);
				if (!"赞助".equals(elements.get(i).html())) // 去掉文本不完全匹配但包含该文本的元素
					elements.remove(i);
			}
			int size = elements.size();
			Random random = new Random();
			DocUtil.clickURL(elements.get(random.nextInt(size)).attr("href"));
			doc = DocUtil.clickURL(elements.get(random.nextInt(size)).attr(
					"href"));
			message.put("赞助情况",
					DocUtil.substring(doc.text(), "=本届已赞助=", 0, "积分排行"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}
}
