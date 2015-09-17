package test;

import java.io.IOException;

import org.jsoup.Jsoup;



public class Test {

	public static void main(String[] args) throws NumberFormatException{
		int n = 30;
		while(n-- >0) {
			Thread thread = new Thread(new Runnable(){
				public void run() {
					try {
						System.out.println(Jsoup.connect("http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=1102349546&sid=Ly8byz0yYhLvJ0ctS1xLvWlytE1rOEp841b484ea0201==&channel=209&g_ut=2&cmd=pve&sub=13").get().text());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();
			try {
				thread.sleep(150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
