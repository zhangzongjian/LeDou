package actionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import util.LoginUtil;
import core.设置面板;

public class ChangeVerifycodeListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent paramMouseEvent) {
		try {
			LoginUtil.refreshVerifycode(设置面板.inputQQ.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent paramMouseEvent) {
		// TODO Auto-generated method stub

	}

}
