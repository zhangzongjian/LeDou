package model.impl;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 武林大会 extends 乐斗项目 {

	public 武林大会(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public 武林大会(HashMap<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}
	
	// 每天13点开始
	public void 报名() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "武林");
			if (doc.text().contains("已报名")) {
				message.put("报名情况", "已报名，无需重复报名！");
				return;
			}
			if (doc.text().contains("随机报名")) {
				doc = DocUtil.clickTextUrl(userKey, doc, "随机报名");
				if (doc.text().contains("需要挑战书")) {
					message.put("报名情况", "报名参加武林大会需要挑战书*1!");
					return;
				} else if (doc.text().contains("已报名")) {
					message.put("报名情况", "报名成功！");
					return;
				} else {
					message.put("报名情况", "报名失败，赛区已满！");
					return;
				}
			} else {
				message.put("报名情况", "非报名时间！");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
