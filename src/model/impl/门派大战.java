package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.DocUtil;

public class 门派大战 extends 乐斗项目 {

	public 门派大战(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 报名() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "门派大战");
			if (!doc.text().contains("未参战")) {
				message.put("报名情况", "已经报过名了！");
				return;
			}
			doc = DocUtil.clickTextUrl(userKey, doc, "参战");
			if (doc.text().contains("不在战斗时间内")) {
				message.put("报名情况", "不在战斗时间内！");
			} else {
				message.put("报名情况",
						"报名情况：" + DocUtil.substring(doc.text(), "门派", 0, "变更"));
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void 领奖() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "门派大战");
			doc = DocUtil.clickTextUrl(userKey, doc, "领奖");
			doc = Jsoup.parse(DocUtil.substring(doc.toString(), "第一名", 0,
					"返回战场"));
			if (doc.text().contains("领取")) {
				doc = DocUtil.clickTextUrl(userKey, doc, "领取");
				message.put("领奖情况",
						DocUtil.substring(doc.text(), "请及时领取", 6, "第一名"));
			} else {
				message.put("领奖情况", "上一期门派大战未参战！");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
