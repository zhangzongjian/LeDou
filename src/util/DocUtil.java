package util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DocUtil {
	
	//Jsoup访问超时时间。
	private static int time_out = 5000;
	
	/**
	 * 全局cookie，当前选择的小号cookie
	 */
	public static Map<String, String> userKey;
	
	/**
	 * 大乐斗首页链接
	 */
	public static String mainURL = "http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=&sid=&channel=209&g_ut=1&cmd=index";
	
	/**
	 * get方式点击指定链接
	 * @param URL
	 * @return 
	 * @throws IOException
	 */
	public static Document clickURL(Map<String, String> userKey, String URL)
			throws IOException {
		if(userKey == null) userKey = new HashMap<String, String>();
		try {
			Document result = Jsoup.connect(URL).cookies(userKey).timeout(time_out).get();

			//QQ密码:(使用明文密码) 登录 申请号码 反馈建议 手机腾讯网-导航- 搜索 小Q报时(10:43)
            //手机统一登录 您还没有输入密码！ 请选择登录帐号 登 录 一键登录 快速登录历史帐号 注册新帐号 忘了密码？
			int j = 1;
			while (result.text().contains("系统繁忙，请稍后再试") || result.text().contains("使用明文密码") || result.text().contains("手机统一登录")) { 
//				System.err.println("重试次数("+j+")-->详细("+result.text()+")");////////////
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				result = Jsoup.connect(URL).cookies(userKey).timeout(time_out)
						.get();
				j++;
				// 出现登录界面，重试1次
				if(result.text().contains("使用明文密码") || result.text().contains("手机统一登录")) {
					return result;
				}
				// 出现繁忙情况，重试9次
				if (j > 9) {
					return null; // 防止死循环
				}
			}
			return result;
		} catch (SocketTimeoutException e) {
			System.out.println("链接超时-->第" +(++count1)+ "次重试！");
			if (count1 > 10)
				throw e;
			return clickURL(userKey, URL);
		} finally {
			count1 = 0;
		}
	}
	
	/**
	 * 点击指定文本的超链接，并返回点击之后的页面。若这样的超链接有多个，默认点击第一个
	 * @return 不存在这样的链接返回null
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static Document clickTextUrl(Map<String, String> userKey, Document doc, String text) throws IOException, InterruptedException{
		Document result = clickTextUrl(userKey, doc, text, 0);
		return result;
	}
	
	private static int count = 0;
	private static int count1 = 0;
	
	/**
	 * 获取文字链接Element数组
	 * @param doc
	 * @param text
	 * @return
	 */
	public static List<Element> getTextUrlElementList(Document doc, String text) {
		Elements elements = doc.getElementsContainingOwnText(text);
		List<Element> list = new ArrayList<Element>();
		//去掉文本不完全匹配但包含该文本的元素
		//去掉非超链接元素
		for(int i=0; i<elements.size(); i++) {
			if(elements.get(i).hasAttr("href") && text.equals(elements.get(i).html())) {//去掉非超链接元素
				list.add(elements.get(i));
			}
		}
		return list;
	}
	
	/**
	 * 点击指定文本的超链接，并返回点击之后的页面。若这样的超链接有多个，指定点击第index个。-1表示倒数第一个
	 * @return	不存在这样的链接返回null
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static Document clickTextUrl(Map<String, String> userKey, Document doc, String text, int index) throws IOException, InterruptedException{
		//获取含有指定文本的元素节点
		Elements elements = doc.getElementsContainingOwnText(text);
		List<Element> list = new ArrayList<Element>();
		//去掉文本不完全匹配但包含该文本的元素
		//去掉非超链接元素
		for(int i=0; i<elements.size(); i++) {
			if(elements.get(i).hasAttr("href") && text.equals(elements.get(i).html())) {//去掉非超链接元素
				list.add(elements.get(i));
			}
		}
		if(list.size() == 0) {
			return null;
		}
		try {
			if(index < 0) { 
				index = list.size()+index;  // 负数，表示倒数
			}
			//点击链接
			Document result = clickURL(userKey, list.get(index).attr("href"));
			return result;
		} catch(SocketTimeoutException e) {
			System.out.println("链接超时-->第" +(++count)+ "次重试！");
			Thread.sleep(1000);
			if(count > 10) throw e;
			return clickTextUrl(userKey, doc, text, index);
		} finally {
			count = 0;
		}
	}

	/**
	 * 截取指定字符串之间的内容，起始字符串要指定长度
	 * @param begin
	 * @param beginLength
	 * @param end
	 * @return
	 */
	public static String substring(String doc, String begin, int beginLength, String end) {
		String result = "";
		result = doc.substring(doc.indexOf(begin) + beginLength, doc.indexOf(end));
		return result;
	}
	
	public static String substring1(String doc, String begin,int beginLength, String end, int endLength) {
		String result = "";
		result = doc.substring(doc.indexOf(begin)+beginLength, doc.indexOf(end)+endLength);
		return result;
	}
	
	/**
	 * 判断页面内指定文本是否带超链接，否则为纯文本。
	 * @param doc
	 * @param text
	 * @return
	 */
	public static boolean isHref(Document doc, String text) {
		return doc.getElementsContainingOwnText(text).get(0).hasAttr("href");
	}
	
	/**
	 * 返回字符串内的子字符串的个数
	 * @param doc
	 * @param text
	 * @return
	 */
	public static int stringNumbers(String doc, String text) {
		int sum = 0;
		while (doc.indexOf(text) != -1) {
			sum++;
			doc = doc.substring(doc.indexOf(text)+text.length());
		}
		return sum;
	}
}
