package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import QQLogin.Login;

import util.UserUtil;
import core.乐斗面板;
import core.小号菜单;
import core.设置面板;

public class AddUserButtonListener implements ActionListener {

	private String username;
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		try {
			//qq登录，获取登录cookies
			String qq = 设置面板.inputQQ.getText();
			String password = 设置面板.inputPassword.getText();
			Login.login(qq, password, "");
			//uin, skey
			Map<String, String> userKey = new HashMap<String, String>();
			userKey.put("uin", Login.cookies.get("uin"));
			userKey.put("skey", Login.cookies.get("skey"));
			
			username = UserUtil.getUsername(userKey);
			//保存(小号昵称--URL)
			if(UserUtil.getSettingByKey("小号") == null) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put(username, userKey);
				UserUtil.addSetting("小号", map); 
				UserUtil.saveSetting();
				if(this.addUserToMenu() == false) return;
				乐斗面板.textArea.append("【系统消息】\n");
				乐斗面板.textArea.append("    添加小号："+username+"\n");
			}
			else {
				((LinkedHashMap<String, Object>)UserUtil.getSettingByKey("小号")).put(username, userKey);
				UserUtil.saveSetting();
				if(this.addUserToMenu() == false) return;
				乐斗面板.textArea.append("【系统消息】\n");
				乐斗面板.textArea.append("    添加小号："+username+"\n");
			}
		} catch (IllegalArgumentException e) {
			乐斗面板.textArea.append("【系统消息】\n");
			乐斗面板.textArea.append("    添加失败！(登录验证失败！)\n");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加小号进选项列表
	 */
	public boolean addUserToMenu() {
		if(username == null) return false;
		for(int i = 0; i<小号菜单.userSelect.getItemCount(); i++) {
			if(小号菜单.userSelect.getItem(i).getName().equals(username)) {
				乐斗面板.textArea.append("【系统消息】\n");
				乐斗面板.textArea.append("    小号已存在，不需重复添加！\n");
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
