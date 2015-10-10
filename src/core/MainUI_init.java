package core;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Task;
import model.impl.助阵;
import model.impl.十二宫;
import model.impl.历练;
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
			taskPanel.add(创建十二宫菜单());
			taskPanel.add(创建助阵菜单());
			taskPanel.add(创建历练菜单());
			JCheckBox selectAll = new JCheckBox("全选");
			selectAll.addActionListener(new SelectAllTaskListener());
			taskPanel.add(selectAll);
			for (String taskName : list) {
				JCheckBox task = new JCheckBox(taskName);
				if (dataTask.contains(taskName))
					task.setSelected(true);
				taskPanel.add(task); // 放到面板上。
				taskList.add(task); // 加入到数组中。
				if (taskName.equals(Task.供奉)) {
					input1 = new JTextField("还魂丹",4);
					taskPanel.add(input1);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return taskPanel;
	}
	
	public static JMenuBar 创建十二宫菜单() throws IOException {
		JMenuBar 十二宫Bar = new JMenuBar();
		Object object = UserUtil.getSettingByKey("十二宫");
		if(null == object) {
			UserUtil.addSetting("十二宫", "双子宫(60-65)");
			UserUtil.saveSetting();
			十二宫Select = new JMenu("十二宫(双子宫)");
		} else {
			十二宫.object = object.toString();
			十二宫Select = new JMenu("十二宫("+object.toString().substring(0, object.toString().indexOf("("))+")");
		}
		JMenuItem item = null;
		item = new JMenuItem("白羊宫(50-55)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("金牛宫(55-60)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("双子宫(60-65)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("巨蟹宫(65-70)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("狮子宫(70-75)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("处女宫(75-80)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("天秤宫(80-85)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("天蝎宫(85-90)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("射手宫(50-95)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("摩羯宫(95-100)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("水瓶宫(101-110)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		十二宫Bar.add(十二宫Select);
		return 十二宫Bar;
	}
	
	public static JMenuBar 创建助阵菜单() throws IOException{
		JMenuBar 助阵Bar = new JMenuBar();
		Object object = UserUtil.getSettingByKey("助阵");
		if(null == object) {
			UserUtil.addSetting("助阵", "以柔克刚1");
			UserUtil.saveSetting();
			助阵Select = new JMenu("助阵技能(以柔克刚1)");
		} else {
			助阵.object = object.toString();
			助阵Select = new JMenu("助阵技能("+object.toString()+")");
		}
		JMenuItem item = null;
		JMenu menu1 = new JMenu("毒光剑影");
		JMenu menu2 = new JMenu("正邪两立");
		JMenu menu3 = new JMenu("致命一击");
		JMenu menu4 = new JMenu("纵剑天下");
		JMenu menu5 = new JMenu("老谋深算");
		JMenu menu6 = new JMenu("智勇双全");
		JMenu menu7 = new JMenu("以柔克刚");
		JMenu menu8 = new JMenu("雕心鹰爪");
		JMenu menu9 = new JMenu("根骨奇特");
		for(int i=1; i<=5; i++) {
			if(i<=1) {
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu1.getText()+""+i));
				menu1.add(item);
			}
			if(i<=2) {
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu2.getText()+""+i));
				menu2.add(item);
			}
			if(i<=3) {
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu3.getText()+""+i));
				menu3.add(item);
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu4.getText()+""+i));
				menu4.add(item);
			}
			if(i<=4) {
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu5.getText()+""+i));
				menu5.add(item);
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu6.getText()+""+i));
				menu6.add(item);
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu7.getText()+""+i));
				menu7.add(item);
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu8.getText()+""+i));
				menu8.add(item);
			}
			if(i<=5) {
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu9.getText()+""+i));
				menu9.add(item);
			}
		}
		助阵Select.add(menu1);
		助阵Select.add(menu2);
		助阵Select.add(menu3);
		助阵Select.add(menu4);
		助阵Select.add(menu5);
		助阵Select.add(menu6);
		助阵Select.add(menu7);
		助阵Select.add(menu8);
		助阵Select.add(menu9);
		助阵Bar.add(助阵Select);
		return 助阵Bar;
	}
	
	public static JMenuBar 创建历练菜单() throws IOException{
		JMenuBar 历练Bar = new JMenuBar();
		Object object = UserUtil.getSettingByKey("历练");
		if(null == object) {
			UserUtil.addSetting("历练", "洞庭湖(37~40级)");
			UserUtil.saveSetting();
			历练Select = new JMenu("历练(洞庭湖)");
		} else {
			历练.object = object.toString();
			历练Select = new JMenu("历练("+object.toString().substring(0, object.toString().indexOf("("))+")");
		}
		JMenuItem item = null;
		item = new JMenuItem("乐斗村(1~10级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("林松郊外(10~20级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("林松城(20~25级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("东海龙宫(25~30级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("踏云镇(30~34级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("摩云山(34~37级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("洞庭湖(37~40级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("苍茫山(40~44级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("玉龙湿地(44~47级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("玉龙雪山(47~50级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("狂沙台地(50~53级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("回声遗迹(53~56级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("悲叹山丘(57~60级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("黄沙漩涡(61~64级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("炎之洞窟(65~68级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("程管小镇(69~73级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("花果山(74~78级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("藏剑山庄(79~83级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("桃花剑冢(84~88级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("鹅王的试炼(89~93级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		历练Bar.add(历练Select);
		return 历练Bar;
	}
}

//十二宫、助阵、历练，三个菜单选项监听器
class MenuItemListener implements ActionListener {
	private String key;
	private Object value;
	public MenuItemListener(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	public void actionPerformed(ActionEvent e) {
		try {
			UserUtil.addSetting(key, value);
			UserUtil.saveSetting();
			if("十二宫".equals(key)) {
				MainUI.十二宫Select.setText("十二宫("+value.toString().substring(0, value.toString().indexOf("("))+")");
			}
			if("历练".equals(key)) {
				MainUI.历练Select.setText("历练("+value.toString().substring(0, value.toString().indexOf("("))+")");
			}
			if("助阵".equals(key)) {
				MainUI.助阵Select.setText("助阵技能("+value.toString()+")");
			}
			MainUI.textArea.append("【"+key+"】\n");
			MainUI.textArea.append("    "+value+"\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
};
