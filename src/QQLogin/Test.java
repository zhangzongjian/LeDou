package QQLogin;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

public class Test {
	
	public static void main(String[] args) throws IOException {
		String url = "http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=&sid=&channel=209&g_ut=1&cmd=index";
//		String url = "http://m.user.qzone.qq.com/2099221914/main";
		Document doc = Jsoup.connect(url)
								 .cookie("uin", "o1105451491")
								 .cookie("skey", "@f2MN24xT4")
//								 .cookie("uin", "o2099221914")
//								 .cookie("skey", "@23J0uAcgh")
//								 .cookie("uin", "o1102349546")
//								 .cookie("skey", "@o6BiTiKXu")
								 .get();
		System.out.println(doc.text());
		System.out.println("sdf(s（d".split("\\（")[1]);
	}
}
