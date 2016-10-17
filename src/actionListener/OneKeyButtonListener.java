package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import model.Task;
import model.其他操作集合;
import model.活动集合;
import model.impl.乐斗boss;
import model.impl.任务;
import model.impl.传功;
import model.impl.供奉;
import model.impl.分享;
import model.impl.助阵;
import model.impl.十二宫;
import model.impl.历练;
import model.impl.回流好友召回;
import model.impl.好友乐斗;
import model.impl.巅峰之战;
import model.impl.帮战奖励;
import model.impl.幻境;
import model.impl.抢地盘;
import model.impl.掠夺;
import model.impl.探险;
import model.impl.斗神塔;
import model.impl.武林大会;
import model.impl.每日领奖;
import model.impl.活跃度;
import model.impl.画卷迷踪;
import model.impl.矿洞;
import model.impl.祭坛;
import model.impl.竞技场;
import model.impl.结拜赛;
import model.impl.群雄逐鹿;
import model.impl.许愿;
import model.impl.踢馆;
import model.impl.锦标赛;
import model.impl.镖行天下;
import model.impl.门派大战;
import model.impl.门派邀请赛;

import org.jsoup.nodes.Document;

import util.DocUtil;
import util.PrintUtil;
import util.TimeUtil;
import util.TimerTask;
import util.UserUtil;
import core.MainUI;
import core.乐斗面板;
import core.计时面板;
import core.设置面板;

//一键乐斗按钮响应
public class OneKeyButtonListener implements ActionListener {

	// 存放线程名列表,（多小号多线程押镖）
	private List<String> users = new ArrayList<String>();
	// 存放线程名列表1,（巅峰复活线程）
	private List<String> users1 = new ArrayList<String>();
	// 乐斗任务列表，只执行列表中的任务，为null时表示没有任务
	private List<String> tasks;

	public void actionPerformed(final ActionEvent paramActionEvent) {
		oneKeyLeDou_all();
		
		//如果不勾选执行完毕自动退出，则每天06:00:10点自动执行
		if( !乐斗面板.autoCloseCheckBox.isSelected() ) {
			TimeUtil.timer.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						/**
						 * 乐斗日志大小检查。异常错误日志大小检查。。待定。。
						 */
						File LeDou_log = new File("resources/LeDou_log.txt");
						if(!LeDou_log.exists() || LeDou_log.length()>20*1024*1024) {
							LeDou_log.delete();
							LeDou_log.createNewFile();
						}
						FileOutputStream out = new FileOutputStream(LeDou_log, true);
						out.write(new String("--------------------------"+new SimpleDateFormat("YYYY/MM/dd HH:mm:ss").format(TimeUtil.getLastDay(new Date()))+"--------------------------\n").getBytes());
						out.write(乐斗面板.textArea.getText().getBytes());
						out.write(new String("\n").getBytes());
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					乐斗面板.textArea.setText("");
					oneKeyLeDou_all();
				}
			}, "06:00:10");
		}
	}

	@SuppressWarnings("unchecked")
	private void oneKeyLeDou_all() {
		tasks = 设置面板.saveTask(); // 一键乐斗前，把任务多选框面板的选项保存一下
		//乐斗面板.initProgressBar(); //初始化进度条
		if (乐斗面板.allUsersCheckBox.isSelected()) {
			try {
				for (final Object s : ((Map<String, Object>) UserUtil.getSetting().get("小号")).values()) {
					Thread t = new Thread(new Runnable() {
						public void run() {
							Map<String, String> userKey = (Map<String, String>)s;
							oneKeyLeDou(userKey);
						}
					});
					t.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			/* 一个小号，新建子线程执行，可以即时输出到TextArea文本框中，若在主线程中执行，需要等全部任务执行完了才输出。 */
			Thread t = new Thread(new Runnable() {
				public void run() {
					Map<String, String> userKey = DocUtil.userKey;
					oneKeyLeDou(userKey);
				}
			});
			t.start();
		}
	}
	
	
	private void oneKeyLeDou(final Map<String, String> userKey) {
		if (userKey == null) {
			PrintUtil.printTitleInfo("系统消息", "未选择小号！");
			return;
		}
		try {
			final String username = UserUtil.getUsername(userKey);
			//若username获取不到，说明skey过期，且需要验证码，得重新手动录入。若不需验证码，则可自动重新录入
			if(username == null) return;
			final Document mainDoc = DocUtil.clickURL(userKey, DocUtil.mainURL);
			// 单个小号线程内的全局userKey（使用场景：遇到像定时锦标赛，还没到指定时间userkey失效的。更新后的userKey放这里）
			final Map<String, Object> timeUserKey = new HashMap<>();
			timeUserKey.put(username, userKey);
			
			if (tasks.contains(Task.巅峰之战)) {
				 巅峰之战 m = new 巅峰之战(userKey, mainDoc);
				 m.领奖和报名(); //周一6点钟之后执行
				 PrintUtil.printAllMessages(m, username);
				 Thread thread1 = new Thread(new Runnable() {
					public void run() {
						JLabel showTime = new JLabel();
						计时面板.timePanel.add(showTime);
						巅峰之战 m = new 巅峰之战(userKey, mainDoc);
						if (!m.is挑战时间()) {
							PrintUtil.printMessage(m, "非挑战时间！", username);
							users1.remove(username); // 结束了就从线程列表中移除
							return;
						}
						int lastTime;
						int i = 0;
						while (true) {
							m.挑战();
							i++;
							if(i > 15) {
								break;
							}
							if (null != m.getMessage().get("挑战结束")) {
								PrintUtil.printMessageByKey(m, "挑战结束", username);
								break;
							}
							PrintUtil.printAllMessages(m, username);
							lastTime = m.getLastTime();
							while (lastTime > 0) {
								lastTime = lastTime - 1; // 每秒更新一次显示
								try {
									showTime.setText("  【"+username+"】巅峰等待中：" + lastTime + "秒");
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							
						}
						users1.remove(username); // 结束了就从线程列表中移除
						计时面板.timePanel.remove(showTime);
						计时面板.timePanel.repaint(); // 重绘jPanel面板，就是刷新
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
						final 镖行天下 m = new 镖行天下(userKey, mainDoc);
						JLabel showTime = new JLabel();
						计时面板.timePanel.add(showTime);
						new Thread(new Runnable() {
							public void run() {
								m.劫镖();
								PrintUtil.printAllMessages(m, username);
							}
						}).start();
						// 计时多次送镖
//						int lastTime;
						int num = m.getNum();
//						if (num == 0) {
//							PrintUtil.printMessage(m, "护送次数已用完！", username);
//							users.remove(username); // 结束了就从线程列表中移除
//							return;
//						}
						if (num >= 0) {
							m.护送押镖();
							// 若正在护送，次数不减
							if (!m.getMessage().get("护送状态")
									.equals("您正在护送押镖中哦！"))
								num--;
							PrintUtil.printAllMessages(m, username);
//							lastTime = m.getLastTime();
							/*while (lastTime > 0) {
								lastTime = lastTime - 1; // 每秒更新一次显示
								try {
									showTime.setText("  【"+username+"】护送押镖(" + num + "/3)："
											+ lastTime + "秒");
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}*/
						}
						users.remove(username); // 结束了就从线程列表中移除
						计时面板.timePanel.remove(showTime);
						计时面板.timePanel.repaint(); // 重绘jPanel面板，就是刷新
					}
				});
				if (!users.contains(username)) { // username账号的送镖线程不存在，则启动线程
					users.add(username); // 启动username账号的线程
					thread1.start();
				}
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.分享)) {
				分享 m = new 分享(userKey, mainDoc);
				m.一键分享();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.斗神塔)) {
				斗神塔 m = new 斗神塔(userKey, mainDoc);
				m.挑战();
				m.查看掉落情况();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.画卷迷踪)) {
				画卷迷踪 m = new 画卷迷踪(userKey, mainDoc);
				m.挑战();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
            if (tasks.contains(Task.抢地盘)) {
                                        抢地盘 m = new 抢地盘(userKey, mainDoc);
                m.doit();
                PrintUtil.printAllMessages(m, username);
            }
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.群雄逐鹿)) {
				群雄逐鹿 m = new 群雄逐鹿(userKey, mainDoc);
				m.报名和领奖();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.门派)) {
				门派大战 m = new 门派大战(userKey, mainDoc);
				m.doit();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.竞技场)) {
				竞技场 m = new 竞技场(userKey, mainDoc);
				m.挑战();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.吃药10)) {
				其他操作集合 m = new 其他操作集合(userKey, mainDoc);
				m.吃药10();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.矿洞)) {
				矿洞 m = new 矿洞(userKey, mainDoc);
				m.挑战();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.传功)) {
				传功 m = new 传功(userKey, mainDoc);
				m.doit();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.乐斗boss)) {
				乐斗boss m = new 乐斗boss(userKey, mainDoc);
				m.一键挑战();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.历练)) {
				历练 m = new 历练(userKey, mainDoc);
				m.挑战();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.幻境)) {
				幻境 m = new 幻境(userKey, mainDoc);
				m.挑战();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.助阵)) {
				助阵 m = new 助阵(userKey, mainDoc);
				m.doit();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.好友乐斗)) {
				好友乐斗 m = new 好友乐斗(userKey, mainDoc);
				m.doit();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.每日领奖)) {
				每日领奖 m = new 每日领奖(userKey, mainDoc);
				m.领取();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.十二宫)) {
				十二宫 m = new 十二宫(userKey, mainDoc);
				m.挑战();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			// 许愿必须得放在好友乐斗之后执行
			if (tasks.contains(Task.许愿)) {
				许愿 m = new 许愿(userKey, mainDoc);
				m.xuYuan();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.回流好友召回)) {
				回流好友召回 m = new 回流好友召回(userKey, mainDoc);
				m.doit();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.踢馆)) {
				踢馆 m = new 踢馆(userKey, mainDoc);
				m.领奖和报名(); // 周六0点开始
				m.挑战(); // 周五6点开始
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.武林大会)) {
				//13:00:05执行报名，预留5秒防止延迟
				long lastTime = TimeUtil.getSecond("13:00:05");
				if (lastTime > 0) {
					PrintUtil.printTitleInfo("武林大会", "武林大会将在13:00:05自动报名！（退出工具则不能自动报名）", username);
				}
				TimeUtil.timer.schedule(new TimerTask() {
					@Override
					public void run() {
						Map<String, String> tempUserKey = updateUserKey(timeUserKey, userKey, username);
						
						武林大会 m1 = new 武林大会(tempUserKey, mainDoc);
						m1.报名(); // 每天13点开始
						PrintUtil.printAllMessages(m1, username);
						
						任务 m2 = new 任务(tempUserKey, mainDoc);
						m2.finish();
						
						活跃度 m3 = new 活跃度(tempUserKey, mainDoc);
						m3.领取();
					}
				}, lastTime < 0 ? 0 : lastTime*1000 );
			}// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.结拜赛)) {
				final 结拜赛 m = new 结拜赛(userKey, mainDoc);
				//12:00:05执行报名，预留5秒防止延迟
				long lastTime = TimeUtil.getSecond("12:00:05");
				if (m.getDay() != 1) {
					lastTime = -1; //不是周一时，lastTime置负数，表示马上执行
				}
				if(lastTime > 0) {
					PrintUtil.printTitleInfo("结拜赛", "结拜赛将在12:00:05自动报名！（退出工具则不能自动报名）", username);
				}
				TimeUtil.timer.schedule(new TimerTask() {
					@Override
					public void run() {
						Map<String, String> tempUserKey = updateUserKey(timeUserKey, userKey, username);
						
						结拜赛 m1 = new 结拜赛(tempUserKey, mainDoc);
						m1.报名(); // 周一12点开始
						PrintUtil.printAllMessages(m1, username);
					}
				}, lastTime < 0 ? 0 : lastTime*1000 );
				m.助威(); // 助威周四0点开始，
				m.助威领奖(); // 领奖周六0点开始
				PrintUtil.printAllMessages(m, username);
			}// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.锦标赛)) {
				//12:00:05执行报名，预留5秒防止延迟
				long lastTime = TimeUtil.getSecond("12:00:05");
				if (lastTime > 0) {
					PrintUtil.printTitleInfo("锦标赛", "锦标赛将在12:00:05自动报名！（退出工具则不能自动报名）", username);
				}
				TimeUtil.timer.schedule(new TimerTask() {
					@Override
					public void run() {
						Map<String, String> tempUserKey = updateUserKey(timeUserKey, userKey, username);
						
						锦标赛 m1 = new 锦标赛(tempUserKey, mainDoc);
						m1.赞助(); // 每天12点开始
						PrintUtil.printAllMessages(m1, username);
						
						任务 m2 = new 任务(tempUserKey, mainDoc);
						m2.finish();
						
						活跃度 m3 = new 活跃度(tempUserKey, mainDoc);
						m3.领取();
					}
				}, lastTime < 0 ? 0 : lastTime*1000 );
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.掠夺)) {
				掠夺 m = new 掠夺(userKey, mainDoc);
				m.领奖(); // 周三6点开始
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.门派邀请赛)) {
				final 门派邀请赛 m = new 门派邀请赛(userKey, mainDoc);
				m.doit();
				PrintUtil.printAllMessages(m, username);
			}
			// /////////////////////////////////////////////////////////
			if (tasks.contains(Task.供奉)) {
				供奉 m = new 供奉(userKey, mainDoc);
				m.一键供奉();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.探险)) {
				探险 m = new 探险(userKey, mainDoc);
				m.doit();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.帮战奖励)) {
				帮战奖励 m = new 帮战奖励(userKey, mainDoc);
				m.领奖(); // 周六6点开始
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			// 完成任务要放到最后执行
			if (tasks.contains(Task.任务)) {
				任务 m = new 任务(userKey, mainDoc);
				m.finish();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.祭坛)) {
				祭坛 m = new 祭坛(userKey, mainDoc);
				m.转动轮盘();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.分享)) {
				分享 m = new 分享(userKey, mainDoc);
				m.一键分享();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			// 活跃度要放到更后面执行
			if (tasks.contains(Task.活跃度)) {
				活跃度 m = new 活跃度(userKey, mainDoc);
				m.领取();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.开锦囊宝箱)) {
				其他操作集合 m = new 其他操作集合(userKey, mainDoc);
				m.开锦囊宝箱();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.微信礼包)) {
				其他操作集合 m = new 其他操作集合(userKey, mainDoc);
				m.微信礼包();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.开通达人)) {
				其他操作集合 m = new 其他操作集合(userKey, mainDoc);
				m.开通达人();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks.contains(Task.活动集合)) {
				活动集合 m = new 活动集合(userKey, mainDoc);
				m.打豆豆();
				m.我要许愿();
				m.乐斗菜单();
				m.补偿礼包();
				m.周周礼包();
				m.登录100QB好礼();
				m.大宝树();
				m.大宝箱();
				m.大笨钟();
				m.九宫宝库();
				m.登录商店();
				m.摇摇乐();
				m.做任务领斗币();
				m.好礼步步升();
				m.登录有礼();
				m.幸运转盘();
				m.猜单双();
				m.斗币领取();
				m.暑期大礼包();
				m.帮派祈福();
				m.晃动的天平();
				m.全民拼图();
				m.国庆全民大礼包();
				PrintUtil.printAllMessages(m, username);
			}
			// //////////////////////////////////////////////////////////
			if (tasks == null || tasks.isEmpty()) {
				PrintUtil.printTitleInfo("系统消息", "未选择任何操作！", username);
			}
			// //////////////////////////////////////////////////////////
			PrintUtil.printInfo("\n");
			MainUI.isRun = true;
		} catch (StringIndexOutOfBoundsException e) {
			PrintUtil.printTitleInfo("系统消息", "skey失效！");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			PrintUtil.printTitleInfo("系统消息", "访问失败，请重试！");
			e.printStackTrace();
		} catch (IOException e) {
			PrintUtil.printTitleInfo("系统消息", "连接超时，请重试！");
			e.printStackTrace();
		}
	}
	
	
	//更新userKey，与getNewUserKey()不同的是，有效则重用，无效则重新获取
	@SuppressWarnings("unchecked")
	private Map<String, String> updateUserKey(Map<String, Object> timeUserKey, Map<String, String> oldUserKey, String username) {
		Map<String, String> newUserKey = (Map<String, String>) timeUserKey.get(username);
		if(UserUtil.checkUserKeyValid(newUserKey) == false) {
			newUserKey = UserUtil.getNewUserKey(oldUserKey, username);
			timeUserKey.put(username, newUserKey);
		}
		return newUserKey;
	}
}