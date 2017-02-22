package model.impl;

import java.io.IOException;

import java.util.Map;import model.乐斗项目;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 幻境 extends 乐斗项目 {

	public static String object = "乐斗村";
	public 幻境(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 挑战() {
		try {
			String name = object;
			if (!mainDoc.text().contains("幻境")) {
				message.put("挑战情况", "未开启幻境功能");
				return;
			}
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "幻境");
			int 挑战前积分 = -1; //初始状态，表示未查询
			if(doc.toString().contains("exchange")) {
				挑战前积分 = Integer.valueOf(DocUtil.substring(DocUtil.clickTextUrl(userKey, doc, "兑换").text(), "我的幻境积分：", 7, "--1级翡翠石--").trim());
			} 
			int i = 0;
			while(true) {
				i++;
				if(i > 50) break;
				if(doc.text().contains("领取奖励")) {
					doc = DocUtil.clickTextUrl(userKey, doc, "领取奖励");
					message.put("领取奖励"+i, "额外奖励："+DocUtil.substring(doc.text(), "获得", 0, "特殊属性说明"));
					continue;
				}
				if(doc.toString().contains("op=fight")) {
					doc = DocUtil.clickURL(userKey, doc.getElementsByAttributeValueMatching("href", "op=fight").attr("href"));
					if(doc.text().contains("挑战已结束")) {
						message.put("挑战情况", "挑战失败！");
					}
					else {
						message.put("挑战情况"+i, DocUtil.substring(doc.text(), "累积星数：", 0, "特殊属性说明"));
					}
				}
				if(doc.text().contains("返回飘渺幻境") && DocUtil.isHref(doc, "返回飘渺幻境")) {
					doc = DocUtil.clickTextUrl(userKey, doc, "返回飘渺幻境");
					continue;
				}
				if(doc.text().contains("挑战次数：0/1")) {
					message.put("挑战结果", "挑战次数已用完！");
					break;
				}
				if(doc.text().contains("挑战次数：1/1")) {
					if(doc.text().contains(name)){
						doc = DocUtil.clickTextUrl(userKey, doc, name);
						continue;
					}
					else {
						Elements 幻境列表 = doc.getElementsByAttributeValueMatching("href", "op=start");
						int num = 幻境列表.size();
						//未开启该场景，自动挑战解锁幻境的倒数第二个。
						if(num == 1) num = 2; //如果只解锁第一个场景，那么只能打第一个场景了。
						doc = DocUtil.clickURL(userKey, 幻境列表.get(num-2).attr("href"));
						message.put("挑战情况1", "未开启该场景，自动挑战解锁幻境的倒数第二个！");
						continue;
					}
				}
			}
			积分统计(doc, 挑战前积分);
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void 积分统计(Document doc, int 挑战前积分) throws NumberFormatException, IOException, InterruptedException {
		int 挑战后积分 = Integer.valueOf(DocUtil.substring(DocUtil.clickTextUrl(userKey, doc, "兑换").text(), "我的幻境积分：", 7, "--1级翡翠石--").trim());
		if(挑战前积分 == -1) {
			message.put("挑战前后积分", "（挑战前后积分："+挑战后积分+"(后) - 未找到结果(前) = 未找到结果）");
		}
		else {
			message.put("挑战前后积分", "（挑战前后积分："+挑战后积分+"(后) - "+挑战前积分+"(前) = "+(挑战后积分 - 挑战前积分)+"）");
		}
	}
}
