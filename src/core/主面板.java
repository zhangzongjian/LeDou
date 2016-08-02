package core;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class 主面板 {
	
	private JPanel main_jPanel = new JPanel();  //主面板JPanel
	private JTabbedPane main_tabs = new JTabbedPane(); //主面板选项卡
	
	private 主面板() {
		main_jPanel.setLayout(null);
		main_jPanel.add(createMainTabs());
	}
	
	private JTabbedPane createMainTabs() {
		main_tabs.setBounds(0,0,400,450);
		main_tabs.addTab("每日必斗",乐斗面板.create());
		main_tabs.addTab("乐斗选项", 设置面板.create());
		main_tabs.addTab("计时窗口", 计时面板.timePanel);
		main_tabs.addTab("版本", 更新面板.create());
		return main_tabs;
	}
	
	public static JPanel create() {
		return new 主面板().main_jPanel;
	}
}
