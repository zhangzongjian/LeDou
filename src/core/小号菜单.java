package core;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import util.DocUtil;
import util.UserUtil;
import actionListener.DeleteUserButtonListener;
import actionListener.SelectUserButtonListener;

public class 小号菜单 {
	
	public JMenuBar userBar = new JMenuBar(); //菜单
	
	public static JMenu userSelect = new JMenu("切换小号：（未添加）"); //菜单选项组
	
	@SuppressWarnings("unchecked")
	private 小号菜单() throws IOException {
		userBar.setBounds(7, 5, 119, 23);
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
			DocUtil.userKey = (Map<String, String>) usersMap.get(username);
		}
		userBar.add(userSelect);
	}
	
	public static JMenuBar create() {
		try {
			return new 小号菜单().userBar;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
