package util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MyUtil {
	
	public volatile static String mainURL = "http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=845309355&sid=38jSkyDdaNWc8KgXR1qJhRwuOkORbbJs326265ab0201==&channel=0&g_ut=1&cmd=index";
	
	/**
	 * get方式点击指定链接
	 * @param URL
	 * @return 
	 * @throws IOException
	 */
	public synchronized static Document clickURL(String URL) throws IOException {
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
		Elements elements = doc.getElementsContainingOwnText(text);
		return elements.attr("href"); 
	}
	
	/**
	 * 获取选中页面指定文本下的超链接，并以get方式点击
	 * @param URL
	 * @return
	 * @throws IOException 
	 */
	public static Document clickTextUrl(Document doc, String text) throws IOException{
		Elements elements = doc.getElementsContainingOwnText(text);
		Document doc1 = Jsoup.connect(elements.attr("href")).get();
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
	
	/**
	 * 延缓当前线程达到计时器的作用，时间到则返回true
	 * @param seconds
	 * @return
	 */
	public static boolean timing(int seconds) {
		//转成毫秒
		int paramLong = (seconds <<3) * 125;
		try {
			Thread.sleep(paramLong);
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
}
