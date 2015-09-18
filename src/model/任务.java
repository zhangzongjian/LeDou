package model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

import util.MyUtil;

public class 任务  {
	private Document mainDoc;

	public 任务(Document mainDoc) {
		this.mainDoc = mainDoc;
	}

	private Map<String, Object> message = new LinkedHashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}

	public void finish(){
		try {
			Document doc = MyUtil.clickURL(MyUtil.getTextUrl(mainDoc, "任务"));
			Document doc1 = MyUtil.clickTextUrl(doc, "一键完成任务");
			int num = 5 - doc1.getElementsContainingOwnText("替换任务").size();
			message.put("任务完成情况", "任务完成情况：（"+num+"/5）");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException {
		任务 r = new 任务(MyUtil.clickURL("http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?cmd=index&channel=0&sid=fybscw20pMc6EMmEIEYSybCPaieZ9Zyg60ea94d80201=="));
		r.finish();
		System.out.println(r.getMessage());
	}
}
