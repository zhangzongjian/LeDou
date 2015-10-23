package core;

import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import actionListener.MenuItemListener;

import model.impl.十二宫;
import util.UserUtil;

public class 十二宫菜单 {
	
	public static JMenu 十二宫Select;
	
	private JMenuBar 十二宫Bar = new JMenuBar();
	
	private 十二宫菜单() throws IOException {
		Object object = UserUtil.getSettingByKey("十二宫");
		if(null == object) {
			UserUtil.addSetting("十二宫", "双子宫(60-65)");
			UserUtil.saveSetting();
			十二宫Select = new JMenu("十二宫(双子宫)");
		} else {
			十二宫.object = object.toString();
			十二宫Select = new JMenu("十二宫("+object.toString().substring(0, object.toString().indexOf("("))+")");
		}
		JMenuItem item = null;
		item = new JMenuItem("白羊宫(50-55)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("金牛宫(55-60)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("双子宫(60-65)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("巨蟹宫(65-70)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("狮子宫(70-75)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("处女宫(75-80)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("天秤宫(80-85)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("天蝎宫(85-90)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("射手宫(50-95)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("摩羯宫(95-100)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		item = new JMenuItem("水瓶宫(101-110)");
		item.addActionListener(new MenuItemListener("十二宫", item.getText()));
		十二宫Select.add(item);
		十二宫Bar.add(十二宫Select);
	}
	
	public static JMenuBar create() throws IOException {
		return new 十二宫菜单().十二宫Bar;
	}
}
