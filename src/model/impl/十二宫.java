package model.impl;

import java.io.IOException;

import model.乐斗项目;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 十二宫 extends 乐斗项目 {

	public 十二宫(Document mainURL) {
		super(mainURL);
	}

	// 1002及以上掉落黄金星辰
	public void 扫荡() {
		try {
			if (!mainDoc.text().contains("十二宫")) {
				message.put("挑战情况", "未开启十二宫功能");
				return;
			}
			Document doc = DocUtil.clickTextUrl(mainDoc, "十二宫");
			// 1005为处女宫，1000为白羊宫
			Elements elements = doc.getElementsByAttributeValueMatching("href",
					"scene_id=1000");
			Document doc1 = DocUtil.clickTextUrl(
					DocUtil.clickURL(elements.get(1).attr("href")), "请猴王扫荡");
			if (doc1.text().contains("挑战次数不足")) {
				message.put("挑战情况", "挑战次数不足");
				return;
			} else if (doc1.text().contains("当前场景进度不足以使用自动挑战功能！")) {
				message.put("挑战情况", "当前场景进度不足以使用自动挑战功能！");
				return;
			} else if (doc1.text().contains("当前正在挑战别的场景！")) {
				message.put("挑战情况", "当前正在挑战别的场景！请重新选择十二宫！");
				return;
			} else if (doc1.text().contains("是否复活")) {
				message.put("挑战情况", "打不过，挂掉了！！");
				return;
			} else {
				String result = doc1.text().substring(
						doc1.text().lastIndexOf("获得了"),
						doc1.text().lastIndexOf("。"));
				message.put("挑战情况", result);
				return;
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}

	public void 挑战() {
		try {
			if (!mainDoc.text().contains("十二宫")) {
				message.put("挑战情况", "未开启十二宫功能");
				return;
			}
			Document doc = DocUtil.clickTextUrl(mainDoc, "十二宫");
			Elements elements = doc.getElementsByAttributeValueMatching("href",
					"scene_id=1000");
			int i = 0;
			Document doc1;
			while (true) {
				doc1 = DocUtil.clickTextUrl(
						DocUtil.clickURL(elements.get(0).attr("href")), "挑战");
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
		}
	}
}
