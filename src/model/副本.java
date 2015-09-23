package model;

import java.io.IOException;

import org.jsoup.nodes.Document;

import util.DocUtil;
import util.乐斗项目;

public class 副本 extends 乐斗项目 {

	public 副本(Document mainURL) {
		super(mainURL);
	}

	public void 挑战() {
		try {
			// 副本主页面
			Document doc = DocUtil.clickURL(DocUtil.getTextUrl(mainDoc, "副本"));
			// int num =
			// Integer.parseInt(doc.text().substring(doc.text().indexOf("征战书")+8,doc.text().lastIndexOf("本")));
			int num1 = Integer.parseInt(doc.text().substring(
					doc.text().indexOf("今天剩余次数") + 7, doc.text().indexOf("/")));
			// int num2 =
			// Integer.parseInt(doc.text().substring(doc.text().indexOf("本周付费次数")+7,doc.text().lastIndexOf("/")));
			// 未组队
			if (doc.text().contains("请先通过“好友组队”组建2名队友")) {
				message.put("挑战状态", "未组队，请手动组队再来尝试！");
			} else if (num1 > 0) {
				DocUtil.clickTextUrl(doc, "快速挑战");
				message.put("挑战状态", "快速挑战完成！");
			} else {
				message.put("挑战状态", "今日剩余次数0！");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}
}
