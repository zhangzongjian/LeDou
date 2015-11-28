package QQLogin;

import java.io.IOException;
import java.text.MessageFormat;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

public class Test {
	
	public static void main(String[] args) throws IOException {
		String url = "http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=&sid=&channel=209&g_ut=1&cmd=index";
//		String url = "http://m.user.qzone.qq.com/2099221914/main";
		Document doc = Jsoup.connect(url)
//								 .cookie("uin", "o1105451491")
//								 .cookie("skey", "@f2MN24xT4")
//								 .cookie("uin", "o2099221914")
//								 .cookie("skey", "@ElxkENvCq")
//								 .cookie("superkey", "ujgbx8T5LgcgOFYofi1QDwyOU3NNjKa4rC9J8M7jVf8_")
								 .cookie("uin", "o1102349546")
								 .cookie("qm_sid", "9d4359c329650f9a4f90023014b87fd4,qLS11bnUzRGtDS3p2RzlvZGZIMm1mUktUUVluMDAxb3NvMXNXdFpuaCpma18")
//								 .cookie("skey", "@o6BiTiKXu")
								 .get();
		System.out.println(doc.text());
		
		System.out.println(MessageFormat.format("该域名{0}被访问了 {1} 次.", "eee" , "5"));
	}
}
