package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 活跃度 extends 乐斗项目 {

	public 活跃度(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 领取() {
		try {
			Document doc = DocUtil.clickURL(userKey, mainDoc.getElementsByAttributeValueMatching("href", "cmd=liveness").attr("href"));
			// 帮派活跃领奖
			if (doc.text().contains("帮派总活跃")) {
				Elements es = doc.getElementsContainingOwnText("领取");
				if (es.hasAttr("href")) {
					Document temp = DocUtil.clickURL(userKey, es.attr("href"));
					message.put(
							"帮派活跃领奖情况",
							"帮派活跃："
									+ temp.text().substring(0,
											temp.text().indexOf("你的职位")));
				} else
					message.put("帮派活跃领奖情况", "帮派活跃：已经领取过了！");
			}
			// 每日活跃
			Document temp1 = DocUtil.clickTextUrl(userKey, doc, "礼包");
			Elements es1 = temp1.getElementsContainingOwnText("领取");
			for (int i = 0; i < es1.size(); i++) {
				if (!es1.get(i).hasAttr("href")) // 去掉非超链接元素
					es1.remove(i);
				if (!"领取".equals(es1.get(i).html())) // 去掉文本不完全匹配但包含该文本的元素
					es1.remove(i);
				else {
					temp1 = DocUtil.clickURL(userKey, es1.get(i).attr("href"));
					message.put("每日活跃领奖情况" + i,
							DocUtil.substring(temp1.text(), "】", 1, "1."));
				}
			}
			message.put("活跃度总分", "（"+DocUtil.substring(temp1.text(), "【", 1, "】")+"）");
			//佣兵签到
			佣兵位签到();
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void 佣兵位签到() throws IOException, InterruptedException {
		Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "佣兵");
		if(doc.text().contains("增加 免费激活")) {
			doc = DocUtil.clickTextUrl(userKey, doc, "免费激活");
			doc = DocUtil.clickTextUrl(userKey, doc, "签到");
			message.put("佣兵签到", "佣兵签到："+DocUtil.substring(doc.text(), "【免费激活佣兵携带位】", 11, "1.20级以上玩家"));
		}
	}
}
