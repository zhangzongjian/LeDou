package model.impl;

import java.io.IOException;

import org.jsoup.nodes.Document;

import util.DocUtil;

import model.乐斗项目;

public class 抢地盘 extends 乐斗项目 {

	public 抢地盘(Document mainURL) {
		super(mainURL);
	}

	public void doit(){
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "今日活跃度");
			if(doc.text().contains("8．[2/2]")) {
				message.put("抢地盘情况", "今日已经抢过2次！");
				return;
			}
			doc = DocUtil.clickTextUrl(doc, "抢地盘");
			doc = DocUtil.clickTextUrl(doc, "无限制区");
			for(int i=0; i<2; i++) {
				if(DocUtil.clickTextUrl(doc, "攻占").text().contains("您的挑战书数量不足")) {
					message.put("抢地盘情况", "您的挑战书数量不足");
					return;
				}
			}
			message.put("抢地盘情况", "成功抢了2次地盘！");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
