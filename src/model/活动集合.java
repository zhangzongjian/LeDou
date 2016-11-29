package model;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.DocUtil;

public class 活动集合 extends 乐斗项目 {

	public 活动集合(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	//乐斗作战
	public void 打豆豆() {
		try {
			if(! mainDoc.text().contains("乐斗作战")) {
				message.put("乐斗作战", null);
				return;
			}
			message.put("活动1", "【打豆豆】");
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "乐斗作战");
			Document 领取 = DocUtil.clickTextUrl(userKey, doc, "领取");
			Document 打豆豆 = DocUtil.clickTextUrl(userKey, 领取, "打豆豆", 0);
			if(打豆豆.text().contains("小铜锤数量不够"))
				message.put("打豆豆", "你的小铜锤数量不够了哦！");
			else
				message.put("打豆豆", "打豆豆成功");
			Document temp = 打豆豆;
			for(String s : new String[]{"三五成群","人山人海","万人空巷"}) {
				if(DocUtil.isHref(doc, s)) {
					temp = DocUtil.clickTextUrl(userKey, temp, s);
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
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "我要许愿");
			String [] 奖励列表 = {"传功符", "剑君魂珠", "神兵原石", "真体力"};
			int i = 0;
			for(String 奖励 : 奖励列表) {
				if(doc.text().contains(奖励)) {
					String 领奖链接 = doc.getElementsContainingOwnText(奖励).attr("href");
					int 奖励id = Integer.valueOf(""+领奖链接.charAt(领奖链接.indexOf("npc_id=")+7));
					//许愿
					doc = DocUtil.clickTextUrl(userKey, doc, "许愿", 奖励id);
					if(!doc.text().contains("不能再许愿了")) {
						message.put("许愿领奖"+(i++), "许愿结果："+DocUtil.substring(doc.text(), "【我要许愿】", 6, "规则说明"));
					}
					//领奖
					doc = DocUtil.clickURL(userKey, 领奖链接);
					if(doc.text().contains("已经领取过")) {
						message.put("许愿领奖"+(i++), 奖励+"愿望领奖：已领取");
						continue;
					}
					else {
						message.put("许愿领奖"+(i++), 奖励+"愿望领奖："+DocUtil.substring(doc.text(), "【我要许愿】", 6, "规则说明"));
						break;
					}
				}
				else {
					continue;
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
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "乐斗菜单");
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
					Document 领取结果 = DocUtil.clickTextUrl(userKey, 套餐[i], "点单");
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
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "补偿礼包");
			Document result = DocUtil.clickTextUrl(userKey, doc, "领取补偿礼包");
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
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "周周礼包");
			Elements 领取 = doc.getElementsContainingOwnText("领取");
			int count = 0;
			Document result;
			for(int i=0; i<领取.size(); i++) {
				if(领取.get(i).hasAttr("href")) {
					count++;
					result = DocUtil.clickURL(userKey, 领取.get(i).attr("href"));
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
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "登录100QB好礼");
			Elements 领取 = doc.getElementsContainingOwnText("领取");
			for(int i=0; i<领取.size(); i++) {
				if(领取.get(i).hasAttr("href")) {
					Document result = DocUtil.clickURL(userKey, 领取.get(i).attr("href"));
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
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "大宝树");
			if(doc.text().contains("我选定的种子：无")) {
				DocUtil.clickTextUrl(userKey, doc, "黄金卷轴");
				message.put("选定种子", "播种成功！");
			}
			Document 浇水 = DocUtil.clickTextUrl(userKey, doc, "浇水");
			message.put("浇水", DocUtil.substring(浇水.text(), "【乐斗大宝树】", 7, "1.选择一个你喜爱的种子"));
			if(浇水.text().contains("7颗果实")) {
				Document 收获 = DocUtil.clickTextUrl(userKey, doc, "收获");
				message.put("收获", DocUtil.substring(收获.text(), "【乐斗大宝树】", 7, "1.选择一个你喜爱的种子"));
			}
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
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "大宝箱");
			if(doc.text().contains("未激活")) {
				if(doc.text().contains("我的累积进度：100%")) {
					DocUtil.clickTextUrl(userKey, doc, "激活宝箱");
					Document result = DocUtil.clickTextUrl(userKey, doc, "打开宝箱");
					message.put("打开宝箱", "打开宝箱："+DocUtil.substring(result.text(), "【乐斗大宝箱】", 7, "规则说明"));
					return;
				} 
				else {
					message.put("打开宝箱", "打开宝箱："+DocUtil.substring1(doc.text(), "我的累积进度", 0, "未激活", 3));
					return;
				}
			}
			else {
				Document result = DocUtil.clickTextUrl(userKey, doc, "打开宝箱");
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
	
	//大笨钟
	public void 大笨钟() {
		try {
			if (!mainDoc.text().contains("大笨钟")) {
				message.put("大笨钟", null);		//非活动时间
				return;
			}
			message.put("活动9", "【大笨钟】");
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "大笨钟");
			if(doc == null) {
				doc = DocUtil.clickTextUrl(userKey, mainDoc, "乐斗大笨钟");
			}
			if(doc.text().contains("9点至12点前：   领取")) {
				doc = DocUtil.clickTextUrl(userKey, doc, "领取");
				message.put("大笨钟领奖", "9点至12点前："+DocUtil.substring(doc.text(), "大笨钟送出的礼品。 ", 9, " 9点至12点前"));
			}
			else if(doc.text().contains("12点至15点前：   领取")) {
				doc = DocUtil.clickTextUrl(userKey, doc, "领取");
				message.put("大笨钟领奖", "12点至15点前："+DocUtil.substring(doc.text(), "大笨钟送出的礼品。 ", 9, " 9点至12点前"));
			}
			else if(doc.text().contains("15点至18点前：   领取")) {
				doc = DocUtil.clickTextUrl(userKey, doc, "领取");
				message.put("大笨钟领奖", "15点至18点前："+DocUtil.substring(doc.text(), "大笨钟送出的礼品。 ", 9, " 9点至12点前"));
			}
			else if(doc.text().contains("18点至21点前：   领取")) {
				doc = DocUtil.clickTextUrl(userKey, doc, "领取");
				message.put("大笨钟领奖", "18点至21点前："+DocUtil.substring(doc.text(), "大笨钟送出的礼品。 ", 9, " 9点至12点前"));
			}
			else {
				message.put("大笨钟领奖", "不在领取时间段或者奖励已领完！");
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//九宫宝库
	public void 九宫宝库() {
		try {
			if (!mainDoc.text().contains("九宫宝库")) {
				message.put("九宫宝库", null);		//非活动时间
				return;
			}
			message.put("活动10", "【九宫宝库】");
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "九宫宝库");
			String type = doc.text();
			int num = 0;
			while(true) {
				//进入时，界面为转盘
				if(type.contains("转动轮盘")) {
					doc = DocUtil.clickTextUrl(userKey, doc, "转动轮盘");
					if(doc.text().contains("开始寻宝")) {
						doc = DocUtil.clickTextUrl(userKey, doc, "开始寻宝");
						Random random = new Random();
						//开三次石棺
						for(int i=0; i<3; i++) {
							doc = DocUtil.clickTextUrl(userKey, doc, "石棺", random.nextInt(9-i));
							if(doc.text().contains("抽奖卡不足")) {
								message.put(num+"石棺抽奖"+i, "抽奖卡不足!");
								return;
							}
							else {
								message.put(num+"石棺抽奖"+i, "石棺："+DocUtil.substring(doc.text(), "活动规则", 4, "本次打开石棺"));
							}
						}
						doc = DocUtil.clickTextUrl(userKey, doc, "退出宝库");
					}
					else if(doc.text().contains("剩余抽奖卡：0")) {
						message.put("转动轮盘"+num, "抽奖卡不足！");
						return;
					}
					else {
						message.put("转动轮盘"+num, "转盘："+DocUtil.substring(doc.text(), "活动规则", 4, "活动时间"));
					}
				}
				
				//进入时，界面为石棺
				else if(type.contains("石棺")) {
					Random random = new Random();
					//最多开三次石棺
					int a = 3;
					if(type.contains("消耗0张抽奖卡")) a = 0;
					if(type.contains("消耗1张抽奖卡")) a = 1;
					if(type.contains("消耗2张抽奖卡")) a = 2;
					for(int i=0; i<3-a; i++) {
						doc = DocUtil.clickTextUrl(userKey, doc, "石棺", random.nextInt(9-a));
						if(doc.text().contains("抽奖卡不足")) {
							message.put(num+"石棺抽奖"+i, "抽奖卡不足!");
							return;
						}
						else {
							message.put(num+"石棺抽奖"+i, "石棺："+DocUtil.substring(doc.text(), "活动规则", 4, "本次打开石棺"));
						}
					}
					doc = DocUtil.clickTextUrl(userKey, doc, "退出宝库");
				}
				type = doc.text();
				num++;
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 登录商店
	public void 登录商店() {
		try {
			if (!mainDoc.text().contains("登录商店")) {
				message.put("登录商店", null); // 非活动时间
				return;
			}
			message.put("活动11", "【登录商店】");
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "登录商店");
			int i = 0;
			while(true) {
				doc = DocUtil.clickTextUrl(userKey, doc, "兑换", 2);
				if(doc.text().contains("兑换积分不足")) {
					message.put("兑换"+i, "抱歉，您的兑换积分不足！");
					return;
				} 
				else if(doc.text().contains("恭喜您兑换成功")) {
					message.put("兑换"+i, DocUtil.substring(doc.text(), "恭喜您", 0, "中体力"));
				}
				i++;
				//防止死循环
				if(i > 50) break;
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 摇摇乐
	public void 摇摇乐() {
		try {
			if (!mainDoc.text().contains("摇摇乐")) {
				message.put("摇摇乐", null); // 非活动时间
				return;
			}
			message.put("活动12", "【摇摇乐】");
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "摇摇乐");
			int i = 0;
			while (true) {
				doc = DocUtil.clickTextUrl(userKey, doc, "转动");
				if (doc.text().contains("转动次数不足")) {
					message.put("转动" + i, "转动次数不足，不能领取！");
					return;
				} else if(doc.text().contains("恭喜你")) {
					message.put("转动" + i,
							DocUtil.substring(doc.text(), "恭喜你", 0, "1.活动时间"));
				}
				i++;
				// 防止死循环
				if (i > 50)
					break;
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 做任务领斗币
	public void 做任务领斗币() {
		try {
			if (!mainDoc.text().contains("做任务领斗币")) {
				message.put("做任务领斗币", null); // 非活动时间
				return;
			}
			message.put("活动13", "【做任务领斗币】");
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "做任务领斗币");
			int i = 0;
			while (doc.toString().contains("act_task")) {
				doc = DocUtil.clickURL(userKey, doc.getElementsByAttributeValueMatching("href", "act_task").attr("href"));
				message.put("领取奖励"+(i++), doc.text().substring(0, doc.text().indexOf("【勤劳能致富~做任务领斗币】")));
			}
			message.put("领取情况", "领取情况："+DocUtil.stringNumbers(doc.text(), "已完成")+"/10");
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 好礼步步升
	public void 好礼步步升() {
		try {
			if (!mainDoc.text().contains("好礼步步升")) {
				message.put("好礼步步升", null); // 非活动时间
				return;
			}
			message.put("活动14", "【好礼步步升】");
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "好礼步步升");
			doc = DocUtil.clickTextUrl(userKey, doc, "领取");
			if(doc != null)
				message.put("好礼领取", DocUtil.substring(doc.text(), "【好礼步步升】", 7, "1、活动期间"));
			else 
				message.put("好礼领取", "今日奖励已领取过了！");
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 登录有礼
	public void 登录有礼() {
		try {
			if (!mainDoc.text().contains("登录有礼")) {
				message.put("登录有礼", null); // 非活动时间
				return;
			}
			message.put("活动15", "【登录有礼】");
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "登录有礼");
			doc = DocUtil.clickTextUrl(userKey, doc, "领取",-1);
			if(doc != null)
				message.put("登录有礼领取",
						DocUtil.substring(doc.text(), "【登录有礼，小花费，大收获~】", 15, "1.活动时间"));
		    else
				message.put("登录有礼领取", "今日奖励已领取过了！");
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 幸运转盘
		public void 幸运转盘() {
			try {
				if (!mainDoc.text().contains("幸运转盘")) {
					message.put("幸运转盘", null); // 非活动时间
					return;
				}
				message.put("活动16", "【幸运转盘】");
				
				Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "幸运转盘");
				int count = 0;
				while(!doc.text().contains("今日剩余转动次数：0")) {
					doc = DocUtil.clickTextUrl(userKey, doc, "转动转盘");
					if(doc.text().contains("恭喜"))
						message.put("幸运转盘转动"+count, DocUtil.substring(doc.text(), "恭喜", 0, "返回大乐斗首页"));
					else
						message.put("幸运转盘转动"+count, "未找到结果！");
					count++;
				}
				message.put("幸运转盘转动"+count, "今日剩余转动次数：0");
			} catch (IOException e) {
				message.put("消息", "连接超时，请重试！");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		// 猜单双
		public void 猜单双() {
			try {
				if (!mainDoc.text().contains("猜单双")) {
					message.put("猜单双", null); // 非活动时间
					return;
				}
				message.put("活动17", "【猜单双】");
				Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "猜单双");
				Random random = new Random();
				for(int i = 1; i<=5; i++) {
					if(DocUtil.getTextUrlElementList(doc, "双数").size() != 0) {
						if(random.nextInt(10) % 2 == 0) {
							doc = DocUtil.clickTextUrl(userKey, doc, "双数");
						}
						else {
							doc = DocUtil.clickTextUrl(userKey, doc, "单数");
						}
						message.put("猜单双"+i, i+"、"+DocUtil.substring(doc.text(), "】", 1, "点击数字板"));
					} else {
						message.put("猜单双"+i, "明天再来！");
						break;
					}
				}
			} catch (IOException e) {
				message.put("消息", "连接超时，请重试！");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 斗币领取
		public void 斗币领取() {
			try {
				if (!mainDoc.text().contains("斗币领取")) {
					message.put("斗币领取", null); // 非活动时间
					return;
				}
				message.put("活动18", "【斗币领取】");
				Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "斗币领取");
				if(DocUtil.getTextUrlElementList(doc, "领取").size() > 0) {
					doc = DocUtil.clickTextUrl(userKey, doc, "领取");
					message.put("领斗币", DocUtil.substring(doc.text(), "等你拿】", 4, "！"));
				}
				message.put("斗币领取进度", DocUtil.substring(doc.text(), "达10000斗币！", 9, "返回大乐斗首页").trim());
			} catch (IOException e) {
				message.put("消息", "连接超时，请重试！");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		// 暑期大礼包
		public void 暑期大礼包() {
			try {
				if (!mainDoc.text().contains("暑期大礼包")) {
					message.put("暑期大礼包", null); // 非活动时间
					return;
				}
				message.put("活动19", "【暑期大礼包】");
				Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "暑期大礼包");
				if(DocUtil.getTextUrlElementList(doc, "领取").size() > 0) {
					doc = DocUtil.clickTextUrl(userKey, doc, "领取");
					message.put("领取暑假礼包", DocUtil.substring(doc.text(), "领取规则", 4, "专属礼包"));
				}
				doc = Jsoup.parse(doc.toString().substring(doc.toString().indexOf("==登录礼包==")));
				message.put("暑假礼包领取进度", DocUtil.substring(doc.text(), "暑期登录礼包1", 0, "返回大乐斗首页").trim());
			} catch (IOException e) {
				message.put("消息", "连接超时，请重试！");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 帮派祈福
		public void 帮派祈福() {
			try {
				if (!mainDoc.text().contains("帮派祈福")) {
					message.put("帮派祈福", null); // 非活动时间
					return;
				}
				message.put("活动20", "【帮派祈福】");
				Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "帮派祈福");
				int i = 0;
				while(true) {
					doc = DocUtil.clickTextUrl(userKey, doc, "祈福");
					if(doc.text().contains("您的祈福令不足")) {
						message.put("祈福"+(i++), "抱歉，您的祈福令不足！");
						break;
					}
					else if(doc.text().contains("不在祈福的时间范围内")) {
						message.put("祈福"+(i++), "不在祈福的时间范围内！");
						break;
					}
					else {
						message.put("祈福"+(i++), doc.text().substring(0, doc.text().indexOf("无兄弟")).trim());
					}
				}
				message.put("祈福进度", DocUtil.substring(doc.text(), "个人成长值", 0, "领取成长值礼包"));
				Document 领奖 = DocUtil.clickTextUrl(userKey, doc, "领取成长值礼包");
				i = 0;
				while(DocUtil.getTextUrlElementList(领奖, "领取").size() > 0) {
					领奖 = DocUtil.clickTextUrl(userKey, 领奖, "领取");
					message.put("祈福领奖"+(i++), DocUtil.substring(领奖.text(), "【成长值礼包】", 7, "个人成长值"));
				}
			} catch (IOException e) {
				message.put("消息", "连接超时，请重试！");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 晃动的天平
		public void 晃动的天平() {
			try {
				if (!mainDoc.text().contains("晃动的天平")) {
					message.put("晃动的天平", null); // 非活动时间
					return;
				}
				message.put("活动21", "【晃动的天平】");
				float 左右重量差;
				String temp;
				int i = 0;
				Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "晃动的天平");
				doc = DocUtil.clickTextUrl(userKey, doc, "开始游戏");
				if(doc.text().contains("未达到20点")) {
					message.put("天平晃动"+(i++), "您的活跃度还未达到20点，请继续努力");
					return;
				}
				while (true) {
					if(i>30) {
						break;
					}
					doc = DocUtil.clickTextUrl(userKey, doc, "增加道具");
					temp = doc.text();
					左右重量差 = Float.valueOf(temp.substring(temp.indexOf("重量=") + 3, temp.lastIndexOf("Kg")));
					if(左右重量差 >= 2) {
						continue;
					}
					else {
						doc = DocUtil.clickTextUrl(userKey, doc, "领取物品");
						message.put("天平晃动" + (i++), DocUtil.substring(doc.text(), "活动规则", 4, "开始游戏").trim());
						message.put("天平晃动" + (i++), "左侧重量-右侧重量="+左右重量差+"Kg");
						break;
					}
				}
				
			} catch (IOException e) {
				message.put("消息", "连接超时，请重试！");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 全民拼图
		public void 全民拼图() {
			try {
				if (!mainDoc.text().contains("全民拼图")) {
					message.put("全民拼图", null); // 非活动时间
					return;
				}
				message.put("活动22", "【全民拼图】");
				Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "全民拼图");
				if(doc.text().contains("请选择您所在的区域")) {
					doc = DocUtil.clickTextUrl(userKey, doc, "广东");
				}
				if(doc.text().contains("您可以去完成拼图啦")) {
					Elements es = doc.getElementsByAttributeValueMatching("href", "region=");
					Document temp;
					for(int i = 0; i<es.size(); i++) {
						temp = DocUtil.clickURL(userKey, es.get(i).attr("href"));
						DocUtil.clickTextUrl(userKey, temp, "乐斗");
					}
					doc = DocUtil.clickTextUrl(userKey, doc, "徽章奖励");
					String s = "恭喜你获得了国旗徽章！";
					if(doc.text().contains( s )) {
						message.put("拼图领奖", "完成拼图：" + s );
					}
					else {
						message.put("拼图领奖", "拼图已经完成了！");
					}
				}
			} catch (IOException e) {
				message.put("消息", "连接超时，请重试！");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 全民大礼包
		public void 全民大礼包() {
			try {
				if (!mainDoc.toString().contains("cmd=reservation")) {
					message.put("全民大礼包", null); // 非活动时间
					return;
				}
				message.put("活动23", "【全民大礼包】");
				Elements es = mainDoc.getElementsByAttributeValueMatching("href", "cmd=reservation");
				Document doc = DocUtil.clickURL(userKey, es.attr("href"));
				int num = DocUtil.getTextUrlElementList(doc, "领取").size();
				if(num == 0) {
					message.put("全民礼包", "已领取过了！");
					return;
				}
				for(int i = 0; i<num; i++) {
					doc = DocUtil.clickTextUrl(userKey, doc, "领取");
					message.put("全民礼包"+i, DocUtil.substring(doc.text(), "领取规则", 4, "专属礼包"));
				}
			} catch (IOException e) {
				message.put("消息", "连接超时，请重试！");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 极地冒险
		public void 极地冒险() {
			try {
				if (!mainDoc.text().contains("极地冒险")) {
					message.put("极地冒险", null); // 非活动时间
					return;
				}
				message.put("活动24", "【极地冒险】");
				Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "极地冒险");
				List<Element> es = DocUtil.getTextUrlElementList(doc, "领铁铲");
				for(Element e : es) {
					DocUtil.clickURL(userKey, e.attr("href")).text();
				}
				int i = 0;
				int x = 2;
				int y = 3;
				int lastY = -1;
				String url = null;
				Elements tmp1 = null;
				Elements tmp2 = null;
				while (true) {
					i++;
					if( i > 10 ) {
						message.put("寻宝"+i, "游戏失败！请手动完成！");
						break;
					}
					//筛选包含r=2的元素
					tmp1 = doc.getElementsByAttributeValueMatching("href", "r="+x); 
					//筛选不包含c=3的元素
					tmp2 = doc.getElementsByAttributeValueMatching("href", "^((?!c="+y+").)*$");
					//筛选既包含r=2又包含c=3的元素
					tmp1.removeAll(tmp2);
					if(tmp1.size() == 0) {
						if(y == 7) x = x+1;
						else y = y+1;
						continue;
					}
					url = tmp1.attr("href");
					doc = DocUtil.clickURL(userKey, url);
					if(doc.text().contains("左边")) {
						if(lastY == -1 || lastY > y) {
							lastY = y; //标注边界值
							y = y/2;
						}
						else {
							y = (y+lastY)/2;
						}
						continue;
					}
					else if(doc.text().contains("右边")) {
						if(lastY == -1 || lastY < y) {
							lastY = y; //标注边界值
							y = (y+7)/2;							
						}
						else {
							y = (y+lastY)/2;
						}
						continue;
					}
					else if(doc.text().contains("上边或者下边")) {
						if(x == 0) {
							x = 4; continue;
						}
						x--; continue;
					}
					else if(doc.text().contains("没有足够的铁铲")) {
						message.put("寻宝"+i, "游戏失败！没有足够的铁铲！");
						break;
					}
					else if(doc.text().contains("挖到了宝箱")) {
						message.put("寻宝"+i, DocUtil.substring(doc.text(), "活动规则", 4, "菜菜提示"));
						break;
					}
					if(doc.text().contains("已经寻找到宝藏了")) {
						message.put("寻宝"+i, "已经寻找到宝藏了");
						break;
					}
				}
			} catch (IOException e) {
				message.put("消息", "连接超时，请重试！");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
