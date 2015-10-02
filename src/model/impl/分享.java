package model.impl;

import java.io.IOException;

import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 分享 extends 乐斗项目 {

	public 分享(Document mainURL) {
		super(mainURL);
	}

	public void 一键分享() {
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "今日活跃度");
			int j = 0;
			while(doc.text().contains("系统繁忙")) {  //出现繁忙情况，重试3次
				doc = DocUtil.clickTextUrl(mainDoc, "今日活跃度");
				j++;
				if(j > 2) break;
			}
			doc = DocUtil.clickTextUrl(doc, "分享");
			doc = DocUtil.clickTextUrl(doc, "一键分享");
			message.put("分享情况",
					DocUtil.substring(doc.text(), "今日分享次数", 0, "开通达人"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
