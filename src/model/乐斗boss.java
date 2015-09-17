package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator.Tag;

import util.MyUtil;

public class 乐斗boss {
	private Document mainDoc;

	public 乐斗boss(Document mainDoc) {
		this.mainDoc = mainDoc;
	}

	private Map<String, Object> message = new HashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}
	
	public void 一键挑战(){
		try {
			//好友boss
			Document doc = MyUtil.clickTextUrl(mainDoc, "好友");
			//若当前页面不是首页，则跳到首页
			if(doc.text().contains("首页 末页")){
				doc = MyUtil.clickTextUrl(doc, "首页");
			}
			//第二个才是使用贡献药水的链接
			Elements elements = doc.getElementsByAttributeValueMatching("href","3038");
			Document temp = Jsoup.parse(MyUtil.substring(doc.toString(), "侠：", 2, "1："));
			Elements boss = temp.getElementsContainingOwnText("乐斗");
			//移除包含乐斗字样的干扰链接和文本，只要乐斗链接
			boss.removeAll(temp.getElementsByAttributeValueMatching("href", "totalinfo"));
			boss.removeAll(temp.getElementsContainingOwnText("已乐斗"));
			boss.removeAll(temp.getElementsByTag("body"));
			int size = boss.size();
			//挑战前先吞4瓶贡献药水
			if(size != 0)
			for(int i=0;i<4;i++) {
				MyUtil.clickURL(elements.get(1).attr("href"));
				message.put("使用贡献药水", "自动使用贡献药水4瓶！");
			}
			if(size == 0)
				message.put("好友boss乐斗情况", "所有好友boss已斗！");
			for(int i=0; i<size; i++) {
				Document d = MyUtil.clickURL(boss.get(i).attr("href"));
				message.put("乐斗结果"+i, MyUtil.substring(d.text(), "侠侣", 2, "查看乐斗过程"));
			}
			//帮派boss
			Document doc1 = MyUtil.clickTextUrl(mainDoc, "帮友");
			if(doc1.text().contains("尚未加入任何帮派")) {
				message.put("乐斗结果", "尚未加入任何帮派");
			}
			else {
				Document temp1 = Jsoup.parse(MyUtil.substring(doc1.toString(), "侠：", 2, "帮："));
				Elements boss1 = temp1.getElementsContainingOwnText("乐斗");
				boss1.removeAll(temp1.getElementsByAttributeValueMatching("href", "totalinfo"));
				boss1.removeAll(temp1.getElementsContainingOwnText("已乐斗"));
				boss1.removeAll(temp1.getElementsByTag("body"));
				int size1 = boss1.size();
				if(size1 == 0)
					message.put("帮派boss乐斗情况", "所有帮派boss已斗！");
				for(int i=0; i<size1; i++) {
					Document d = MyUtil.clickURL(boss1.get(i).attr("href"));
					message.put("乐斗结果_"+i, MyUtil.substring(d.text(), "侠侣", 2, "查看乐斗过程"));
				}
			}
			//侠侣boss
			Document doc2 = MyUtil.clickTextUrl(mainDoc, "侠侣");
			String subStringResult = doc2.toString().substring(doc2.toString().indexOf("侠：")+2, doc2.toString().indexOf("19级")+231);
			Document temp2 = Jsoup.parse(subStringResult);
			Elements boss2 = temp2.getElementsContainingOwnText("乐斗");
			boss2.removeAll(temp2.getElementsByAttributeValueMatching("href", "totalinfo"));
			boss2.removeAll(temp2.getElementsContainingOwnText("已乐斗"));
			boss2.removeAll(temp2.getElementsByTag("body"));
			int size2 = boss2.size();
			if(size2 == 0)
				message.put("侠侣boss乐斗情况", "所有侠侣boss已斗！");
			for(int i=0; i<size2; i++) {
				Document d = MyUtil.clickURL(boss2.get(i).attr("href"));
				if(d.text().contains("查看乐斗过程"))
					message.put("乐斗结果-"+i, MyUtil.substring(d.text(), "侠侣", 2, "查看乐斗过程"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
