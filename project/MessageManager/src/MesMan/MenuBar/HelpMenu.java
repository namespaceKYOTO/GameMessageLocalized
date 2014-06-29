package MesMan.MenuBar;

import java.awt.Component;

import javax.swing.JPanel;

import MesMan.MesTableDefine;
import MesMan.MessageManager;

/**
 * ヘルプ.
 * @author t_sato
 *
 */
public class HelpMenu extends MenuBarBase
{
//    JOptionPane.showMessageDialog(this, "JOptionPane");
	// ようこそ　-> Dialog標示
	// ヘルプ目次　-> Dialog標示
	// テストデータ -> テストデータ標示
	
	/**
	 * コンストラクタ.
	 * @param parent UIの親
	 * @param mesman メッセージ管理
	 */
	public HelpMenu(Component parent, MessageManager mesman)
	{
		super(parent, mesman, mesman.getMessage(MesTableDefine.mes_help));
	}

	/**
	 * UIの表示言語変更.
	 */
	public void LanguageChange()
	{
		
	}
}
