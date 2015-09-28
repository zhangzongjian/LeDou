package model.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 任务 extends 乐斗项目 {

	public 任务(Document mainURL) {
		super(mainURL);
	}

	public void finish() {
		try {
			// 普通任务
			Document doc = DocUtil.clickTextUrl(mainDoc, "任务");
			if(doc.text().contains("炼丹 替换任务")) {
				DocUtil.clickTextUrl(DocUtil.clickTextUrl(mainDoc, "炼丹"),"炼制");
			}
			if(doc.text().contains("好友切磋 替换任务") || doc.text().contains("挑战陌生人 替换任务")) {
				Document temp = DocUtil.clickTextUrl(mainDoc, "斗友");
				if (doc.text().contains("好友切磋 替换任务"))
					for (int i = 0; i < 7; i++)
						DocUtil.clickTextUrl(temp, "乐斗", i);
				if (doc.text().contains("挑战陌生人 替换任务"))
					for (int i = 0; i < 3; i++)
						DocUtil.clickTextUrl(temp, "乐斗", i);
			}
			if(doc.text().contains("查看好友资料 替换任务")) {
				Document temp = DocUtil.clickTextUrl(mainDoc, "好友");
				for(int i=0; i<5; i++)
					DocUtil.clickURL(temp.getElementsByAttributeValueMatching("href", "from_pf_list=1").get(i).attr("href"));
			}
			Document doc1 = DocUtil.clickTextUrl(doc, "一键完成任务");
			int num = 5 - doc1.getElementsContainingOwnText("替换任务").size();
			message.put("任务完成情况", "任务完成情况：（" + num + "/5）");
			
			
			// 帮派任务
			Document doc2 = DocUtil.clickTextUrl(doc, "帮派任务");
			Document 帮派首页 = DocUtil.clickTextUrl(mainDoc, "我的帮派");
			if(doc2.text().contains("帮友强化 未完成")) {
				Elements es = DocUtil.clickTextUrl(DocUtil.clickTextUrl(mainDoc, "炼丹"),"帮友丹炉").getElementsByAttributeValueMatching("href", "subtype=4");
				for(int i=0; i<3; i++)
					DocUtil.clickURL(es.get(i).attr("href"));
			}
			if(doc2.text().contains("查看帮战 未完成") || doc2.text().contains("帮战冠军 未完成")) {
				DocUtil.clickTextUrl(帮派首页, "帮战");
			}
			if(doc2.text().contains("加速贡献 未完成")) {
				DocUtil.clickURL(DocUtil.clickTextUrl(mainDoc, "好友").getElementsByAttributeValueMatching("href",
						"3038").get(1).attr("href"));
			}
			if (doc2.text().contains("查看帮贡 未完成")) {
				DocUtil.clickTextUrl(帮派首页,"贡献度");
			}
			if (doc2.text().contains("帮派供奉 未完成")) {
				供奉 m = new 供奉(mainDoc);
				m.一键供奉();
			}
			if (doc2.text().contains("帮派修炼 未完成")) {
				Element button = DocUtil.clickTextUrl(帮派首页,"帮修").getElementsByTag("anchor").get(0);
				String href = button.getElementsByTag("go").attr("href");
				Map<String, String> parameters = new HashMap<String, String>();
				for(Element e : button.getElementsByTag("postfield")) {
					parameters.put(e.attr("name"), e.attr("value"));
				}
				Jsoup.connect(href).data(parameters).data("num", "1").post();
			}
			doc2 = DocUtil.clickTextUrl(doc, "帮派任务");
			while (doc2.toString().contains("sub=3")) {  //sub=3，仅领取奖励的链接有，如果不包含，说明不存在领取奖励的链接
				doc2 = DocUtil.clickTextUrl(doc2, "领取奖励");
			}
			message.put("帮派任务完成情况",
					"帮派任务：（" + DocUtil.stringNumbers(doc2.text(), "已领奖")
							+ "/3）");
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
