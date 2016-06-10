package model.impl;

import java.io.IOException;
import java.util.Map;

import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.DocUtil;

public class 巅峰之战 extends 乐斗项目 {
	public 巅峰之战(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	/**
	 * 返回剩余挑战次数
	 * 
	 * @return
	 */
	public void 挑战() {
		try {
			if (!mainDoc.text().contains("巅峰之战进行中")) {
				message.put("挑战结束", "未开启巅峰之战功能！");
				return;
			}
			if (day == 1 || day == 2) {
				message.put("挑战结束", "挑战时间为每周三~周日！");
				return;
			}
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "巅峰之战进行中");
			if (doc.text().contains("未参加")) {
				message.put("挑战结束", "本周未参战，报名时间为每周一~周二！");
				return;
			}
			int i = 0;
			int j = 0;
			message.clear();
			while (true) {
				i++;
				while(doc.text().contains("繁忙")) {  //出现繁忙情况，重试3次
					doc = DocUtil.clickTextUrl(userKey, mainDoc, "巅峰之战进行中");
					j++;
					if(j > 2) break;
				}
				doc = DocUtil.clickTextUrl(userKey, doc, "征战");
				if (doc.text().contains("已经用完复活次数")) {
					message.put("挑战结束", "今日复活次数已用完！");
					break;
				} else if (doc.text().contains("查看乐斗过程")) {
					message.put("挑战情况" + i, DocUtil.substring(doc.text(),
							"【巅峰之战】", 6, "查看乐斗过程"));
				} else if (doc.text().contains("等待时间")) {
					message.put("等待时间", "等待时间结束后才能再次挑战！");
					break;
				} else {
					message.put("挑战结束", "您今天挑战次数已经达到上限了，请明天再来！");
					break;
				}
				if (i > 5)
					break; // 防止死循环
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void 领奖和报名() {
		try {
			if (!mainDoc.text().contains("巅峰之战进行中")) {
				message.put("报名情况", "未开启巅峰之战功能！");
				return;
			}
			if (day != 1 && day != 2) {
				message.put("报名情况", "不在报名时间内！");
				return;
			}
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "巅峰之战进行中");
			// 领奖
			doc = DocUtil.clickTextUrl(userKey, doc, "领奖");
			String result = DocUtil.substring(doc.text(), "【巅峰之战】", 6, "领奖");
			message.put("领奖情况", "领奖：" + result);
			// 报名
			if (doc.text().contains("所属：南派") || doc.text().contains("所属：北派")) {
				message.put("报名情况", "报名情况：已参赛状态!");
				return;
			}
			Document temp = Jsoup.parse(DocUtil.substring(doc.toString(),
					"选择阵营加入", 0, "额外战功奖励"));
			if (result.contains("上届所在的南派在巅峰之战中惜败") || result.contains("上届所在的北派在巅峰之战中取得胜利")) { // 上次南派跪了，则报名南派
				temp = DocUtil.clickTextUrl(userKey, temp, "南派");
				temp = DocUtil.clickTextUrl(userKey, temp, "确定");
				if (temp.text().contains("挑战书不足")) {
					message.put("报名情况", "报名情况：挑战书不足！");
					return;
				}
				message.put("报名情况", "报名情况：成功报名南派！");
			} else if (result.contains("上届所在的北派在巅峰之战中惜败") || result.contains("上届所在的南派在巅峰之战中取得胜利")) {
				temp = DocUtil.clickTextUrl(userKey, temp, "北派");
				temp = DocUtil.clickTextUrl(userKey, temp, "确定");
				if (temp.text().contains("挑战书不足")) {
					message.put("报名情况", "报名情况：挑战书不足！");
					return;
				}
				message.put("报名情况", "报名情况：成功报名北派！");
			} else {
				temp = DocUtil.clickTextUrl(userKey, temp, "随机加入");
				temp = DocUtil.clickTextUrl(userKey, temp, "确定");
				if (temp.text().contains("挑战书不足")) {
					message.put("报名情况", "报名情况：挑战书不足！");
					return;
				}
				message.put("报名情况", "报名情况：成功报名,随机加入！");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回等待时间(秒)
	 * 
	 * @return
	 */
	public int getLastTime() {
		if (!mainDoc.text().contains("巅峰之战进行中")) {
			return 0;
		}
		Document doc;
		int lastTime = 0;
		try {
			doc = DocUtil.clickTextUrl(userKey, mainDoc, "巅峰之战进行中");
			if (!doc.text().contains("等待时间"))
				return 0;
			int minutes = Integer.valueOf(doc.text().charAt(
					doc.text().indexOf("等待时间：") + 5)
					+ "");
			int seconds = Integer.valueOf(DocUtil.substring(doc.text(),
					"等待时间：", 7, "清除").trim());
			lastTime = minutes * 60 + seconds;
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastTime;
	}

	/**
	 * 返回剩余复活次数
	 * 
	 * @return
	 */
	public int getNum() {
		try {
			if (!mainDoc.text().contains("巅峰之战进行中")) {
				return 0;
			}
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc,
					"巅峰之战进行中");
			if (doc.text().contains("未参加")) {
				return 0;
			}
			int num = Integer.valueOf(doc.text().charAt(
					doc.text().indexOf("复活：") + 3)
					+ "");
			return num;
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
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
