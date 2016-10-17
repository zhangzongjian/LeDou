package util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	/**
	 * 定时任务线程
	 */
	public static Timer timer = new Timer();
	
	/**
	 * 计算当前时间，离指定时间(二十四小时制)剩余多少秒
	 * @param time 指定时间  例：16:00:05
	 * @return
	 */
	public static long getSecond(String time) {
		String[] t = time.split(":");
		int hour = Integer.parseInt(t[0]);
		int minute = Integer.parseInt(t[1]);
		int second = Integer.parseInt(t[2]);
		long lastSeconds = (hour * 3600 + minute * 60 + second * 1)-(new Date().getHours()*3600+new Date().getMinutes()*60+new Date().getSeconds());
		return lastSeconds;
	}
	
	//模拟一天只有60秒
	public static long getSecondtest(String time) {
		String[] t = time.split(":");
		int hour = Integer.parseInt(t[0]);
		int minute = Integer.parseInt(t[1]);
		int second = Integer.parseInt(t[2]);
		long lastSeconds = (second * 1)-(new Date().getSeconds());
		return lastSeconds;
	}
	
	/**
	 * 获取指定日期的上一天
	 * @param date
	 * @return
	 */
	public static Date getLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 指定时间调用对象的指定函数
	 * @param object
	 * @param objectParams 构造参数
	 * @param methodName 函数
	 * @param params 函数参数
	 * @param time 例：16:00:05
	 */
	public static Object doMethodAtTime(final Object object, final Object[] objectParams, final String methodName, final Object[] methodParams, String time) {
		long paramLong = 1000 * getSecond(time); 
		timer.schedule(new TimerTask() {
			public void run() {
				doMethodResult = ObjectUtil.doObjectMethod(object, objectParams, methodName, methodParams);
			}
		}, paramLong < 0 ? 0 : paramLong);
		return doMethodResult;
	}
	
	private static Object doMethodResult = null;
}



