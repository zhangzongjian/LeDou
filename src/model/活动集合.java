package model;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 活动集合 extends 乐斗项目 {

	public 活动集合(Document mainURL) {
		super(mainURL);
	}

	//乐斗作战
	public void 打豆豆() {
		try {
			if(! mainDoc.text().contains("乐斗作战")) {
				message.put("乐斗作战", null);
				return;
			}
			message.put("活动1", "【打豆豆】");
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
				message.put("我要许愿", null);
				return;
			}
			message.put("活动2", "【我要许愿】");
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
				message.put("乐斗菜单", null);
				return;
			}
			message.put("活动3", "【乐斗菜单】");
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
				message.put("补偿礼包", null);
				return;
			}
			message.put("活动4", "【补偿礼包】");
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

	//周周礼包
	public void 周周礼包() {
		try {
			if (!mainDoc.text().contains("周周礼包")) {
				message.put("周周礼包", null);
				return;
			}
			message.put("活动5", "【周周礼包】");
			Document doc = DocUtil.clickTextUrl(mainDoc, "周周礼包");
			Elements 领取 = doc.getElementsContainingOwnText("领取");
			int count = 0;
			Document result;
			for(int i=0; i<领取.size(); i++) {
				if(领取.get(i).hasAttr("href")) {
					count++;
					result = DocUtil.clickURL(领取.get(i).attr("href"));
					message.put("周周礼包"+i, "礼包"+count+"："+DocUtil.substring(result.text(), "【周周有礼】", 6, "活动时间"));
				}
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//登录100QB好礼
	public void 登录100QB好礼() {
		try {
			if (!mainDoc.text().contains("登录100QB好礼")) {
				message.put("登录100QB好礼", null);
				return;
			}
			message.put("活动6", "【登录100QB好礼】");
			Document doc = DocUtil.clickTextUrl(mainDoc, "登录100QB好礼");
			Elements 领取 = doc.getElementsContainingOwnText("领取");
			for(int i=0; i<领取.size(); i++) {
				if(领取.get(i).hasAttr("href")) {
					Document result = DocUtil.clickURL(领取.get(i).attr("href"));
					message.put("登录100QB好礼"+i, DocUtil.substring(result.text(), "【100QB登录好礼】", 11, "。"));
					return; //领取了就结束
				}
			}
			//doc没有领取链接
			message.put("登录100QB好礼", "今天已经领取过了，明天再来哦！");
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//大宝树
	public void 大宝树() {
		try {
			if (!mainDoc.text().contains("大宝树")) {
				message.put("大宝树", null);		//非活动时间
				return;
			}
			message.put("活动7", "【大宝树】");
			Document doc = DocUtil.clickTextUrl(mainDoc, "大宝树");
			if(doc.text().contains("我选定的种子：无")) {
				DocUtil.clickTextUrl(doc, "黄金卷轴");
				message.put("选定种子", "播种成功！");
			}
			Document 浇水 = DocUtil.clickTextUrl(doc, "浇水");
			message.put("浇水", DocUtil.substring(浇水.text(), "【乐斗大宝树】", 7, "1.选择一个你喜爱的种子"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//大宝箱
	public void 大宝箱() {
		try {
			if (!mainDoc.text().contains("大宝箱")) {
				message.put("大宝箱", null);		//非活动时间
				return;
			}
			message.put("活动8", "【大宝箱】");
			Document doc = DocUtil.clickTextUrl(mainDoc, "大宝箱");
			if(doc.text().contains("未激活")) {
				if(doc.text().contains("我的累积进度：100%")) {
					DocUtil.clickTextUrl(doc, "激活宝箱");
					Document result = DocUtil.clickTextUrl(doc, "打开宝箱");
					message.put("打开宝箱", "打开宝箱："+DocUtil.substring(result.text(), "【乐斗大宝箱】", 7, "规则说明"));
					return;
				} 
				else {
					message.put("打开宝箱", "打开宝箱："+DocUtil.substring1(doc.text(), "我的累积进度", 0, "未激活", 3));
					return;
				}
			}
			else {
				Document result = DocUtil.clickTextUrl(doc, "打开宝箱");
				message.put("打开宝箱", "打开宝箱："+DocUtil.substring(result.text(), "【乐斗大宝箱】", 7, "规则说明"));
				return;
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("http://fight.pet.qq.com/cgi-bin/petpk?cmd=weiduan2&gb_id=49")
								.get();
		System.out.println(doc);
	}
}
