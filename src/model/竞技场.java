package model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import util.MyUtil;

public class 竞技场  {
	private Document mainDoc;

	public 竞技场(Document mainDoc) {
		this.mainDoc = mainDoc;
	}

	private Map<String, Object> message = new LinkedHashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}

	public void 挑战(){
		try {
			if(!mainDoc.text().contains("竞技场")) return;
			Document doc = MyUtil.clickURL(MyUtil.getTextUrl(mainDoc, "竞技场"));
			if(doc.text().contains("赛季中")) {
				int num = Integer.parseInt(doc.text().charAt(doc.text().indexOf("今日已挑战")+6)+"");
				Document doc1 = doc;
				while(num < 5) {
					doc1 = MyUtil.clickTextUrl(doc, "免费挑战");
					message.put("挑战情况"+num, MyUtil.substring(doc1.text(), "竞技点商店", 5, "赛季状态"));
					num = Integer.parseInt(doc1.text().charAt(doc1.text().indexOf("今日已挑战")+6)+"");
				}
				if(MyUtil.isHref(doc1, "领取奖励")) {
					MyUtil.clickTextUrl(doc1, "领取奖励");
					message.put("挑战情况", "已领取奖励！");
				}
				if(num == 5)
					message.put("挑战情况", "挑战次数已用完！");
			} 
			else {
				message.put("挑战情况", "非赛季时间!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
