package model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.DocUtil;

public class 门派大战 {
	private Document mainDoc;

	public 门派大战(Document mainURL) {
		this.mainDoc = mainURL;
	}

	private Map<String, Object> message = new LinkedHashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}
	
	public void 报名() {
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "门派大战");
			doc = DocUtil.clickTextUrl(doc, "参战");
			if(doc.text().contains("不在战斗时间内")) {
				message.put("报名情况", "不在战斗时间内！");
			}
			else {
				message.put("报名情况", "报名情况："+DocUtil.substring(doc.text(), "门派", 0, "变更"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void 领奖() {
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "门派大战");
			doc = DocUtil.clickTextUrl(doc, "领奖");
			doc = Jsoup.parse(DocUtil.substring(doc.toString(), "第一名", 0, "返回战场"));
			if(doc.text().contains("领取")) {
				doc = DocUtil.clickTextUrl(doc, "领取");
				message.put("领奖情况", DocUtil.substring(doc.text(), "请及时领取", 6, "第一名"));
			}
			else {
				message.put("领奖情况", "上一期门派大战未参战！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
