package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import util.MyUtil;

public class 斗神塔  {
	private Document mainDoc;

	public 斗神塔(Document mainDoc) {
		this.mainDoc = mainDoc;
	}

	private Map<String, Object> message = new HashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}

	public void 挑战() {
		try {
			// 斗神塔主页面
			Document doc = MyUtil.clickURL(MyUtil.getTextUrl(mainDoc, "斗神塔"));
			int num1 = Integer.parseInt(doc.text().charAt(doc.text().indexOf("今日剩余次数")+7)+"");
//			int num2 = Integer.parseInt(doc.text().charAt(doc.text().indexOf("本周付费次数")+7)+"");
			//不复活
			if (doc.text().contains("结束挑战")) {
				MyUtil.clickTextUrl(MyUtil.clickTextUrl(doc, "结束挑战"),"取消");
			}
			if(num1 > 0) {
				MyUtil.clickTextUrl(doc, "自动挑战");
				message.put("挑战状态", "开始自动挑战！");
			}
			if(doc.text().contains("正在自动挑战中")) {
				message.put("挑战状态", "正在自动挑战中！");
			}
			else 
				message.put("挑战状态", "今日剩余次数0");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void 查看掉落情况(){
		try {
			MyUtil.clickURL(MyUtil.getTextUrl(mainDoc, "斗神塔"));
			Document doc = MyUtil.clickTextUrl(mainDoc,"企鹅动态");
			System.out.println(doc.text());
			String result = doc.text().substring(doc.text().indexOf("1:")+2, doc.text().indexOf("2:"));
			if(result.contains("斗神塔"))
				message.put("掉落情况", "上一次掉落："+result);
			else {
				message.put("掉落情况", "掉落情况：找不到记录！");
			}
		} catch (IOException e) {
			message.put("掉落情况", "掉落情况：找不到记录！");
			e.printStackTrace();
		}
	}
}
