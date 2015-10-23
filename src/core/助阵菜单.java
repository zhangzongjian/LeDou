package core;

import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import actionListener.MenuItemListener;

import model.impl.助阵;
import util.UserUtil;

public class 助阵菜单{
	public static JMenu 助阵Select;
	
	private JMenuBar 助阵Bar = new JMenuBar();
	
	private 助阵菜单() throws IOException {
		Object object = UserUtil.getSettingByKey("助阵");
		if(null == object) {
			UserUtil.addSetting("助阵", "以柔克刚1");
			UserUtil.saveSetting();
			助阵Select = new JMenu("助阵技能(以柔克刚1)");
		} else {
			助阵.object = object.toString();
			助阵Select = new JMenu("助阵技能("+object.toString()+")");
		}
		JMenuItem item = null;
		JMenu menu1 = new JMenu("毒光剑影");
		JMenu menu2 = new JMenu("正邪两立");
		JMenu menu3 = new JMenu("致命一击");
		JMenu menu4 = new JMenu("纵剑天下");
		JMenu menu5 = new JMenu("老谋深算");
		JMenu menu6 = new JMenu("智勇双全");
		JMenu menu7 = new JMenu("以柔克刚");
		JMenu menu8 = new JMenu("雕心鹰爪");
		JMenu menu9 = new JMenu("根骨奇特");
		for(int i=1; i<=5; i++) {
			if(i<=1) {
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu1.getText()+""+i));
				menu1.add(item);
			}
			if(i<=2) {
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu2.getText()+""+i));
				menu2.add(item);
			}
			if(i<=3) {
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu3.getText()+""+i));
				menu3.add(item);
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu4.getText()+""+i));
				menu4.add(item);
			}
			if(i<=4) {
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu5.getText()+""+i));
				menu5.add(item);
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu6.getText()+""+i));
				menu6.add(item);
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu7.getText()+""+i));
				menu7.add(item);
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu8.getText()+""+i));
				menu8.add(item);
			}
			if(i<=5) {
				item = new JMenuItem("技能"+i);
				item.addActionListener(new MenuItemListener("助阵", menu9.getText()+""+i));
				menu9.add(item);
			}
		}
		助阵Select.add(menu1);
		助阵Select.add(menu2);
		助阵Select.add(menu3);
		助阵Select.add(menu4);
		助阵Select.add(menu5);
		助阵Select.add(menu6);
		助阵Select.add(menu7);
		助阵Select.add(menu8);
		助阵Select.add(menu9);
		助阵Bar.add(助阵Select);
	}
	
	public static JMenuBar create() throws IOException {
		return new 助阵菜单().助阵Bar;
	}
}
