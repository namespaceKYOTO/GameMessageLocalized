package MesMan.MenuBar;

import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;

import MesMan.MesMan;
import MesMan.MesTableDefine;

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

	private JMenuItem welcome;
	private JMenuItem helpContent;
	private JMenuItem sampleData;
	
	/**
	 * コンストラクタ.
	 * @param mesman メッセージ管理
	 */
	public HelpMenu(MesMan mesman)
	{
		super(mesman, mesman.getMesDataMan().getMessage(MesTableDefine.mes_help));
		
		welcome = new JMenuItem("Welcome");
		helpContent = new JMenuItem("Help Content");
		sampleData = new JMenuItem("Sample Data");
	}

	/**
	 * UIの表示言語変更.
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
		
		if(obj == welcome)
		{
		}
		else if(obj == helpContent)
		{
			
		}
		else if(obj == sampleData)
		{
			
		}
	}
}
