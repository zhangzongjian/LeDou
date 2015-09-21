package model;

import java.io.IOException;

import org.jsoup.nodes.Document;

import util.DocUtil;
import util.乐斗项目;

public class 任务 extends 乐斗项目{

	public 任务(Document mainURL) {
		super(mainURL);
	}
	public void finish(){
		try {
			Document doc = DocUtil.clickURL(DocUtil.getTextUrl(mainDoc, "任务"));
			Document doc1 = DocUtil.clickTextUrl(doc, "一键完成任务");
			int num = 5 - doc1.getElementsContainingOwnText("替换任务").size();
			message.put("任务完成情况", "任务完成情况：（"+num+"/5）");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException {
		任务 r = new 任务(DocUtil.clickURL("http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?cmd=index&channel=0&sid=fybscw20pMc6EMmEIEYSybCPaieZ9Zyg60ea94d80201=="));
		r.finish();
		System.out.println(r.getMessage());
	}
}
