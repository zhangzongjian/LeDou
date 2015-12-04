package util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
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
	public static Document clickURL(Map<String, String> userKey, String URL) throws IOException {
		Document result = Jsoup.connect(URL).cookies(userKey).timeout(time_out).get();
		//QQ密码:(使用明文密码) 登录 申请号码 反馈建议 手机腾讯网-导航- 搜索 小Q报时(10:43)
		if(result.text().contains("使用明文密码")) {  //奇葩滴冒出这种情况，重试一次
			result = Jsoup.connect(URL).cookies(userKey).timeout(time_out).get();
		}
		int j = 0;
		while(result.text().contains("很抱歉，系统繁忙，请稍后再试")) {  //出现繁忙情况，重试
			System.out.println(j+" "+result.text());////////////
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			result = Jsoup.connect(URL).cookies(userKey).timeout(time_out).get();
			j++;
			if(j > 100) break; //防止死循环
		}
		return result;
	}
	/**
	 * 获取选中页面指定文本下的超链接
	 * @param URL
	 * @return
	 * @throws IOException 
	 */
	public static String getTextUrl(String URL, String text) throws IOException{
		Document doc = Jsoup.connect(URL).cookies(userKey).timeout(time_out).get();
	    Elements elements = doc.getElementsContainingOwnText(text);
		return elements.attr("href"); 
	}
	
	/**
	 * 点击指定文本的超链接，并返回点击之后的页面。若这样的超链接有多个，默认点击第一个
	 * @return
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static Document clickTextUrl(Map<String, String> userKey, Document doc, String text) throws IOException, InterruptedException{
		Document result = clickTextUrl(userKey, doc, text, 0);
		//QQ密码:(使用明文密码) 登录 申请号码 反馈建议 手机腾讯网-导航- 搜索 小Q报时(10:43)
		if(result.text().contains("使用明文密码")) {  //奇葩滴冒出这种情况，重试一次
			result = clickTextUrl(userKey, doc, text, 0);
		}
		int j = 0;
		while(result.text().contains("很抱歉，系统繁忙，请稍后再试")) {  //出现繁忙情况，重试
			System.out.println(j+" "+text+" "+result.text());////////////
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			result = DocUtil.clickTextUrl(userKey, doc, text, 0);
			j++;
			if(j > 100) break; //防止死循环
		}
		return result;
	}
	
	/**
	 * 点击指定文本的超链接，并返回点击之后的页面。若这样的超链接有多个，指定点击第index个。-1表示倒数第一个
	 * @return
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
		try {
			if(index < 0) index = list.size()+index;  // 负数，表示倒数
			return Jsoup.connect(list.get(index).attr("href")).cookies(userKey).timeout(time_out).get();
		} catch(SocketTimeoutException e) {
			Thread.sleep(2000);  //一次超时异常，缓两秒，再试一次
			try {
				return Jsoup.connect(list.get(index).attr("href")).cookies(userKey).timeout(time_out).get();
			} catch(SocketTimeoutException e1) {
				e1.printStackTrace();
				Thread.sleep(1000);	//再次超时异常，缓一秒，再试一次
				return Jsoup.connect(list.get(index).attr("href")).cookies(userKey).timeout(time_out).get();
			}
		////////////////////待测试，indexoutof
		} catch(IndexOutOfBoundsException e) {
			System.out.println("clickTextUrl:");
			System.out.println("text: "+text+" index: "+index+" list: "+list);
			e.printStackTrace();
			return Jsoup.connect(list.get(index).attr("href")).cookies(userKey).timeout(time_out).get();
		}
	}

	/**
	 * 截取指定字符串之间的内容，起始字符串要指定长度
	 * @param begin
	 * @param beginLength
	 * @param end
	 * @return
	 */
	public static String substring(String doc, String begin,int beginLength, String end) {
		try {
			String result = "";
			result = doc.substring(doc.indexOf(begin)+beginLength, doc.indexOf(end));
			return result;
		////////////////////待测试，indexoutof
		} catch(IndexOutOfBoundsException e) {
			System.out.println("DocUtil.substring: ");
			System.out.println("begin: "+begin+" end: "+end+" doc: "+doc);
			e.printStackTrace();
			return "";
		}
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
