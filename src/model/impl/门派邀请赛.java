package model.impl;

import java.io.IOException;
import java.util.Map;

import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.DocUtil;

public class 门派邀请赛 extends 乐斗项目 {

	public 门派邀请赛(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void doit() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "门派邀请赛");
			领取奖励(doc);
			if(DocUtil.getTextUrlElementList(doc, "组队报名").size() != 0) {
				DocUtil.clickTextUrl(userKey, doc, "组队报名");
				message.put("报名情况", "主动报名成功！报名结束后由系统自动分配队友。");
			}
			else if(doc.text().contains("查看队伍")) {
				doc = DocUtil.clickTextUrl(userKey, doc, "查看队伍");
				String 队伍成员 = DocUtil.substring(doc.text(), "队伍成员", 0, "返回邀请赛首页");
				message.put("报名情况", "无需重复报名！"+ 队伍成员 );
			}
			else if(doc.text().contains("开始挑战")) {
				Document temp = doc;
				int 挑战前积分 = Integer.valueOf(DocUtil.substring(temp.text(), "队伍积分：", 5, "下一分段积分").trim());
				String 战斗结果 = null;
				int i = 0;
				while(!temp.text().contains("已达最大挑战上限")) {
					temp = DocUtil.clickTextUrl(userKey, temp, "开始挑战");
					if(temp.text().contains("战胜了")) {
						战斗结果 = "(胜)";
					}
					else if(temp.text().contains("门派战书不足")) {
						boolean b = 兑换门派材料x1("门派战书");
						if(b == false) 
							break;
						else
							continue;
					}
					else if(temp.text().contains("已达最大挑战上限")) {
						break;
					}
					else {
						战斗结果 = "(负)";
					}
					message.put("战斗结果"+(i++), 战斗结果+DocUtil.substring(temp.text(), "规则", 2, "剩余挑战次数"));
					if(i > 50) break;
				}
				int 挑战后积分 = Integer.valueOf(DocUtil.substring(temp.text(), "队伍积分：", 5, "下一分段积分").trim());
				message.put("战斗结束", "（挑战前积分："+挑战前积分+"-->挑战后积分："+挑战后积分+"）！");
			}
			else {
				message.put("报名情况", "系统正在匹配队友！");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void 领取奖励(Document doc) throws IOException, Exception {
		Document 排行榜 = DocUtil.clickTextUrl(userKey, doc, "排行榜");
		if(DocUtil.getTextUrlElementList(排行榜, "领取奖励").size() != 0) {
			排行榜 = DocUtil.clickTextUrl(userKey, 排行榜, "领取奖励");
			message.put("领取奖励", "上届比赛奖励："+DocUtil.substring(排行榜.text(), "恭喜您获得", 0, "剩余挑战次数"));
		}
	}
	
	public boolean 兑换门派材料x1(String name) throws IOException, Exception {
		Document 门派邀请赛首页 = DocUtil.clickTextUrl(userKey, mainDoc, "门派邀请赛");
		Document 商店 = DocUtil.clickTextUrl(userKey, 门派邀请赛首页, "商店");
		商店 = Jsoup.parse(DocUtil.substring(商店.toString(), name, 0, "返回大乐斗首页"));
		String text = DocUtil.clickTextUrl(userKey, 商店, "兑换").text();
		if(text.contains("积分不足")) {
			return false;
		}
		else {
			return true;
		}
	}
}
