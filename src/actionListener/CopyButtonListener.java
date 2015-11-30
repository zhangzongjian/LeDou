package actionListener;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class CopyButtonListener implements ActionListener {
	
	private JTextField jTextField;
	
	public CopyButtonListener(JTextField jTextField) {
		this.jTextField = jTextField;
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		String text = jTextField.getText();
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(new StringSelection(text), null);
	}
}