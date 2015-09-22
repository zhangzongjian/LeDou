package model;

import java.io.IOException;

import org.jsoup.nodes.Document;

import util.DocUtil;
import util.乐斗项目;

public class 任务 extends 乐斗项目{

	public 任务(Document mainURL) {
		super(mainURL);
	}
	public void finish(){
		try {
			//普通任务
			Document doc = DocUtil.clickURL(DocUtil.getTextUrl(mainDoc, "任务"));
			Document doc1 = DocUtil.clickTextUrl(doc, "一键完成任务");
			int num = 5 - doc1.getElementsContainingOwnText("替换任务").size();
			message.put("任务完成情况", "任务完成情况：（"+num+"/5）");
			//帮派任务
			Document doc2 = DocUtil.clickTextUrl(doc, "帮派任务");
			while(doc2.text().contains("领取奖励")) {
				doc2 = DocUtil.clickTextUrl(doc2, "领取奖励");
			}
			System.out.println(doc2.text());
			message.put("帮派任务完成情况", "帮派任务：（"+DocUtil.stringNumbers(doc2.text(), "已领奖")+"/3）");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
