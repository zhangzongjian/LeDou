package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import util.MyUtil;

public class 领取每日奖励  {
	private Document mainDoc;

	public 领取每日奖励(Document mainDoc) {
		this.mainDoc = mainDoc;
	}

	private Map<String, Object> message = new HashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}
	
	public void 领取(){
		try {
			Document doc = mainDoc;
			if(doc.text().contains("领取达人礼包")){
				Document doc1 = MyUtil.clickTextUrl(doc, "领取达人礼包");
				if(doc1.text().contains("您还不是达人"))
					message.put("领取达人礼包", "达人礼包：您还不是达人");
				else
					message.put("领取达人礼包",MyUtil.substring(doc1.text(), "获得：", 3, "！"));
			} else message.put("领取达人礼包", "达人礼包：已领取");
			if(doc.text().contains("领取每日奖励")){
				Document doc2 = MyUtil.clickTextUrl(doc, "领取每日奖励");
				String end = "";
				if(doc2.text().contains("开通乐斗达人"))
					end = "开通乐斗达人";
				else if(doc2.text().contains("(VIP特权)续费乐斗达人"))
					end = "(VIP特权)续费乐斗达人";
				else 
					end = "恭喜你";
				message.put("领取每日奖励", MyUtil.substring(doc2.text(), "获得了", 3, end));
			} else message.put("领取每日奖励", "每日奖励：已领取");
			if(doc.text().contains("领取每日双倍奖励")){
				Document doc2 = MyUtil.clickTextUrl(doc, "领取每日双倍奖励");
				message.put("领取每日奖励", MyUtil.substring(doc2.text(), "获得了", 3, doc2.text().contains("开通乐斗达人")?"开通乐斗达人":"恭喜你"));
			} else message.put("领取每日奖励", "每日奖励：已领取");
			if(doc.text().contains("领取徒弟经验")){
				Document doc3 = MyUtil.clickTextUrl(doc, "领取徒弟经验");
				message.put("领取徒弟经验", "徒弟经验："+MyUtil.substring(doc3.text(), "其中", 2, "多跟徒弟"));
			} else message.put("领取徒弟经验", "徒弟经验：已领取");
			if(doc.text().contains("无字天书")) {
				Document doc4 = MyUtil.clickTextUrl(doc, "无字天书");
				if(doc4.text().contains("达人玩家才能领取")) {
					message.put("领取无字天书", "无字天书：达人玩家才能领取！");
				} else {
					message.put("领取无字天书","无字天书：领取成功！");
				}
			} else message.put("领取无字天书", "无字天书：已领取");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
