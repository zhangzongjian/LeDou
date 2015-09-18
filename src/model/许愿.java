package model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.MyUtil;

public class 许愿 {
	private Document mainDoc;

	public 许愿(Document mainDoc) {
		this.mainDoc = mainDoc;
	}

	private Map<String, Object> message = new LinkedHashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}
	
	public void xuYuan(){
		try {
			Elements elements = mainDoc.getElementsByAttributeValueMatching("href","wish");
			Document doc = MyUtil.clickURL(elements.get(0).attr("href"));
			if(doc.text().contains("明天领取")) {
				message.put("许愿情况", "今日已许过愿了！");
				return;
			}
			if(doc.text().contains("领取许愿奖励")) {
				doc = MyUtil.clickTextUrl(doc, "领取许愿奖励");
				message.put("每日许愿奖励", MyUtil.substring(doc.text(), "【每日许愿】", 6, "。"));
			}
			if(doc.text().contains("首胜后才能许愿")) {
				message.put("许愿情况", "首胜后才能许愿！");
				return;
			}
			MyUtil.clickTextUrl(MyUtil.clickURL(doc.getElementsByAttributeValueMatching("href", "sub=4").get(0).attr("href")),"向月敏上香许愿");
			int num = Integer.parseInt(doc.text().charAt(doc.text().indexOf("连续许愿3天")+7)+"");
			if(num == 3) {
				MyUtil.clickTextUrl(doc, "领取");
				message.put("连续3天许愿奖励", "获得魂珠碎片宝箱*1");
			}
			message.put("许愿情况", "许愿成功！");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
