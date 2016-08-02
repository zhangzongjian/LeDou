package core;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Version;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import util.UserUtil;
import actionListener.UpdateButtonListener;

public class 更新面板 {
	
	public static JPanel updatePanel = new JPanel();
	private Version thisVersion = new Version();
	private Version headVersion = new Version();
	private Document updateMessage = null;

	public static JPanel create() {
		try {
			return new 更新面板().updatePanel;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private 更新面板() throws IOException {
		init();
		updatePanel.add(getContent());
		if(!isHeadVersion()) {
			updatePanel.add(getUpdateButton());
		}
	}
	
	private JLabel getContent() throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>当前版本："+thisVersion.getVersion()+"<br>");
		sb.append("更新内容："+thisVersion.getContent()+"<br>");
		sb.append("备注："+thisVersion.getRemark()+"<br>");
		sb.append("<br>");
		if(isHeadVersion()) {
			sb.append("最新版本：(当前版本已是最新)<br>");
		}
		else {
			sb.append("最新版本："+headVersion.getVersion()+"<br>");
			sb.append("更新内容："+headVersion.getContent()+"<br>");
			sb.append("备注："+headVersion.getRemark()+"<br>");
		}
		sb.append("</html>");
		JLabel content = new JLabel(sb.toString());
		return content;
	}
	
	private JButton getUpdateButton() {
		JButton updateButton = new JButton("立即更新");
		updateButton.addActionListener(new UpdateButtonListener(headVersion));
		return updateButton;
	}
	
	private Document getUpdateMessage() throws IOException {
		String url = "http://7xwzdp.com1.z0.glb.clouddn.com/UpdateMessage.txt?v="+System.currentTimeMillis();
		Document updateMessage = Jsoup.connect(url).ignoreContentType(true).get();
		return updateMessage;
	}
	
	private boolean isHeadVersion() {
		if(thisVersion == null || thisVersion.getVersion() == null) {
			JOptionPane.showMessageDialog(null, "有新版本，详情请看<版本>界面！", "更新",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(thisVersion.getVersion().equals(headVersion.getVersion())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private void init() throws IOException {
		//thisVersion
		Object version = UserUtil.getSetting().get("version");
		thisVersion.setVersion(version != null ? version.toString() : null);
		Object content = UserUtil.getSetting().get("content");
		thisVersion.setContent(content != null ? content.toString() : null);
		Object remark = UserUtil.getSetting().get("remark");
		thisVersion.setRemark(remark != null ? remark.toString() : null);
		//headVersion
		updateMessage = getUpdateMessage();
		headVersion.setVersion(updateMessage.getElementsByTag("version").html()); 
		headVersion.setContent(updateMessage.getElementsByTag("content").html()); 
		headVersion.setRemark(updateMessage.getElementsByTag("remark").html()); 
	}
}
