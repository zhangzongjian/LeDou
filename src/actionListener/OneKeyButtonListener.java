package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
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
import model.每日领奖;

import org.jsoup.nodes.Document;

import core.MainUI;

import util.DocUtil;
import util.Task;
import util.UserUtil;

//一键乐斗按钮响应
public class OneKeyButtonListener implements ActionListener {
	
	//存放线程名列表,（多小号多线程押镖）
	public static List<String> users = new ArrayList<String>();
	//乐斗任务列表，只执行列表中的任务，为null时表示没有任务
	public static List<String> tasks;
	
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		long startTime = System.currentTimeMillis();
		String mainURL = DocUtil.mainURL;
		if(mainURL == null) {
			MainUI.textArea.append("【系统消息】\n");
			MainUI.textArea.append("    未选择小号！\n");
			return;
		}
		try {
			tasks = saveTask();
			final String username = UserUtil.getUsername(mainURL);
			final Document mainDoc = DocUtil.clickURL(mainURL);
			if (tasks.contains(Task.镖行天下)) {
				Thread thread1 = new Thread(new Runnable() {
					public void run() {
						镖行天下 m = new 镖行天下(mainDoc);
						JLabel showTime = new JLabel();
						MainUI.timePanel.add(showTime);
						MainUI.tabs.setSelectedIndex(1); //切换选项卡到计时器面板
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
									showTime.setText("护送押镖("+m.getNum()+"/3)："+lastTime+"秒");
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
						users.remove(username);  //结束了就从线程列表中移除
						MainUI.timePanel.remove(showTime);
						MainUI.timePanel.repaint(); //重绘jPanel面板，就是刷新
					}
				}, username);
				if(!users.contains(thread1.getName())){ //username账号的送镖线程不存在，则启动线程
					users.add(username);	//启动username账号的线程
					thread1.start();
				}
			}
			if (tasks.contains(Task.答题)) {
				答题 m = new 答题(mainDoc);
				m.answer();
				MainUI.textArea.append("【答题】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (tasks.contains(Task.斗神塔)) {
				斗神塔 m = new 斗神塔(mainDoc);
				m.挑战();
				m.查看掉落情况();
				MainUI.textArea.append("【斗神塔】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (tasks.contains(Task.副本)) {
				副本 m = new 副本(mainDoc);
				m.挑战();
				MainUI.textArea.append("【副本】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (tasks.contains(Task.竞技场)) {
				竞技场 m = new 竞技场(mainDoc);
				m.挑战();
				MainUI.textArea.append("【竞技场】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (tasks.contains(Task.矿洞)) {
				矿洞 m = new 矿洞(mainDoc);
				m.挑战();
				MainUI.textArea.append("【矿洞】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (tasks.contains(Task.乐斗boss)) {
				乐斗boss m = new 乐斗boss(mainDoc);
				m.一键挑战();
				MainUI.textArea.append("【乐斗boss】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (tasks.contains(Task.历练)) {
				历练 m = new 历练(mainDoc);
				m.挑战();
				MainUI.textArea.append("【历练】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (tasks.contains(Task.每日领奖)) {
				每日领奖 m = new 每日领奖(mainDoc);
				m.领取();
				MainUI.textArea.append("【领取每日奖励】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (tasks.contains(Task.十二宫)) {
				十二宫 m = new 十二宫(mainDoc);
				//m.挑战();
				m.扫荡();
				MainUI.textArea.append("【十二宫】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			//许愿必须得放在好友乐斗之后执行
			if (tasks.contains(Task.许愿)) {
				许愿 m = new 许愿(mainDoc);
				m.xuYuan();
				MainUI.textArea.append("【许愿】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			//完成任务要放到最后执行
			if (tasks.contains(Task.任务)) {
				任务 m = new 任务(mainDoc);
				m.finish();
				MainUI.textArea.append("【任务】\n");
				for (Object o : m.getMessage().values()) {
					MainUI.textArea.append("    " + o.toString() + "\n");
				}
			}
			if(tasks == null || tasks.isEmpty()) {
				MainUI.textArea.append("【系统消息】\n");
				MainUI.textArea.append("    未选择任何操作！\n");
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

	//保存任务选项
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