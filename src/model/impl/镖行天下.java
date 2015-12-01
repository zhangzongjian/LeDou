package model.impl;

import java.io.IOException;
import java.util.Map;

import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.DocUtil;

public class 镖行天下 extends 乐斗项目 {

	public 镖行天下(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 护送押镖() {
		try {
			message.clear();
			// 劫镖主页面，护送时间：10分钟
			if (!mainDoc.text().contains("镖行天下"))
				return;
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "镖行天下");
			if (doc.text().contains("护送完成")) {
				Document temp = DocUtil.clickTextUrl(userKey, doc, "护送完成");
				message.put("护送奖励",
						DocUtil.substring(temp.text(), "获得奖励", 0, "！"));
				doc = DocUtil.clickTextUrl(userKey, temp, "领取奖励");
			}
			// 启动护送
			if (doc.text().contains("护送押镖"))
				if (!DocUtil.isHref(doc, "护送押镖")) { // 判断是否护送中
					message.put("护送状态", "您正在护送押镖中哦！");
					return;
				}
			Document doc1 = DocUtil.clickTextUrl(userKey, doc, "护送押镖");
			int flushNum = Integer.parseInt(doc1.text().charAt(
					doc1.text().indexOf("免费刷新次数：") + 7)
					+ "");
			if (flushNum > 0) {
				DocUtil.clickTextUrl(userKey, doc1, "刷新押镖");
			}
			if (DocUtil.clickTextUrl(userKey, doc1, "启程护送").text()
					.contains("今天没有护送次数了"))
				message.put("护送状态", "今天没有护送次数了!");
			else {
				message.put("护送状态", "押镖已上路！");
			}

		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//只劫 温良恭
	public void 劫镖() {
		// 劫镖主页面，护送时间：10分钟
		Document doc;
		try {
			if (!mainDoc.text().contains("镖行天下"))
				return;
			doc = DocUtil.clickTextUrl(userKey, mainDoc, "镖行天下");
			int num = Integer.parseInt(doc.text().charAt(
					doc.text().indexOf("剩余拦截次数") + 7)
					+ "");
			if (num == 0) {
				message.put("拦截结果", "拦截次数已用完！");
				return;
			}
			String result = "";
			while (num > 0) {
				Document doc1 = DocUtil.clickTextUrl(userKey, doc, "刷新");
				int j = 0;
				while(doc1.text().contains("过于频繁") || !doc1.text().contains("温良恭")) {  //出现繁忙 或者 没刷出温良恭
					System.out.println(j+" 劫镖刷新  没刷出温良恭！");////////////
					Thread.sleep(1500);
					doc1 = DocUtil.clickTextUrl(userKey, doc, "刷新");
					j++;
					if(j > 100) break;
				}
				if(doc1.text().contains("温良恭")) {
					System.out.println(" 劫镖刷新  刷出温良恭！");
					doc1 = Jsoup.parse(DocUtil.substring(doc1.toString(), "温良恭", 3, "返回大乐斗首页"));
				}
				Document doc2 = DocUtil.clickTextUrl(userKey, doc1, "拦截");
				result = DocUtil.substring(doc2.text(), "威望商店", 4, "护送");
				message.put("劫镖奖励" + num, "劫镖奖励：" + result);
				num = Integer.parseInt(doc2.text().charAt(
						doc2.text().indexOf("剩余拦截次数") + 7)
						+ "");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回当前护送押镖的剩余时间(秒)
	 * 
	 * @return
	 */
	public int getLastTime() {
		try {
			if (!mainDoc.text().contains("镖行天下"))
				return 0;
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "镖行天下");
			int minutes = Integer.parseInt(DocUtil.substring(doc.text(),
					"剩余时间：", 5, "分"));
			int seconds = Integer.parseInt(DocUtil.substring(doc.text(), "分",
					1, "秒"));
			return minutes * 60 + seconds;
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 返回剩余送镖次数
	 * 
	 * @return
	 */
	// 并发下，有问题，Exception: read timed out
	public int getNum() {
		try {
			if (!mainDoc.text().contains("镖行天下"))
				return 0;
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "镖行天下");
			int num = Integer.parseInt(doc.text().charAt(
					doc.text().indexOf("剩余护送次数") + 7)
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

}
