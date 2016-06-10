package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import util.DocUtil;

public class 其他操作集合 extends 乐斗项目 {

	public 其他操作集合(Map<String, String> userKey, Document mainURL) {
		super(userKey, mainURL);
	}

	public void 开锦囊宝箱() {
		try {
			message.put("开锦囊宝箱", "【开锦囊宝箱】");
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "背包");
			Document 锦囊 = DocUtil.clickTextUrl(userKey, doc, "锦囊");
			int count = 0;
			String href = null;
			do {
				href = 锦囊.getElementsByAttributeValueMatching("href", "cmd=use").attr("href");
				if("".equals(href)) {
					//message.put("开锦囊"+count, "操作完成！");
					break;
				}
				锦囊 = DocUtil.clickURL(userKey, href);
				message.put("开锦囊"+count, 锦囊.text().substring(0, 锦囊.text().indexOf("斗豆：")));
				System.out.println( 锦囊.text().substring(0, 锦囊.text().indexOf("斗豆：")));///////
				count ++;
			} while (true);
			
			//其他使用物品
			String[] things = {"巅峰之战一等勋章", "巅峰之战二等勋章", "资源补给箱",
								"魂珠碎片宝箱", "一等武林宝箱", "贡献叉烧包",
								"阅历羊皮卷", "贡献小笼包", "二等武林宝箱"};
			for (String thing : things) {
				doc = DocUtil.clickTextUrl(userKey, mainDoc, "背包");
				do {
					if (doc.toString().contains(thing)) {
						doc = DocUtil.clickTextUrl(userKey, doc, thing);
						if (doc != null) {
							if (doc.toString().contains("cmd=use")) {
								href = doc.getElementsByAttributeValueMatching(
										"href", "cmd=use").attr("href");
								while (true) {
									doc = DocUtil.clickURL(userKey, href);
									if (doc.text().contains("斗豆：")) {
										message.put(
												"使用物品" + count,
												doc.text().substring(
														0,
														doc.text().indexOf(
																"斗豆：")));
										System.out.println(thing+":"+doc.text().substring(0, doc.text().indexOf("斗豆：")));
									} else {
										break;
									}
									count++;
								}
							} else {
								// 该物品不能被使用
								break;
							}
						}
					} else if (doc.text().contains("下页")) {
						doc = DocUtil.clickTextUrl(userKey, doc, "下页");
						continue;
					} else {
						// 背包里没有该物品
						message.put("使用物品"+count, "操作完成！");
						break;
					}
				} while (true);
			}
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void 吃药10() {
		try {
			message.put("吃药10", "【吃药10】");
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "好友");
			String start = "大力丸 速购 使用 剩";
			String end = "次 风之息";
			int 大力丸;
			while(true) {
				大力丸 = new Integer(DocUtil.substring(doc.text(), start, start.length(), end));
				if(大力丸 >= 10) break;
				doc = DocUtil.clickURL(userKey, doc.getElementsByAttributeValueMatching("href", "id=3016")
													.get(1).attr("href"));
			}
			start = "迅捷珠 速购 使用 剩";
			end = "次 活血散";
			int 迅捷珠;
			while(true) {
				迅捷珠 = new Integer(DocUtil.substring(doc.text(), start, start.length(), end));
				if(迅捷珠 >= 10) break;
				doc = DocUtil.clickURL(userKey, doc.getElementsByAttributeValueMatching("href", "id=3017")
													.get(1).attr("href"));
			}
			start = "风之息 速购 使用 剩";
			end = "次 迅捷珠";
			int 风之息;
			while(true) {
				风之息 = new Integer(DocUtil.substring(doc.text(), start, start.length(), end));
				if(风之息 >= 10) break;
				doc = DocUtil.clickURL(userKey, doc.getElementsByAttributeValueMatching("href", "id=3018")
													.get(1).attr("href"));
			}
			start = "活血散 速购 使用 剩";
			end = "次 心魔";
			int 活血散;
			while(true) {
				活血散 = new Integer(DocUtil.substring(doc.text(), start, start.length(), end));
				if(活血散 >= 10) break;
				doc = DocUtil.clickURL(userKey, doc.getElementsByAttributeValueMatching("href", "id=3004")
													.get(1).attr("href"));
			}
			message.put("吃药10", "大力丸("+大力丸+"次)--迅捷珠("+迅捷珠+"次)--风之息("+风之息+"次)--活血散("+活血散+"次)");
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String 微信兑换码 = "";
	public void 微信礼包() {
		try {
			message.put("微信礼包", "【微信礼包】");
			Document doc = DocUtil.clickTextUrl(userKey, mainDoc, "微信礼包");
			System.out.println(微信兑换码);
			Element button = doc.getElementsByTag("anchor").get(0);
			String href = button.getElementsByTag("go").attr("href");
			Map<String, String> parameters = new HashMap<String, String>();
			for(Element e : button.getElementsByTag("postfield")) {
				parameters.put(e.attr("name"), e.attr("value"));
			}
			doc = Jsoup.connect(href).cookies(userKey).data(parameters)
					.data("cdkey", 微信兑换码).post();
			message.put("微信礼包兑换", DocUtil.substring(doc.text(), "【关注微信领礼包】", 9, "当前关注量"));
		} catch (IOException e) {
			message.put("消息", "连接超时，请重试！");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
