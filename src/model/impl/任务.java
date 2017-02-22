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

	public 任务(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void finish() {
		try {
			// 普通任务
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "任务");
			if(doc.text().contains("好友切磋 替换任务") || doc.text().contains("挑战陌生人 替换任务") || doc.text().contains("挑战好友 替换任务")) {
				Document temp = DocUtil.clickTextUrl(userKey, mainDoc, "斗友");
				if (doc.text().contains("好友切磋 替换任务"))
					for (int i = 0; i < 7; i++)
						DocUtil.clickTextUrl(userKey, temp, "乐斗", i);
				if (doc.text().contains("挑战陌生人 替换任务"))
					for (int i = 0; i < 3; i++)
						DocUtil.clickTextUrl(userKey, temp, "乐斗", i);
				if (doc.text().contains("挑战好友 替换任务")) 
					for (int i = 0; i < 4; i++)
						DocUtil.clickTextUrl(userKey, temp, "乐斗", i);
			}
			if(doc.text().contains("查看好友资料 替换任务")) {
				Document temp = DocUtil.clickTextUrl(userKey, mainDoc, "好友");
				for(int i=0; i<5; i++)
					DocUtil.clickURL(userKey, temp.getElementsByAttributeValueMatching("href", "from_pf_list=1").get(i).attr("href"));
			}
//			if(doc.text().contains("强化神装 替换任务")) {
//				if(doc.toString().contains("id=116"))
//					DocUtil.clickTextUrl(userKey, DocUtil.clickTextUrl(userKey, mainDoc, "神装"), "升级");
//			}
//			if(doc.text().contains("武器专精 替换任务")) {
//				if(doc.toString().contains("id=114"))
//					DocUtil.clickTextUrl(userKey, DocUtil.clickTextUrl(userKey, mainDoc, "专精"), "升级");
//				if(doc.toString().contains("id=115"))
//					DocUtil.clickTextUrl(userKey, DocUtil.clickTextUrl(userKey, DocUtil.clickTextUrl(userKey, mainDoc, "专精"), "武器栏"), "升级");
//			}
			if(doc.text().contains("兵法研习 替换任务")) {
				Document temp = DocUtil.clickTextUrl(userKey, mainDoc, "兵法");
					DocUtil.clickTextUrl(userKey, temp, "研习");
			}
			Document doc1 = DocUtil.clickTextUrl(userKey, doc, "一键完成任务");
			int num = 5 - doc1.getElementsContainingOwnText("替换任务").size();
			message.put("任务完成情况", "任务完成情况：（" + num + "/5）");
			
			
			
			
			// 帮派任务
			Document 帮派首页 = DocUtil.clickTextUrl(userKey, mainDoc, "我的帮派");
			if (帮派首页.text().contains("尚未加入任何帮派")|| 帮派首页.text().contains("你还没有申请加入任何帮派")) {
				message.put("帮派任务完成情况", "尚未加入任何帮派");
				return;
			}
			Document doc2 = DocUtil.clickTextUrl(userKey, 帮派首页, "任务");
			if(doc2.text().contains("帮友强化 未完成")) {
				Elements es = DocUtil.clickTextUrl(userKey, DocUtil.clickTextUrl(userKey, mainDoc, "炼丹"),"帮友丹炉").getElementsByAttributeValueMatching("href", "subtype=4");
				for(int i=0; i<5; i++)
					DocUtil.clickURL(userKey, es.get(i).attr("href"));
			}
			if(doc2.text().contains("查看帮战 未完成") || doc2.text().contains("帮战冠军 未完成")) {
				DocUtil.clickTextUrl(userKey, 帮派首页, "帮战");
			}
			if(doc2.text().contains("加速贡献 未完成")) {
				DocUtil.clickURL(userKey, DocUtil.clickTextUrl(userKey, mainDoc, "好友").getElementsByAttributeValueMatching("href",
						"3038").get(1).attr("href"));
			}
			if (doc2.text().contains("查看帮贡 未完成")) {
				DocUtil.clickTextUrl(userKey, 帮派首页,"贡献度");
			}
			if (doc2.text().contains("查看要闻 未完成")) {
				DocUtil.clickTextUrl(userKey, 帮派首页,"帮派要闻");
			}
			if (doc2.text().contains("帮派供奉 未完成")) {
				供奉 m = new 供奉(userKey, mainDoc);
				m.一键供奉();
			}
			if (doc2.text().contains("帮派修炼 未完成")) {
				//修炼帮修技能的优先次序。
				int [] 技能列表 = new int[]{0,1,5,4,8,  2,3,6,7}; //分别为 命中、闪避、减伤、加伤、生命
				int index = 0;
				for(int i=0; i<3; i++) {//任务要求，修炼三次
					Element button = DocUtil.clickTextUrl(userKey, 帮派首页,"帮修").getElementsByTag("anchor").get(技能列表[index]);
					String href = button.getElementsByTag("go").attr("href");
					Map<String, String> parameters = new HashMap<String, String>();
					for(Element e : button.getElementsByTag("postfield")) {
						parameters.put(e.attr("name"), e.attr("value"));
					}
					Document document = Jsoup.connect(href).cookies(userKey).data(parameters).data("num", "1").post();
					if(document.text().contains("你需要提升帮派等级来让你进行下一步的修炼")) {
						if(index < 技能列表.length-1) {
							index++;
							i--;
						}
						else {
							index = 技能列表.length-1;
						}
					}
				}
			}
			if (doc2.text().contains("帮派留言 未完成") || doc2.text().contains("查看留言 未完成")) {
				Element button = DocUtil.clickTextUrl(userKey, 帮派首页,"留言").getElementsByTag("anchor").get(0);
				String href = button.getElementsByTag("go").attr("href");
				Map<String, String> parameters = new HashMap<String, String>();
				for(Element e : button.getElementsByTag("postfield")) {
					parameters.put(e.attr("name"), e.attr("value"));
				}
				Jsoup.connect(href).cookies(userKey).data(parameters).data("word", "任务！").post();
			}
			doc2 = DocUtil.clickTextUrl(userKey, doc, "帮派任务");
			while (doc2.toString().contains("sub=3")) {  //sub=3，仅领取奖励的链接有，如果不包含，说明不存在领取奖励的链接
				doc2 = DocUtil.clickTextUrl(userKey, doc2, "领取奖励");
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
