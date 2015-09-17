package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import util.MyUtil;

public class 矿洞   {
	private Document mainDoc;

	public 矿洞(Document mainDoc) {
		this.mainDoc = mainDoc;
	}

	private Map<String, Object> message = new HashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}
	
	public void 挑战(){
		try {
			if(!mainDoc.text().contains("矿洞")) return;
			Document doc = MyUtil.clickTextUrl(mainDoc, "矿洞");
			int num = Integer.parseInt(doc.text().charAt(doc.text().indexOf("剩余次数")+5)+"");
			if(num == 0) {
				message.put("挑战情况", "剩余挑战次数0！");
				return;
			}
			while(num > 0) {
				Document doc1 = MyUtil.clickTextUrl(doc, "挑战");
				message.put("挑战情况"+num, "");
				num = Integer.parseInt(doc1.text().charAt(doc1.text().indexOf("剩余次数")+5)+"");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
