package util;

public class Timer {
	/**
	 * 倒计时执行
	 * @param timerTask 任务
	 * @param l 倒计时
	 */
    public void schedule(final TimerTask timerTask, final long l) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Thread(timerTask).start();
            }
        }).start();
    }
    
    /**
     * 定时重复执行
     * @param timerTask 任务
     * @param time 字符串格式的时间（例如 06:00:10）
     */
    public void schedule(final TimerTask timerTask, final String time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
            	long lastTime;
            	while(true) {
	                try {
	                	lastTime = TimeUtil.getSecond(time);
	                	lastTime = lastTime <= 0 ? (3600*24+lastTime)*1000 : lastTime*1000;
	                    Thread.sleep(lastTime);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                new Thread(timerTask).start();
            	}
            }
        }).start();
    }
}
