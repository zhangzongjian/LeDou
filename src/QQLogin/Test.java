package QQLogin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

import org.jsoup.Jsoup;

import util.ObjectUtil;
import util.UserUtil;

public class Test {
	
	public static void main(String[] args) throws IOException {
		System.out.println(UserUtil.getSetting());
		//http://fight.pet.qq.com/cgi-bin/petpk?cmd=visit&puin=2099221914&kind=1

		System.out.println(Jsoup.connect("http://fight.pet.qq.com/cgi-bin/petpk?cmd=weiduan2&gb_id=49")
//						.cookie("uin", "o1105451491")
//						.cookie("skey", "@beF7BeO2P")
						.header("Cookie", "ts_uid=3525320928; ts_last=fight.pet.qq.com/newindex.html; wherefrom=31; kaipingusertype=0; pt2gguin=o11023495456; RK=BvnSTXW4N6; ptcz=7e352f83a022e2faae3218a53dbab27cb6f074fe7a78dd89566e6eccab7df633; pgv_pvid=9189854895; pgv_pvi=3199111168; uin_cookie=1102349546; euin_cookie=6629F77CAF07C6142B79BFE8D094C9CD629EEF7CF017BE84; ptui_loginuin=2099221914; ptisp=ctc; verifysession=h019bd4381f76fff50532d9d84506b793c9f0a83476e74e92c2a9144e9ee315ddbfaf16ea07d6af6bcc4fa1e689a2249ac1; uin=o1105451491; skey=@beF7BeO2P; pgv_si=s8840066048; pgv_info=ssid=s3571481344; petuv=df5d60a1fc94b7cb9a31047bc26e9c89")
						.userAgent("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.2)")
						.get().text());
	}
}

