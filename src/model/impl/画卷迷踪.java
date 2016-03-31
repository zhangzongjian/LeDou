package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 画卷迷踪 extends 乐斗项目 {

	public 画卷迷踪(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 挑战() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "画卷迷踪");
			int i=0;
			Document select;
			String temp = null;
			while(true) {
				select = DocUtil.clickTextUrl(userKey, doc, "选择");
				doc = DocUtil.clickTextUrl(userKey, select != null ? select: doc, "准备完成进入战斗");
				if(doc.text().contains("您获得了")) {
					message.put("挑战结果"+(i++), DocUtil.substring(doc.text(), "您获得了", 0, "我的最高记录"));
					temp = DocUtil.substring(doc.text(), "推荐战力", 0, "准备完成进入战斗").trim();
				}
				else if(doc.text().contains("弱爆了")) {
					message.put("挑战结果"+(i++), DocUtil.substring(doc.text(), "弱爆了", 0, "我的最高记录") + "("+ temp +")");
				}
				else if(doc.text().contains("没有挑战次数了")) {
					message.put("挑战结果", "挑战次数已用完！");
					return;
				}
				else if(doc.text().contains("征战书不足")) {
					message.put("挑战结果", "征战书不足！");
					return;
				}
				else {
					message.put("挑战结果", "未知错误！");
					return;
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
