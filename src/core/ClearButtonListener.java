package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.jsoup.Jsoup;

import model.镖行天下;

//清屏按钮响应
public class ClearButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		Main.textArea.setText("");
	}
}