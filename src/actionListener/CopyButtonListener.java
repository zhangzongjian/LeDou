package actionListener;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JTextField;

import util.UserUtil;

public class CopyButtonListener implements ActionListener {
	
	private JTextField jTextField = null;
	private boolean flag = false;
	
	public CopyButtonListener(JTextField jTextField) {
		this.jTextField = jTextField;
	}

	public CopyButtonListener(JTextField jTextField, boolean flag) {
		this.jTextField = jTextField;
		this.flag = flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		//复制按钮动作
		if(flag == false) {
			String text = jTextField.getText();
			clipboard.setContents(new StringSelection(text), null);
		}
		//书签按钮动作
		else {
			Map<String, Object> map;
			try {
				map = (LinkedHashMap<String, Object>)UserUtil.getSetting().get("小号");
				Map<String, String> userKey = (Map<String, String>) map.get(jTextField.getText());
				String qq = userKey.get("QQ");
				String sid = userKey.get("sid");
				String url = "http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin="+qq+"&sid="+sid+"&channel=0&g_ut=1&cmd=index";
				clipboard.setContents(new StringSelection(url), null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}