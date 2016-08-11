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
	public static int versionStatus = 0;
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
		if(getVersionStatus() == 0) {
//			JOptionPane.showMessageDialog(null, "有新版本，详情请看<版本>界面！", "更新",JOptionPane.WARNING_MESSAGE);
			updatePanel.add(getUpdateButton());
		}
	}
	
	private JLabel getContent() throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>当前版本："+thisVersion.getVersion()+"<br>");
		sb.append("更新内容："+thisVersion.getContent()+"<br>");
		sb.append("备注："+thisVersion.getRemark()+"<br>");
		sb.append("<br>");
		if(getVersionStatus() == 1) {
			sb.append("最新版本：(当前版本已是最新)<br>");
		}
		else if(getVersionStatus() == -1) {
			sb.append("最新版本：(获取版本信息失败)<br>");
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
	
	private Document getUpdateMessage() {
		String url = "http://7xwzdp.com1.z0.glb.clouddn.com/UpdateMessage.txt?v="+System.currentTimeMillis();
		Document updateMessage;
		try {
			updateMessage = Jsoup.connect(url).ignoreContentType(true).get();
			return updateMessage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 返回0表示：当前不是最新版；返回1表示：当前版本是最新版；返回-1表示：获取版本信息失败；
	 * @return
	 */
	private int getVersionStatus() {
		if(thisVersion.getVersion() == null && headVersion.getVersion() != null) {
			versionStatus = 0;
			return 0;
		}
		if(headVersion.getVersion() == null) {
			versionStatus = -1;
			return -1;
		}
		if(thisVersion.getVersion().equals(headVersion.getVersion())) {
			versionStatus = 1;
			return 1;
		}
		else {
			versionStatus = 0;
			return 0;
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
		if(updateMessage != null) {
			headVersion.setVersion(updateMessage.getElementsByTag("version").get(0).html()); 
			headVersion.setContent(updateMessage.getElementsByTag("content").get(0).html()); 
			headVersion.setRemark(updateMessage.getElementsByTag("remark").get(0).html());
		}
	}
}
