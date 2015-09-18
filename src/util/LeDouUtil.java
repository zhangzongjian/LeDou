package util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LeDouUtil {
	
	/**
	 * 从免登陆链接页面中，获取用户昵称
	 * @return
	 * @throws IOException 
	 */
	public static String getUsername(String mainURL) throws IOException {
		Document doc = Jsoup.connect(mainURL).get();
		String username = "";
		if(doc.text().contains("开通达人"))
			username = MyUtil.substring(doc.text(), "帮友|侠侣", 5, "开通达人");
		else 
			username = MyUtil.substring(doc.text(), "帮友|侠侣", 5, "续费达人");
		return username;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(LeDouUtil.getUsername("http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?cmd=index&channel=0&sid=fybscw20pMc6EMmEIEYSybCPaieZ9Zyg60ea94d80201=="));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
