package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import core.查看对话框;

public class ShowUsersButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		JDialog dialog = 查看对话框.create();
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
}