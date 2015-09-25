package model.impl;

import java.io.IOException;
import java.util.Calendar;

import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 踢馆 extends 乐斗项目 {

	public 踢馆(Document mainURL) {
		super(mainURL);
	}
	
	private int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1; // 周日为0

	public void 挑战() {
		message.clear();
		if (!mainDoc.text().contains("踢馆")) {
			message.put("挑战结束", "未开启踢馆功能！");
			return;
		}
		if (day != 5) {
			message.put("挑战结束", "挑战时间为每周五！");
			return;
		}
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "踢馆");
			int 生命 = Integer.parseInt(doc.text().charAt(
					doc.text().indexOf("生命：") + 3)
					+ "");
			if(生命 == 0) {
				if (doc.text().contains("您已用完试炼机会")) {
					message.put("挑战结束", "您的复活次数已耗尽，无法继续挑战！");
					return;
				}
			}
			doc = DocUtil.clickTextUrl(doc, "高倍转盘");
			if(!doc.text().contains("您已经使用过1次"))
				message.put("高倍转盘", DocUtil.substring(doc.text(), "功勋商店", 4, "！"));
			int i = 0;
			// 试练
			while (生命 > 0) {
				i++;
				doc = DocUtil.clickTextUrl(doc, "试练");
				if (doc.text().contains("您已用完试炼机会")) {
					message.put("试练情况" + i, "您已用完试炼机会！");
					break;
				}
				if (doc.text().contains("您的复活次数已耗尽")) {
					message.put("试炼情况" + i, "您的复活次数已耗尽，无法继续挑战！");
					break;
				}
				message.put("试练情况" + i,
						DocUtil.substring(doc.text(), "功勋商店", 4, "！"));
				生命 = Integer.parseInt(doc.text().charAt(
						doc.text().indexOf("生命：") + 3)
						+ "");
			}
			// 挑战
			while (true) {
				i++;
				doc = DocUtil.clickTextUrl(doc, "挑战");
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
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}

	// 周五挑战结束后，到下周四
	public void 领奖() {
		if (!mainDoc.text().contains("踢馆")) {
			message.put("领奖情况", "踢馆领奖：未开启踢馆功能！");
			return;
		}
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "踢馆");
			doc = DocUtil.clickTextUrl(doc, "领奖");
			message.put("领奖情况",
					"踢馆领奖：" + DocUtil.substring(doc.text(), "功勋商店", 4, "！"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}
}
