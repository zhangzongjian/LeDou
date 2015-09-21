package model;

import java.io.IOException;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.DocUtil;
import util.乐斗项目;

public class 巅峰之战  extends 乐斗项目{
	public 巅峰之战(Document mainURL) {
		super(mainURL);
	}

	private int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1; // 周日为0
	private static int lastTime = 0;

	/**
	 * 返回剩余挑战次数
	 * @return
	 */
	public void 挑战() {
		try {
			if (!mainDoc.text().contains("巅峰之战进行中")) {
				message.put("挑战情况", "未开启巅峰之战功能！");
				return;
			}
			if (day == 1 || day == 2) {
				message.put("挑战情况", "挑战时间为每周三~周日！");
				return;
			}
			Document doc = DocUtil.clickTextUrl(mainDoc, "巅峰之战进行中");
			if(doc.text().contains("未参加")) {
				message.put("挑战情况", "本周未参战，报名时间为每周一~周二！");
				return;
			}
			int i = 0;
			while(true) {
				i++;
				doc = DocUtil.clickTextUrl(doc, "征战");
				if(doc.text().contains("已经用完复活次数")) {
					message.put("挑战情况", "今日挑战次数已用完！");
					break;
				}
				message.put("挑战情况"+i, DocUtil.substring(doc.text(), "【巅峰之战】", 6, "查看乐斗过程"));
				if(doc.text().contains("等待时间")) {
					//貌似有异常，未测试
					int minutes = Integer.valueOf(doc.text().charAt(doc.text().indexOf("等待时间：")+5)+"");
					int seconds = Integer.valueOf(DocUtil.substring(doc.text(), "等待时间：", 7, "清除").trim());
					lastTime = minutes * 60 +seconds;
					message.put("挑战失败", lastTime+"秒之后才能再次挑战！");
					break;
				}
				if(i > 5) break;  //防止死循环
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//周一领奖+报名，执行时间在调用的时候控制
	public void 领奖和报名() {
		try {
			if (!mainDoc.text().contains("巅峰之战进行中")) {
				message.put("挑战情况", "未开启巅峰之战功能！");
				return;
			}
			Document doc = DocUtil.clickTextUrl(mainDoc, "巅峰之战进行中");
			//领奖
			doc = DocUtil.clickTextUrl(doc, "领奖");
			String result = DocUtil.substring(doc.text(), "【巅峰之战】", 6, "领奖");
			message.put("领奖情况", "领奖："+result);
			//报名
			if(!doc.text().contains("选择阵营加入")) {
				message.put("报名情况", "报名情况：已经报过名了!");
				return;
			}
			Document temp = Jsoup.parse(DocUtil.substring(doc.toString(), "选择阵营加入", 0, "额外战功奖励"));
			if(result.contains("南派")) { //上次南派胜利，则报名北派
				temp = DocUtil.clickTextUrl(temp, "北派");
				temp = DocUtil.clickTextUrl(temp, "确定");
				if(temp.text().contains("挑战书不足")) {
					message.put("报名情况", "报名情况：挑战书不足！");
					return;
				}
				message.put("报名情况", "报名情况：成功报名北派");
			}
			else if(result.contains("北派")) {
				temp = DocUtil.clickTextUrl(temp, "南派");
				temp = DocUtil.clickTextUrl(temp, "确定");
				if(temp.text().contains("挑战书不足")) {
					message.put("报名情况", "报名情况：挑战书不足！");
					return;
				}
				message.put("报名情况", "报名情况：成功报名南派");
			}
			else {
				temp = DocUtil.clickTextUrl(temp, "随机加入");
				temp = DocUtil.clickTextUrl(temp, "确定");
				if(temp.text().contains("挑战书不足")) {
					message.put("报名情况", "报名情况：挑战书不足！");
					return;
				}
				message.put("报名情况", "报名情况：成功报名,随机加入");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回等待时间(秒)
	 * @return
	 */
	public int getLastTime() {
		if (!mainDoc.text().contains("巅峰之战进行中")) {
			return 0;
		}
		return lastTime;
	}
	
	/**
	 * 返回剩余复活次数
	 * @return
	 */
	public int getNum(){
		try {
			if (!mainDoc.text().contains("巅峰之战进行中")) {
				return 0;
			}
			Document doc = DocUtil.clickURL(DocUtil.getTextUrl(mainDoc, "巅峰之战进行中"));
			int num = Integer.valueOf(doc.text().charAt(doc.text().indexOf("复活：")+3)+"");
			return num;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean is挑战时间() {
		if (day == 1 || day == 2) {
			return false;
		}
		return true;
	}
}
