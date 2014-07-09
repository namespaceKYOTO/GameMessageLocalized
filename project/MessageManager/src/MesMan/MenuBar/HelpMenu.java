package MesMan.MenuBar;

import java.awt.event.ActionEvent;

import javax.swing.JMenu;
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
		
		JMenu menu = getMenu();
		
		welcome = new JMenuItem("Welcome");
		helpContent = new JMenuItem("Help Content");
		sampleData = new JMenuItem("Sample Data");
		welcome.addActionListener(this);
		helpContent.addActionListener(this);
		sampleData.addActionListener(this);
		menu.add(welcome);
		menu.add(helpContent);
		menu.add(sampleData);
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
			System.out.println("Open Sample Window");
			String[] args = {
					"-m", "sample",
					"-mt", "/res/message.mtbl",
					"-tt", "/res/tag.ttbl", 
					"-ct", "/res/charSize.ctbl",
			};
			MesMan mesman = new MesMan(args, false);
			
			// 現在設定されている言語をSampleWindow側にも反映させる
			int language = getMesman().getMesDataMan().getLanguageNo();
			mesman.getMesDataMan().setLanguageNo(language);
			mesman.getMenubar().languageChange();
		}
	}
}
