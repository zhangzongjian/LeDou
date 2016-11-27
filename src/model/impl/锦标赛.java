package model.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 锦标赛 extends 乐斗项目 {

	public 锦标赛(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	// 随机赞助
	public void 赞助() {
		if (!mainDoc.text().contains("锦标赛")) {
			message.put("赞助情况", "未开启锦标赛功能！");
			return;
		}
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "锦标赛");
			if (doc.text().contains("免费赞助：0")) {
				message.put("赞助情况",
						DocUtil.substring(doc.text(), "=本届已赞助=", 0, "积分排行"));
				return;
			}
			if (doc.text().contains("领取奖励")) {
				message.put("领奖情况",
						DocUtil.substring(doc.text(), "【百米锦标赛】", 7, "领取奖励"));
				doc = DocUtil.clickTextUrl(userKey, doc, "领取奖励");
			}
			Random random = new Random();
			//按各个选手最近获得冠军次数赞助，次数越多，赞助该选手的概率越大。
			List<Integer> tmp_list = new ArrayList<Integer>();
			Document tmp_doc = null;
			int num; //冠军次数+1，零次冠军则放一个进list
			for(int i = 0; i<5; i++) {
				tmp_doc = DocUtil.clickTextUrl(userKey, doc, "查看选手", i);
				num = Integer.valueOf(DocUtil.substring(tmp_doc.text(), "冠军：", 3, "次 亚军"))+1;
				while(num-- >0) {
					tmp_list.add(i);
				}
			}
			int size = tmp_list.size();
			while(true) {
				doc = DocUtil.clickTextUrl(userKey, doc, "赞助", tmp_list.get(random.nextInt(size)));
				if(doc.text().contains("没有足够的赞助券")) break;
			}
			message.put("赞助情况",
					DocUtil.substring(doc.text(), "=本届已赞助=", 0, "积分排行"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
