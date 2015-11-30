package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JTextField;

import util.UserUtil;

public class SaveUsersButtonListener implements ActionListener {

	private LinkedHashMap<String, List<JTextField>> userTextFieldsMap;
	private JDialog usersDialog;
	
	public SaveUsersButtonListener(LinkedHashMap<String, List<JTextField>> userComponentsMap, JDialog usersDialog) {
		this.userTextFieldsMap = userComponentsMap;
		this.usersDialog = usersDialog;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		try {
			LinkedHashMap<String, Object> usersMap = (LinkedHashMap<String, Object>)UserUtil.getSettingByKey("小号");
			
			for(String username : userTextFieldsMap.keySet()) {
				Map<String, String> userKey = (Map<String, String>) usersMap.get(username);
				userKey.put("QQ", userTextFieldsMap.get(username).get(1).getText());
				userKey.put("password", userTextFieldsMap.get(username).get(2).getText());
				
				String nickName = userTextFieldsMap.get(username).get(0).getText();
				//若昵称被改了，需要特殊处理
				if(!username.equals(nickName)) {
					usersMap.put(nickName, userKey); //录入新昵称的小号
					usersMap.remove(username); //移除旧昵称的小号
				}
			}
			UserUtil.saveSetting();
			usersDialog.setVisible(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}