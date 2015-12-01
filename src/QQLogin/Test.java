package QQLogin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

import org.jsoup.Jsoup;

import util.ObjectUtil;

public class Test {
	
	public static void main(String[] args) throws IOException {
		System.out.println(Jsoup.connect("http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=2099221914&B_UID=0&sid=JGxiOfT/Jhvg33OKSpMXfIEyqhyVSyQt7d1f959a0201==&channel=0&g_ut=1&cmd=lottery").get().text());
	}
}

