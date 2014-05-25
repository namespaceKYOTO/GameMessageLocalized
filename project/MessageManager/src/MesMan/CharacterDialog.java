package MesMan;

import java.awt.Dialog;
import java.awt.Frame;
import java.util.Stack;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class CharacterDialog {
	private String  charset = "Character set";
	
	JDialog dialog;
	TableEx table;

	public CharacterDialog(Frame owner, int x, int y, int width, int height) {
		dialog = new JDialog(owner, charset, Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setBounds(x, y, width, height);
		
		String[] columns = {"char", "size"};
		table = new TableEx(columns, width, height);
		
		dialog.add(table.getPanel());
	}
	
	public void show()
	{
		dialog.setVisible(true);
	}
	
	public void hide()
	{
		dialog.setVisible(false);
	}
}
