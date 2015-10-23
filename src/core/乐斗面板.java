package core;

import java.awt.Color;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import actionListener.ClearButtonListener;
import actionListener.OneKeyButtonListener;


public class 乐斗面板 {
	public static JTextArea textArea; //信息输出文本框
	public static JCheckBox allUsersCheckBox = new JCheckBox("所有小号");
	private JPanel fight_jPanel = new JPanel(); //主界面
	private static JTabbedPane user_tabs = new JTabbedPane();
	
	private 乐斗面板() {
		//设置面板无布局  ，即手动指定组件位置尺寸
		fight_jPanel.setLayout(null);
		fight_jPanel.add(小号菜单.create());
		fight_jPanel.add(getOneKeyButton());
		fight_jPanel.add(getClearButton());
		fight_jPanel.add(getAllUsersCheckBox());
		fight_jPanel.add(createUserTab());
	}
	
	//每个用户独立一个选项卡
	public JTabbedPane createUserTab() {
		user_tabs.setBounds(5, 33, 383, 360);
		user_tabs.add(小号菜单.userSelect.getText(), getUserPanel());
		return user_tabs;
	}
	
	private JPanel getUserPanel() {
		JPanel userPanel = new JPanel();
		userPanel.setLayout(null);
		//竖直分割的面板，上下
		JSplitPane vSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		vSplitPane.setBounds(0, 0, 383, 330);
		vSplitPane.setDividerLocation(220);
		vSplitPane.setLeftComponent(getScrollTextArea());
		//水平分割的面吧，左右
		JSplitPane hSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		hSplitPane.setDividerLocation(75);
		hSplitPane.setDividerSize(8); //分割线粗细
		hSplitPane.setOneTouchExpandable(true); //UI小部件
		hSplitPane.setLeftComponent(计时面板.timePanel.add(new Label("heheh")));
		hSplitPane.setRightComponent(new JScrollPane(new JPanel()));
		vSplitPane.setRightComponent(hSplitPane);
		userPanel.add(vSplitPane);
//		userPanel.add(getScrollTextArea());
		return userPanel;
	}
	
	//普通文本框，转为带滚动条的
	private JScrollPane getScrollTextArea() {
		textArea = new JTextArea();
//		textArea.setEditable(false);
		JScrollPane scrollTextArea = new JScrollPane(textArea);
		scrollTextArea.setBounds(0, 0, 30, 25);
		scrollTextArea.setBorder(BorderFactory.createLineBorder(Color.red));
		return scrollTextArea;
	}
	
	private JButton getOneKeyButton() {
		JButton oneKeyButton = new JButton("一键乐斗");
		oneKeyButton.setBounds(204, 5, 115, 23);
		oneKeyButton.addActionListener(new OneKeyButtonListener());
		return oneKeyButton;
	}
	
	private JButton getClearButton() {
		JButton clearButton = new JButton("清屏");
		clearButton.setBounds(325, 5, 63, 23);
		clearButton.addActionListener(new ClearButtonListener());
		return clearButton;
	}
	
	private JCheckBox getAllUsersCheckBox() {
		allUsersCheckBox.setBounds(125, 5, 78, 23);
		return allUsersCheckBox;
	}
	
	public static JPanel create() {
		return new 乐斗面板().fight_jPanel;
	}
	
}
