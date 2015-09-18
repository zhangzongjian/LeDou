package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import model.UserSetting;
import util.LeDouUtil;

public class AddUserButtonListener implements ActionListener {

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		try {
			String mainURL = Main.input.getText();
			String username = LeDouUtil.getUsername(mainURL);
			//保存(小号昵称--URL)
			if(UserSetting.getSettingByKey("小号") == null) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put(username, mainURL);
				UserSetting.addSetting("小号", map); 
				UserSetting.saveSetting();
				Main.textArea.append("    小号添加成功："+username+"\n");
			}
			else {
				((LinkedHashMap<String, Object>)UserSetting.getSettingByKey("小号")).put(username, mainURL);
				UserSetting.saveSetting();
				Main.textArea.append("    小号添加成功："+username+"\n");
			}
		} catch (IllegalArgumentException e) {
			System.out.println("无效的链接！");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
