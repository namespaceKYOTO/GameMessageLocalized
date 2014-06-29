package MesMan.MenuBar;

import java.awt.Component;
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
	private Component parent;
	private MessageManager mesman;
	private MenuBar menuBar;
	private JMenu menu;
	
	/**
	 * コンストラクタ.
	 * @param mesman メッセージ管理
	 * @param menuName メニュー名
	 */
	public MenuBarBase(Component parent, MessageManager mesman, String menuName)
	{
		this.parent = parent;
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
	 * 親の取得.
	 * @return UIの親
	 */
	public Component getParent()
	{
		return this.parent;
	}

	/**
	 * メニュー取得.
	 * @return メニュー
	 */
	public JMenu getMenu()
	{
		return this.menu;
	}

	/**
	 * メニューバー取得
	 * @return メニューバー
	 */
	public MenuBar getMenuBar()
	{
		return this.menuBar;
	}
	
	/**
	 * メニューバーの設定
	 * @param menu メニューバー
	 */
	public void setMenuBar(MenuBar menu)
	{
		this.menuBar = menu;
	}
	
	/**
	 * メッセージ管理の取得
	 * @return メッセージ管理
	 */
	public MessageManager getMesman() {
		return mesman;
	}

	/**
	 * メッセージ管理の設定
	 * @param mesman メッセージ管理
	 */
	public void setMesman(MessageManager mesman) {
		this.mesman = mesman;
	}

	/* (非 Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
	}
	
}
