package model.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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
			else if(门派首页.text().contains("领取门派引荐书*1")) {
				门派首页 = DocUtil.clickTextUrl(userKey, 门派首页, "领取门派引荐书*1");
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
			//高级香炉
			if(!香炉.text().contains("可获门贡：40  已点燃")) {
				香炉 = DocUtil.clickTextUrl(userKey, 香炉, "点燃");
				if(香炉.text().contains("数量不足")) {
					boolean b = new 门派邀请赛(userKey, mainDoc).兑换门派材料x1("门派高香");
					if(b == true) {
						香炉 = DocUtil.clickTextUrl(userKey, 香炉, "点燃");
						message.put("高级香炉", "高级香炉："+DocUtil.substring(香炉.text(), "提升自身的修行。", 8, "普通香炉"));
					}
					else {
						message.put("高级香炉", "高级香炉：门派高香不足且兑换失败！");
					}
				}
			}
			else {
				message.put("高级香炉", "高级香炉：每日点燃一次！");
			}
			
			//训练切磋
			Document 训练切磋 = DocUtil.clickURL(userKey, getHrefMatching(门派首页, "op=showtraining"));
			message.put("木桩训练", "木桩训练："+DocUtil.substring(DocUtil.clickTextUrl(userKey, 训练切磋, "进入木桩训练").text(), "】", 1, "进入木桩训练"));
			message.put("同门切磋1", "同门切磋1："+DocUtil.substring(DocUtil.clickTextUrl(userKey, 训练切磋, "进入同门切磋").text(), "】", 1, "进入木桩训练"));
			Document 同门切磋2 = DocUtil.clickTextUrl(userKey, 训练切磋, "进入同门切磋");
			if(同门切磋2.text().contains("门派战书数量不足")) {
				boolean b = new 门派邀请赛(userKey, mainDoc).兑换门派材料x1("门派战书");
				if(b == true) {
					message.put("同门切磋2", "同门切磋2："+DocUtil.substring(DocUtil.clickTextUrl(userKey, 训练切磋, "进入同门切磋").text(), "】", 1, "进入木桩训练"));
				}
				else {
					message.put("同门切磋2", "同门切磋2：门派战书不足且兑换失败！");
				}
			}
			else {
				message.put("同门切磋2", "同门切磋2："+DocUtil.substring(同门切磋2.text(), "】", 1, "进入木桩训练"));
			}
			
			//职位挑战
			Document 职位挑战 = DocUtil.clickURL(userKey, getHrefMatching(门派首页, "op=showcouncil"));
			for(int i : new int[]{0,1,2,3,4,5,6}){
				DocUtil.clickTextUrl(userKey, 职位挑战, "切磋", i);
			}
			
			//门派武器技能
			Document 门派武器技能 = DocUtil.clickURL(userKey, getHrefMatching(门派首页, "cmd=sect_trump"));
			
			//心法
			Document 心法 = DocUtil.clickURL(userKey, getHrefMatching(门派首页, "cmd=sect_art"));
			
			//任务
			Document 任务 = DocUtil.clickURL(userKey, getHrefMatching(门派首页, "cmd=sect_task"));
			Pattern p1 = Pattern.compile("查看一名同门成员的资料  奖励：门贡[0-9]{1,} 去做任务");
			Pattern p2 = Pattern.compile("查看一名其他门派成员的资料  奖励：门贡[0-9]{1,} 去做任务");
			Pattern p3 = Pattern.compile("与其他门派成员进行一次武艺切磋  奖励：门贡[0-9]{1,} 去做任务");
			while(p1.matcher(任务.text()).find() || p2.matcher(任务.text()).find() || p3.matcher(任务.text()).find()) {
				String 门派名称  = DocUtil.substring(门派首页.text(), "【", 1, "】");
				if(p2.matcher(任务.text()).find() || p3.matcher(任务.text()).find()) {
					List<String> list = new ArrayList<String>();
					list.add("丐帮");list.add("峨眉");list.add("少林");list.add("华山");
					list.remove(门派名称);
					门派名称 = list.get(0);
				}
				Document 斗友;
				do {
					斗友 = DocUtil.clickTextUrl(userKey, mainDoc, "斗友");
				} while(!斗友.text().contains(门派名称));
				斗友 = Jsoup.parse(DocUtil.substring(斗友.toString(), "当前体力值", 5, 门派名称));
				Elements es = 斗友.getElementsByAttributeValueMatching("href", "from_pf_list");
				//查看资料
				Document 成员个人主页 = DocUtil.clickURL(userKey, es.get(es.size() - 1).attr("href"));
				//战斗
				DocUtil.clickTextUrl(userKey, 成员个人主页, "乐斗");
				任务 = DocUtil.clickURL(userKey, getHrefMatching(门派首页, "cmd=sect_task"));
			}
			Pattern p4 = Pattern.compile("进行一次心法修炼  奖励：门贡[0-9]{1,} 去做任务");
			if(p4.matcher(任务.text()).find()) {
				DocUtil.clickTextUrl(userKey, 心法, "修炼");
			}
			Document temp = DocUtil.clickURL(userKey, getHrefMatching(门派首页, "cmd=sect_task"));
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
