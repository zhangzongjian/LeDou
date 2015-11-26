package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;
import java.util.Map;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 分享 extends 乐斗项目 {

	public 分享(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 一键分享() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "今日活跃度");
			doc = DocUtil.clickTextUrl(userKey, doc, "分享");
			doc = DocUtil.clickTextUrl(userKey, doc, "一键分享");
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
