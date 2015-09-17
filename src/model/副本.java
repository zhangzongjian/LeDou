package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import util.MyUtil;

public class 副本  {
	private Document mainDoc;

	public 副本(Document mainDoc) {
		this.mainDoc = mainDoc;
	}

	private Map<String, Object> message = new HashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}

	public void 挑战() {
		try {
			//副本主页面
			Document doc = MyUtil.clickURL(MyUtil.getTextUrl(mainDoc, "副本"));
			int num = Integer.parseInt(doc.text().substring(doc.text().indexOf("征战书")+8,doc.text().lastIndexOf("本")));
			int num1 = Integer.parseInt(doc.text().substring(doc.text().indexOf("今天剩余次数")+7,doc.text().indexOf("/")));
			int num2 = Integer.parseInt(doc.text().substring(doc.text().indexOf("本周付费次数")+7,doc.text().lastIndexOf("/")));
			//未组队
			if(doc.text().contains("请先通过“好友组队”组建2名队友")){
				message.put("挑战状态", "未组队，请手动组队再来尝试！");
			}
			else if(num1 > 0){
				MyUtil.clickTextUrl(doc, "快速挑战");
				message.put("挑战状态", "快速挑战完成！");
			} 
			else {
				message.put("挑战状态", "今日剩余次数0！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
