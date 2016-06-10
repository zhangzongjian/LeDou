package core;

import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import actionListener.MenuItemListener;

import model.impl.幻境;
import util.UserUtil;

public class 幻境菜单{
	public static JMenu 幻境Select;
	
	private JMenuBar 幻境Bar = new JMenuBar();
	
	private 幻境菜单() throws IOException {
		Object object = UserUtil.getSettingByKey("幻境");
		if(null == object) {
			UserUtil.addSetting("幻境", "乐斗村");
			UserUtil.saveSetting();
			幻境Select = new JMenu("幻境(乐斗村)");
		} else {
			幻境.object = object.toString();
			幻境Select = new JMenu("幻境("+object+")");
		}
		JMenuItem item = null;
		item = new JMenuItem("乐斗村");
		item.addActionListener(new MenuItemListener("幻境", item.getText()));
		幻境Select.add(item);
		item = new JMenuItem("林松郊外");
		item.addActionListener(new MenuItemListener("幻境", item.getText()));
		幻境Select.add(item);
		item = new JMenuItem("林松城");
		item.addActionListener(new MenuItemListener("幻境", item.getText()));
		幻境Select.add(item);
		item = new JMenuItem("东海龙宫");
		item.addActionListener(new MenuItemListener("幻境", item.getText()));
		幻境Select.add(item);
		item = new JMenuItem("踏云镇");
		item.addActionListener(new MenuItemListener("幻境", item.getText()));
		幻境Select.add(item);
		item = new JMenuItem("摩云山");
		item.addActionListener(new MenuItemListener("幻境", item.getText()));
		幻境Select.add(item);
		item = new JMenuItem("洞庭湖");
		item.addActionListener(new MenuItemListener("幻境", item.getText()));
		幻境Select.add(item);
		item = new JMenuItem("苍莽山");
		item.addActionListener(new MenuItemListener("幻境", item.getText()));
		幻境Select.add(item);
		item = new JMenuItem("玉龙湿地");
		item.addActionListener(new MenuItemListener("幻境", item.getText()));
		幻境Select.add(item);
		幻境Bar.add(幻境Select);
	}
	
	public static JMenuBar create() throws IOException {
		return new 幻境菜单().幻境Bar;
	}
}
