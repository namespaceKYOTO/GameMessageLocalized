package MesMan.MenuBar;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
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

	private JMenuItem welcome;
	private JMenuItem helpContent;
	private JMenuItem sampleData;
	
	/**
	 * コンストラクタ.
	 * @param parent UIの親
	 * @param mesman メッセージ管理
	 */
	public HelpMenu(Component parent, MessageManager mesman)
	{
		super(parent, mesman, mesman.getMessage(MesTableDefine.mes_help));
		
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
