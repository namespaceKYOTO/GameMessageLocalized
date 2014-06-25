package MesMan.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;

import MesMan.MessageManager;

/**
 * メニュバーベース.
 * @author t_sato
 *
 */
public class MenuBarBase implements ActionListener
{
	private MessageManager mesman;
	private JMenu menu;
	
	/**
	 * コンストラクタ.
	 * @param mesman メッセージ管理
	 * @param menuName メニュー名
	 */
	public MenuBarBase(MessageManager mesman, String menuName)
	{
		this.mesman = mesman;
		this.menu = new JMenu(menuName);
	}
	
	/**
	 * UI表示言語変更.
	 */
	public void LanguageChange()
	{
	}

	/**
	 * メニュー取得.
	 * @return
	 */
	public JMenu getMenu()
	{
		return this.menu;
	}

	public void actionPerformed(ActionEvent arg0)
	{
	}
	
}
