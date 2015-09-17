package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.MyUtil;

public class 历练  {
	private Document mainDoc;

	public 历练(Document mainDoc) {
		this.mainDoc = mainDoc;
	}

	private Map<String, Object> message = new HashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}
	//随机挑战
	public void 挑战(){
		try {
			Document doc = MyUtil.clickTextUrl(mainDoc, "历练");
			//活力值
			Elements elements = doc.getElementsByAttributeValueMatching("href","mapid");
			int size = elements.size();
			Random random = new Random();
			Document doc1 = MyUtil.clickURL(elements.get(random.nextInt(size)).attr("href"));
			int num = Integer.parseInt(doc1.text().substring(doc1.text().indexOf("活力值")+4,doc1.text().indexOf("/")));
			if(num < 10) {
				message.put("历练情况", "活力值不足10点！");
				return;
			}
			while(num >= 10) {
				Document doc2 = MyUtil.clickTextUrl(doc1, "乐斗");
				message.put("历练情况"+num, MyUtil.substring(doc2.text(), "获得了", 0, "查看乐斗过程"));
				num = Integer.parseInt(doc2.text().substring(doc2.text().indexOf("活力值")+4,doc2.text().indexOf("/")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
