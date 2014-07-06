package MesMan.MenuBar;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import MesMan.MesMan;
import MesMan.MesTableDefine;
import MesMan.MessageDataManager;

/**
 * 設定.
 * @author t_sato
 *
 */
public class SettingMenu extends MenuBarBase
{
	public enum eMenuType
	{
		Normal,
		CharactorSize,
	};
	
	private String fileName;
	private String defaultDirectory;
	private Integer defaultLanguage;
	private Integer defaultCharactorSize;
	LinkedList<String> buffer;

	private static String DefaultDirectoryLabel = "\"defaultDirectory\":";
	private static String DefaultDirectory = "C:\\";
	private static String DefaultLanguageLabel = "\"defaultLanguage\":";
	private static int DefaultLanguage = MesTableDefine.Language_ENG;
	private static String DefaultCharactorSizeLabel = "\"defaultCharactorSize\":";
	private static int DefaultCharactorSize = 0;
	
	private static int directoryIdx = 0;
	private static int languageIdx = 1;
	private static int charactorsizeIdx = 2;
	
	private JMenuItem defaulttDir;
	private JMenu defaultLang;
	private JMenuItem setting_jpn;
	private JMenuItem setting_eng;
	private JMenuItem setting_deu;
	private JMenuItem setting_fra;
	private JMenuItem setting_ita;
	private JMenuItem setting_spa;
	private JMenuItem setting_rus;
	private JMenuItem setting_tha;
	private JMenuItem reset;
	
	private JMenuItem defaultCharSize;

	private JFileChooser directoryChooser;
	
	
	/**
	 * 指定ファイルからデフォルト言語の取得.
	 * @param fileName ファイル名
	 * @return 言語番号
	 */
	public static int getDefaultLanguage(String fileName)
	{
		File file = new File(fileName);
		if(!file.exists()) {
			return DefaultLanguage;
		}
		
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			LinkedList<String> buffer = new LinkedList<String>();
			
			// ファイルから情報取得
			String line = null;
			while((line = br.readLine()) != null) {
				buffer.add(line);
			}
			br.close();
			return Integer.valueOf(buffer.get(languageIdx).substring(DefaultLanguageLabel.length()));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return DefaultLanguage;
		}
	}
	
	/**
	 * コンストラクタ.
	 * @param mesman　メッセージ管理
	 * @param settingFile 設定ファイル
	 */
	public SettingMenu(MesMan mesman, String settingFile)
	{
		super(mesman, mesman.getMesDataMan().getMessage(MesTableDefine.mes_setting));
		
		MessageDataManager mesDataMan = mesman.getMesDataMan();
		
		buffer = new LinkedList<String>();
		fileName = null;
		defaultDirectory = new String(DefaultDirectory);
		defaultLanguage = DefaultLanguage;
		defaultCharactorSize = DefaultCharactorSize;
		try
		{
			fileName = settingFile;
			File file = new File(fileName);
			if(!file.exists())
			{
				buffer.add(DefaultDirectoryLabel + DefaultDirectory);
				buffer.add(DefaultLanguageLabel + String.format("%d", DefaultLanguage));
				buffer.add(DefaultCharactorSizeLabel + String.format("%d", DefaultCharactorSize));
			}
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			// ファイルから情報取得
			String line = null;
			while((line = br.readLine()) != null) {
				buffer.add(line);
			}
			br.close();
			
			defaultDirectory = buffer.get(directoryIdx).substring(DefaultDirectoryLabel.length());
			defaultLanguage = Integer.valueOf(buffer.get(languageIdx).substring(DefaultLanguageLabel.length()));
			defaultCharactorSize = Integer.valueOf(buffer.get(charactorsizeIdx).substring(DefaultCharactorSizeLabel.length()));
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			reset();
		}
		
		JMenu menu = getMenu();
		
		defaulttDir = new JMenuItem ("Default Directory");
		defaultLang = new JMenu("Default Language");
		reset = new JMenuItem("reset");
		defaultCharSize = new JMenuItem("Default Charactor Size");
		defaulttDir.addActionListener(this);
		defaultLang.addActionListener(this);
		reset.addActionListener(this);
		defaultCharSize.addActionListener(this);
		
		
		setting_jpn = new JMenuItem(mesDataMan.getMessage(MesTableDefine.mes_JPN));
		setting_eng = new JMenuItem(mesDataMan.getMessage(MesTableDefine.mes_ENG));
		setting_deu = new JMenuItem(mesDataMan.getMessage(MesTableDefine.mes_DEU));
		setting_fra = new JMenuItem(mesDataMan.getMessage(MesTableDefine.mes_FRA));
		setting_ita = new JMenuItem(mesDataMan.getMessage(MesTableDefine.mes_ITA));
		setting_spa = new JMenuItem(mesDataMan.getMessage(MesTableDefine.mes_SPA));
		setting_rus = new JMenuItem(mesDataMan.getMessage(MesTableDefine.mes_RUS));
		setting_tha = new JMenuItem(mesDataMan.getMessage(MesTableDefine.mes_THA));
		setting_jpn.addActionListener(this);
		setting_eng.addActionListener(this);
		setting_deu.addActionListener(this);
		setting_fra.addActionListener(this);
		setting_ita.addActionListener(this);
		setting_spa.addActionListener(this);
		setting_rus.addActionListener(this);
		setting_tha.addActionListener(this);
		defaultLang.add(setting_jpn);
		defaultLang.add(setting_eng);
		defaultLang.add(setting_deu);
		defaultLang.add(setting_fra);
		defaultLang.add(setting_ita);
		defaultLang.add(setting_spa);
		defaultLang.add(setting_rus);
		defaultLang.add(setting_tha);

		menu.add(defaulttDir);
		menu.add(defaultLang);
		menu.add(reset);
		menu.add(defaultCharSize);
		
		directoryChooser = new JFileChooser();
		directoryChooser.setDialogType(JFileChooser.CUSTOM_DIALOG);
		directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		changeMenu(eMenuType.Normal);
	}
	
	/**
	 * デフォルト言語取得.
	 * @return 言語番号
	 */
	public Integer getDefaultLanguage() {
		return defaultLanguage;
	}

	/**
	 * デフォルト言語設定.
	 * @param defaultLanguage 言語番号
	 */
	public void setDefaultLanguage(int defaultLanguage)
	{
		buffer.remove(languageIdx);
		buffer.add(languageIdx, DefaultLanguageLabel + String.valueOf(defaultLanguage));
		this.defaultLanguage = new Integer(defaultLanguage);
	}
	
	/**
	 * デフォルトディレクト取得.
	 * @return ディレクト
	 */
	public String getDefaultDirectory()
	{
		return defaultDirectory;
	}
	
	/**
	 * デフォルトディレクトリ設定.
	 * @param defaultDirectory ディレクトリ
	 */
	public void setDefaultDirectory(String defaultDirectory)
	{
		buffer.remove(directoryIdx);
		buffer.add(directoryIdx, DefaultDirectoryLabel + defaultDirectory);
		this.defaultDirectory = defaultDirectory;
	}
	
	/**
	 * デフォルト文字サイズの取得.
	 * @return　文字サイズ
	 */
	public Integer getDefaultCharactorSize()
	{
		return defaultCharactorSize;
	}

	/**
	 * デフォルト文字サイズの設定
	 * @param defaultCharactorSize 文字サイズ
	 */
	public void setDefaultCharactorSize(Integer defaultCharactorSize)
	{
		buffer.remove(charactorsizeIdx);
		buffer.add(charactorsizeIdx, DefaultCharactorSizeLabel + String.valueOf(defaultCharactorSize));
		this.defaultCharactorSize = defaultCharactorSize;
	}

	/**
	 * 設定のリセット.
	 */
	public void reset()
	{
		defaultDirectory = new String(DefaultDirectory);
		defaultLanguage = MesTableDefine.Language_ENG;
		
		buffer.clear();
		buffer.add(directoryIdx, DefaultDirectoryLabel + defaultDirectory);
		buffer.add(languageIdx, DefaultLanguageLabel + String.valueOf(defaultLanguage));
	}
	
	/**
	 * 設定の保存.
	 */
	public void save()
	{
		try
		{
			File file = new File(fileName);
			if(!file.exists()) {
				file.createNewFile();
			}
			PrintWriter pw = new PrintWriter(file);
			
			for (String str : this.buffer) {
				pw.write(str);
				pw.write("\r\n");
				pw.flush();
			}
			pw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * UI表示言語変更.
	 */
	public void LanguageChange()
	{
		getMenu().setText(getMesman().getMesDataMan().getMessage(MesTableDefine.mes_setting));
	}
	
	/**
	 * メニュータイプの変更.
	 * @param type タイプ
	 */
	public void changeMenu(eMenuType type)
	{
		switch(type)
		{
			case Normal:
				defaulttDir.setVisible(true);
				defaultLang.setVisible(true);
				reset.setVisible(true);
				defaultCharSize.setVisible(false);
				break;
				
			case CharactorSize:
				defaulttDir.setVisible(false);
				defaultLang.setVisible(false);
				reset.setVisible(false);
				defaultCharSize.setVisible(true);
				break;
		}
	}
	
	/* (非 Javadoc)
	 * @see MesMan.MenuBar.MenuBarBase#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		System.out.println(arg0.paramString());
		Object obj = arg0.getSource();
		
		 if(obj == this.defaulttDir)
		{
			int ret = this.directoryChooser.showOpenDialog(this.getParent());
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				File file = this.directoryChooser.getSelectedFile();
				this.setDefaultDirectory(file.getPath());
			}
		}
		else if(obj == this.defaultCharSize) 
		{
			String input = JOptionPane.showInputDialog("Charactor Size");
			try
			{
				this.setDefaultCharactorSize(Integer.valueOf(input));
			}
			catch(NumberFormatException e)
			{
				e.printStackTrace();
			}
		}
		else if(obj == this.reset)
		{
			this.reset();
		}
		else
		{
			int languageNo = this.getLanguageNo(obj);
			this.setDefaultLanguage(languageNo);
		}
	}
	
	/**
	 * 言語番号取得.
	 * @param src メニューアイテム
	 * @return 言語番号
	 */
	private int getLanguageNo(Object src)
	{
			 if(src == setting_jpn){ return MesTableDefine.Language_JPN; }
		else if(src == setting_eng){ return MesTableDefine.Language_ENG; }
		else if(src == setting_deu){ return MesTableDefine.Language_DEU; }
		else if(src == setting_fra){ return MesTableDefine.Language_FRA; }
		else if(src == setting_ita){ return MesTableDefine.Language_ITA; }
		else if(src == setting_spa){ return MesTableDefine.Language_SPA; }
		else if(src == setting_rus){ return MesTableDefine.Language_RUS; }
		else if(src == setting_tha){ return MesTableDefine.Language_THA; }
		return MesTableDefine.Language_ENG;
	}
}
