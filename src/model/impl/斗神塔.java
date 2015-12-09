package model.impl;

import java.io.IOException;
import java.util.Map;

import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 斗神塔 extends 乐斗项目 {

	public 斗神塔(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 挑战() {
		try {
			// 斗神塔主页面
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "斗神塔");
			int num1 = Integer.parseInt(doc.text().charAt(
					doc.text().indexOf("今日剩余次数") + 7)
					+ "");
			// 不复活
			if (doc.text().contains("结束挑战") && num1 == 1) {
				doc = DocUtil.clickTextUrl(userKey, doc, "结束挑战");
				if(doc.text().contains("取消")) {
					DocUtil.clickTextUrl(userKey, doc, "取消");
				}
			}
			if (doc.text().contains("正在自动挑战中")) {
				message.put("挑战状态", "正在自动挑战中！");
			} else if (num1 > 0) {
				DocUtil.clickTextUrl(userKey, doc, "自动挑战");
				message.put("挑战状态", "开始自动挑战！");
			} else {
				message.put("挑战状态", "今日剩余次数0");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void 查看掉落情况() {
		try {
			DocUtil.clickURL(userKey, mainDoc.getElementsByAttributeValueMatching(
					"href", "towerfight").attr("href"));
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "企鹅动态");
			String result = DocUtil.substring(doc.text(), "1:", 2, "今天");
//			String result = doc.text().substring(doc.text().indexOf("1:") + 2,
//					doc.text().indexOf("今天"));
			if (result.contains("斗神塔"))
				message.put("掉落情况", "上一次挑战奖励：" + result);
			else {
				message.put("掉落情况", "掉落情况：找不到记录！");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
