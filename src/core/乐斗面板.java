package core;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import util.PrintUtil;
import util.UserUtil;
import actionListener.ClearButtonListener;
import actionListener.OneKeyButtonListener;


public class 乐斗面板 {
	public static JTextArea textArea; //信息输出文本框
	public static JCheckBox allUsersCheckBox = new JCheckBox("所有小号");
	public static JProgressBar progressBar = new JProgressBar(); //进度条
	private JPanel fight_jPanel = new JPanel(); //主界面
	
	//进度条参数
	private static int progressValue = 0;
	private static int progressSum = 0;
	
	private 乐斗面板() {
		//设置面板无布局  ，即手动指定组件位置尺寸
		fight_jPanel.setLayout(null);
		fight_jPanel.add(小号菜单.create());
		fight_jPanel.add(getOneKeyButton());
		fight_jPanel.add(getClearButton());
		fight_jPanel.add(getAllUsersCheckBox());
		fight_jPanel.add(getScrollTextArea());
		//fight_jPanel.add(getProgressBar());
	}
	
	//普通文本框，转为带滚动条的
	private JScrollPane getScrollTextArea() {
		textArea = new JTextArea();
		JScrollPane scrollTextArea = new JScrollPane(textArea);
		scrollTextArea.setBounds(6, 48, 381, 353);
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
	
	private JProgressBar getProgressBar() {
		progressBar.setBounds(6, 31, 381, 14);
		progressBar.setStringPainted(true);
		progressBar.setString("未开始！");
		return progressBar;
	}
	
	//每次一键乐斗之前都初始化一遍
	public static void initProgressBar() {
		progressBar.setValue(0);
		progressValue = getProgressValue();
		progressSum = 0;
		PrintUtil.printTask.clear();
	}
	
	/**
	 * 更新进度条
	 */
	public static void updateProgress() {
		progressBar.setString(null);
		progressSum += progressValue;
		progressBar.setValue(progressSum);
	}
	
	/**
	 * 计算进度条速度
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static int getProgressValue() {
		try {
			//当前执行的小号数量
			int userNum;
			//存储的乐斗项目的数量
			int taskNum = ((List<String>)UserUtil.getSettingByKey("任务列表")).size();
			if(乐斗面板.allUsersCheckBox.isSelected())
				userNum = ((Map<String, Object>)UserUtil.getSettingByKey("小号")).entrySet().size();
			else 
				userNum = 1;
			//返回每个任务所占的百分比。
			double result = 100.0 / (userNum * taskNum);
			if(result - (int)result > 0.5)
				return (int)result + 1;
			else 
				return (int)result == 0 ? 1 : (int)result;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			//nothing to do
		}
		return 0;
	}
	
	public static JPanel create() {
		return new 乐斗面板().fight_jPanel;
	}
}
