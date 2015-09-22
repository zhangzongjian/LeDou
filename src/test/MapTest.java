package test;

import java.io.IOException;

import org.jsoup.Jsoup;


public class MapTest {

	public static void main(String[] args) {
		Thread t = new Thread(new Runnable(){
			public void run() {
				try {
					String doc = Jsoup.connect("http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=447721727&B_UID=447721727&sid=Ac_9eIykC6KdfNmsG-kfxJKd&channel=0&g_ut=3&cmd=question&sub=1").get().text();
					System.out.println(doc);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t1 = new Thread(new Runnable(){
			public void run() {
				try {
					String doc = Jsoup.connect("http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=1102349546&B_UID=0&sid=Ly8byz0yYhLvJ0ctS1xLvWlytE1rOEp841b484ea0201==&channel=209&g_ut=3&cmd=question&sub=1").get().text();
					System.out.println(doc);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable(){
			public void run() {
				try {
					while(true) {
//						Thread.sleep(1);
						String doc = Jsoup.connect("http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=1625986264&B_UID=0&sid=fybscw20pMc6EMmEIEYSybCPaieZ9Zyg60ea94d80201==&channel=0&g_ut=3&cmd=question&sub=1").get().text();
						System.out.println(doc);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
//		t.start();
//		t1.start();
		t2.start();
	}

}
