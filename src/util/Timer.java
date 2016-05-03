package util;

public class Timer {
    public void schedule(final TimerTask timerTask, final long l) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timerTask.start();
            }
        }).start();
    }
}
