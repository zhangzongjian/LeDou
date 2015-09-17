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
	}

	public static JFrame jFrame = new JFrame("一键乐斗小工具");
	public static JPanel jPanel = new JPanel();
	public static Container container = jFrame.getContentPane();
	public static JTextArea textArea;
	public static JTextField input;
	public static JLabel tag;
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

	public void OneKeyButton() {

	}

}

// 一键乐斗按钮响应
class OneKeyButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		long startTime = System.currentTimeMillis();
		String mainURL = Main.input.getText();
		ArrayList<String> list = new ArrayList<String>();
		list.add("镖行天下");
		list.add("副本");
		list.add("答题");
		list.add("竞技场");
		list.add("斗神塔");
		list.add("矿洞");
		list.add("任务");
		list.add("乐斗boss");
		list.add("历练");
		list.add("领取每日奖励");
		list.add("十二宫");
		list.add("许愿");
		try {
			Document mainDoc = MyUtil.clickURL(mainURL);
			if (list.contains("镖行天下")) {
				镖行天下 m = new 镖行天下(mainDoc);
				m.护送押镖();
				m.劫镖();
				Main.textArea.append("【镖行天下】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("答题")) {
				答题 m = new 答题(mainDoc);
				m.answer();
				Main.textArea.append("【答题】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("斗神塔")) {
				斗神塔 m = new 斗神塔(mainDoc);
				m.查看掉落情况();
				m.挑战();
				Main.textArea.append("【斗神塔】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("副本")) {
				副本 m = new 副本(mainDoc);
				m.挑战();
				Main.textArea.append("【副本】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("竞技场")) {
				竞技场 m = new 竞技场(mainDoc);
				m.挑战();
				Main.textArea.append("【竞技场】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("矿洞")) {
				矿洞 m = new 矿洞(mainDoc);
				m.挑战();
				Main.textArea.append("【矿洞】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("乐斗boss")) {
				乐斗boss m = new 乐斗boss(mainDoc);
				m.一键挑战();
				Main.textArea.append("【乐斗boss】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("历练")) {
				历练 m = new 历练(mainDoc);
				m.挑战();
				Main.textArea.append("【历练】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("领取每日奖励")) {
				领取每日奖励 m = new 领取每日奖励(mainDoc);
				m.领取();
				Main.textArea.append("【领取每日奖励】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("任务")) {
				任务 m = new 任务(mainDoc);
				m.finish();
				Main.textArea.append("【任务】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("十二宫")) {
				十二宫 m = new 十二宫(mainDoc);
				m.挑战(); // 没问题
				m.扫荡(); // 待测试
				Main.textArea.append("【十二宫】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("许愿")) {
				许愿 m = new 许愿(mainDoc);
				m.xuYuan();
				Main.textArea.append("【许愿】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}

			long endTime = System.currentTimeMillis();
			Main.textArea
					.append("(耗时：" + (endTime - startTime) / 1000.0 + "秒)\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

// 清屏按钮响应
class ClearButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		Main.textArea.setText("");
	}
}
