package model;

import java.io.IOException;

import org.jsoup.nodes.Document;

import util.DocUtil;
import util.乐斗项目;

public class 踢馆 extends 乐斗项目 {

	public 踢馆(Document mainURL) {
		super(mainURL);
	}

	public void 挑战() {
		if (!mainDoc.text().contains("踢馆")) {
			message.put("挑战情况", "未开启踢馆功能！");
			return;
		}
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "踢馆");
			doc = DocUtil.clickTextUrl(doc, "高倍转盘");
			message.put("高倍转盘", DocUtil.substring(doc.text(), "功勋商店", 4, "领奖"));
			int 生命 = Integer.parseInt(doc.text().charAt(
					doc.text().indexOf("生命：") + 3)
					+ "");
			int 试练次数 = Integer.parseInt(doc.text().charAt(
					doc.text().indexOf("规则  试练") + 8)
					+ "");
			int i = 0;
			// 试练
			while (生命 > 0 && 试练次数 < 5) {
				i++;
				doc = DocUtil.clickTextUrl(doc, "试练");
				message.put("试练情况" + i,
						DocUtil.substring(doc.text(), "功勋商店", 4, "领奖"));
				生命 = Integer.parseInt(doc.text().charAt(
						doc.text().indexOf("生命：") + 3)
						+ "");
				试练次数 = Integer.parseInt(doc.text().charAt(
						doc.text().indexOf("规则  试练") + 8)
						+ "");
				if (i > 5)
					break;
			}
			// 挑战
			while (生命 > 0) {
				i++;
				doc = DocUtil.clickTextUrl(doc, "挑战");
				// 具体用完次数的提示信息未核实/////////////
				if (doc.text().contains("用完次数")) {
					message.put("挑战情况" + i, "挑战次数已用完！");
					return;
				}
				message.put("挑战情况" + i,
						DocUtil.substring(doc.text(), "功勋商店", 4, "领奖"));
				生命 = Integer.parseInt(doc.text().charAt(
						doc.text().indexOf("生命：") + 3)
						+ "");
				if (i > 35)
					break;
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
					"踢馆领奖：" + DocUtil.substring(doc.text(), "功勋商店", 4, "领奖"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}
}
