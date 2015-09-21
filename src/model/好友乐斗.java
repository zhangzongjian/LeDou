package model;

import java.io.IOException;

import org.jsoup.nodes.Document;

import util.DocUtil;
import util.乐斗项目;

public class 好友乐斗 extends 乐斗项目{

	public 好友乐斗(Document mainURL) {
		super(mainURL);
	}

	public void fight(){
		try {
			Document doc = DocUtil.clickURL(DocUtil.getTextUrl(mainDoc, "好友"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
