package model.impl;

import java.io.IOException;

import java.util.Map;import java.util.Random;

import model.乐斗项目;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 历练 extends 乐斗项目 {

	public static String object = "洞庭湖(37~40级)";
	public 历练(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 挑战() {
		try {
			String name = object.substring(0,object.indexOf("("));
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "历练");
			// 活力值
			if(!doc.text().contains(name)) {
				message.put("历练情况", "未开启该历练场景，自动切换随机挑战！");
				随机挑战();
				return;
			}
			Document doc1 = DocUtil.clickTextUrl(userKey, doc, name);
			if(doc1.text().contains("下一页"))
				doc1 = DocUtil.clickTextUrl(userKey, doc1, "下一页");
			int num = Integer.parseInt(doc1.text().substring(
					doc1.text().indexOf("活力值") + 4, doc1.text().indexOf("/")));
			if (num < 10) {
				message.put("历练情况", "活力值不足10点！");
				return;
			}
			while (num >= 10) {
				//优先挑战有次数限制的boss
				doc1 = DocUtil.clickTextUrl(userKey, doc1, "乐斗", -1);
				if(doc1.text().contains("上限")) {
					doc1 = DocUtil.clickTextUrl(userKey, doc1, "乐斗", -2);
					if(doc1.text().contains("上限"))
						doc1 = DocUtil.clickTextUrl(userKey, doc1, "乐斗", -3);
				}
				if (doc1.text().contains("获得了")
						&& doc1.text().contains("查看乐斗过程")) {
					message.put("历练情况" + num,
							DocUtil.substring(doc1.text(), "获得了", 0, "查看乐斗过程"));
				} else {
					message.put("历练情况" + num, "未找到结果！");
				}
				num = Integer.parseInt(doc1.text().substring(
						doc1.text().indexOf("活力值") + 4,
						doc1.text().indexOf("/")));
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 随机挑战
	private void 随机挑战() throws IOException, InterruptedException {
		Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "历练");
		// 活力值
		Elements elements = doc.getElementsByAttributeValueMatching("href",
				"mapid");
		int size = elements.size();
		Random random = new Random();
		Document doc1 = DocUtil.clickURL(userKey,
				elements.get(random.nextInt(size)).attr("href"));
		int num = Integer.parseInt(doc1.text().substring(
				doc1.text().indexOf("活力值") + 4, doc1.text().indexOf("/")));
		if (num < 10) {
			message.put("历练情况", "活力值不足10点！");
			return;
		}
		while (num >= 10) {
			Document doc2 = DocUtil.clickTextUrl(userKey, doc1, "乐斗");
			if (doc2.text().contains("获得了") && doc2.text().contains("查看乐斗过程")) {
				message.put("历练情况" + num,
						DocUtil.substring(doc2.text(), "获得了", 0, "查看乐斗过程"));
			} else {
				message.put("历练情况" + num, "未找到结果！");
			}
			num = Integer.parseInt(doc2.text().substring(
					doc2.text().indexOf("活力值") + 4, doc2.text().indexOf("/")));
		}
	}
}
