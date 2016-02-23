package model.impl;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 群雄逐鹿 extends 乐斗项目 {

	public 群雄逐鹿(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public 群雄逐鹿(HashMap<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}
	
	// 周五两点开始报名。
	// 周一到周三，下午两点可以领奖
	public void 报名和领奖() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "群雄逐鹿");
			doc = DocUtil.clickTextUrl(userKey, doc, "报名");
			message.put("报名", DocUtil.substring1(doc.text(), "【群雄逐鹿】", 6, "！", 1));
			doc = DocUtil.clickTextUrl(userKey, doc, "领奖");
			message.put("领奖", DocUtil.substring(doc.text(), "【群雄逐鹿】", 6, "报名"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
