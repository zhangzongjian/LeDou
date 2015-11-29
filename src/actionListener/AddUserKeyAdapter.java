package actionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//添加小号的回车事件
public class AddUserKeyAdapter extends KeyAdapter {
	public void keyPressed(KeyEvent e){
        int k = e.getKeyCode();
        if(k == KeyEvent.VK_ENTER){
        	new AddUserButtonListener().doAdd();
        }
	 }
}
