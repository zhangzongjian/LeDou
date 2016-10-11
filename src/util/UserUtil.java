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
	private static int count = 0;
	
	/**
	 * 从大乐斗首页中，获取用户昵称
	 * @return 获取失败时返回null
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public static String getUsername(Map<String, String> userKey) throws IOException {
		if(userKey == null) {
			return null;
		}
	    String oldUsername = null;
		try {
			String mainURL = "http://dld.qzapp.z.qq.com/qpet/cgi-bin/phonepk?zapp_uin=&sid=&channel=209&g_ut=1&cmd=index";
			Document doc = Jsoup.connect(mainURL).cookies(userKey)
					.timeout(5000).get();
			String username = "";
			if (doc.text().contains("开通达人")) {
				username = DocUtil.substring(doc.text(), "帮友|侠侣", 5, "开通达人");
			} else if (doc.text().contains("续费达人")) {
				username = DocUtil.substring(doc.text(), "帮友|侠侣", 5,
						"续费达人   等级");
			} else {
				// 若获取不到username，表示skey已失效，重新获取
                synchronized (object) {
                	String QQ = userKey.get("QQ");
                	
                	LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) UserUtil
                            .getSettingByKey("小号");
                	//利用QQ号，从账号记录(setting)中查找小号昵称（若用户改昵称了，则账号记录里的为旧昵称oldUsername）
                	for (String u : map.keySet()) {
                		if (getUserKeyByUsrname(u).get("QQ").equals(QQ)) {
                			username = u;
                			oldUsername = username;
                			break;
                		}
                	}
                	
                    userKey = getNewUserKey(userKey, username);
                    username = getUsername(userKey);  //从乐斗里获取小号的最新昵称
                    
                    if(username != null && userKey != null) {
	                    Map<String, Object> users = (LinkedHashMap<String, Object>) UserUtil
	                            .getSettingByKey("小号");
	                    Object object = users.put(username, userKey);
	                    if (object == null) { // 若object为null，说明有小号昵称改变，需要去重处理
	                        users.remove(oldUsername);
	                        System.out.println(oldUsername+" 昵称有变化： "+username);
	                    }
	                    UserUtil.saveSetting();
	                    return username;
                    }
                }
            }
			// 获取到的昵称前后会带一个空格，去掉
			if(username == null) {
				return null;
			}
			else {
				return username.substring(1, username.length() - 1);
			}
		} catch(SocketTimeoutException e) {
			System.out.println("获取用户昵称超时-->第" +(++count)+ "次重试！");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(count > 10) throw e;
			return getUsername(userKey);
		} finally {
			count = 0;
		}
		
		
	}

	/**
	 * skey失效，重新登录获取新的。若登录失败返回null
	 * @param userKey
	 * @return
	 * @throws IOException 
	 */
	public synchronized static Map<String, String> getNewUserKey(Map<String, String> userKey, String username) {
		String QQ = userKey.get("QQ");
        String password = userKey.get("password");
		try {
			int loginStatus = LoginUtil.login(QQ, password, "");
			if (0 != loginStatus) {
	            if (2 == loginStatus) {
	                PrintUtil.printTitleInfo("系统消息", "小号密码有变，请重新录入！", username);
	            } else {
	                PrintUtil.printTitleInfo("系统消息", "skey失效，请输入验证码！", username);
	            }
				设置面板.inputQQ.setText(QQ);
				设置面板.inputPassword.setText(password);
				if (1 == loginStatus || -1 == loginStatus) {
					设置面板.showVerifyCode(true);
				}
				return null;
	        }
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
        userKey.put("uin", QQLogin.cookies.get("uin"));
        userKey.put("skey", QQLogin.cookies.get("skey"));
		return userKey;
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
	
	/**
	 * 检查userKey是否有效。（访问乐斗主页，得到登录页面，说明失效了）
	 * @return 有效返回true，无效返回false
	 */
	public static boolean checkUserKeyValid(Map<String, String> userKey) {
		try {
			Document mainDoc = DocUtil.clickURL(userKey, DocUtil.mainURL);
			if(mainDoc.text().contains("手机统一登录")) {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(UserUtil.getSetting());
		Map<String, Object> map = (LinkedHashMap<String, Object>)UserUtil.getSetting().get("小号");
		for(Object o : map.values()) {
			((Map<String, Object>)o).put("skey", "ee");
		}
		UserUtil.saveSetting();
	}
}
