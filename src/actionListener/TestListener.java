package actionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestListener implements ActionListener{
	
	private String s;
	
	public TestListener(String s){
		this.s = s;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(this.s);
	}
}
