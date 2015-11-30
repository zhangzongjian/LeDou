package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.查看对话框;

public class ShowUsersButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		查看对话框.create().setVisible(true);
	}
}