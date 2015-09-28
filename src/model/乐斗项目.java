package model;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

public class 乐斗项目 {
	protected Document mainDoc;

	public 乐斗项目(Document mainURL) {
		this.mainDoc = mainURL;
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
