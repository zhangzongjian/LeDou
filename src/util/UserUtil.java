package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketTimeoutException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import QQLogin.QQLogin;
import core.设置面板;


public class UserUtil {

	private static Map<String, Object> setting = null;
	private static String settingFile = "resources/setting.data"; //相对路径
	
	public static Map<String, Object> getSetting() throws IOException {
		setting = loadSetting();
		return setting;
	}
	/**
	 * 添加设置，未保存，保存请调用saveSetting()方法
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public static void addSetting(String key, Object value) throws IOException {
		setting = loadSetting();
		setting.put(key, value);
	}
	
	/**
	 * 删除指定设置，未保存，保存请调用saveSetting()方法
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public static void removeSetting(String key, Object value)  throws IOException {
		setting = loadSetting();
		setting.put(key, value);
	}
	
	/**
	 * 获取指定设置
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static Object getSettingByKey(String key) throws IOException {
		setting = loadSetting();
		return setting.get(key);
	}
	
	/**
	 * 保存设置到文件
	 * @throws IOException
	 */
	public synchronized static void saveSetting() throws IOException {
		if(setting == null || setting.isEmpty()) return; //防止清空设置
		File file = new File(settingFile);
		FileOutputStream fout = new FileOutputStream(file);
		try {
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(setting);
			fout.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 从设置文件中加载数据
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public synchronized static Map<String, Object> loadSetting() throws IOException {
		File file = new File(settingFile);
		FileInputStream fin = new FileInputStream(file);
		ObjectInputStream in = new ObjectInputStream(fin);
		Map<String, Object> map = null;
		try {
			map = (Map<String, Object>) in.readObject();
			fin.close();
			in.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	private static Object object = new Object();
	/**
	 * 从大乐斗首页中，获取用户昵称
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public static String getUsername(Map<String, String> userKey) throws IOException {
		String oldUsername = null;
		try {
			String qq = userKey.get("QQ");
			String sid = userKey.get("sid");
			if(sid == null) {
				userKey.remove("skey");
				sid = "";
			}
			String mainURL = DocUtil.getMainURL(qq, sid);
			Document doc = Jsoup.connect(mainURL)
					.timeout(5000).get();
			String username = "";
			if (doc.text().contains("开通达人")) {
				username = DocUtil.substring(doc.text(), "帮友|侠侣", 5, "开通达人");
			} else if (doc.text().contains("续费达人")) {
				username = DocUtil.substring(doc.text(), "帮友|侠侣", 5,
						"续费达人   等级");
			} else {
				synchronized (object) {
					// 若获取不到username，表示sid已失效，重新获取
					String QQ = userKey.get("QQ");
					String password = userKey.get("password");
					int loginStatus = LoginUtil.login(QQ, password, "");
					LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) UserUtil
							.getSettingByKey("小号");
					for (String u : map.keySet()) {
						if (getUserKeyByUsrname(u).get("QQ").equals(QQ)) {
							username = u;
							oldUsername = username;
						}
					}
					if (0 != loginStatus) {
						if (2 == loginStatus) {
							PrintUtil.printTitleInfo("系统消息", "小号密码有变，请重新录入！",
									username);
						} else {
							PrintUtil.printTitleInfo("系统消息", "skey失效，请输入验证码！",
									username);
						}
						设置面板.inputQQ.setText(QQ);
						设置面板.inputPassword.setText(password);
						if (1 == loginStatus || -1 == loginStatus) {
							设置面板.showVerifyCode(true);
						}
						return null;
					}
					userKey.put("uin", QQLogin.cookiesAndSid.get("uin"));
					// userKey.put("skey", QQLogin.cookiesAndSid.get("skey"));
					// //废弃
					userKey.put("sid", QQLogin.cookiesAndSid.get("sid"));
					username = getUsername(userKey);
					Map<String, Object> users = (LinkedHashMap<String, Object>) UserUtil
							.getSettingByKey("小号");
					Object object = users.put(username, userKey);
					if (object == null) { // 若object为null，说明有小号昵称改变，需要去重处理
						users.remove(oldUsername);
					}
					UserUtil.saveSetting();
					return username;
				}
			}
			// 获取到的昵称前后会带一个空格，去掉
			return username.substring(1, username.length() - 1);
		} catch (SocketTimeoutException e) {
			return getUsername(userKey);
		}
	}

	/**
	 * 获取小号对应的userKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getUserKeyByUsrname(String username) {
		Map<String, String> userInfo = null;
		try {
			userInfo = (Map<String, String>) ((LinkedHashMap<String, Object>)UserUtil.getSettingByKey("小号")).get(username);
			return userInfo;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userInfo;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(UserUtil.getSetting());
		Map<String, Object> map = (LinkedHashMap<String, Object>)UserUtil.getSetting().get("小号");
		for(Object o : map.values()) {
			((Map<String, Object>)o).put("sid", "ee");
		}
		UserUtil.saveSetting();
		System.out.println(UserUtil.getSetting());
	}
}
