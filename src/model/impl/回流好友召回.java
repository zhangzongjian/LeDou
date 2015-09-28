package model.impl;

import java.io.IOException;

import org.jsoup.nodes.Document;

import util.DocUtil;

import model.乐斗项目;

public class 回流好友召回 extends 乐斗项目 {

	public 回流好友召回(Document mainURL) {
		super(mainURL);
	}

	public void doit() {
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "今日活跃度");
			if (doc.text().contains("4．[3/3]")) {
				message.put("召回情况", "今日已召回过好友了！");
				return;
			}
			
			int j = 0;
			while(doc.text().contains("系统繁忙")) {  //出现繁忙情况，重试3次
				doc = DocUtil.clickTextUrl(mainDoc, "回流好友召回");
				j++;
				if(j > 2) break;
			}
			
			doc = DocUtil.clickTextUrl(doc, "回流好友召回");
			if(!doc.text().contains("回来玩吧！")) {
				message.put("召回情况", "没有可召回的好友！");
				return;
			}
			for(int i=0; i<3; i++) {
				DocUtil.clickTextUrl(doc, "回来玩吧！");
			}
			message.put("召回情况", "OK！");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
