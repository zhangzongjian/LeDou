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
	public static JPanel main_jPanel = new JPanel(); //主界面
	public static JPanel taskPanel = new JPanel(); //乐斗任务选项面板
	public static JPanel schedulePanel = new JPanel(); //挑战时间表面板，日程表面板
	public static JPanel timePanel = new JPanel(); //计时结果面板
	public static Container container = jFrame.getContentPane();
	public static JTabbedPane tabs;  //选项卡
	public static JTextArea textArea; //文本框
	public static JTextField input;  //小号链接输入框
	public static JTextField input1;  //供奉物品输入框
	public static JMenuBar userBar;  //小号菜单
	public static JMenu userSelect;	 //小号菜单选项
	public static JMenu 十二宫Select = null;
	public static JMenu 历练Select = null;
	public static JMenu 助阵Select = null;
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
//		textArea1.setBounds(7, 65, 380, 195);
		textArea1.setBounds(7, 65, 380, 327);
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
		tabs.setBounds(7, 262, 379, 130);
		timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabs.addTab("押镖、巅峰倒计时", timePanel);
		
		main_jPanel.setLayout(null);  //设置面板为无布局形式，即手动指定组件位置尺寸
		main_jPanel.add(tag);
		main_jPanel.add(input);
		main_jPanel.add(addUserButton);
		main_jPanel.add(userBar);
		main_jPanel.add(oneKeyButton);
		main_jPanel.add(clearButton);
		main_jPanel.add(textArea1);
//		main_jPanel.add(tabs);
		allUsers.setBounds(125, 33, 78, 23);
		main_jPanel.add(allUsers);
		
		JPanel jPanel = new JPanel();  //总窗口面板
		jPanel.setLayout(null);
		JTabbedPane main_tabs = new JTabbedPane();
		main_tabs.setBounds(0,0,400,450);
		main_tabs.addTab("每日必斗",main_jPanel);
		main_tabs.addTab("乐斗设置", MainUI_init.createTaskPanel());
		main_tabs.addTab("押镖、巅峰倒计时", timePanel);
		jPanel.add(main_tabs);
		container.add(jPanel);
		jFrame.setVisible(true); // 使窗体可视
		jFrame.setSize(400, 450); // 设置窗体大小
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		test();
	}
	
	public void test() {  //打开窗口马上执行一键乐斗
		allUsers.setSelected(true);
		final OneKeyButtonListener o = new OneKeyButtonListener();
		o.actionPerformed(null);
	}
	
}




