package model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import util.MyUtil;

public class 答题{
	private Document mainDoc;
	public 答题(Document mainURL){
		this.mainDoc = mainURL;
	}
	private Map<String, Object> message = new LinkedHashMap<String, Object>();
	public Map<String, Object> getMessage(){
		return message; 
	}
	public void answer(){
		try {
			if(mainDoc.text().contains("答题")){
				Document doc = MyUtil.clickURL(MyUtil.getTextUrl(mainDoc, "答题"));
				MyUtil.clickURL(MyUtil.getTextUrl(doc, "选择"));
				message.put("success", "OK!");
			} 
			else {
				message.put("error", "已经答题过了!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
