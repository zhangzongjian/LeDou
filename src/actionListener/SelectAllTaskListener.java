package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import core.MainUI;

public class SelectAllTaskListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if(((JCheckBox)e.getSource()).isSelected())
			for (JCheckBox c : MainUI.taskList) {
				if (!c.isSelected()) {
					c.setSelected(true);
				}
			}
		else {
			for (JCheckBox c : MainUI.taskList) {
				if (c.isSelected()) {
					c.setSelected(false);
				}
			}
		}
	}

}
