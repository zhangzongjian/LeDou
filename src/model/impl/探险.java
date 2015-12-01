package model.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import model.乐斗项目;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sun.org.apache.bcel.internal.generic.NEW;

import util.DocUtil;

public class 探险 extends 乐斗项目 {

	public 探险(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void doit() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "探险");
			if(doc.text().contains("探险次数：0/1")) {
				message.put("探险情况", "探险次数：0/1");
				return;
			}
			if(doc.text().contains("探险次数：1/1")) {
				doc = DocUtil.clickTextUrl(userKey, doc, "开始探险");
			}
			Random random = new Random();
			int i = 0;
			while (true) {
				// 入口界面
				if (doc.toString().contains("op=explore")) {
					// 鹰眼状态
					if(doc.text().contains("宝箱")) {
						doc = DocUtil.clickTextUrl(userKey, doc, "宝箱");
					}
					// 鹰眼状态
					else if(doc.text().contains("打人")) {
						doc = DocUtil.clickTextUrl(userKey, doc, "打人");
					}
					else if(doc.text().contains("打怪")) {
						doc = DocUtil.clickTextUrl(userKey, doc, "打怪");
					}
					else {
						Elements elements = doc.getElementsByAttributeValueMatching("href", "op=explore");
						int size = elements.size();
						doc = DocUtil.clickURL(userKey, elements.get(random.nextInt(size)).attr("href"));
					}
				}
				// 答题
				else if (doc.toString().contains("op=answer")) {
					Elements elements = doc.getElementsByAttributeValueMatching("href", "op=answer");
					doc = DocUtil.clickURL(userKey, elements.get(random.nextInt(4)).attr("href"));
				}
				else if (doc.text().contains("继续探险")) {
					message.put("探险情况"+(i++), DocUtil.substring(doc.text(), "【斗神遗宝】", 6, "继续探险").replaceAll("查看过程", ""));
					doc = DocUtil.clickTextUrl(userKey, doc, "继续探险");
				}
				else if(doc.text().contains("复活")) {
					message.put("探险情况"+(i++), DocUtil.substring(doc.text(), "【斗神遗宝】", 6, "结束").replaceAll("查看过程", ""));
					doc = DocUtil.clickTextUrl(userKey, doc, "复活");
				}
				// 结束领奖
				else if(doc.text().contains("抵达终点")) {
					doc = DocUtil.clickTextUrl(userKey, doc, "领奖");
					message.put("探险情况"+(i++), DocUtil.substring(doc.text(), "【斗神遗宝】", 6, "开始探险"));
					return;
				}
				if(i > 70) break; //防止死循环
			}
			
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
