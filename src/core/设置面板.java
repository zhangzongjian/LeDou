package core;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Task;
import model.其他操作集合;
import model.impl.供奉;
import util.UserUtil;
import actionListener.AddUserButtonListener;
import actionListener.AddUserKeyAdapter;
import actionListener.ChangeVerifycodeListener;
import actionListener.SelectAllTaskListener;
import actionListener.ShowUsersButtonListener;

public class 设置面板 {
	
	public static JTextField inputQQ;  
	public static JTextField inputPassword; 
	public static JTextField inputVerifyCode;
	public static JTextField input1;  //供奉物品输入框
	public static JTextField input2 = new JTextField("输入微信兑换码", 8);  //微信兑换码输入框
	public static List<JCheckBox> taskList = new ArrayList<JCheckBox>();//任务复选框组
	public static List<JCheckBox> otherTaskList = new ArrayList<JCheckBox>();//其他操作复选框
	
	private static JLabel codeTip;
	public static JLabel codeImge;
	
	public static JPanel taskPanel = new JPanel(); //乐斗设置面板
	
	private 设置面板() {
		try {
			//taskPanel面板布局设置
			taskPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			taskPanel.add(new JLabel("QQ"));
			taskPanel.add(getInputQQ());
			taskPanel.add(new JLabel("密码"));
			taskPanel.add(getInputPassword());
			taskPanel.add(getAddUserButton());
			taskPanel.add(getShowUsersButton());
			
			initVerifyCode();
			showVerifyCode(false);
			
			taskPanel.add(new JLabel("------------------------------------  乐斗选项  -------------------------------------------"));
			taskPanel.add(getSelectAllCheckBox());
			initTaskCheckBox();
			taskPanel.add(new JLabel("------------------------------------  其他选项  -------------------------------------------"));
			taskPanel.add(十二宫菜单.create());
			taskPanel.add(助阵菜单.create());
			taskPanel.add(历练菜单.create());
			taskPanel.add(幻境菜单.create());
			
			JCheckBox 开锦囊宝箱 = new JCheckBox("开锦囊宝箱"); 
			JCheckBox 吃药10 = new JCheckBox("吃药10"); 
			JCheckBox 开通达人 = new JCheckBox("开通达人"); 
			JCheckBox 微信礼包 = new JCheckBox("微信礼包"); 
			taskPanel.add(开锦囊宝箱);
			taskPanel.add(吃药10);
			taskPanel.add(开通达人);
			taskPanel.add(微信礼包);
			taskPanel.add(input2);
			otherTaskList.add(开锦囊宝箱);
			otherTaskList.add(吃药10);
			otherTaskList.add(开通达人);
			otherTaskList.add(微信礼包);
			
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
			taskList.add(task); // 加入到数组中。
			taskPanel.add(task); // 放到面板上。
			if (taskName.equals(Task.供奉)) {
				taskPanel.add(getInput1());
			}
		}
	}
	
	//初始化验证码模块
	private void initVerifyCode() {
		codeTip= new JLabel("验证码");
		taskPanel.add(codeTip);
		taskPanel.add(getInputVerifyCode());
		codeImge = new JLabel(new ImageIcon(getImage()));
		codeImge.addMouseListener(new ChangeVerifycodeListener());
		taskPanel.add(codeImge);
	}
	
	//获取乐斗选项列表
	private List<String> getTaskList() throws IOException {
		List<String> list = new ArrayList<String>();
		list.add(Task.任务);
		list.add(Task.传功);
		list.add(Task.历练);
		list.add(Task.许愿);
		list.add(Task.踢馆);
		list.add(Task.掠夺);
		list.add(Task.分享);
		list.add(Task.矿洞);
		list.add(Task.助阵);
		list.add(Task.探险);
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
		list.add(Task.每日领奖);
		list.add(Task.好友乐斗);
		list.add(Task.帮战奖励);
		list.add(Task.活动集合);
		list.add(Task.群雄逐鹿);
		list.add(Task.画卷迷踪);
		list.add(Task.幻境);
		list.add(Task.门派);
		list.add(Task.门派邀请赛);
		list.add(Task.祭坛);
		list.add(Task.会武);
		list.add(Task.问鼎天下);
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
			for (JCheckBox j : 设置面板.otherTaskList) {
				if (j.isSelected())
					tasks.add(j.getText());
			}
			UserUtil.addSetting("任务列表", tasks);
			UserUtil.saveSetting();
			供奉.thing = 设置面板.input1.getText();
			其他操作集合.微信兑换码 = 设置面板.input2.getText();
			UserUtil.addSetting("供奉", 设置面板.input1.getText());
			UserUtil.saveSetting();
			return tasks;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 设置验证码模块是否可见
	 * @param isShow
	 */
	public static void showVerifyCode(boolean isShow) {
		codeTip.setVisible(isShow);
		inputVerifyCode.setText("");
		inputVerifyCode.setVisible(isShow);
		codeImge.setIcon(new ImageIcon(getImage()));
		codeImge.setVisible(isShow);
	}
	
	private static byte[] getImage() {
		byte[] bytes = null;
		try {
			FileInputStream in = new FileInputStream(new File("resources/VerifyCode.jpg")) ;
			bytes = new byte[in.available()];
			in.read(bytes);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	private JTextField getInputQQ() {
		inputQQ = new JTextField(8);
		inputQQ.addKeyListener(new AddUserKeyAdapter());
		return inputQQ;
	}
	
	private JTextField getInputPassword() {
		inputPassword = new JTextField(9);
		inputPassword.addKeyListener(new AddUserKeyAdapter());
		return inputPassword;
	}
	
	private JTextField getInputVerifyCode() {
		inputVerifyCode = new JTextField(11);
		inputVerifyCode.addKeyListener(new AddUserKeyAdapter());
		return inputVerifyCode;
	}
	
	private JButton getAddUserButton() {
		JButton addUserButton = new JButton("添加");
		addUserButton.addActionListener(new AddUserButtonListener());
		return addUserButton;
	}
	
	private JButton getShowUsersButton() {
		JButton showUsersButton = new JButton("查看");
		showUsersButton.addActionListener(new ShowUsersButtonListener());
		return showUsersButton;
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
