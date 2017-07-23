package model.impl;

import java.io.IOException;
import java.util.Map;

import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 问鼎天下 extends 乐斗项目 {

	public 问鼎天下(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 攻占() {
		if(super.day ==6 || super.day ==0) {
			try {
				Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "问鼎天下");
				if(!doc.toString().contains("<br> 我的助威帮派：<br>")) {
					message.put("助威", "已助威！");
					return;
				}
				for(String s : new String[]{"东海","南荒","西泽","北寒"}) {
					if(super.day == 6) {
						doc = DocUtil.clickTextUrl(userKey, doc, s);
					}
					if(doc.toString().contains("id=96690")) {
						doc = DocUtil.clickURL(userKey, doc.getElementsByAttributeValueMatching("href", "96690").attr("href")); //傲龙ㄟ盛世 
						message.put("助威", DocUtil.substring(doc.text(), "规则", 2, "资源点争夺"));
						return;
					}
				}
				message.put("助威", "助威失败！");
			} catch (IOException e) {
				message.put("消息", "连接超时，请重试！");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(super.day >=1 && super.day <=5) {
			try {
				Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "问鼎天下");
				doc = DocUtil.clickTextUrl(userKey, doc, "资源点争夺");
				if(super.day == 1) {
					Document 领取奖励 = DocUtil.clickTextUrl(userKey, doc, "领取奖励");
					message.put("领取奖励", DocUtil.substring(领取奖励.text(), "规则", 2, "资源点争夺"));
				}
				if(DocUtil.getTextUrlElementList(doc, "放弃").size() == 1) {
					message.put("攻占", "攻占失败，已占有领地！");
					return;
				}
				if(DocUtil.getTextUrlElementList(doc, "领取").size() == 1) {
					doc = DocUtil.clickTextUrl(userKey, doc, "领取");
					message.put("领取帮资", DocUtil.substring(doc.text(), "规则", 2, "资源点争夺"));
				}
				if(doc.text().contains("已标记")) {
					doc = Jsoup.parse(DocUtil.substring(doc.toString(), "我的资源点", 5, "已标记"));
					Elements es = doc.getElementsByAttributeValueMatching("href", "region=");
					int size = es.size();
					doc = DocUtil.clickURL(userKey, es.get(size-1).attr("href"));
				}
				int 剩余攻占次数 = Integer.valueOf(DocUtil.substring(doc.text(), "剩余抢占次数：", 7, "本帮排名").trim());
				int i = 0;
				while(剩余攻占次数 > 0) {
					if(i++ > 20) break;
					if(剩余攻占次数 > 1) {
						doc = DocUtil.clickTextUrl(userKey, doc, "攻占");
					}
					else {
						doc = DocUtil.clickTextUrl(userKey, doc, "刷新");
						doc = DocUtil.clickTextUrl(userKey, doc, "攻占", -1);
					}
					message.put("攻占"+剩余攻占次数, DocUtil.substring(doc.text(), "规则", 2, "资源点争夺"));
					剩余攻占次数 = Integer.valueOf(DocUtil.substring(doc.text(), "剩余抢占次数：", 7, "本帮排名").trim());
				}
				message.put("攻占0", "剩余抢占次数为0");
			} catch (IOException e) {
				message.put("消息", "连接超时，请重试！");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}