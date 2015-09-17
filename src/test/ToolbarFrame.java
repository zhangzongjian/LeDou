package test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author root
 */
public class ToolbarFrame extends Frame {
	/** Creates a new instance of ToolbarFrame */
	public ToolbarFrame() {
		super("My Toolbar(Swing)");// 通过调用基类Frame的构造函数初始化标题栏文字
		setSize(450, 250);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}