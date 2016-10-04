package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 踢馆 extends 乐斗项目 {

	public 踢馆(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}
	
	public void 挑战() {
		if (!mainDoc.text().contains("踢馆")) {
			message.put("挑战结束", "未开启踢馆功能！");
			return;
		}
		if (day != 5) {
			message.put("挑战结束", "挑战时间为每周五！");
			return;
		}
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "踢馆");
			int 生命 = Integer.parseInt(doc.text().charAt(
					doc.text().indexOf("生命：") + 3)
					+ "");
			if(生命 == 0) {
				if (doc.text().contains("您已用完试炼机会")) {
					message.put("挑战结束", "您的复活次数已耗尽，无法继续挑战！");
					return;
				}
			}
			doc = DocUtil.clickTextUrl(userKey, doc, "高倍转盘");
			if(!doc.text().contains("您已经使用过1次"))
				message.put("高倍转盘", DocUtil.substring(doc.text(), "功勋商店", 4, "！"));
			int i = 0;
			// 试练
			while (生命 > 0) {
				i++;
				doc = DocUtil.clickTextUrl(userKey, doc, "试练");
				if (doc.text().contains("您已用完试炼机会")) {
					message.put("试练情况" + i, "您已用完试炼机会！");
					break;
				}
				if (doc.text().contains("您的复活次数已耗尽")) {
					message.put("试炼情况" + i, "您的复活次数已耗尽，无法继续挑战！");
					break;
				}
				message.put("试练情况" + i,
						DocUtil.substring(doc.text(), "功勋商店", 4, "贵帮已报名参与！"));
				生命 = Integer.parseInt(doc.text().charAt(
						doc.text().indexOf("生命：") + 3)
						+ "");
				if(i > 10) {
					break;
				}
			}
			i = 0;
			// 挑战
			while (true) {
				i++;
				doc = DocUtil.clickTextUrl(userKey, doc, "挑战");
				if (doc.text().contains("挑战次数已用光")) {
					message.put("挑战情况" + i, "挑战次数已用光！");
					break;
				}
				if (doc.text().contains("您的复活次数已耗尽")) {
					message.put("挑战情况" + i, "您的复活次数已耗尽，无法继续挑战！");
					break;
				}
				message.put("挑战情况" + i,
						DocUtil.substring(doc.text(), "功勋商店", 4, "！"));
				if(i > 80) {
					break;
				}
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 周五挑战结束后，到下周四
	public void 领奖和报名() {
		if (!mainDoc.text().contains("踢馆")) {
			message.put("领奖情况", "踢馆领奖：未开启踢馆功能！");
			return;
		}
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "踢馆");
			//有两个"报名"字符串，说明未报名状态，则第二个"报名"字符串为报名链接
			if(DocUtil.stringNumbers(doc.text(), "报名") == 2) {
				doc = DocUtil.clickTextUrl(userKey, doc, "报名");
				message.put("报名情况", "报名情况："+DocUtil.substring(doc.text(), "功勋商店", 4, "！"));
			}
			doc = DocUtil.clickTextUrl(userKey, doc, "领奖");
			message.put("领奖情况",
					"踢馆领奖：" + DocUtil.substring(doc.text(), "功勋商店", 4, "！"));
			doc = DocUtil.clickTextUrl(userKey, doc, "排行奖励");
			int i = 0;
			while(true) {
				doc = DocUtil.clickTextUrl(userKey, doc,"领取奖励");
				if(doc.text().contains("！")) break;
				if(i>10) break; //防止死循环
			}
			message.put("领奖情况1", "踢馆排行领奖："+DocUtil.substring(doc.text(), "领取奖励", 4, "！"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
