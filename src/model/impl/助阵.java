package model.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.乐斗项目;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import util.DocUtil;

public class 助阵 extends 乐斗项目 {

	public 助阵(Document mainURL) {
		super(mainURL);
	}

	/**
	 * 选择指定组合，指定技能提升
	 * @param name 助阵组合
	 * @param index 组合内第index个技能，从1开始
	 */
	public void doit(String name, int index){
		name = "以柔克刚";
		index = 1;
		try {
			Document doc = DocUtil.clickTextUrl(mainDoc, "今日活跃度");
			if(doc.text().contains("16.[3/3]")) {
				message.put("提升助阵情况", "今日已经提升助阵3次了！");
				return;
			}
			doc = DocUtil.clickTextUrl(doc, "提升助阵属性");
			if(!DocUtil.isHref(doc, name)) {
				message.put("提升助阵情况", "暂未开启该助阵组合！");
				return;
			}
			doc = DocUtil.clickTextUrl(doc, name);
			if(doc.getElementsByTag("anchor").size() < index) {
				message.put("提升助阵情况", "暂未激活该技能！");
				return;
			}
			Element button = doc.getElementsByTag("anchor").get(index-1);  //第index个"提升"按钮，该按钮提交表单
			String href = button.getElementsByTag("go").attr("href");
			Map<String, String> parameters = new HashMap<String, String>();
			for(Element e : button.getElementsByTag("postfield")) {
				parameters.put(e.attr("name"), e.attr("value"));
			}
			for(int i=0; i<3; i++) {
				doc = Jsoup.connect(href)
							.data(parameters)
							.data("times", "1")
							.post();
				
				if(doc.text().contains("无字天书数量不足")) {
					message.put("提升助阵情况", "无字天书数量不足！");
					return;
				}
			}
			message.put("提升助阵情况", "成功提升助阵3次！");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
