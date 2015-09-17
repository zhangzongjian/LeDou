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
						for (int i = 0; i < 3; i++) {
							if (MyUtil.timing(3)) {
								System.out.println("护送押镖");
								Main.textArea.append("护送押镖\n");
								// m.护送押镖();
							}
						}
						Main.textArea.append("【镖行天下】\n");
						for (Object o : m.getMessage().values()) {
							Main.textArea.append("    " + o.toString() + "\n");
						}
					}
				});
				thread1.start();
			}
			if (list.contains("答题")) {
				答题 m = new 答题(mainDoc);
				m.answer();
				Main.textArea.append("【答题】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("斗神塔")) {
				斗神塔 m = new 斗神塔(mainDoc);
				m.查看掉落情况();
				m.挑战();
				Main.textArea.append("【斗神塔】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("副本")) {
				副本 m = new 副本(mainDoc);
				m.挑战();
				Main.textArea.append("【副本】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("竞技场")) {
				竞技场 m = new 竞技场(mainDoc);
				m.挑战();
				Main.textArea.append("【竞技场】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("矿洞")) {
				矿洞 m = new 矿洞(mainDoc);
				m.挑战();
				Main.textArea.append("【矿洞】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("乐斗boss")) {
				乐斗boss m = new 乐斗boss(mainDoc);
				m.一键挑战();
				Main.textArea.append("【乐斗boss】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("历练")) {
				历练 m = new 历练(mainDoc);
				m.挑战();
				Main.textArea.append("【历练】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("领取每日奖励")) {
				领取每日奖励 m = new 领取每日奖励(mainDoc);
				m.领取();
				Main.textArea.append("【领取每日奖励】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("任务")) {
				任务 m = new 任务(mainDoc);
				m.finish();
				Main.textArea.append("【任务】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("十二宫")) {
				十二宫 m = new 十二宫(mainDoc);
				m.挑战(); // 没问题
				m.扫荡(); // 待测试
				Main.textArea.append("【十二宫】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}
			if (list.contains("许愿")) {
				许愿 m = new 许愿(mainDoc);
				m.xuYuan();
				Main.textArea.append("【许愿】\n");
				for (Object o : m.getMessage().values()) {
					Main.textArea.append("    " + o.toString() + "\n");
				}
			}

			long endTime = System.currentTimeMillis();
			Main.textArea.append("(耗时：" + (endTime - startTime) / 1000.0
					+ "秒)\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}