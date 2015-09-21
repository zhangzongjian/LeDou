package util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DocUtil {
	
	/**
	 * 全局免登陆链接，当前选择的链接
	 */
	public static String mainURL;
	
	/**
	 * get方式点击指定链接
	 * @param URL
	 * @return 
	 * @throws IOException
	 */
	public static Document clickURL(String URL) throws IOException {
		Document doc = Jsoup.connect(URL).get();
		return doc;
	}
	/**
	 * 获取选中页面指定文本下的超链接
	 * @param URL
	 * @return
	 * @throws IOException 
	 */
	public static String getTextUrl(String URL, String text) throws IOException{
		Document doc = Jsoup.connect(URL).get();
	    Elements elements = doc.getElementsContainingOwnText(text);
		return elements.attr("href"); 
	}
	
	/**
	 * 获取选中页面指定文本下的超链接
	 * @param URL
	 * @return
	 * @throws IOException 
	 */
	public static String getTextUrl(Document doc, String text){
		//获取含有指定文本的元素节点
				Elements elements = doc.getElementsContainingOwnText(text);
				for(int i=0; i<elements.size(); i++) {
					if(!elements.get(i).hasAttr("href")) //去掉非超链接元素
						elements.remove(i);
					if(!text.equals(elements.get(i).html())) //去掉文本不完全匹配但包含该文本的元素
						elements.remove(i);
				}
		return elements.attr("href"); 
	}
	
	/**
	 * 点击指定文本的超链接，并返回点击之后的页面。若这样的超链接有多个，默认点击第一个
	 * @return
	 * @throws IOException 
	 */
	public static Document clickTextUrl(Document doc, String text) throws IOException{
		//获取含有指定文本的元素节点
		Elements elements = doc.getElementsContainingOwnText(text);
		for(int i=0; i<elements.size(); i++) {
			if(!elements.get(i).hasAttr("href")) //去掉非超链接元素
				elements.remove(i);
			if(!text.equals(elements.get(i).html())) //去掉文本不完全匹配但包含该文本的元素
				elements.remove(i);
		}
		Document doc1 = Jsoup.connect(elements.get(0).attr("href")).get();
		return doc1;
	}
	
	/**
	 * 点击指定文本的超链接，并返回点击之后的页面。若这样的超链接有多个，指定点击第index个
	 * @return
	 * @throws IOException 
	 */
	public static Document clickTextUrl(Document doc, String text, int index) throws IOException{
		//获取含有指定文本的元素节点
		Elements elements = doc.getElementsContainingOwnText(text);
		for(int i=0; i<elements.size(); i++) {
			if(!elements.get(i).hasAttr("href")) //去掉非超链接元素
				elements.remove(i);
			if(!text.equals(elements.get(i).html())) //去掉文本不完全匹配但包含该文本的元素
				elements.remove(i);
		}
		Document doc1 = Jsoup.connect(elements.get(index).attr("href")).get();
		return doc1;
	}

	/**
	 * 截取指定字符串之间的内容，起始字符串要指定长度
	 * @param begin
	 * @param beginLength
	 * @param end
	 * @return
	 */
	public static String substring(String doc, String begin,int beginLength, String end) {
		String result = "";
		result = doc.substring(doc.indexOf(begin)+beginLength, doc.indexOf(end));
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
	 * 延缓当前线程达到计时器的作用，时间到则返回true
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public static boolean timing(int minutes, int seconds) {
		//转成毫秒
		int paramLong = (minutes << 4) * 3750 + (seconds <<3) * 125;
		try {
			Thread.sleep(paramLong);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
}
