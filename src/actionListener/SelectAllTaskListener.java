package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import core.设置面板;

public class SelectAllTaskListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if(((JCheckBox)e.getSource()).isSelected())
			for (JCheckBox c : 设置面板.taskList) {
				if (!c.isSelected()) {
					c.setSelected(true);
				}
			}
		else {
			for (JCheckBox c : 设置面板.taskList) {
				if (c.isSelected()) {
					c.setSelected(false);
				}
			}
		}
	}

}
