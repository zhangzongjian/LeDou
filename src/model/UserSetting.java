package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserSetting {

	private static Map<String, Object> setting = null;
	private static String settingFile = System.getProperty("user.dir")+"\\resources\\setting.data";
	
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
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
//			UserSetting.saveSetting();
//			System.out.println(UserSetting.getSetting());
//			System.out.println(((LinkedHashMap<String, Object>)UserSetting.getSettingByKey("小号")).remove("詠NO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
			UserSetting.saveSetting();
			System.out.println(UserSetting.getSetting());
			Object o = UserSetting.getSetting().get("小号");
			HashMap<String, Object> m = (HashMap<String, Object>)o;
			System.out.println(m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
