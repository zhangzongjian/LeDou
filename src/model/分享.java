package model;

import java.io.IOException;

import org.jsoup.nodes.Document;

import util.DocUtil;
import util.乐斗项目;

public class 分享 extends 乐斗项目{

	public 分享(Document mainURL) {
		super(mainURL);
	}
	
	public void 一键分享() {
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "今日活跃度");
			doc = DocUtil.clickTextUrl(doc, "分享");
			doc = DocUtil.clickTextUrl(doc, "一键分享");
			message.put("分享情况", DocUtil.substring(doc.text(), "今日分享次数", 0, "开通达人"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
