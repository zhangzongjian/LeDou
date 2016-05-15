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

	public void doit() {
		try {
			Document 门派首页 = DocUtil.clickTextUrl(userKey, mainDoc, "门派");
			if(门派首页 == null){ //未加入门派
				门派首页 = DocUtil.clickTextUrl(userKey, mainDoc, "全新门派系统开启，点击加入！");
				门派首页 = DocUtil.clickTextUrl(userKey, 门派首页, "加入", -1);
				门派首页 = DocUtil.clickTextUrl(userKey, 门派首页, "开启全新门派征程");
				message.put("所属门派", "恭喜您成功加入了华山！");
			}
			//香炉
			Document 香炉 = DocUtil.clickURL(userKey, getHrefMatching(门派首页, "op=showfumigate"));
			if(!香炉.text().contains("可获门贡：20  已点燃")) {
				香炉 = DocUtil.clickTextUrl(userKey, 香炉, "点燃");
				message.put("香炉", "香炉："+DocUtil.substring(香炉.text(), "提升自身的修行。", 8, "普通香炉"));
			}
			else {
				message.put("香炉", "香炉：每日点燃一次！");
			}
			//训练切磋
			Document 训练切磋 = DocUtil.clickURL(userKey, getHrefMatching(门派首页, "op=showtraining"));
			message.put("木桩训练", "木桩训练："+DocUtil.substring(DocUtil.clickTextUrl(userKey, 训练切磋, "进入木桩训练").text(), "】", 1, "进入木桩训练"));
			message.put("同门切磋", "同门切磋："+DocUtil.substring(DocUtil.clickTextUrl(userKey, 训练切磋, "进入同门切磋").text(), "】", 1, "进入木桩训练"));
			
			//职位挑战
			Document 职位挑战 = DocUtil.clickURL(userKey, getHrefMatching(门派首页, "op=showcouncil"));
			for(int i : new int[]{0,2,6}){
				DocUtil.clickTextUrl(userKey, 职位挑战, "切磋", i);
			}
			
			//门派武器技能
			Document 门派武器技能 = DocUtil.clickURL(userKey, getHrefMatching(门派首页, "cmd=sect_trump"));
			
			//心法
			Document 心法 = DocUtil.clickURL(userKey, getHrefMatching(门派首页, "cmd=sect_art"));
			
			//任务
			Document 任务 = DocUtil.clickURL(userKey, getHrefMatching(门派首页, "cmd=sect_task"));
			Document temp = 任务;
			do {
				temp = DocUtil.clickTextUrl(userKey, temp, "完成");
				if(temp != null) 任务  = temp;
			}
			while(null != temp);
			message.put("任务", "任务：（"+DocUtil.stringNumbers(任务.text(), "已完成")+"/3)");
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getHrefMatching(Document doc, String regex) {
		return doc.getElementsByAttributeValueMatching("href", regex).attr("href");
	}
}
