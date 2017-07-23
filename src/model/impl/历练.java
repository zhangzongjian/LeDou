package model.impl;

import java.io.IOException;
import java.util.Map;

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
			Document doc = DocUtil.clickURL(userKey, mainDoc.getElementsByAttributeValueMatching("href", "cmd=liveness").attr("href"));
			if (doc.text().contains("3．[5/5]")) {
				message.put("历练情况", "今日历练5次了！");
				return;
			}
			
			开启自动使用活力药水(mainDoc);
			
			String name = object.substring(0,object.indexOf("("));
			doc = DocUtil.clickTextUrl(userKey, mainDoc, "历练");

			Document doc1;
			if(!doc.text().contains(name)) {
				message.put("历练情况", "未开启该历练场景，已切换为自动模式！");
				doc1 = 自动选择场景();
			}
			else { 
				doc1 = DocUtil.clickTextUrl(userKey, doc, name);
			}
			if(doc1.text().contains("下一页"))
				doc1 = DocUtil.clickTextUrl(userKey, doc1, "下一页");
			
			for(int i = 0; i < 5; i++) {
				//优先挑战有次数限制的boss
				doc1 = DocUtil.clickTextUrl(userKey, doc1, "乐斗", -1);
				if(doc1.text().contains("上限")) {
					doc1 = DocUtil.clickTextUrl(userKey, doc1, "乐斗", -2);
					if(doc1.text().contains("上限"))
						doc1 = DocUtil.clickTextUrl(userKey, doc1, "乐斗", -3);
				}
				if (doc1.text().contains("获得了")
						&& doc1.text().contains("查看乐斗过程")) {
					message.put("历练情况" + i,
							DocUtil.substring(doc1.text(), "获得了", 0, "查看乐斗过程"));
				} else {
					message.put("历练情况" + i, "未找到结果！");
				}
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 随机挑战
	private Document 自动选择场景() throws IOException, InterruptedException {
		Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "历练");
		// 活力值
		Elements elements = doc.getElementsByAttributeValueMatching("href",
				"mapid");
		int size = elements.size();
		Document doc1 = DocUtil.clickURL(userKey,
				elements.get( size - 1 ).attr("href"));
		return doc1;
	}
	
	private void 开启自动使用活力药水(Document mainDoc) throws IOException, InterruptedException {
		Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "好友"); 
		doc = DocUtil.clickTextUrl(userKey, doc, "助手");
		if(doc.text().contains("开启自动使用活力药水")) {
			DocUtil.clickTextUrl(userKey, doc, "开启自动使用活力药水");
		}
	}
}
