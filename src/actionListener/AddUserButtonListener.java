package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import util.LoginUtil;
import util.PrintUtil;
import util.UserUtil;
import QQLogin.QQLogin;
import core.小号菜单;
import core.设置面板;

public class AddUserButtonListener implements ActionListener {

	private String username;
	
	private Map<String, String> userKey;
	
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		doAdd();
	}
	
	@SuppressWarnings("unchecked")
	public void doAdd() {
		try {
			//qq登录，获取登录cookies
			String qq = 设置面板.inputQQ.getText();
			String password = 设置面板.inputPassword.getText();
			String vcode = 设置面板.inputVerifyCode.getText();
			
			int status = LoginUtil.login(qq, password, vcode);
			switch (status) {
			case 0:
				PrintUtil.printTitleInfo("系统消息", "登录成功！");
				LoginUtil.checkResult = ""; //登录完成后，账号check状态清空掉
				设置面板.inputQQ.setText("");
				设置面板.inputPassword.setText("");
				设置面板.showVerifyCode(false);
				break;
			case 1:
				PrintUtil.printTitleInfo("系统消息", "需要输入验证码登录！");
				设置面板.showVerifyCode(true);
				break;
			case -1:
				PrintUtil.printTitleInfo("系统消息", "验证码错误！");
				设置面板.showVerifyCode(true);
				break;
			default:
				PrintUtil.printTitleInfo("系统消息", "QQ或密码输入有误！");
				break;
			}

			//uin, skey
			userKey = new HashMap<String, String>();
			userKey.put("uin", QQLogin.cookies.get("uin"));
			userKey.put("skey", QQLogin.cookies.get("skey"));
			userKey.put("QQ", qq);
			userKey.put("password", password);
			
			username = UserUtil.getUsername(userKey);
			//保存(小号昵称--URL)
			if(UserUtil.getSettingByKey("小号") == null) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put(username, userKey);
				UserUtil.addSetting("小号", map); 
				UserUtil.saveSetting();
				if(this.addUserToMenu() == false) return;
				PrintUtil.printTitleInfo("系统消息", "添加小号："+username+"");
			}
			else {
				((LinkedHashMap<String, Object>)UserUtil.getSettingByKey("小号")).put(username, userKey);
				UserUtil.saveSetting();
				if(this.addUserToMenu() == false) return;
				PrintUtil.printTitleInfo("系统消息", "添加小号："+username+"");
			}
		} catch (IllegalArgumentException e) {
			PrintUtil.printTitleInfo("系统消息", "添加失败！(登录验证失败！)");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加小号进选项列表
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public boolean addUserToMenu() throws IOException {
		if(username == null) return false;
		for(int i = 0; i<小号菜单.userSelect.getItemCount(); i++) {
			if(小号菜单.userSelect.getItem(i).getName().equals(username)) {
				((LinkedHashMap<String, Object>)UserUtil.getSettingByKey("小号")).put(username, userKey);
				UserUtil.saveSetting();
				PrintUtil.printTitleInfo("系统消息", "更新小号："+username+"");
				return false;
			}
		}
		JMenuItem select = new JMenuItem("选择");
		select.addActionListener(new SelectUserButtonListener(username));
		JMenuItem delete = new JMenuItem("删除");
		delete.addActionListener(new DeleteUserButtonListener(username));
		JMenu userMenu = new JMenu(username);
		userMenu.setName(username);
		userMenu.add(select);
		userMenu.add(delete);
		小号菜单.userSelect.add(userMenu); // 菜单选项
		if(小号菜单.userSelect.getItemCount() == 1) {
			小号菜单.userSelect.setText("切换小号："+username);
		}
		return true;
	}
}
