package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import util.DocUtil;
import util.UserUtil;
import core.乐斗面板;
import core.小号菜单;

public class SelectUserButtonListener implements ActionListener{
	
	private String username;

	public SelectUserButtonListener(String username) {
		this.username = username;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		小号菜单.userSelect.setText("切换小号："+username);
		Map<String, String> userKey = DocUtil.userKey = UserUtil.getUserKeyByUsrname(username);
		String uin = userKey.get("QQ");
		String sid = userKey.get("sid");
		DocUtil.mainURL = DocUtil.getMainURL(uin, sid);
		乐斗面板.textArea.append("【系统消息】\n");
		乐斗面板.textArea.append("    切换小号：" + username + "\n");
	}
}
