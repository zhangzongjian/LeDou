package model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import util.MyUtil;

public class 好友乐斗  {
	private Document mainDoc;

	public 好友乐斗(Document mainDoc) {
		this.mainDoc = mainDoc;
	}

	private Map<String, Object> message = new LinkedHashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}

	public void fight(){
		try {
			Document doc = MyUtil.clickURL(MyUtil.getTextUrl(mainDoc, "好友"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
