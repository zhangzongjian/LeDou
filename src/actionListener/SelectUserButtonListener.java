package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import util.DocUtil;
import util.UserUtil;

import core.MainUI;

public class SelectUserButtonListener implements ActionListener{
	
	private String username;

	public SelectUserButtonListener(String username) {
		this.username = username;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MainUI.userSelect.setText("切换小号："+username);
		DocUtil.mainURL = UserUtil.getMainURLByUsrname(username);
		MainUI.textArea.append("【系统消息】\n");
		MainUI.textArea.append("    切换小号：" + username + "\n");
	}
}
