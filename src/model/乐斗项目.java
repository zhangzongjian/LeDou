package model;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

public class 乐斗项目 {
	protected Document mainDoc;
	protected Map<String, String> userKey;

	public 乐斗项目(){
		
	}
	public 乐斗项目(Map<String, String> userKey, Document mainURL) {
		this.mainDoc = mainURL;
		this.userKey = userKey;
	}

	protected Map<String, Object> message = new LinkedHashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}
	
	/**
	 * 周一~周六，day = 1~6；周日，day = 0；
	 */
	protected int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1; // 周日为0
	
	/**
	 * 周一~周六，day = 1~6；周日，day = 0；
	 */
	public int getDay() {
		return this.day;
	}
	
}
