package MesMan;

import java.io.*;
import java.net.URL;
import java.util.Stack;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import MesMan.MenuBar.FileMenu;
import MesMan.MenuBar.LanguageMenu;
import MesMan.MenuBar.MenuBar;
import MesMan.MenuBar.SettingMenu;

/**
 * メッセージ管理クラス.
 * @author t_sato
 * 
 *
 */
public class MesMan extends JFrame implements ActionListener, MenuListener, WindowListener
{
	private MessageManager mesman;
	private MenuBar menubar;
	private FileMenu fileMenu;
	private SettingMenu settingMenu;
	private LanguageMenu language;
//	private MesTable mesTable;
//	private TagTable tagTable;
//	private CharacterSizeTable charSizeTable;
//	private ResultTable resultTable;
	private TableMenu tableMenu;
//	private CheckParamPanel checkParam;
//	private JFileChooser tblChooser;
//	private JFileChooser outputChooser;
//	private JFileChooser directoryChooser;
	private Object selectMenu = null;
	
	private String FRAME_TITLE = "Message Manager";
	
//	private JMenu file;
//	private JMenu setting;
//	private JMenu language;
	private JMenu help;
	private JMenu tools;
//	private JMenu open;
//	private JMenu save;

//	private JTabbedPane tab;
	
//	private JMenuItem openMesTbl;
//	private JMenuItem openTabTbl;
//	private JMenuItem openCharTbl;
//	private JMenuItem saveMesTbl;
//	private JMenuItem saveTabTbl;
//	private JMenuItem saveCharTbl;
//	private JMenuItem output;
	
//	private JMenuItem defaulttDirectory;
//	private JMenu defaultLanguage;
//	private JMenuItem setting_jpn;
//	private JMenuItem setting_eng;
//	private JMenuItem setting_deu;
//	private JMenuItem setting_fra;
//	private JMenuItem setting_ita;
//	private JMenuItem setting_spa;
//	private JMenuItem setting_rus;
//	private JMenuItem setting_tha;
//	private JMenuItem reset;
//
//	private JMenuItem jpn;
//	private JMenuItem eng;
//	private JMenuItem deu;
//	private JMenuItem fra;
//	private JMenuItem ita;
//	private JMenuItem spa;
//	private JMenuItem rus;
//	private JMenuItem tha;
	
	private JMenuItem check;
	
	
	public static void main(String[] args)
	{	
		MesMan mesMan = new MesMan();
	}
	
	/**
	 * コンストラクタ.
	 */
	public MesMan()
	{
		addWindowListener(this);
		
		mesman = new MessageManager("/res/MesTableDefine.bin");
		mesman.setLanguageNo(SettingMenu.getDefaultLanguage("./config.txt"));
		
		setTitle(FRAME_TITLE);
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rec = env.getMaximumWindowBounds();
		int width = (int)rec.getWidth() / 2;
		int height = (int)rec.getHeight() / 2;
		int x = ((int)rec.getWidth() / 2) - (width / 2);
		int y = ((int)rec.getHeight() / 2) - (height / 2);
		setBounds( x, y, width, height );		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//// menu bar
//		JMenuBar menuBar = new JMenuBar();
		menubar = new MenuBar(this);
		
		{
			fileMenu = new FileMenu(this, mesman, tableMenu, settingMenu);
			settingMenu = new SettingMenu(this, mesman, "./config.txt");
			language = new LanguageMenu(this, mesman);
			help = new JMenu(mesman.getMessage(MesTableDefine.mes_help));
			tools = new JMenu(mesman.getMessage(MesTableDefine.mes_tools));
//			language.addMenuListener(this);
		}
		
//		//// File
//		// Open
//		{
//			open = new JMenu(mesman.getMessage(MesTableDefine.mes_open));
//			openMesTbl = new JMenuItem(mesman.getMessage(MesTableDefine.mes_mtbl));
//			openTabTbl = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ttbl));
//			openCharTbl = new JMenuItem("ctbl");
//			openMesTbl.addActionListener(this);
//			openTabTbl.addActionListener(this);
//			openCharTbl.addActionListener(this);
//			open.add(openMesTbl);
//			open.add(openTabTbl);
//			open.add(openCharTbl);
//			file.add(open);
//		}
//		
//		// Save
//		{
//			save = new JMenu(mesman.getMessage(MesTableDefine.mes_save));
//			saveMesTbl = new JMenuItem(mesman.getMessage(MesTableDefine.mes_mtbl));
//			saveTabTbl = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ttbl));
//			saveCharTbl = new JMenuItem("ctbl");
//			saveMesTbl.addActionListener(this);
//			saveTabTbl.addActionListener(this);
//			saveCharTbl.addActionListener(this);
//			save.add(saveMesTbl);
//			save.add(saveTabTbl);
//			save.add(saveCharTbl);
//			file.add(save);
//		}
//		
//		// Output
//		{
//			output = new JMenuItem(mesman.getMessage(MesTableDefine.mes_output));
//			output.addActionListener(this);
//			file.add(output);
//		}
		
		//// Setting
		{
//			defaulttDirectory = new JMenuItem ("Default Directory");
//			defaultLanguage = new JMenu("Default Language");
//			reset = new JMenuItem("reset");
//			defaulttDirectory.addActionListener(this);
//			defaultLanguage.addActionListener(this);
//			defaultLanguage.addMenuListener(this);
//			reset.addActionListener(this);
//			
//			setting_jpn = new JMenuItem(mesman.getMessage(MesTableDefine.mes_JPN));
//			setting_eng = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ENG));
//			setting_deu = new JMenuItem(mesman.getMessage(MesTableDefine.mes_DEU));
//			setting_fra = new JMenuItem(mesman.getMessage(MesTableDefine.mes_FRA));
//			setting_ita = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ITA));
//			setting_spa = new JMenuItem(mesman.getMessage(MesTableDefine.mes_SPA));
//			setting_rus = new JMenuItem(mesman.getMessage(MesTableDefine.mes_RUS));
//			setting_tha = new JMenuItem(mesman.getMessage(MesTableDefine.mes_THA));
//			setting_jpn.addActionListener(this);
//			setting_eng.addActionListener(this);
//			setting_deu.addActionListener(this);
//			setting_fra.addActionListener(this);
//			setting_ita.addActionListener(this);
//			setting_spa.addActionListener(this);
//			setting_rus.addActionListener(this);
//			setting_tha.addActionListener(this);
//			defaultLanguage.add(setting_jpn);
//			defaultLanguage.add(setting_eng);
//			defaultLanguage.add(setting_deu);
//			defaultLanguage.add(setting_fra);
//			defaultLanguage.add(setting_ita);
//			defaultLanguage.add(setting_spa);
//			defaultLanguage.add(setting_rus);
//			defaultLanguage.add(setting_tha);
//
//			
//			setting.add(defaulttDirectory);
//			setting.add(defaultLanguage);
//			setting.add(reset);
		}
		
		// Language
		{
//			jpn = new JMenuItem(mesman.getMessage(MesTableDefine.mes_JPN));
//			eng = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ENG));
//			deu = new JMenuItem(mesman.getMessage(MesTableDefine.mes_DEU));
//			fra = new JMenuItem(mesman.getMessage(MesTableDefine.mes_FRA));
//			ita = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ITA));
//			spa = new JMenuItem(mesman.getMessage(MesTableDefine.mes_SPA));
//			rus = new JMenuItem(mesman.getMessage(MesTableDefine.mes_RUS));
//			tha = new JMenuItem(mesman.getMessage(MesTableDefine.mes_THA));
//			jpn.addActionListener(this);
//			eng.addActionListener(this);
//			deu.addActionListener(this);
//			fra.addActionListener(this);
//			ita.addActionListener(this);
//			spa.addActionListener(this);
//			rus.addActionListener(this);
//			tha.addActionListener(this);
//			language.add(jpn);
//			language.add(eng);
//			language.add(deu);
//			language.add(fra);
//			language.add(ita);
//			language.add(spa);
//			language.add(rus);
//			language.add(tha);
		}
		
		// tools
		{
			check = new JMenuItem("Message Sizes Check");
			check.addActionListener(this);
			tools.add(check);
		}
		
		menubar.add(fileMenu);
		menubar.add(settingMenu);
		menubar.add(language);
//		menuBar.add(help);
//		menuBar.add(tools);
		
		setJMenuBar(menubar.getMenuBar());
		
		setVisible(true);
		
		// size
		Dimension size = getContentPane().getSize();
		
//		// create message table
//		this.mesTable = new MesTable(mesman, size.width, size.height);
//		// create tab table
//		this.tagTable = new TagTable(mesman, size.width, size.height);
//		//
//		this.charSizeTable = new CharacterSizeTable(mesman, width, height);
//		//
//		this.resultTable = new ResultTable(mesman, width, height);
//		
//		BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
//		setLayout(layout);
//		tab.addTab("Tag", this.tagTable.getPanel());
//		tab.addTab("Message", this.mesTable.getPanel());
//		tab.addTab("CharacterSize", this.charSizeTable.getPanel());
//		tab.addTab("Result", this.resultTable.getPanel());
		this.tableMenu = new TableMenu(this.mesman, size.width, size.height);
		add(this.tableMenu.getComponent(), BorderLayout.CENTER);
		
		
		// file Chooser
//		this.checkParam = new CheckParamPanel();
//		this.outputChooser = new JFileChooser();
//		this.directoryChooser = new JFileChooser();
		
//		this.outputChooser.setAccessory(this.checkParam.getPanel());
//		this.directoryChooser.setDialogType(JFileChooser.CUSTOM_DIALOG);
//		this.directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
//		this.tblChooser = new JFileChooser();
		
		getContentPane().validate();
		
		// resize listener
		{
			Stack<TableEx> tables = new Stack<TableEx>();
			tables.push(this.tableMenu.getTagTable());
			tables.push(this.tableMenu.getMesTable());
			tables.push(this.tableMenu.getCharsizeTable());
			tables.push(this.tableMenu.getResultTable());
			addComponentListener(new MyComponentListener(getContentPane(), tables));
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.paramString());
		Object obj = e.getSource();
		
//		if(obj == openMesTbl || obj == openTabTbl || obj == openCharTbl)
//		{
//			String suffix = null;
//			String description =null;
//			String charset = "UTF-16";
//			TableEx table = null;
//			int tabIdx = 0;
//
//			     if(obj == openMesTbl ) { suffix = ".mtbl"; description = "Message Table(.mtbl)"; table = this.tableMenu.getMesTable(); tabIdx = 1; }
//			else if(obj == openTabTbl ) { suffix = ".ttbl"; description = "Tag Table(.ttbl)"; table = this.tableMenu.getTagTable(); tabIdx = 0; }
//			else if(obj == openCharTbl) { suffix = ".ctbl"; description = "Character Size Table(.ctbl)"; table = this.tableMenu.getCharsizeTable(); tabIdx = 2; }
//			
//			File file = new File(set.getDefaultDirectory());
//			this.tblChooser.setCurrentDirectory(file);
//			this.tblChooser.setFileFilter(new FileFilterEx(suffix,description));
//			int ret = this.tblChooser.showOpenDialog(this);
//			if(ret == JFileChooser.APPROVE_OPTION)
//			{
//				File inputFile = this.tblChooser.getSelectedFile();
//				TableFile tableFile = new TableFile(suffix, charset);
//				tableFile.open(inputFile, table);
//				this.tableMenu.setTabSelectedIndex(tabIdx);
//			}
//		}
//		else if(obj == saveMesTbl || obj == saveTabTbl || obj == saveCharTbl)
//		{
//			String suffix = null;
//			String description =null;
//			String charset = "UTF-16";
//			TableEx table = null;
//
//			     if(obj == saveMesTbl ) { suffix = ".mtbl"; description = "Message Table(.mtbl)"; table = this.tableMenu.getMesTable(); }
//			else if(obj == saveTabTbl ) { suffix = ".ttbl"; description = "Tag Table(.ttbl)"; table = this.tableMenu.getTagTable(); }
//			else if(obj == saveCharTbl) { suffix = ".ctbl"; description = "Character Size Table(.ctbl)"; table = this.tableMenu.getCharsizeTable(); }
//			     
//			File file = new File(set.getDefaultDirectory());
//			this.tblChooser.setCurrentDirectory(file);
//			this.tblChooser.setFileFilter(new FileFilterEx(suffix, description));
//			int ret = this.tblChooser.showSaveDialog(this);
//			if(ret == JFileChooser.APPROVE_OPTION)
//			{
//				File inputFile = this.tblChooser.getSelectedFile();
//				TableFile tableFile = new TableFile(suffix, charset);
//				tableFile.save(inputFile, table);
//			}	
//		}
//		else if(obj == output)
//		{
//			File file = new File(set.getDefaultDirectory());
//			this.outputChooser.setCurrentDirectory(file);
//			this.outputChooser.setFileFilter(null);
//			int ret = this.outputChooser.showSaveDialog(this);
//			if(ret == JFileChooser.APPROVE_OPTION)
//			{
//				File outFile = this.outputChooser.getSelectedFile();
//				OutPuter outPuter = new OutPuter();
//				outPuter.outPut(outFile, this.tableMenu.getTagTable(), this.tableMenu.getMesTable(), this.checkParam.getOutFileFlag(), this.checkParam.getCaraCodeFlag());
//			}
//		}
//		else if(obj == this.defaulttDirectory)
//		{
//			int ret = this.directoryChooser.showOpenDialog(this);
//			if(ret == JFileChooser.APPROVE_OPTION)
//			{
//				File file = this.directoryChooser.getSelectedFile();
//				this.set.setDefaultDirectory(file.getPath());
//			}
//		}
//		else if(obj == this.reset)
//		{
//			this.set.reset();
//		}
		// Character Size
//		else
			if(obj == this.check)
		{
			JOptionPane pane = new JOptionPane("language select", JOptionPane.YES_OPTION);
			String input = pane.showInputDialog("language select");
			this.tableMenu.checkMessageSize(input);
		}
		// Language Change
		else
		{
//			if(this.selectMenu == this.language) {
//				LanguageChange(obj);
//			}
//			else if(this.selectMenu == this.defaultLanguage) {
//				int languageNo = this.getLanguageNo(obj);
//				this.set.setDefaultLanguage(languageNo);
//			}
		}
	}
	
	/**
	 * 言語番号取得.
	 * @param src メニューアイテム
	 * @return 言語番号
	 */
//	private int getLanguageNo(Object src)
//	{
//			 if(src == jpn || src == setting_jpn){return MesTableDefine.Language_JPN; }
//		else if(src == eng || src == setting_eng){return MesTableDefine.Language_ENG; }
//		else if(src == deu || src == setting_deu){return MesTableDefine.Language_DEU; }
//		else if(src == fra || src == setting_fra){return MesTableDefine.Language_FRA; }
//		else if(src == ita || src == setting_ita){return MesTableDefine.Language_ITA; }
//		else if(src == spa || src == setting_spa){return MesTableDefine.Language_SPA; }
//		else if(src == rus || src == setting_rus){return MesTableDefine.Language_RUS; }
//		else if(src == tha || src == setting_tha){return MesTableDefine.Language_THA; }
//		return MesTableDefine.Language_ENG;
//	}
	
	/**
	 * UIの表示言語変更.
	 * @param src　メニューアイテム
	 */
//	private void LanguageChange(Object src)
//	{
//		mesman.setLanguageNo(this.getLanguageNo(src));
//		
//
////		file.setText(mesman.getMessage(MesTableDefine.mes_file));
////		setting.setText(mesman.getMessage(MesTableDefine.mes_setting));
//		language.setText(mesman.getMessage(MesTableDefine.mes_language));
//		help.setText(mesman.getMessage(MesTableDefine.mes_help));
//		tools.setText(mesman.getMessage(MesTableDefine.mes_tools));
//		
////		open.setText(mesman.getMessage(MesTableDefine.mes_open));
////		openMesTbl.setText(mesman.getMessage(MesTableDefine.mes_mtbl));
////		openTabTbl.setText(mesman.getMessage(MesTableDefine.mes_ttbl));
//		
////		save.setText(mesman.getMessage(MesTableDefine.mes_save));
////		saveMesTbl.setText(mesman.getMessage(MesTableDefine.mes_mtbl));
////		saveTabTbl.setText(mesman.getMessage(MesTableDefine.mes_ttbl));
//
////		output.setText(mesman.getMessage(MesTableDefine.mes_output));
//		
////		mesTable.LanguageChange();
////		tagTable.LanguageChange();
//		this.tableMenu.LanguageChange();
//		this.fileMenu.LanguageChange();
//		this.settingMenu.LanguageChange();
//	}

	public void menuCanceled(MenuEvent arg0) {}
	public void menuDeselected(MenuEvent arg0) {}
	public void menuSelected(MenuEvent arg0) {
		this.selectMenu = arg0.getSource();
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowClosing(WindowEvent arg0) {
		// 終了時処理
		this.settingMenu.save();
	}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}
