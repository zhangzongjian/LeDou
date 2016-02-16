package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.impl.助阵;
import model.impl.十二宫;
import model.impl.历练;
import model.impl.幻境;

import util.UserUtil;
import core.乐斗面板;
import core.助阵菜单;
import core.十二宫菜单;
import core.历练菜单;
import core.幻境菜单;

//十二宫、助阵、历练，三个菜单选项监听器
public class MenuItemListener implements ActionListener {
	private String key;
	private Object value;
	public MenuItemListener(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	public void actionPerformed(ActionEvent e) {
		try {
			UserUtil.addSetting(key, value);
			UserUtil.saveSetting();
			if("十二宫".equals(key)) {
				十二宫.object = value.toString();
				十二宫菜单.十二宫Select.setText("十二宫("+value.toString().substring(0, value.toString().indexOf("("))+")");
			}
			if("历练".equals(key)) {
				历练.object = value.toString();
				历练菜单.历练Select.setText("历练("+value.toString().substring(0, value.toString().indexOf("("))+")");
			}
			if("助阵".equals(key)) {
				助阵.object = value.toString();
				助阵菜单.助阵Select.setText("助阵技能("+value.toString()+")");
			}
			if("幻境".equals(key)) {
				幻境.object = value.toString();
				幻境菜单.幻境Select.setText("幻境("+value.toString()+")");
			}
			乐斗面板.textArea.append("【"+key+"】\n");
			乐斗面板.textArea.append("    "+value+"\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
