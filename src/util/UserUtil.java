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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 从免登陆链接页面中，获取用户昵称
	 * @return
	 * @throws IOException 
	 */
	public static String getUsername(String mainURL) throws IOException, StringIndexOutOfBoundsException {
		Document doc = Jsoup.connect(mainURL).get();
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
	 * 获取小号对应的免登陆链接
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getMainURLByUsrname(String username) {
		String mainURL = null;
		try {
			mainURL = ((LinkedHashMap<String, Object>)UserUtil.getSettingByKey("小号")).get(username).toString();
			return mainURL;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mainURL;
	}
	
	//重置设置数据
	/*private static void resetSetting() {
		try {
			setting = new LinkedHashMap<String, Object>();
			//放入设置关键字，以及对应的数据类型，免得等到用该设置的时候再去判断是否存在关键字
			setting.put("小号", new LinkedHashMap<String, Object>());
			setting.put("任务列表", new ArrayList<String>());
			File file = new File(settingFile);
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(file));
			out.writeObject(setting);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
