package model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 历练  {
	private Document mainDoc;

	public 历练(Document mainDoc) {
		this.mainDoc = mainDoc;
	}

	private Map<String, Object> message = new LinkedHashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}
	//随机挑战
	public void 挑战(){
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "历练");
			//活力值
			Elements elements = doc.getElementsByAttributeValueMatching("href","mapid");
			int size = elements.size();
			Random random = new Random();
			Document doc1 = DocUtil.clickURL(elements.get(random.nextInt(size)).attr("href"));
			int num = Integer.parseInt(doc1.text().substring(doc1.text().indexOf("活力值")+4,doc1.text().indexOf("/")));
			if(num < 10) {
				message.put("历练情况", "活力值不足10点！");
				return;
			}
			while(num >= 10) {
				Document doc2 = DocUtil.clickTextUrl(doc1, "乐斗");
				if(doc2.text().contains("获得了") && doc2.text().contains("查看乐斗过程")) {
					message.put("历练情况"+num, DocUtil.substring(doc2.text(), "获得了", 0, "查看乐斗过程"));
				}
				else {
					message.put("历练情况"+num, "未找到结果！");
				}
				num = Integer.parseInt(doc2.text().substring(doc2.text().indexOf("活力值")+4,doc2.text().indexOf("/")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
