package model.impl;

import java.io.IOException;

import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 好友乐斗 extends 乐斗项目 {

	public 好友乐斗(Document mainURL) {
		super(mainURL);
	}

	public void fight() {
		try {
			Document doc = DocUtil.clickURL(DocUtil.getTextUrl(mainDoc, "好友"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}
}
