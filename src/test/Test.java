package test;


public class Test {

	public static void main(String[] args) throws NumberFormatException {
		Thread thread = new Thread(new Runnable() {
			int i = 10;
			public void run() {
				while(i > 0) {
					i--;
					System.out.println(i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		},"eee");
		thread.start();
		
		Thread thread1 = new Thread(new Runnable() {
			int i = 10;
			public void run() {
				while(i > 0) {
					i--;
					System.out.println(i+" "+i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		},"eee");
		System.out.println(thread1.getName());
	}
}
