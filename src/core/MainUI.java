package core;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import actionListener.OneKeyButtonListener;

public class MainUI {

	public static void main(String[] args) {
		MainUI main = new MainUI();
		main.createJFrame("一键乐斗小工具");
	}
	
	//创建窗口
	public void createJFrame(String title) {
		JFrame jFrame = new JFrame("一键乐斗小工具");
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




