package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.MainUI;

//清屏按钮响应
public class ClearButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		MainUI.textArea.setText("");
	}
}