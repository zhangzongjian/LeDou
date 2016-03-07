package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 结拜赛 extends 乐斗项目 {

	public 结拜赛(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	// 每周一 12点开始
	public void 报名() {
		try {
			if (!mainDoc.text().contains("结拜")) {
				message.put("报名情况", "未开启结拜功能！");
				return;
			}
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "结拜");
			if (doc.text().contains("已经报名")) {
				message.put("报名情况", "已报名，无需重复操作！");
				return;
			}
			Elements elements = doc.getElementsContainingOwnText("报名");
			for (int i = 0; i < elements.size(); i++) {
				if (!elements.get(i).hasAttr("href")) // 去掉非超链接元素
					elements.remove(i);
				else if (!"报名".equals(elements.get(i).html())) // 去掉文本不完全匹配但包含该文本的元素
					elements.remove(i);
			}
			if (elements.size() == 0) {
				message.put("报名情况", "非报名时间！");
			} else {
				Document temp;
				for (Element e : elements) {
					temp = DocUtil.clickURL(userKey, e.attr("href"));
					if (temp.text().contains("您尚未结拜")) {
						message.put("报名情况", "您尚未结拜，无法报名！");
						return;
					}
					if (temp.text().contains("挑战书")) {
						message.put("报名情况", "报名失败，挑战书不足！");
						return;
					}
					if (temp.text().contains("已经报名")) {
						message.put("报名情况", DocUtil.substring(temp.text(),
								"报名状态", 0, "助威状态"));
						return;
					}
				}
				message.put("报名情况", "报名失败，赛区已满！");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 public void 助威() {
		 if (!mainDoc.text().contains("结拜")) {
			 message.put("报名情况", "未开启结拜功能！");
			 return;
		 }
		 if (day != 4) {
			 message.put("助威情况", "助威：非助威时间！");
			 return;
		 }
		 try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "结拜");
			if(!doc.text().contains("本届未助威")) {
				message.put("助威情况", "助威状态：已助威！");
				return;
			} else {
				doc = DocUtil.clickTextUrl(userKey, doc, "助威");
				doc = DocUtil.clickTextUrl(userKey, DocUtil.clickTextUrl(userKey, doc, "助威"), "确定");
				message.put("助威情况", "助威成功！");
				return;
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }

	// 周六~周日
	public void 助威领奖() {
		if (!mainDoc.text().contains("结拜")) {
			message.put("领奖情况", "助威领奖：未开启结拜功能！");
			return;
		}
		if (day != 6 && day != 0) {
			message.put("领奖情况", "助威领奖：非领奖时间！");
			return;
		}
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "结拜");
			doc = DocUtil.clickTextUrl(userKey, doc, "助威");
			if (doc.text().contains("本届未助威")) {
				message.put("领奖情况", "助威领奖：本届未助威，不能领奖！");
				return;
			} else {
				doc = DocUtil.clickTextUrl(userKey, doc, "领奖");
				message.put(
						"领奖情况",
						"助威领奖："
								+ DocUtil.substring(doc.text(), "领奖", 2,
										"30~50级"));
				return;
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
