package util;

import java.util.HashSet;
import java.util.Set;

import model.乐斗项目;
import core.乐斗面板;

public class PrintUtil {
	
	/**
	 * 打印不带标题的信息
	 * @param text
	 */
	public static void printInfo(String text) {
		乐斗面板.textArea.append(text);
	}
	
	/**
	 * 打印乐斗项目的所有message
	 * @param model 乐斗项目
	 * @param username 用户名
	 */
	public static void printAllMessages(乐斗项目 model, String username) {
		乐斗面板.textArea.append("【"+get乐斗项目名(model)+"】【"+username+"】\n");
		for (Object o : model.getMessage().values()) {
			乐斗面板.textArea.append("    " + o.toString() + "\n");
			乐斗面板.textArea.setCaretPosition(乐斗面板.textArea.getText()
					.length());
		}
		
		//执行当前打印函数的时候，同时更新进度条
		updateProgress("【"+get乐斗项目名(model)+"】【"+username+"】");
	}
	
	public static Set<String> printTask = new HashSet<String>();
	private static void updateProgress(String string) {
		//添加成功，不存在重复的，就执行更新进度条
		if(printTask.add(string))
			乐斗面板.updateProgress();
	}
	
	/**
	 * 打印乐斗项目的信息
	 * @param model 乐斗项目
	 * @param key 指定message所需关键字
	 * @param username 用户名
	 */
	public static void printMessage(乐斗项目 model, String message, String username) {
		乐斗面板.textArea.append("【"+get乐斗项目名(model)+"】【"+username+"】\n");
		乐斗面板.textArea.append("    "+message+"\n");
		
		//执行当前打印函数的时候，同时更新进度条
		updateProgress("【"+get乐斗项目名(model)+"】【"+username+"】");
	}
	
	/**
	 * 打印乐斗项目的指定message
	 * @param model 乐斗项目
	 * @param key 指定message所需关键字
	 * @param username 用户名
	 */
	public static void printMessageByKey(乐斗项目 model, String key, String username) {
		乐斗面板.textArea.append("【"+get乐斗项目名(model)+"】【"+username+"】\n");
		乐斗面板.textArea.append("    "
				+ model.getMessage().get(key) + "\n");
	}
	
	/**
	 * 打印带标题的信息
	 * @param title 标题
	 * @param message 信息内容
	 * @param username 用户名
	 */
	public static void printTitleInfo(String title, String message, String username) {
		乐斗面板.textArea.append("【"+title+"】【"+username+"】\n");
		乐斗面板.textArea.append("    "+message+"\n");
	}
	
	/**
	 * 打印带标题的信息
	 * @param title 标题
	 * @param message 信息内容
	 */
	public static void printTitleInfo(String title, String message) {
		乐斗面板.textArea.append("【"+title+"】\n");
		乐斗面板.textArea.append("    "+message+"\n");
	}
	
	private static String get乐斗项目名(乐斗项目 model) {
		String className = model.getClass().toString(); 
		return className.substring(className.lastIndexOf(".")+1);
	}
}
