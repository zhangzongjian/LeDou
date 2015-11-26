package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


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
		if(setting.get(key) == null) {
			setting.put(key, null);
		}
		return setting.get(key);
	}
	
	/**
	 * 保存设置到文件
	 * @throws IOException
	 */
	public static void saveSetting() throws IOException {
		if(setting == null || setting.isEmpty()) return; //防止清空设置
		File file = new File(settingFile);
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(setting);
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
	public static Map<String, Object> loadSetting() throws IOException {
		File file = new File(settingFile);
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		Map<String, Object> map = null;
		try {
			map = (Map<String, Object>) in.readObject();
			in.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 从大乐斗首页中，获取用户昵称
	 * @return
	 * @throws IOException 
	 */
	public static String getUsername(Map<String, String> userKey) throws IOException, StringIndexOutOfBoundsException {
		String mainURL = "http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=&sid=&channel=209&g_ut=1&cmd=index";
		Document doc = Jsoup.connect(mainURL)
							.cookies(userKey)
							.timeout(5000)
							.get();
		String username = "";
		if(doc.text().contains("开通达人")) {
			username = DocUtil.substring(doc.text(), "帮友|侠侣", 5, "开通达人");
		}
		else { 
			username = DocUtil.substring(doc.text(), "帮友|侠侣", 5, "续费达人   等级");
		}
		//获取到的昵称前后会带一个空格，去掉
		return username.substring(1, username.length()-1);
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
	}
}
