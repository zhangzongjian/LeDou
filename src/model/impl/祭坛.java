package model.impl;

import java.io.IOException;
import java.util.Map;

import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 祭坛 extends 乐斗项目 {

	public 祭坛(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 转动轮盘() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "帮派祭坛");
			int i = 0;
			while (true) {
				if(i > 30) {
					break;
				}
				if(doc.text().contains("【偷取|选择帮派】") || doc.text().contains("【掠夺|选择帮派】")) {
					doc = DocUtil.clickTextUrl(userKey, doc, "选择");
					if(doc.text().contains("选择路线")) {
						//指定方向，，向前，向左
						doc = DocUtil.clickTextUrl(userKey, doc, "向右");
						message.put("轮盘转动"+(i++), getResult(doc));
					}
				}
				else if(doc.text().contains("【祭坛轮盘】")) {
					doc = DocUtil.clickTextUrl(userKey, doc, "转动轮盘");
					if(doc.text().contains("转转券不足")) {
						message.put("轮盘转动"+(i++), "转转券不足");
						break;
					}
					else if(doc.text().contains("【祭坛轮盘】")) {
						message.put("轮盘转动"+(i++), getResult(doc));
						continue;
					}
					else {
						continue;
					}
				}
				else if(doc.text().contains("【祭祀奖励】")) {
					doc = DocUtil.clickTextUrl(userKey, doc, "领取奖励");
					message.put("轮盘转动"+(i++), getResult(doc));
				}
				else {
					message.put("轮盘奖励"+(i++), "未知错误！");
				}
				i++;
			}
			
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getResult(Document doc) {
		if(!doc.text().contains("积分兑换") && !doc.text().contains("【祭坛轮盘】")) {
			return "未知错误！";
		}
		else {
			return DocUtil.substring(doc.text(), "积分兑换", 4, "【祭坛轮盘】");
		}
	}
}
