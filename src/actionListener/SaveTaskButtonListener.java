package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import util.UserUtil;
import core.MainUI;

public class SaveTaskButtonListener implements ActionListener{
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			OneKeyButtonListener.tasks = new ArrayList<String>();
			for (JCheckBox j : MainUI.taskList) {
				if (j.isSelected())
					OneKeyButtonListener.tasks.add(j.getText());
			}
			UserUtil.addSetting("任务列表", OneKeyButtonListener.tasks);
			UserUtil.saveSetting();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
