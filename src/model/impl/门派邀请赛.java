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
			else {
				message.put("报名情况", "未知结果！");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
