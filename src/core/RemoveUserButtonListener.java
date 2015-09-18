package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import model.UserSetting;
import util.LeDouUtil;

public class RemoveUserButtonListener implements ActionListener {

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		try {
			String username = "";
			((LinkedHashMap<String, Object>)UserSetting.getSettingByKey("小号")).remove(username);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
