package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;

import model.Version;
import util.UserUtil;

//更新版本按钮
public class UpdateButtonListener implements ActionListener {
	
	private Version headVersion;
	public UpdateButtonListener(Version headVersion) {
		this.headVersion = headVersion;
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		FileOutputStream out;
		try {
			String url = "http://7xwzdp.com1.z0.glb.clouddn.com/LeDouHelper.jar?v="+System.currentTimeMillis();
			URL u = new URL(url);
			InputStream input = u.openStream();
			byte[] b = new byte[1024];
			int i;
			out = new FileOutputStream("LeDouHelper.jar");
			while(-1 != (i = input.read(b))) {
				out.write(b, 0, i);
			}
			saveUpdateMessage();
			JOptionPane.showMessageDialog(null, "更新完成，重新启动工具生效！", "更新",JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "更新失败，未找到更新文件！", "更新",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		} finally {
			System.exit(0);  //更新完成后退出程序，重新启动生效
		}
	}
	
	private void saveUpdateMessage() throws IOException {
		UserUtil.addSetting("version", headVersion.getVersion());
		UserUtil.saveSetting();
		UserUtil.addSetting("content", headVersion.getContent());
		UserUtil.saveSetting();
		UserUtil.addSetting("remark", headVersion.getRemark());
		UserUtil.saveSetting();
	}
}