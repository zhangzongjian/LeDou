package QQLogin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jsoup.Jsoup;

import util.ObjectUtil;
import util.UserUtil;

public class Test {
	
	public static void main(String[] args) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("hehe", "eeee");
		long start = System.currentTimeMillis();
		for(int i=0; i<20000000; i++) {
			map.get("heheh");
		}
		System.out.println(System.currentTimeMillis()-start);
		
		long start1 = System.currentTimeMillis();
		map.get("heheh");
		for(int i=0; i<2000; i++) {
		}
		System.out.println(System.currentTimeMillis()-start1);
	}
}

