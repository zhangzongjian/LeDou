package core;

import java.awt.FlowLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import model.Task;
import util.DocUtil;
import util.UserUtil;
import actionListener.DeleteUserButtonListener;
import actionListener.SelectAllTaskListener;
import actionListener.SelectUserButtonListener;

public class MainUI_init extends MainUI{
	
	//打开窗口时，将已存储的小号加载到选择菜单中
	@SuppressWarnings("unchecked")
	public static void loadUserList() {
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
	
	// 创建乐斗选项多选框面板
	@SuppressWarnings("unchecked")
	public static JPanel createTaskPanel() {
		try {
			taskPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			List<String> dataTask = (List<String>) UserUtil
					.getSettingByKey("任务列表");
			List<String> list = new ArrayList<String>();
			list.add(Task.任务);
			list.add(Task.传功);
			list.add(Task.历练);
			list.add(Task.副本);
			list.add(Task.许愿);
			list.add(Task.踢馆);
			list.add(Task.掠夺);
			list.add(Task.供奉);
			list.add(Task.分享);
			list.add(Task.矿洞);
			list.add(Task.助阵);
			list.add(Task.答题);
			list.add(Task.锦标赛);
			list.add(Task.斗神塔);
			list.add(Task.抢地盘);
			list.add(Task.十二宫);
			list.add(Task.竞技场);
			list.add(Task.结拜赛); // 含助威
			list.add(Task.活跃度);
			list.add(Task.乐斗boss);
			list.add(Task.回流好友召回);
			list.add(Task.镖行天下);
			list.add(Task.巅峰之战);
			list.add(Task.武林大会);
			list.add(Task.门派大战);
			list.add(Task.每日领奖);
			 list.add(Task.好友乐斗);
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
}
