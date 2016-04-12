package core;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import util.UserUtil;
import actionListener.CopyButtonListener;
import actionListener.SaveUsersButtonListener;

public class 查看对话框 {
	private JDialog usersDialog;
	private JPanel jpanel;
	
	
	@SuppressWarnings("unchecked")
	private 查看对话框() throws IOException {
		initDialog();
		LinkedHashMap<String, Object> usersMap = (LinkedHashMap<String, Object>)UserUtil.getSettingByKey("小号");
		//将输入框控件装起来，保存按钮的时候会用到
		LinkedHashMap<String, List<JTextField>> userTextFieldsMap = new LinkedHashMap<String, List<JTextField>>();
		
		jpanel = new JPanel(new GridLayout(usersMap.keySet().size()+2, 1, 5, 5));
		JPanel head = new JPanel(new GridLayout(1,5,10,10));
		head.add(new JLabel("昵称"));
		head.add(new JLabel("QQ"));
//		head.add(new JLabel());
		head.add(new JLabel("密码"));
		head.add(new JLabel());
		
		jpanel.add(head);
		for(String username : usersMap.keySet()) {
			JPanel p = new JPanel(new GridLayout(1,5,5,5));
			List<JTextField> list = new ArrayList<JTextField>();
			//昵称输入框
			JTextField usernameInput = new JTextField(username, 2);
			usernameInput.setCaretPosition(0);
			list.add(usernameInput); //list.get(0)
        	p.add(usernameInput);
        	//qq输入框
        	JTextField qqInput = new JTextField(((Map<String, String>)usersMap.get(username)).get("QQ"), 5);
        	qqInput.setCaretPosition(0);
        	list.add(qqInput); //list.get(1)
        	p.add(qqInput);
        	//按钮
//        	JButton jButton  = new JButton("复制");
//        	jButton.addActionListener(new CopyButtonListener(qqInput));
//        	p.add(jButton);
        	//密码输入框
        	JTextField passwordInput = new JTextField(((Map<String, String>)usersMap.get(username)).get("password"), 5);
        	passwordInput.setCaretPosition(0);
        	list.add(passwordInput); //list.get(2)
        	p.add(passwordInput);
        	//按钮
//        	JButton jButton1  = new JButton("复制");
//        	jButton1.addActionListener(new CopyButtonListener(passwordInput));
//        	p.add(jButton1);
        	//按钮
        	JButton jButton2  = new JButton("拷贝书签");
        	jButton2.addActionListener(new CopyButtonListener(usernameInput, true));
        	p.add(jButton2);
        	
        	userTextFieldsMap.put(username, list);
        	jpanel.add(p);
		}
		JButton saveUsersButton = new JButton("保存");
		saveUsersButton.addActionListener(new SaveUsersButtonListener(userTextFieldsMap, usersDialog));
		jpanel.add(saveUsersButton);
		usersDialog.add(new JScrollPane(jpanel));
	}
	
	public static JDialog create() {
		try {
			return new 查看对话框().usersDialog;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void initDialog() {
		int x = MainUI.jFrame.getX();
		int y = MainUI.jFrame.getY();
		int width = MainUI.jFrame.getSize().width;
		int height = MainUI.jFrame.getSize().height;
		int d_width = 360;
		int d_height = 230;
		usersDialog = new JDialog();
		usersDialog.setTitle("查看小号信息");
		usersDialog.setBounds(x+width/2-d_width/2, y+height/2-d_height/2, d_width, d_height);
	}
}


class saveButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent paramActionEvent) {
		
	}
}
