package model.impl;

import java.io.IOException;
import java.util.Map;

import model.乐斗项目;

import org.jsoup.nodes.Document;

import util.DocUtil;

public class 门派邀请赛 extends 乐斗项目 {

	public 门派邀请赛(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void doit() {
		try {
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "门派邀请赛");
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
				while(temp.text().contains("挑战消耗：免费挑战")) {
					temp = DocUtil.clickTextUrl(userKey, temp, "开始挑战");
					if(temp.text().contains("战胜了")) 战斗结果 = "(胜)";
					else 战斗结果 = "(负)";
					message.put("战斗结果"+(i++), 战斗结果+DocUtil.substring(temp.text(), "规则", 2, "剩余挑战次数"));
				}
				int 挑战后积分 = Integer.valueOf(DocUtil.substring(temp.text(), "队伍积分：", 5, "下一分段积分").trim());
				message.put("战斗结束", "免费挑战次数已用完（挑战前积分："+挑战前积分+"-->挑战后积分："+挑战后积分+"）！");
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
}
