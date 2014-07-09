package MesMan.MenuBar;

import java.awt.event.ActionEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import MesMan.MesMan;
import MesMan.MesTableDefine;
import MesMan.Table.TableMenu;

/**
 * ツールメニュー.
 * @author t_sato
 *
 */
public class ToolsMenu extends MenuBarBase
{
	private JMenuItem check;
	private TableMenu tableMenu;
	
	/**
	 * コンストラクタ.
	 * @param mesman メッセージ管理
	 */
	public ToolsMenu(MesMan mesman, TableMenu tableMenu)
	{
		super(mesman, mesman.getMesDataMan().getMessage(MesTableDefine.mes_tools));
		
		this.tableMenu = tableMenu;
		
		JMenu menu = getMenu();
		
		check = new JMenuItem("Message Sizes Check");
		check.addActionListener(this);
		menu.add(check);
	}
	
	/**
	 * UI表示言語変更.
	 */
	public void LanguageChange()
	{
	}
	
	/* (非 Javadoc)
	 * @see MesMan.MenuBar.MenuBarBase#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		System.out.println(arg0.paramString());
		Object obj = arg0.getSource();
		
		if(obj == this.check)
		{
			String input = JOptionPane.showInputDialog("language select");
			if(input != null && input.length() > 0)
			{
				this.tableMenu.checkMessageSize(input);	
			}
		}
	}

}
