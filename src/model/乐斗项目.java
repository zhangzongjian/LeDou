package model;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import util.DocUtil;

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
	
	/**
	 * 返回：0~23，表示当前时间（二十四小时制）
	 */
	protected int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	
	/**
	 * 返回：0~23，表示当前时间（二十四小时制）
	 */
	public int getHour() {
		return this.hour;
	}
	
	public Map<String, String> getUserKey() {
		return userKey;
	}
	
	public void setUserKey(Map<String, String> userKey) {
		this.userKey = userKey;
	}
}
