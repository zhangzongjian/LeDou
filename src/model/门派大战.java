package model;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.DocUtil;
import util.乐斗项目;

public class 门派大战 extends 乐斗项目 {

	public 门派大战(Document mainURL) {
		super(mainURL);
	}

	public void 报名() {
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "门派大战");
			if (!doc.text().contains("未参战")) {
				message.put("报名情况", "已经报过名了！");
				return;
			}
			doc = DocUtil.clickTextUrl(doc, "参战");
			if (doc.text().contains("不在战斗时间内")) {
				message.put("报名情况", "不在战斗时间内！");
			} else {
				message.put("报名情况",
						"报名情况：" + DocUtil.substring(doc.text(), "门派", 0, "变更"));
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}

	public void 领奖() {
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "门派大战");
			doc = DocUtil.clickTextUrl(doc, "领奖");
			doc = Jsoup.parse(DocUtil.substring(doc.toString(), "第一名", 0,
					"返回战场"));
			if (doc.text().contains("领取")) {
				doc = DocUtil.clickTextUrl(doc, "领取");
				message.put("领奖情况",
						DocUtil.substring(doc.text(), "请及时领取", 6, "第一名"));
			} else {
				message.put("领奖情况", "上一期门派大战未参战！");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}
}
