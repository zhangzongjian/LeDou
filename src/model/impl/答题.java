package model.impl;

import java.io.IOException;
import java.util.Map;

import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 答题 extends 乐斗项目 {
	public 答题(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void answer() {
		try {
			if (mainDoc.text().contains("答题")) {
				Document doc = DocUtil.clickTextUrl(userKey, mainDoc,
						"答题");
				DocUtil.clickTextUrl(userKey, doc, "选择");
				message.put("success", "OK!");
			} else {
				message.put("error", "已经答题过了!");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
