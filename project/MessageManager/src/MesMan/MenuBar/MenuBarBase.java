package MesMan.MenuBar;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;

import MesMan.MesMan;

/**
 * メニュバーベース.
 * @author t_sato
 *
 */
public class MenuBarBase implements ActionListener
{
	private MesMan mesman;
	private MenuBar menuBar;
	private JMenu menu;
	
	/**
	 * コンストラクタ.
	 * @param mesman メッセージ管理
	 * @param menuName メニュー名
	 */
	public MenuBarBase(MesMan mesman, String menuName)
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
	 * 親の取得.
	 * @return UIの親
	 */
	public Component getParent()
	{
		return this.mesman;
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
	public MesMan getMesman() {
		return mesman;
	}

	/* (非 Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
	}
	
}
