package core;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import actionListener.ClearButtonListener;
import actionListener.OneKeyButtonListener;


public class 乐斗面板 {
	public static JTextArea textArea; //信息输出文本框
	public static JCheckBox allUsersCheckBox = new JCheckBox("所有小号");
	private JPanel fight_jPanel = new JPanel(); //主界面
	
	private 乐斗面板() {
		//设置面板无布局  ，即手动指定组件位置尺寸
		fight_jPanel.setLayout(null);
		fight_jPanel.add(小号菜单.create());
		fight_jPanel.add(getOneKeyButton());
		fight_jPanel.add(getClearButton());
		fight_jPanel.add(getAllUsersCheckBox());
		fight_jPanel.add(getScrollTextArea());
	}
	
	//普通文本框，转为带滚动条的
	private JScrollPane getScrollTextArea() {
		textArea = new JTextArea();
		JScrollPane scrollTextArea = new JScrollPane(textArea);
		scrollTextArea.setBounds(6, 35, 381, 358);
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
