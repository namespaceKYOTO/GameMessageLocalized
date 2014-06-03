package MesMan;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class CharacterDialog implements ActionListener {
	
	private String MENU_CHECK = "Check";
	private String OPEN = "Open";
	private String SAVE = "Save";
	
	private String  charset = "Character set";
	
	JDialog dialog;
	TableEx table;

	public CharacterDialog(Frame owner, MessageManager messageMan, int x, int y, int width, int height) {
		dialog = new JDialog(owner, charset, Dialog.ModalityType.MODELESS);
		dialog.setBounds(x, y, width, height);
		
		String[] columns = {"char", "size"};
		table = new TableEx(messageMan, columns, width, height);
		
		// menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu check = new JMenu(MENU_CHECK);
		JMenu open = new JMenu(OPEN);
		JMenu save = new JMenu(SAVE);
		
		
		menuBar.add(check);
		menuBar.add(open);
		menuBar.add(save);
		
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

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(MENU_CHECK)) {
		}
	}
}
