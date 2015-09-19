package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;

import util.UserUtil;
import core.MainUI;

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
			MainUI.textArea.append("【系统消息】\n");
			MainUI.textArea.append("    删除小号：" + username + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从选项列表中删除小号
	 */
	public void removeUserFromMenu() {
		//只能根据序列号获取对应菜单项；不知如何根据name获取component，只能遍历筛选了
		for(int i = 0; i<MainUI.userSelect.getItemCount(); i++) {
			if(MainUI.userSelect.getItem(i).getName().equals(username)) {
				MainUI.userSelect.remove(i);
				return;
			}
		}
	}
}
