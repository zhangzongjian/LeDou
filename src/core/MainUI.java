package core;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import util.DocUtil;
import util.Task;
import util.UserUtil;
import actionListener.AddUserButtonListener;
import actionListener.ClearButtonListener;
import actionListener.DeleteUserButtonListener;
import actionListener.OneKeyButtonListener;
import actionListener.SelectAllTaskListener;
import actionListener.SelectUserButtonListener;

public class MainUI {

	public static void main(String[] args) {
		MainUI main = new MainUI();
		main.createJFrame("一键乐斗小工具");
	}
	
	//UI 组件，需要随时获取信息的，设置成全局静态变量
	public static JFrame jFrame = new JFrame("一键乐斗小工具"); 
	public static JPanel jPanel = new JPanel(); //总面板
	public static JPanel taskPanel = new JPanel(); //乐斗任务选项面板
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
		loadUserList();
		//面板
		JScrollPane timePanelScroll = new JScrollPane(timePanel); //计时结果面板,滚动窗形式
		timePanelScroll.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		//选项卡
		tabs = new JTabbedPane(); //选项卡
		tabs.setBounds(7, 190, 379, 200);
//		tabs.addTab("乐斗选项", new JScrollPane(createTaskPanel()));
		tabs.addTab("乐斗选项", createTaskPanel());
		timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabs.addTab("定时器", timePanel);
		
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
	
	
	@SuppressWarnings("unchecked")
	//打开窗口时，将已存储的小号加载到选择菜单中
	public void loadUserList() {
		try {
			Map<String, Object> usersMap;
			if(UserUtil.getSettingByKey("小号") == null) {
				userBar.add(userSelect);
				return;
			}
			usersMap = (Map<String, Object>) UserUtil.getSettingByKey("小号");
			Set<String> usernames = usersMap.keySet();
			for (String username : usernames) {
				JMenuItem select = new JMenuItem("选择");
				select.addActionListener(new SelectUserButtonListener(username));
				JMenuItem delete = new JMenuItem("删除");
				delete.addActionListener(new DeleteUserButtonListener(username));
				JMenu userMenu = new JMenu(username); //控件显示的名称
				userMenu.setName(username); //控件的名称
				userMenu.add(select);
				userMenu.add(delete);
				userSelect.add(userMenu); // 添加到菜单列表
				userSelect.setText("切换小号："+username);
				DocUtil.mainURL = usersMap.get(username).toString();
			}
			userBar.add(userSelect);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//创建乐斗选项多选框面板
	@SuppressWarnings("unchecked")
	public JPanel createTaskPanel() {
		try {
			taskPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			List<String> dataTask = (List<String>) UserUtil.getSettingByKey("任务列表");
			List<String> list = new ArrayList<String>();
			list.add(Task.任务);
			list.add(Task.历练);
			list.add(Task.副本);
			list.add(Task.许愿);
			list.add(Task.踢馆);
			list.add(Task.掠夺);
			list.add(Task.供奉);
			list.add(Task.分享);
			list.add(Task.矿洞);
//			list.add(Task.助阵);
			list.add(Task.答题);
			list.add(Task.锦标赛);
			list.add(Task.斗神塔);
//			list.add(Task.抢地盘);
			list.add(Task.十二宫);
			list.add(Task.竞技场);
			list.add(Task.结拜赛); // 含助威
			list.add(Task.活跃度);
			list.add(Task.乐斗boss);
			list.add(Task.镖行天下);
			list.add(Task.巅峰之战);
			list.add(Task.武林大会);
			list.add(Task.门派大战);
			list.add(Task.每日领奖);
//			list.add(Task.好友乐斗);
			list.add(Task.帮战奖励);
			JCheckBox selectAll = new JCheckBox("全选");
			selectAll.addActionListener(new SelectAllTaskListener());
			taskPanel.add(selectAll);
			for (String taskName : list) {
				JCheckBox task = new JCheckBox(taskName);
				if (dataTask.contains(taskName))
					task.setSelected(true);
				taskPanel.add(task); // 放到面板上。
				taskList.add(task); // 加入到数组中。
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return taskPanel;
	}
	
	public void test() {
		for(int i = 0;i<100; i++) {
			timePanel.add(new JLabel(i+" "));
		}
	}
	
}




