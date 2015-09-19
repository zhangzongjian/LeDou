package core;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import util.DocUtil;
import util.UserUtil;
import actionListener.AddUserButtonListener;
import actionListener.ClearButtonListener;
import actionListener.DeleteUserButtonListener;
import actionListener.OneKeyButtonListener;
import actionListener.SelectUserButtonListener;

public class MainUI {

	public static void main(String[] args) {
		MainUI main = new MainUI();
		main.CreateJFrame("一键乐斗小工具");
	}

	public static JFrame jFrame = new JFrame("一键乐斗小工具");
	public static JPanel jPanel = new JPanel();
	public static Container container = jFrame.getContentPane();
	public static JTextArea textArea;
	public static JTextField input;
	public static JLabel tag;
	public static JLabel showTime; //显示押镖剩余时间
	public static JScrollPane textArea1;
	public static JButton oneKeyButton;
	public static JButton clearButton;
	public static JButton addUserButton;
	public static JMenuBar userBar;
	public static JMenu userSelect;
	public static JMenuItem userItem;

	//打开窗口
	public void CreateJFrame(String title) {
		jFrame.setLocation(0, 200); // 窗口起始位置
		// 标签
		tag = new JLabel("添加小号");
		showTime = new JLabel();
		// 输入框
		input = new JTextField(20);
		input.setName("免登陆链接");
		input.setText("请复制免登陆链接到这里！");
		// 文本框，JScorllPane：文本框设置滚动条
		textArea = new JTextArea(10, 30);
		textArea.setName("输出框");
		textArea1 = new JScrollPane(textArea);
		// 按钮
		oneKeyButton = new JButton("一键乐斗");
		oneKeyButton.setName("一键乐斗");
		oneKeyButton.addActionListener(new OneKeyButtonListener());
		clearButton = new JButton("清屏");
		clearButton.addActionListener(new ClearButtonListener());
		addUserButton = new JButton("添加");
		addUserButton.addActionListener(new AddUserButtonListener());
		//菜单控件
		userBar = new JMenuBar(); //菜单
		userSelect = new JMenu("切换小号：（未添加）"); //菜单选项组
		loadUserList();
		
		jPanel.add(tag, BorderLayout.NORTH);
		jPanel.add(input, BorderLayout.NORTH);
		jPanel.add(addUserButton, BorderLayout.NORTH);
		jPanel.add(userBar);
		jPanel.add(oneKeyButton, BorderLayout.NORTH);
		jPanel.add(clearButton, BorderLayout.NORTH);
		jPanel.add(textArea1, BorderLayout.SOUTH);
		jPanel.add(MainUI.showTime, BorderLayout.SOUTH);
		container.add(jPanel);
		jFrame.setVisible(true); // 使窗体可视
		jFrame.setSize(400, 350); // 设置窗体大小
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	
	@SuppressWarnings("unchecked")
	//打开窗口时，将已存储的小号加载到选择菜单中
	public void loadUserList() {
		try {
			Map<String, Object> usersMap;
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
}




