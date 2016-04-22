package core;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import util.PrintUtil;

import actionListener.OneKeyButtonListener;

public class MainUI {

    public static boolean isRun = false; //表示，刚打开窗口，未运行过
    
	public static void main(String[] args) {
		MainUI main = new MainUI();
		main.createJFrame("一键乐斗小工具");
		OK:
		while(true) {
		    if(Thread.currentThread().activeCount() == 4 && isRun == true && 乐斗面板.autoCloseCheckBox.isSelected()) {
		        for(int i = 10; i >= -1; i--) {
		            try {
		                PrintUtil.printTitleInfo("系统消息", "任务结束，程序将在 "+i+" 秒后自动关闭！");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
		            if(乐斗面板.autoCloseCheckBox.isSelected() == false) {
		                PrintUtil.printTitleInfo("系统消息", "自动关闭已取消！");
		                isRun = false;
		                continue OK;
		            }
		        }
		        System.exit(0);
		    }
		}
	}

	public static JFrame jFrame;
	
	//创建窗口
	public void createJFrame(String title) {
		jFrame = new JFrame("一键乐斗小工具");
		Container container = jFrame.getContentPane();
		container.add(主面板.create());
		jFrame.setLocation(0, 200); // 窗口起始位置
		jFrame.setResizable(false); //固定大小，不可变
		jFrame.setVisible(true); // 使窗体可视
		jFrame.setSize(400, 450); // 设置窗体大小
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		test();
	}
	
	public void test() {  //打开窗口马上执行一键乐斗
		乐斗面板.allUsersCheckBox.setSelected(true);
		final OneKeyButtonListener o = new OneKeyButtonListener();
		o.actionPerformed(null);
	}
	
}




