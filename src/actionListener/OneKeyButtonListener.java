package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import model.乐斗boss;
import model.任务;
import model.副本;
import model.十二宫;
import model.历练;
import model.斗神塔;
import model.矿洞;
import model.竞技场;
import model.答题;
import model.许愿;
import model.镖行天下;
import model.领取每日奖励;

import org.jsoup.nodes.Document;

import core.MainUI;

import util.DocUtil;
import util.UserUtil;

//一键乐斗按钮响应
public class OneKeyButtonListener implements ActionListener {
	
	//存放线程名列表,（多小号多线程押镖）
	public static List<String> users = new ArrayList<String>();

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		long startTime = System.currentTimeMillis();
		String mainURL = DocUtil.mainURL;
		if(mainURL == null) {
			MainUI.textArea.append("【系统消息】\n");
			MainUI.textArea.append("    未选择小号！\n");
			return;
		}
		ArrayList<String> list = new ArrayList<String>();
//		list.add("镖行天下");  //多线程
//		list.add("答题");   //无异常
//		list.add("副本");  //无异常
//		list.add("竞技场");  //无异常
//		list.add("斗神塔"); //无异常
//		list.add("矿洞");  //无异常
//		list.add("任务");  //无异常
//		list.add("乐斗boss"); //无异常
//		list.add("历练"); //无异常
//		list.add("领取每日奖励");  //无异常
//		list.add("十二宫"); //无异常
//		list.add("许愿"); //无异常
		try {
			final String username = UserUtil.getUsername(mainURL);
			final Document mainDoc = DocUtil.clickURL(mainURL);
			if (list.contains("镖行天下")) {
				Thread thread1 = new Thread(new Runnable() {
					public void run() {
						镖行天下 m = new 镖行天下(mainDoc);
						JLabel showTime = new JLabel();
						MainUI.jPanel.add(showTime);
						m.劫镖();
						MainUI.textArea.append("【镖行天下】\n");
						for (Object o : m.getMessage().values()) {
							MainUI.textArea.append("    " + o.toString() + "\n");
						}
						//计时多次送镖
						int lastTime;
						int num = m.getNum();
						if(num == 0) {
							MainUI.textArea.append("【镖行天下】\n");
							MainUI.textArea.append("    护送次数已用完！\n");
							return;
						}
						for (int i = 0; i < num; i++) {
							m.护送押镖();
							MainUI.textArea.append("【镖行天下】\n");
							MainUI.textArea.append("    " + m.getMessage().get("护送状态") + "\n");
							lastTime = m.getLastTime();
							while(lastTime > 0) {
								lastTime = lastTime - 1;  //每秒更新一次显示
								try {
									showTime.setText(username+"：护送押镖("+m.getNum()+"/3)："+lastTime+"秒");
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
						users.remove(username);  //结束了就从线程列表中移除
						MainUI.jPanel.remove(showTime);
						MainUI.jPanel.repaint(); //重绘jPanel面板，就是刷新
						System.out.println(showTime.getText());
					}
				}, username);
				if(!users.contains(thread1.getName())){ //username账号的送镖线程不存在，则启动线程
					users.add(username);	//启动username账号的线程
					thread1.start();
				}
			}
			if (list.contains("答题")) {
				答题 m = new 答题(mainDoc);
				m.answer();
				MainUI.textArea.append("【答题】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("斗神塔")) {
				斗神塔 m = new 斗神塔(mainDoc);
				m.挑战();
				m.查看掉落情况();
				MainUI.textArea.append("【斗神塔】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("副本")) {
				副本 m = new 副本(mainDoc);
				m.挑战();
				MainUI.textArea.append("【副本】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("竞技场")) {
				竞技场 m = new 竞技场(mainDoc);
				m.挑战();
				MainUI.textArea.append("【竞技场】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("矿洞")) {
				矿洞 m = new 矿洞(mainDoc);
				m.挑战();
				MainUI.textArea.append("【矿洞】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("乐斗boss")) {
				乐斗boss m = new 乐斗boss(mainDoc);
				m.一键挑战();
				MainUI.textArea.append("【乐斗boss】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("历练")) {
				历练 m = new 历练(mainDoc);
				m.挑战();
				MainUI.textArea.append("【历练】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("领取每日奖励")) {
				领取每日奖励 m = new 领取每日奖励(mainDoc);
				m.领取();
				MainUI.textArea.append("【领取每日奖励】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("十二宫")) {
				十二宫 m = new 十二宫(mainDoc);
				//m.挑战();
				m.扫荡();
				MainUI.textArea.append("【十二宫】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			//许愿必须得放在好友乐斗之后执行
			if (list.contains("许愿")) {
				许愿 m = new 许愿(mainDoc);
				m.xuYuan();
				MainUI.textArea.append("【许愿】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			//完成任务要放到最后执行
			if (list.contains("任务")) {
				任务 m = new 任务(mainDoc);
				m.finish();
				MainUI.textArea.append("【任务】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}

			long endTime = System.currentTimeMillis();
			MainUI.textArea.append("(耗时：" + (endTime - startTime) / 1000.0
					+ "秒)\n");
		} catch (IllegalArgumentException e) {
			MainUI.textArea.append("【系统消息】\n");
			MainUI.textArea.append("    访问失败，请重试！\n");
			e.printStackTrace();
		} catch (IOException e) {
			MainUI.textArea.append("【系统消息】\n");
			MainUI.textArea.append("    连接超时，请重试！\n");
			e.printStackTrace();
		}
	}

}