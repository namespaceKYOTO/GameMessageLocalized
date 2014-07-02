package MesMan.MenuBar;

import java.awt.Component;
import java.util.Stack;

import javax.swing.JMenuBar;

/**
 * メニューバー.
 * @author t_sato
 *
 */
public class MenuBar
{
	private Component parent;
	private JMenuBar menuBar;
	private Stack<MenuBarBase> menuStack;
	
	/**
	 * コンストラクタ.
	 * @param parent UI親
	 */
	public MenuBar(Component parent)
	{
		this.parent = parent;
		menuBar = new JMenuBar();
		menuStack = new Stack<MenuBarBase>();
	}
	
	/**
	 * メニューの追加.
	 * @param menu 追加するメニュー
	 */
	public void add(MenuBarBase menu)
	{
		menu.setMenuBar(this);
		menuBar.add(menu.getMenu());
		menuStack.push(menu);
	}
	
	/**
	 * メニューの取得
	 * @param index メニュー番号
	 * @return メニュー
	 */
	public MenuBarBase get(int index)
	{
		return menuStack.get(index);
	}
	
	/**
	 * メニューバーの取得.
	 * @return メニューバー
	 */
	public JMenuBar getMenuBar()
	{
		return this.menuBar;
	}
	
	/**
	 * UI表示言語の変更
	 */
	public void languageChange()
	{
		for (MenuBarBase menu : menuStack) {
			menu.LanguageChange();
		}
	}
	
}
