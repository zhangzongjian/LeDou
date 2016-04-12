package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 幻境 extends 乐斗项目 {

	public static String object = "乐斗村";
	public 幻境(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 挑战() {
		try {
			String name = object;
			if (!mainDoc.text().contains("幻境")) {
				message.put("挑战情况", "未开启幻境功能");
				return;
			}
			Document doc = DocUtil.clickTextUrl(mainDoc, "幻境");
			int i = 0;
			while(true) {
				if(doc.text().contains("领取奖励")) {
					doc = DocUtil.clickTextUrl(doc, "领取奖励");
					message.put("领取奖励"+(i++), "额外奖励："+DocUtil.substring(doc.text(), "获得", 0, "特殊属性说明"));
					continue;
				}
				if(doc.toString().contains("op=fight")) {
					doc = DocUtil.clickURL(doc.getElementsByAttributeValueMatching("href", "op=fight").attr("href"));
					if(doc.text().contains("挑战已结束")) {
						message.put("挑战情况", "挑战失败！");
					}
					else {
						message.put("挑战情况", "全部通关！");
					}
				}
				if(doc.text().contains("返回飘渺幻境") && DocUtil.isHref(doc, "返回飘渺幻境")) {
					doc = DocUtil.clickTextUrl(doc, "返回飘渺幻境");
					continue;
				}
				if(doc.text().contains("挑战次数：0/1")) {
					message.put("挑战结果", "挑战次数已用完！");
					return;
				}
				if(doc.text().contains("挑战次数：1/1")) {
					if(doc.text().contains(name)){
						doc = DocUtil.clickTextUrl(doc, name);
						continue;
					}
					else {
						doc = DocUtil.clickTextUrl(doc, "乐斗村");
						message.put("挑战情况1", "未开启该场景，自动挑战第一个场景！");
						continue;
					}
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
