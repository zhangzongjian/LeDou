package model.impl;

import java.io.IOException;

import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 供奉 extends 乐斗项目 {

	public 供奉(Document mainURL) {
		super(mainURL);
	}

	public void 一键供奉() {
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "今日活跃度");
			if(doc.text().contains("12.[1/1]")) {
				message.put("供奉情况", "今日已经供奉过了！");
				return;
			}
			doc = DocUtil.clickTextUrl(doc, "供奉");
			do {
				// 3089还魂丹
				if (doc.toString().contains("3089")) {
					DocUtil.clickURL(doc.getElementsByAttributeValueMatching(
							"href", "3089").attr("href"));
					message.put("供奉情况", "供奉成功！");
					return;
				} else if (doc.text().contains("下页")) {
					doc = DocUtil.clickTextUrl(doc, "下页");
				}
				if (!doc.text().contains("下页")) {
					message.put("供奉情况", "供奉失败，背包里木有还魂丹！");
					return;
				}
			} while (doc.text().contains("下页"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
