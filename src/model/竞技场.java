package model;

import java.io.IOException;

import org.jsoup.nodes.Document;

import util.DocUtil;
import util.乐斗项目;

public class 竞技场 extends 乐斗项目{

	public 竞技场(Document mainURL) {
		super(mainURL);
	}

	public void 挑战(){
		try {
			if(!mainDoc.text().contains("竞技场")) {
				message.put("挑战情况", "未开启竞技场功能");
				return;
			}
			Document doc = DocUtil.clickURL(DocUtil.getTextUrl(mainDoc, "竞技场"));
			if(doc.text().contains("赛季中")) {
				int num = Integer.parseInt(doc.text().charAt(doc.text().indexOf("今日已挑战")+6)+"");
				Document doc1 = doc;
				while(num < 5) {
					doc1 = DocUtil.clickTextUrl(doc, "免费挑战");
					message.put("挑战情况"+num, DocUtil.substring(doc1.text(), "竞技点商店", 5, "赛季状态"));
					num = Integer.parseInt(doc1.text().charAt(doc1.text().indexOf("今日已挑战")+6)+"");
				}
				if(DocUtil.isHref(doc1, "领取奖励")) {
					DocUtil.clickTextUrl(doc1, "领取奖励");
					message.put("领奖情况", "已领取奖励！");
				}
				if(num == 5)
					message.put("挑战情况", "挑战次数已用完！");
			} 
			else {
				message.put("挑战情况", "非赛季时间!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
