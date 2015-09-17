package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import util.MyUtil;

public class 镖行天下{
	
	private Document mainDoc;
	public 镖行天下(Document mainDoc){
		this.mainDoc = mainDoc;
	}
	private Map<String, Object> message = new HashMap<String, Object>();
	public Map<String, Object> getMessage(){
		return message; 
	}
	public void 护送押镖() {
		try {
			//劫镖主页面，护送时间：10分钟
			if(!mainDoc.text().contains("镖行天下")) return;
			Document doc = MyUtil.clickURL(MyUtil.getTextUrl(mainDoc, "镖行天下"));
			if(doc.text().contains("护送完成")) {
				Document temp = MyUtil.clickURL(MyUtil.getTextUrl(doc, "护送完成"));
				message.put("领取奖励", MyUtil.substring(temp.text(), "获得奖励", 0, "！"));
				doc = MyUtil.clickURL(MyUtil.getTextUrl(temp, "领取奖励"));
			}
			//启动护送
			if(doc.text().contains("护送押镖"))
			if(!MyUtil.isHref(doc, "护送押镖")) {  //判断是否护送中
				message.put("护送状态", "您正在护送押镖中哦！");
				return;
			}
			Document doc1 = MyUtil.clickURL(MyUtil.getTextUrl(doc, "护送押镖"));
			int flushNum = Integer.parseInt(doc1.text().charAt(doc1.text().indexOf("免费刷新次数：")+7)+"");
			if(flushNum > 0)
				MyUtil.clickURL(MyUtil.getTextUrl(doc1, "刷新押镖"));
			if(MyUtil.clickURL(MyUtil.getTextUrl(doc1, "启程护送")).text().contains("今天没有护送次数了"))
				message.put("护送状态", "今天没有护送次数了!");
			else
				message.put("护送状态", "押镖已上路！");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void 劫镖(){
		//劫镖主页面，护送时间：10分钟
		Document doc;
		try {
			if(!mainDoc.text().contains("镖行天下")) return;
			doc = MyUtil.clickURL(MyUtil.getTextUrl(mainDoc, "镖行天下"));
			int num = Integer.parseInt(doc.text().charAt(doc.text().indexOf("剩余拦截次数")+7)+"");
			if(num == 0) {
				message.put("拦截结果", "拦截次数已用完！");
				return;
			}
			String result = "";
			while(num > 0) {
				Document doc1 = MyUtil.clickURL(MyUtil.getTextUrl(doc, "刷新"));
				String temp = MyUtil.clickURL(MyUtil.getTextUrl(doc1, "拦截")).text();
				//下面这句bug
				if(temp.contains("恭喜你")) {
					result = temp.substring(temp.indexOf("恭喜你"),temp.indexOf("恭喜你")+23);
					message.put("劫镖奖励"+num, "劫镖奖励："+result);
				}
				else 
					message.put("劫镖奖励"+num, "劫镖奖励：打不过呀！");
				num = Integer.parseInt(doc1.text().charAt(doc1.text().indexOf("剩余拦截次数")+7)+"");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
