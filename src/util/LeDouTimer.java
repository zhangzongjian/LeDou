package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LeDouTimer extends Thread{

	private int minutes;
	private int seconds;
	
	public void run() {
		timing(minutes, seconds);
	}
	
	/**
	 * 计时器，时间到则返回true
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public boolean timing(int minutes, int seconds) {
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
	
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		int i = 0;
		Thread t = new Thread(new Runnable(){
			public void run() {
				System.out.println(timing(0, 4));
			}
		});
		t.start();
		while(true) {
			try {
				System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(i++ > 50) break;
		}
	}

	public final int getMinutes() {
		return minutes;
	}

	public final void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public final int getSeconds() {
		return seconds;
	}

	public final void setSeconds(int seconds) {
		this.seconds = seconds;
	}
}
