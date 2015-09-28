package core;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import actionListener.AddUserButtonListener;
import actionListener.ClearButtonListener;
import actionListener.OneKeyButtonListener;

public class MainUI {

	public static void main(String[] args) {
		MainUI main = new MainUI();
		main.createJFrame("一键乐斗小工具");
	}
	
	//UI 组件，需要随时获取信息的，设置成全局静态变量
	public static JFrame jFrame = new JFrame("一键乐斗小工具"); 
	public static JPanel jPanel = new JPanel(); //总面板
	public static JPanel taskPanel = new JPanel(); //乐斗任务选项面板
	public static JPanel schedulePanel = new JPanel(); //挑战时间表面板，日程表面板
	public static JPanel timePanel = new JPanel(); //计时结果面板
	public static Container container = jFrame.getContentPane();
	public static JTabbedPane tabs;  //选项卡
	public static JTextArea textArea; //文本框
	public static JTextField input;  //输入框
	public static JMenuBar userBar;  //小号菜单
	public static JMenu userSelect;	 //小号菜单选项
	public static List<JCheckBox> taskList = new ArrayList<JCheckBox>();
	public static JCheckBox allUsers = new JCheckBox("所有小号");
	
	//创建主窗口
	public void createJFrame(String title) {
		//窗口
		jFrame.setLocation(0, 200); // 窗口起始位置
		jFrame.setResizable(false); //固定大小，不可变
		// 标签
		JLabel tag = new JLabel("添加小号");
		tag.setBounds(7, 3, 58, 25);
		// 输入框
		input = new JTextField(23);
		input.setName("免登陆链接");
		input.setText("请复制免登陆链接到这里！");
		input.setBounds(65, 5, 255, 23);
		// 文本框，JScorllPane：文本框设置滚动条
		textArea = new JTextArea();
		JScrollPane textArea1 = new JScrollPane(textArea);
		textArea1.setBounds(7, 65, 380, 120);
		// 按钮
		JButton oneKeyButton = new JButton("一键乐斗");
		oneKeyButton.setBounds(204, 33, 115, 23);
		oneKeyButton.addActionListener(new OneKeyButtonListener());
		JButton clearButton = new JButton("清屏");
		clearButton.setBounds(325, 33, 63, 23);
		clearButton.addActionListener(new ClearButtonListener());
		JButton addUserButton = new JButton("添加");
		addUserButton.addActionListener(new AddUserButtonListener());
		addUserButton.setBounds(325, 5, 63, 23);
		//菜单控件
		userBar = new JMenuBar(); //菜单
		userSelect = new JMenu("切换小号：（未添加）"); //菜单选项组
		userBar.setBounds(7, 33, 119, 23);
		MainUI_init.loadUserList();
		//面板
		JScrollPane timePanelScroll = new JScrollPane(timePanel); //计时结果面板,滚动窗形式
		timePanelScroll.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		//选项卡
		tabs = new JTabbedPane(); //选项卡
		tabs.setBounds(7, 190, 379, 200);
		tabs.addTab("乐斗选项", MainUI_init.createTaskPanel());
		timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabs.addTab("计时器", timePanel);
		
		jPanel.setLayout(null);  //设置面板为无布局形式，即手动指定组件位置尺寸
		jPanel.add(tag);
		jPanel.add(input);
		jPanel.add(addUserButton);
		jPanel.add(userBar);
		jPanel.add(oneKeyButton);
		jPanel.add(clearButton);
		jPanel.add(textArea1);
		jPanel.add(tabs);
		
		allUsers.setBounds(125, 33, 78, 23);
		jPanel.add(allUsers);
		
		container.add(jPanel);
		jFrame.setVisible(true); // 使窗体可视
		jFrame.setSize(400, 430); // 设置窗体大小
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void test() {
		for(int i = 0;i<100; i++) {
			timePanel.add(new JLabel(i+" "));
		}
	}
	
}




