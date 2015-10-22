package model;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.DocUtil;

public class 活动集合 extends 乐斗项目 {

	public 活动集合(Document mainURL) {
		super(mainURL);
	}

	//乐斗作战
	public void 打豆豆() {
		try {
			if(! mainDoc.text().contains("乐斗作战")) {
				message.put("乐斗作战", "非活动时间！");
				return;
			}
			Document doc = DocUtil.clickTextUrl(mainDoc, "乐斗作战");
			Document 领取 = DocUtil.clickTextUrl(doc, "领取");
			Document 打豆豆 = DocUtil.clickTextUrl(领取, "打豆豆", 0);
			if(打豆豆.text().contains("小铜锤数量不够"))
				message.put("打豆豆", "你的小铜锤数量不够了哦！");
			else
				message.put("打豆豆", "打豆豆成功");
			Document temp = 打豆豆;
			for(String s : new String[]{"三五成群","人山人海","万人空巷"}) {
				if(DocUtil.isHref(doc, s)) {
					temp = DocUtil.clickTextUrl(temp, s);
					message.put(s, s+":"+DocUtil.substring(temp.text(), "活动规则", 4, "小铜锤"));
				}
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//我要许愿
	public void 我要许愿() {
		try {
			if(! mainDoc.text().contains("我要许愿")) {
				message.put("我要许愿", "非活动时间！");
				return;
			}
			Document doc = DocUtil.clickTextUrl(mainDoc, "我要许愿");
			//传功符奖励
			if(! doc.text().contains("虔诚度：7/7   许愿   传功符")) {
				Document 传功符 = Jsoup.parse(DocUtil.substring(doc.toString(), "剑君", 2, "传功符"));
				doc = DocUtil.clickTextUrl(传功符, "许愿");
				if(doc.text().contains("已经许愿过了"))
					message.put("我要许愿", "您今天已经许愿过了！");
				else
					message.put("我要许愿", "许愿成功！");
				//许愿之后验证一下是否满7次了
				if(doc.text().contains("虔诚度：7/7   许愿   传功符")) {
					Document result = DocUtil.clickURL(doc.getElementsContainingOwnText("传功符").attr("href"));
					if(! result.text().contains("已经领取过"))
						message.put("许愿领奖", DocUtil.substring(result.text(), "【我要许愿】", 6, "规则说明"));
				}
			}
			//魂珠奖励
			else if(! doc.text().contains("虔诚度：7/7   许愿   剑君魂珠")) {
				Document 魂珠 = Jsoup.parse(DocUtil.substring(doc.toString(), "月璇", 2, "剑君魂珠"));
				doc = DocUtil.clickTextUrl(魂珠, "许愿");
				if(doc.text().contains("已经许愿过了"))
					message.put("我要许愿", "您今天已经许愿过了！");
				else
					message.put("我要许愿", "许愿成功！");
				if(doc.text().contains("虔诚度：7/7   许愿   剑君魂珠")) {
					Document result = DocUtil.clickURL(doc.getElementsContainingOwnText("剑君魂珠").attr("href"));
					if(! result.text().contains("已经领取过"))
						message.put("许愿领奖", DocUtil.substring(result.text(), "【我要许愿】", 6, "规则说明"));
				}
			}
			//体力药水奖励
			else if(! doc.text().contains("虔诚度：7/7   许愿   真体力")) {
				Document 体力药水 = Jsoup.parse(DocUtil.substring(doc.toString(), "小王子", 2, "真体力"));
				doc = DocUtil.clickTextUrl(体力药水, "许愿");
				if(doc.text().contains("已经许愿过了"))
					message.put("我要许愿", "您今天已经许愿过了！");
				else
					message.put("我要许愿", "许愿成功！");
				if(doc.text().contains("虔诚度：7/7   许愿   真体力")) {
					Document result = DocUtil.clickURL(doc.getElementsContainingOwnText("真体力").attr("href"));
					if(! result.text().contains("已经领取过"))
							message.put("许愿领奖", DocUtil.substring(result.text(), "【我要许愿】", 6, "规则说明"));
				}
			}
			//许愿进度
			String 进度 = DocUtil.substring(doc.text(), "虔诚奖励。", 5, "返回大乐斗首页");
			message.put("许愿进度", "许愿进度："+进度);
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//乐斗菜单
	public void 乐斗菜单() {
		try {
			if (!mainDoc.text().contains("乐斗菜单")) {
				message.put("乐斗菜单", "非活动时间！");
				return;
			}
			Document doc = DocUtil.clickTextUrl(mainDoc, "乐斗菜单");
			Document[] 套餐 = new Document[5];
			//套餐优先级为数组下标
			套餐[2] = Jsoup.parse(DocUtil.substring(doc.toString(), "套餐一", 0, "套餐二"));
			套餐[3] = Jsoup.parse(DocUtil.substring(doc.toString(), "套餐二", 0, "套餐三"));
			套餐[4] = Jsoup.parse(DocUtil.substring(doc.toString(), "套餐三", 0, "套餐四"));
			套餐[1] = Jsoup.parse(DocUtil.substring(doc.toString(), "套餐四", 0, "套餐五"));
			套餐[0] = Jsoup.parse(DocUtil.substring(doc.toString(), "套餐五", 0, "返回大乐斗首页"));
			int i;
			for(i = 0; i<5; i++) {
				if(DocUtil.isHref(套餐[i], "点单")) {
					Document 领取结果 = DocUtil.clickTextUrl(套餐[i], "点单");
					message.put("套餐领取", "点单："+DocUtil.substring(领取结果.text(), "套餐哦！", 4, "套餐一"));
					break;
				}
			}
			if(i > 4) message.put("套餐领取", "这期乐斗菜单已全部领完！");
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//补偿礼包
	public void 补偿礼包() {
		try {
			if (!mainDoc.text().contains("补偿礼包")) {
				message.put("补偿礼包", "非活动时间！");
				return;
			}
			Document doc = DocUtil.clickTextUrl(mainDoc, "补偿礼包");
			Document result = DocUtil.clickTextUrl(doc, "领取补偿礼包");
			message.put("补偿礼包", DocUtil.substring(result.text(), "【补偿礼包】", 6, "领取时间"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=985488587&B_UID=0&sid=d9G0RkJR6fdploFdvQb/jxLw3y4iXe7q3abd5ccb0201==&channel=0&g_ut=2&cmd=menuact").get();
		doc = Jsoup.parse(DocUtil.substring(doc.toString(), "套餐四", 0, "套餐五"));
		System.out.println(DocUtil.isHref(doc, "点单"));
	}
}
