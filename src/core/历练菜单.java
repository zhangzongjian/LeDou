package core;

import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import actionListener.MenuItemListener;

import model.impl.历练;
import util.UserUtil;

public class 历练菜单{
	public static JMenu 历练Select;
	
	private JMenuBar 历练Bar = new JMenuBar();
	
	private 历练菜单() throws IOException {
		Object object = UserUtil.getSettingByKey("历练");
		if(null == object) {
			UserUtil.addSetting("历练", "洞庭湖(37~40级)");
			UserUtil.saveSetting();
			历练Select = new JMenu("历练(洞庭湖)");
		} else {
			历练.object = object.toString();
			历练Select = new JMenu("历练("+object.toString().substring(0, object.toString().indexOf("("))+")");
		}
		JMenuItem item = null;
		item = new JMenuItem("乐斗村(1~10级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("林松郊外(10~20级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("林松城(20~25级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("东海龙宫(25~30级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("踏云镇(30~34级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("摩云山(34~37级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("洞庭湖(37~40级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("苍茫山(40~44级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("玉龙湿地(44~47级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("玉龙雪山(47~50级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("狂沙台地(50~53级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("回声遗迹(53~56级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("悲叹山丘(57~60级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("黄沙漩涡(61~64级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("炎之洞窟(65~68级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("程管小镇(69~73级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("花果山(74~78级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("藏剑山庄(79~83级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("桃花剑冢(84~88级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		item = new JMenuItem("鹅王的试炼(89~93级)");
		item.addActionListener(new MenuItemListener("历练", item.getText()));
		历练Select.add(item);
		历练Bar.add(历练Select);
	}
	
	public static JMenuBar create() throws IOException {
		return new 历练菜单().历练Bar;
	}
}
