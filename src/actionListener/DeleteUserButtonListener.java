package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;

import util.DocUtil;
import util.UserUtil;
import core.乐斗面板;
import core.小号菜单;

public class DeleteUserButtonListener implements ActionListener {

	private String username;

	public DeleteUserButtonListener(String username) {
		this.username = username;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		try {
			((LinkedHashMap<String, Object>) UserUtil.getSettingByKey("小号")).remove(username);
			UserUtil.saveSetting();
			this.removeUserFromMenu();
			乐斗面板.textArea.append("【系统消息】\n");
			乐斗面板.textArea.append("    删除小号：" + username + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从选项列表中删除小号
	 */
	public void removeUserFromMenu() {
		//只能根据序列号获取对应菜单项；不知如何根据name获取component，只能遍历筛选了
		for(int i = 0; i<小号菜单.userSelect.getItemCount(); i++) {
			if(小号菜单.userSelect.getItem(i).getName().equals(username)) {
				小号菜单.userSelect.remove(i);
				break;
			}
		}
		//全部删除了
		if(小号菜单.userSelect.getItemCount() == 0) {
			小号菜单.userSelect.setText("切换小号：（未添加）");
			DocUtil.mainURL = null;
			DocUtil.userKey = null;
		}
	}
}
