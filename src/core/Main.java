package core;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import model.*;

import org.jsoup.nodes.Document;

import util.MyUtil;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		main.CreateJFrame("一键乐斗小工具");
		main.showTime();
	}

	public static JFrame jFrame = new JFrame("一键乐斗小工具");
	public static JPanel jPanel = new JPanel();
	public static Container container = jFrame.getContentPane();
	public static JTextArea textArea;
	public static JTextField input;
	public static JLabel tag;
	public static JLabel showTime;
	public static JScrollPane textArea1;
	public static JButton oneKeyButton;
	public static JButton clearButton;

	public void CreateJFrame(String title) {
		jFrame.setLocation(0, 200); // 窗口起始位置
		// 标签
		tag = new JLabel("免登陆链接");
		// 输入框
		input = new JTextField(12);
		input.setName("免登陆链接");
		// 文本框，JScorllPane：文本框设置滚动条
		textArea = new JTextArea(10, 30);
		textArea.setName("输出框");
		textArea1 = new JScrollPane(textArea);
		// 按钮
		oneKeyButton = new JButton("一键乐斗");
		oneKeyButton.setName("一键乐斗");
		oneKeyButton.addActionListener(new OneKeyButtonListener());
		jPanel.add(oneKeyButton, BorderLayout.NORTH);
		clearButton = new JButton("清屏");
		clearButton.addActionListener(new ClearButtonListener());

		jPanel.add(tag, BorderLayout.NORTH);
		jPanel.add(input, BorderLayout.NORTH);
		jPanel.add(oneKeyButton, BorderLayout.NORTH);
		jPanel.add(clearButton, BorderLayout.NORTH);
		jPanel.add(textArea1, BorderLayout.SOUTH);
		container.add(jPanel);
		jFrame.setVisible(true); // 使窗体可视
		jFrame.setSize(400, 350); // 设置窗体大小
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void showTime() {
		int i = 0;
		showTime = new JLabel("1234" + 1);
		jPanel.add(showTime, BorderLayout.SOUTH);
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			showTime.setText(String.valueOf(i++));
		}
	}
}




