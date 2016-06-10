package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 十二宫 extends 乐斗项目 {

	public static String object = "双子宫(60-65)";
	public 十二宫(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 挑战() {
		try {
			String name = object.substring(0,object.indexOf("("));
			if (!mainDoc.text().contains("十二宫")) {
				message.put("挑战情况", "未开启十二宫功能");
				return;
			}
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "十二宫");
			if(!doc.text().contains(name)) {
				message.put("挑战情况", "未开启该十二宫，已自动挑战双子宫！");
				name = "双子宫";
			}
			Document doc1 = DocUtil.clickTextUrl(userKey, doc, name);
			int i = 0;
			while (true) {
				doc1 = DocUtil.clickTextUrl(userKey, doc1, "挑战");
				if (doc1.text().contains("挑战次数不足")) {
					message.put("挑战情况", "挑战次数不足");
					return;
				}
				// 打死最后一个boss
				else if (doc1.text().contains("恭喜你")) {
					String result = doc1.text().substring(
							doc1.text().lastIndexOf("获得了"),
							doc1.text().indexOf("查看乐斗过程"));
					message.put("挑战情况", result);
					return;
				}
				// 挑战失败，不复活
				else if (doc1.text().contains("你已经不幸阵亡")) {
					message.put("挑战情况", "打不过，挂掉了！！");
					return;
				} else if (doc1.text().contains("当前正在挑战别的场景")) {
					message.put("挑战情况", "当前正在挑战别的场景！请重新选择十二宫！");
					return;
				}
				i++;
				// 防止死循环
				if (i > 15)
					break;
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
