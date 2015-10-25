package model.impl;

import java.io.IOException;

import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 帮战奖励 extends 乐斗项目 {

	public 帮战奖励(Document mainURL) {
		super(mainURL);
	}

	public void 领奖() {
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "帮战");
			doc = DocUtil.clickTextUrl(doc, "领取奖励");
			int j = 0;
			while(doc.text().contains("系统繁忙，请稍后重试！")) { 
				System.out.println(j+" 领取奖励 "+doc.text());////////////
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				doc = DocUtil.clickTextUrl(doc, "领取奖励");
				j++;
			}
			if (doc.text().contains("只能领取一次")) {
				message.put("领奖情况", "已领取过了！");
			} else {
				message.put("领奖情况", "领取成功！");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
