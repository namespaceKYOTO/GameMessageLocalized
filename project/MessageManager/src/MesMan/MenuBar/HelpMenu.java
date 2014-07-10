package MesMan.MenuBar;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

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
			InputStream fileStream = this.getClass().getResourceAsStream("/help/JPN/welcom.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));
			String readLine = null;
			JTextArea area = new JTextArea();
			try {
				while((readLine = br.readLine()) != null)
				{
					area.append(readLine + "\n");
				}
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
//			JOptionPane dialog = new JOptionPane("Welcom", JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(this.getMesman(), area);
//			dialog.setVisible(true);
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
