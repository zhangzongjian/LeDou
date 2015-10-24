package core;

import java.awt.FlowLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Task;
import model.impl.供奉;
import util.UserUtil;
import actionListener.AddUserButtonListener;
import actionListener.SelectAllTaskListener;

public class 设置面板 {
	
	public static JTextField input;  //小号链接输入框
	public static JTextField input1;  //供奉物品输入框
	public static List<JCheckBox> taskList = new ArrayList<JCheckBox>();//任务复选框组
	
	public JPanel taskPanel = new JPanel(); //乐斗设置面板
	
	private 设置面板() {
		try {
			//taskPanel面板布局设置
			taskPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			taskPanel.add(getInput());
			taskPanel.add(getAddUserButton());
			taskPanel.add(十二宫菜单.create());
			taskPanel.add(助阵菜单.create());
			taskPanel.add(历练菜单.create());
			taskPanel.add(getSelectAllCheckBox());
			initTaskCheckBox();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static JPanel create() {
		return new 设置面板().taskPanel;
	}
	
	//初始化乐斗选项复选框，并填充到设置面板taskPanel上
	private void initTaskCheckBox() throws IOException {
		for (String taskName : getTaskList()) {
			JCheckBox task = new JCheckBox(taskName);
			if (getTaskData().contains(taskName))
				task.setSelected(true);
			taskPanel.add(task); // 放到面板上。
			taskList.add(task); // 加入到数组中。
			if (taskName.equals(Task.供奉)) {
				taskPanel.add(getInput1());
			}
		}
	}
	
	//获取乐斗选项列表
	private List<String> getTaskList() throws IOException {
		List<String> list = new ArrayList<String>();
		list.add(Task.任务);
		list.add(Task.传功);
		list.add(Task.历练);
		list.add(Task.副本);
		list.add(Task.许愿);
		list.add(Task.踢馆);
		list.add(Task.掠夺);
		list.add(Task.分享);
		list.add(Task.矿洞);
		list.add(Task.助阵);
		list.add(Task.答题);
		list.add(Task.十二宫);
		list.add(Task.锦标赛);
		list.add(Task.斗神塔);
		list.add(Task.抢地盘);
		list.add(Task.竞技场);
		list.add(Task.结拜赛);
		list.add(Task.活跃度);
		list.add(Task.乐斗boss);
		list.add(Task.供奉);
		list.add(Task.镖行天下);
		list.add(Task.回流好友召回);
		list.add(Task.巅峰之战);
		list.add(Task.武林大会);
		list.add(Task.门派大战);
		list.add(Task.每日领奖);
		list.add(Task.好友乐斗);
		list.add(Task.帮战奖励);
		return list;
	}
	
	//获取持久化数据
	@SuppressWarnings("unchecked")
	private List<String> getTaskData() throws IOException {
		return (List<String>) UserUtil.getSettingByKey("任务列表");
	}
	
	//持久化数据（保存任务选项）
	public static List<String> saveTask() {
		try {
			List<String> tasks = new ArrayList<String>();
			for (JCheckBox j : 设置面板.taskList) {
				if (j.isSelected())
					tasks.add(j.getText());
			}
			UserUtil.addSetting("任务列表", tasks);
			UserUtil.saveSetting();
			供奉.thing = 设置面板.input1.getText();
			UserUtil.addSetting("供奉", 设置面板.input1.getText());
			UserUtil.saveSetting();
			return tasks;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	private JTextField getInput() {
		input = new JTextField(28);
		input.setText("添加小号-请复制免登陆链接到这里！");
		return input;
	}
	
	private JButton getAddUserButton() {
		JButton addUserButton = new JButton("添加");
		addUserButton.addActionListener(new AddUserButtonListener());
		return addUserButton;
	}
	
	private JCheckBox getSelectAllCheckBox() {
		JCheckBox selectAllCheckBox = new JCheckBox("全选");
		selectAllCheckBox.addActionListener(new SelectAllTaskListener());
		return selectAllCheckBox;
	}
	
	private JTextField getInput1() throws IOException {
		Object object = UserUtil.getSettingByKey("供奉");
		if(null == object) {
			UserUtil.addSetting("供奉", "还魂丹");
			UserUtil.saveSetting();
			input1 = new JTextField("还魂丹",4);
		} else {
			input1 = new JTextField(object.toString(),4);
		}
		return input1;
	}
}
