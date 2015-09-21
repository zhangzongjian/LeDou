package model;

import java.io.IOException;

import org.jsoup.nodes.Document;

import util.DocUtil;
import util.乐斗项目;

public class 答题 extends 乐斗项目{
	public 答题(Document mainURL){
		super(mainURL);
	}
	public void answer(){
		try {
			if(mainDoc.text().contains("答题")){
				Document doc = DocUtil.clickURL(DocUtil.getTextUrl(mainDoc, "答题"));
				DocUtil.clickURL(DocUtil.getTextUrl(doc, "选择"));
				message.put("success", "OK!");
			} 
			else {
				message.put("error", "已经答题过了!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
