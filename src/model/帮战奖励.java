package model;

import java.io.IOException;

import org.jsoup.nodes.Document;

import util.DocUtil;
import util.乐斗项目;

public class 帮战奖励 extends 乐斗项目 {

	public 帮战奖励(Document mainURL) {
		super(mainURL);
	}
	
	public void 领奖() {
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "帮战");
			doc = DocUtil.clickTextUrl(doc, "领取奖励");
			if(doc.text().contains("只能领取一次")) {
				message.put("领奖情况", "已领取过了！");
			}
			else {
				message.put("领奖情况", "领取成功！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
