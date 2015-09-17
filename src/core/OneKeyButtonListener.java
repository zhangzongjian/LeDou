package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

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

import util.MyUtil;

//一键乐斗按钮响应
public class OneKeyButtonListener implements ActionListener {

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		long startTime = System.currentTimeMillis();
		String mainURL = Main.input.getText();
		ArrayList<String> list = new ArrayList<String>();
		list.add("镖行天下");
		list.add("答题");
		list.add("副本");
		list.add("竞技场");
		list.add("斗神塔");
		list.add("矿洞");
		list.add("任务");
		list.add("乐斗boss");
		list.add("历练");
		list.add("领取每日奖励");
		list.add("十二宫");
		list.add("许愿");
		try {
			final Document mainDoc = MyUtil.clickURL(mainURL);
			if (list.contains("镖行天下")) {
				Thread thread1 = new Thread(new Runnable() {
					public void run() {
						镖行天下 m = new 镖行天下(mainDoc);
						m.劫镖();
						// for (int i = 0; i < 3; i++) {
						// if (MyUtil.timing(3)) {
						// System.out.println("护送押镖");
						// Main.textArea.append("护送押镖\n");
						// // m.护送押镖();
						// }
						// }
						Main.textArea.append("【镖行天下】\n");
						for (Object o : m.getMessage().values()) {
							Main.textArea.append("    " + o.toString() + "\n");
						}
					}
				});
				thread1.start();
				thread1.sleep(200);
			}
			if (list.contains("答题")) {
				Thread thread2 = new Thread(new Runnable() {
					public void run() {
						答题 m = new 答题(mainDoc);
						m.answer();
						Main.textArea.append("【答题】\n");
						for (Object o : m.getMessage().values()) {
							Main.textArea.append("    " + o.toString() + "\n");
						}
					}
				});
				thread2.start();
				thread2.sleep(200);
			}
			if (list.contains("斗神塔")) {
				Thread thread3 = new Thread(new Runnable() {
					public void run() {
						斗神塔 m = new 斗神塔(mainDoc);
						m.查看掉落情况();
						m.挑战();
						Main.textArea.append("【斗神塔】\n");
						for (Object o : m.getMessage().values()) {
							Main.textArea.append("    " + o.toString() + "\n");
						}
					}
				});
				thread3.start();
				thread3.sleep(200);
			}
			if (list.contains("副本")) {
				Thread thread4 = new Thread(new Runnable() {
					public void run() {
						副本 m = new 副本(mainDoc);
						m.挑战();
						Main.textArea.append("【副本】\n");
						for (Object o : m.getMessage().values()) {
							Main.textArea.append("    " + o.toString() + "\n");
						}
					}
				});
				thread4.start();
				thread4.sleep(200);
			}
			if (list.contains("竞技场")) {
				Thread thread5 = new Thread(new Runnable() {
					public void run() {
						竞技场 m = new 竞技场(mainDoc);
						m.挑战();
						Main.textArea.append("【竞技场】\n");
						for (Object o : m.getMessage().values()) {
							Main.textArea.append("    " + o.toString() + "\n");
						}
					}
				});
				thread5.start();
				thread5.sleep(200);
			}
			if (list.contains("矿洞")) {
				Thread thread6 = new Thread(new Runnable() {
					public void run() {
						矿洞 m = new 矿洞(mainDoc);
						m.挑战();
						Main.textArea.append("【矿洞】\n");
						for (Object o : m.getMessage().values()) {
							Main.textArea.append("    " + o.toString() + "\n");
						}
					}
				});
				thread6.start();
				thread6.sleep(200);
			}
			if (list.contains("乐斗boss")) {
				Thread thread7 = new Thread(new Runnable() {
					public void run() {
						乐斗boss m = new 乐斗boss(mainDoc);
						m.一键挑战();
						Main.textArea.append("【乐斗boss】\n");
						for (Object o : m.getMessage().values()) {
							Main.textArea.append("    " + o.toString() + "\n");
						}
					}
				});
				thread7.start();
				thread7.sleep(200);
			}
			if (list.contains("历练")) {
				Thread thread8 = new Thread(new Runnable() {
					public void run() {
						历练 m = new 历练(mainDoc);
						m.挑战();
						Main.textArea.append("【历练】\n");
						for (Object o : m.getMessage().values()) {
							Main.textArea.append("    " + o.toString() + "\n");
						}
					}
				});
				thread8.start();
				thread8.sleep(200);
			}
			if (list.contains("领取每日奖励")) {
				Thread thread9 = new Thread(new Runnable() {
					public void run() {
						领取每日奖励 m = new 领取每日奖励(mainDoc);
						m.领取();
						Main.textArea.append("【领取每日奖励】\n");
						for (Object o : m.getMessage().values()) {
							Main.textArea.append("    " + o.toString() + "\n");
						}
					}
				});
				thread9.start();
				thread9.sleep(200);
			}
			if (list.contains("任务")) {
				Thread thread10 = new Thread(new Runnable() {
					public void run() {
						任务 m = new 任务(mainDoc);
						m.finish();
						Main.textArea.append("【任务】\n");
						for (Object o : m.getMessage().values()) {
							Main.textArea.append("    " + o.toString() + "\n");
						}
					}
				});
				thread10.start();
				thread10.sleep(200);
			}
			if (list.contains("十二宫")) {
				Thread thread11 = new Thread(new Runnable() {
					public void run() {
						十二宫 m = new 十二宫(mainDoc);
						m.挑战(); // 没问题
						m.扫荡(); // 待测试
						Main.textArea.append("【十二宫】\n");
						for (Object o : m.getMessage().values()) {
							Main.textArea.append("    " + o.toString() + "\n");
						}
					}
				});
				thread11.start();
				thread11.sleep(200);
			}
			if (list.contains("许愿")) {
				Thread thread12 = new Thread(new Runnable() {
					public void run() {
						许愿 m = new 许愿(mainDoc);
						m.xuYuan();
						Main.textArea.append("【许愿】\n");
						for (Object o : m.getMessage().values()) {
							Main.textArea.append("    " + o.toString() + "\n");
						}
					}
				});
				thread12.start();
				thread12.sleep(200);
			}

			long endTime = System.currentTimeMillis();
			Main.textArea.append("(耗时：" + (endTime - startTime) / 1000.0
					+ "秒)\n");
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}