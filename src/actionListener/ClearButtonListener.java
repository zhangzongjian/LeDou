package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.乐斗面板;

//清屏按钮响应
public class ClearButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		乐斗面板.textArea.setText("");
	}
}