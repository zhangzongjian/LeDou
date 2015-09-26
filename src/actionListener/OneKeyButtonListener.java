package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import model.Task;
import model.impl.*;

import org.jsoup.nodes.Document;

import util.DocUtil;
import util.UserUtil;
import core.MainUI;

//一键乐斗按钮响应
public class OneKeyButtonListener implements ActionListener {

	// 存放线程名列表,（多小号多线程押镖）
	public static List<String> users = new ArrayList<String>();
	// 存放线程名列表1,（巅峰复活线程）
	public static List<String> users1 = new ArrayList<String>();
	// 乐斗任务列表，只执行列表中的任务，为null时表示没有任务
	public static List<String> tasks;

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent paramActionEvent) {
		tasks = saveTask();  //一键乐斗前，把任务多选框面板的选项保存一下
		if(MainUI.allUsers.isSelected()) {
			try {
				for (final Object s : ((Map<String, Object>) UserUtil
						.getSetting().get("小号")).values()) {
					Thread t = new Thread(new Runnable() {
						public void run() {
							oneKeyLeDou(s.toString());
						}
					});
					t.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			//一个小号执行的时候，用一个线程开始，比不用线程体验要好得多
			Thread t = new Thread(new Runnable(){
				public void run() {
					String mainURL = DocUtil.mainURL;
					oneKeyLeDou(mainURL);
				}
			});
			t.start();
		}
		
	}
	
	public void oneKeyLeDou(String mainURL) {
		if (mainURL == null) {
			MainUI.textArea.append("【系统消息】\n");
			MainUI.textArea.append("    未选择小号！\n");
			return;
		}
		try {
			final Document mainDoc = DocUtil.clickURL(mainURL);
			final String username = UserUtil.getUsername(mainURL);
			if (tasks.contains(Task.巅峰之战)) {
				// 巅峰之战 m = new 巅峰之战(mainDoc);
				// m.领奖和报名(); //周一6点钟之后执行
				// MainUI.textArea.append("【巅峰之战】\n");
				// for (Object o : m.getMessage().values()) {
				// MainUI.textArea.append("    " + o.toString() + "\n");
				// }
				Thread thread1 = new Thread(new Runnable() {
					public void run() {
						JLabel showTime = new JLabel();
						MainUI.timePanel.add(showTime);
						巅峰之战 m = new 巅峰之战(mainDoc);
						if (!m.is挑战时间()) {
							MainUI.textArea.append("【巅峰之战】\n");
							MainUI.textArea.append("    非挑战时间！\n");
							users1.remove(username); // 结束了就从线程列表中移除
							return;
						}
						int lastTime;
						while (true) {
							m.挑战();
							if (null != m.getMessage().get("挑战结束")) {
								MainUI.textArea.append("【巅峰之战】\n");
								MainUI.textArea.append("    "+m.getMessage().get("挑战结束")+"\n");
								break;
							}
							MainUI.tabs.setSelectedIndex(2); // 切换选项卡到计时器面板
							MainUI.textArea.append("【巅峰之战】\n");
							for (Object o : m.getMessage().values()) {
								MainUI.textArea.append("    " + o.toString()
										+ "\n");
							}
							lastTime = m.getLastTime();
							while (lastTime > 0) {
								lastTime = lastTime - 1; // 每秒更新一次显示
								try {
									showTime.setText("巅峰等待中：" + lastTime + "秒");
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
						users1.remove(username); // 结束了就从线程列表中移除
						MainUI.timePanel.remove(showTime);
						MainUI.timePanel.repaint(); // 重绘jPanel面板，就是刷新
					}
				}, username);
				if (!users1.contains(thread1.getName())) { // username账号的送镖线程不存在，则启动线程
					users1.add(username); // 启动username账号的线程
					thread1.start();
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.镖行天下)) {
				Thread thread1 = new Thread(new Runnable() {
					public void run() {
						镖行天下 m = new 镖行天下(mainDoc);
						JLabel showTime = new JLabel();
						MainUI.timePanel.add(showTime);
						m.劫镖();
						MainUI.textArea.append("【镖行天下】\n");
						for (Object o : m.getMessage().values()) {
							MainUI.textArea.append("    " + o.toString() + "\n");
						}
						// 计时多次送镖
						int lastTime;
						int num = m.getNum();
						if (num == 0) {
							MainUI.textArea.append("【镖行天下】\n");
							MainUI.textArea.append("    护送次数已用完！\n");
							users.remove(username); // 结束了就从线程列表中移除
							return;
						}
						MainUI.tabs.setSelectedIndex(2); // 切换选项卡到计时器面板
						while (num > 0) {
							m.护送押镖();
							// 若正在护送，次数不减
							if (!m.getMessage().get("护送状态")
									.equals("您正在护送押镖中哦！"))
								num--;
							MainUI.textArea.append("【镖行天下】\n");
							MainUI.textArea.append("    "
									+ m.getMessage().get("护送状态") + "\n");
							lastTime = m.getLastTime();
							while (lastTime > 0) {
								lastTime = lastTime - 1; // 每秒更新一次显示
								try {
									showTime.setText("护送押镖(" + num + "/3)："
											+ lastTime + "秒");
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
						users.remove(username); // 结束了就从线程列表中移除
						MainUI.timePanel.remove(showTime);
						MainUI.timePanel.repaint(); // 重绘jPanel面板，就是刷新
					}
				});
				if (!users.contains(username)) { // username账号的送镖线程不存在，则启动线程
					users.add(username); // 启动username账号的线程
					thread1.start();
				}
			}
			// /////////////////////////////////////////////////////////////////////
			if (tasks.contains(Task.答题)) {
				答题 m = new 答题(mainDoc);
				m.answer();
				MainUI.textArea.append("【答题】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.斗神塔)) {
				斗神塔 m = new 斗神塔(mainDoc);
				m.挑战();
				m.查看掉落情况();
				MainUI.textArea.append("【斗神塔】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.副本)) {
				副本 m = new 副本(mainDoc);
				m.挑战();
				MainUI.textArea.append("【副本】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.助阵)) {
				助阵 m = new 助阵(mainDoc);
				m.doit("以柔克刚", 1);
				MainUI.textArea.append("【助阵】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.竞技场)) {
				竞技场 m = new 竞技场(mainDoc);
				m.挑战();
				MainUI.textArea.append("【竞技场】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.矿洞)) {
				矿洞 m = new 矿洞(mainDoc);
				m.挑战();
				MainUI.textArea.append("【矿洞】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.传功)) {
				传功 m = new 传功(mainDoc);
				m.doit();
				MainUI.textArea.append("【传功】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.乐斗boss)) {
				乐斗boss m = new 乐斗boss(mainDoc);
				m.一键挑战();
				MainUI.textArea.append("【乐斗boss】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.历练)) {
				历练 m = new 历练(mainDoc);
				m.挑战();
				MainUI.textArea.append("【历练】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.抢地盘)) {
				抢地盘 m = new 抢地盘(mainDoc);
				m.doit();
				MainUI.textArea.append("【抢地盘】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.好友乐斗)) {
				好友乐斗 m = new 好友乐斗(mainDoc);
				m.doit();
				MainUI.textArea.append("【好友乐斗】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.每日领奖)) {
				每日领奖 m = new 每日领奖(mainDoc);
				m.领取();
				MainUI.textArea.append("【领取每日奖励】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.十二宫)) {
				十二宫 m = new 十二宫(mainDoc);
				// m.挑战();
				m.扫荡();
				MainUI.textArea.append("【十二宫】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
				MainUI.jPanel.repaint();
			}
			// //////////////////////////////////////////////////////////
			// 许愿必须得放在好友乐斗之后执行
			if (tasks.contains(Task.许愿)) {
				许愿 m = new 许愿(mainDoc);
				m.xuYuan();
				MainUI.textArea.append("【许愿】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.门派大战)) {
				门派大战 m = new 门派大战(mainDoc);
				m.报名(); // 周三6点开始
				m.领奖(); // 周一6点开始
				MainUI.textArea.append("【门派大战】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.结拜赛)) {
				结拜赛 m = new 结拜赛(mainDoc);
				m.报名(); // 周一12点开始
				m.助威(); //助威周四0点开始，
				m.助威领奖(); //领奖周六0点开始
				MainUI.textArea.append("【结拜赛】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			////////////////////////////////////////////////////////////
			if (tasks.contains(Task.回流好友召回)) {
				回流好友召回 m = new 回流好友召回(mainDoc);
				m.doit();
				MainUI.textArea.append("【回流好友召回】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.踢馆)) {
				踢馆 m = new 踢馆(mainDoc);
				m.领奖和报名(); // 周六0点开始
				m.挑战(); // 周五6点开始
				MainUI.textArea.append("【踢馆】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.武林大会)) {
				武林大会 m = new 武林大会(mainDoc);
				m.报名(); // 每天13点开始
				MainUI.textArea.append("【武林大会】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.掠夺)) {
				掠夺 m = new 掠夺(mainDoc);
				m.领奖(); // 周三6点开始
				MainUI.textArea.append("【掠夺】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.锦标赛)) {
				锦标赛 m = new 锦标赛(mainDoc);
				m.赞助(); // 每天12点开始
				MainUI.textArea.append("【锦标赛】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// /////////////////////////////////////////////////////////
			if (tasks.contains(Task.供奉)) {
				供奉 m = new 供奉(mainDoc);
				m.一键供奉();
				MainUI.textArea.append("【供奉】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.分享)) {
				分享 m = new 分享(mainDoc);
				m.一键分享();
				MainUI.textArea.append("【分享】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.帮战奖励)) {
				帮战奖励 m = new 帮战奖励(mainDoc);
				m.领奖(); // 周六6点开始
				MainUI.textArea.append("【帮战奖励】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			// 完成任务要放到最后执行
			if (tasks.contains(Task.任务)) {
				任务 m = new 任务(mainDoc);
				m.finish();
				MainUI.textArea.append("【任务】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			// 活跃度要放到更后面执行
			if (tasks.contains(Task.活跃度)) {
				活跃度 m = new 活跃度(mainDoc);
				m.领取();
				MainUI.textArea.append("【活跃度】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks == null || tasks.isEmpty()) {
				MainUI.textArea.append("【系统消息】\n");
				MainUI.textArea.append("    未选择任何操作！\n");
			}
			// //////////////////////////////////////////////////////////
			MainUI.textArea.append("\n");
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

	// 保存任务选项
	public List<String> saveTask() {
		try {
			tasks = new ArrayList<String>();
			for (JCheckBox j : MainUI.taskList) {
				if (j.isSelected())
					OneKeyButtonListener.tasks.add(j.getText());
			}
			UserUtil.addSetting("任务列表", tasks);
			UserUtil.saveSetting();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return tasks;
	}
}