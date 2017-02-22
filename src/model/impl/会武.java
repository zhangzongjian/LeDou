package model.impl;

import java.io.IOException;
import java.util.Map;

import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 会武 extends 乐斗项目 {

	public 会武(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void doit() {
		try {
			Document 六门会武 = DocUtil.clickTextUrl(userKey, mainDoc, "会武");
			// 试炼。。
			String[] 试炼场列表 = new String[]{"初级试炼场", "中级试炼场", "高级试炼场"};
			Document doc = 六门会武;
			for(String 试炼场 : 试炼场列表) {
				if(DocUtil.isHref(doc, 试炼场) == true) {
					doc = DocUtil.clickTextUrl(userKey, doc, 试炼场);
					String tmp = null;
					for(int i = 0; i<5; i++) {
						doc = DocUtil.clickTextUrl(userKey, doc, "挑战");
						if(doc.text().contains("试炼书不足")) {
							message.put(试炼场+i, "你的试炼书不足！");
							break;
						}
						if(doc.text().contains("你在") && doc.text().contains("名称"))
							tmp = "名称";
						if(doc.text().contains("你在") && doc.text().contains("累积伤害"))
							tmp = "累积伤害";
						message.put(试炼场+i, DocUtil.substring(doc.text(), "你在", 0, tmp));
						//挑战结束
						if(doc.text().contains("【六门会武】")) {
							break;
						}
					}
				}
			}
			
			//冠军助威
			if(DocUtil.isHref(doc, "冠军助威") == true) {
				Document 助威 = DocUtil.clickTextUrl(userKey, 六门会武, "冠军助威");
				if(助威.text().contains("已助威")) {
					message.put("试炼助威", "冠军助威：已助威");
				}
				else {
					//0少林， 1峨眉， 2华山， 3丐帮， 4武当， 5明教
					助威 = DocUtil.clickTextUrl(userKey, 助威, "助威", 3);
					message.put("试炼助威", "冠军助威：助威成功！");
				}
			}
			
			//领奖
			if(DocUtil.isHref(doc, "领奖") == true) {
				Document 领奖 = DocUtil.clickTextUrl(userKey, 六门会武, "领奖");
				if(领奖.text().contains("已领取")) {
					message.put("试炼领奖", "领奖：已领取！");
				}
				else {
					领奖 = DocUtil.clickTextUrl(userKey, 领奖, "领取");
					message.put("试炼领奖", "领奖："+DocUtil.substring(领奖.text(), "【领奖】", 4, "已领取"));
				}
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
