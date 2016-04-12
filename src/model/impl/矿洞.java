package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 矿洞 extends 乐斗项目 {

	public 矿洞(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 挑战() {
		try {
			if (!mainDoc.text().contains("矿洞")) {
				message.put("挑战情况", "未开启矿洞功能");
				return;
			}
			Document doc = DocUtil.clickTextUrl(mainDoc, "矿洞");
			if (doc.text().contains("领取奖励")) { // 上一期矿洞已打完
				message.put(
						"领取奖励",
						"上一期奖励："
								+ DocUtil.substring(doc.text(), "【矿洞副本】", 6,
										"领取奖励"));
				doc = DocUtil.clickTextUrl(doc, "领取奖励");
			}

			// ///矿洞未报名情况，未处理

			int num = Integer.parseInt(doc.text().charAt(
					doc.text().indexOf("剩余次数") + 5)
					+ "");
			if (num == 0) {
				message.put("挑战情况", "剩余挑战次数0！");
				return;
			}
			while (num > 0) {
				if(doc.text().contains("副本未开启")) {
					message.put("挑战情况", "副本未开启！");
					return;
				}
				Document doc1 = DocUtil.clickTextUrl(doc, "挑战");
				message.put("挑战情况" + num, DocUtil.substring(doc1.text(),
						"矿石商店", 4, "== 副本挑战中 =="));
				num = Integer.parseInt(doc1.text().charAt(
						doc1.text().indexOf("剩余次数") + 5)
						+ "");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
