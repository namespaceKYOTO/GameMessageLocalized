package MesMan;

import java.util.Stack;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.swing.*;

import MesMan.MenuBar.FileMenu;
import MesMan.MenuBar.HelpMenu;
import MesMan.MenuBar.LanguageMenu;
import MesMan.MenuBar.MenuBar;
import MesMan.MenuBar.SettingMenu;
import MesMan.MenuBar.ToolsMenu;

/**
 * メッセージ管理クラス.
 * @author t_sato
 * 
 *
 */
public class MesMan extends JFrame implements ActionListener, WindowListener
{
	private static final long serialVersionUID = 715723283093561615L;
	
	private CmdArgEx cmdArg;
	private MessageDataManager mesman;
	private MenuBar menubar;
	private FileMenu fileMenu;
	private SettingMenu settingMenu;
	private LanguageMenu languageMenu;
	private ToolsMenu toolsMenu;
	private HelpMenu helpMenu;
	private TableMenu tableMenu;
	
	private String FRAME_TITLE_NORMAL = "Message Manager";
	private String FRAME_TITLE_SAMPLE = "Sample";
	
	/**
	 * コンストラクタ.
	 * @param args オプション
	 * @param independence 独立したWindowか?
	 */
	public MesMan(String[] args, boolean independence)
	{
		cmdArg = new CmdArgEx();
		cmdArg.analyzeCommandArguments(args);
		boolean isSampleMode = (cmdArg.getMode() == CmdArgEx.eMode.Sample);
		
		addWindowListener(this);
		
		mesman = new MessageDataManager("/res/MesTableDefine.bin");
		mesman.setLanguageNo(SettingMenu.getDefaultLanguage("./config.txt"));
		
		if( !isSampleMode ) {
			setTitle(FRAME_TITLE_NORMAL);
		} else {
			setTitle(FRAME_TITLE_SAMPLE);
		}
		
		if(independence) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rec = env.getMaximumWindowBounds();
		int width = (int)rec.getWidth() / 2;
		int height = (int)rec.getHeight() / 2;
		int x = ((int)rec.getWidth() / 2) - (width / 2);
		int y = ((int)rec.getHeight() / 2) - (height / 2);
		setBounds( x, y, width, height );
		
		//// menu bar
		menubar = new MenuBar(this);

		// size
		Dimension size = getContentPane().getSize();
		tableMenu = new TableMenu(mesman, size.width, size.height);
		add(tableMenu.getComponent(), BorderLayout.CENTER);
		if( isSampleMode ) {
			String charset = "UTF-16";
			TableFile tableFile = new TableFile("", charset);
			String mtblFile = cmdArg.getMtblFile();
			String ttblFile = cmdArg.getTtblFile();
			String ctblFile = cmdArg.getCtblFile();
			try
			{
				if(mtblFile != null && mtblFile.length() > 0) {
					InputStreamReader mtblFileStream = new InputStreamReader(this.getClass().getResourceAsStream(mtblFile), charset);
					tableFile.open(mtblFileStream, tableMenu.getMesTable());
				}
				if(ttblFile != null && ttblFile.length() > 0) {
					InputStreamReader ttblFileStream = new InputStreamReader(this.getClass().getResourceAsStream(ttblFile), charset);
					tableFile.open(ttblFileStream, tableMenu.getTagTable());
				}
				if(ctblFile != null && ctblFile.length() > 0) {
					InputStreamReader ctblFileStream = new InputStreamReader(this.getClass().getResourceAsStream(ctblFile), charset);
					tableFile.open(ctblFileStream, tableMenu.getCharsizeTable());
				}
			}
			catch(UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		
		{
			settingMenu = new SettingMenu(this, "./config.txt");
			fileMenu = new FileMenu(this, tableMenu, settingMenu);
			languageMenu = new LanguageMenu(this);
			toolsMenu = new ToolsMenu(this, tableMenu);
			helpMenu = new HelpMenu(this);
		}
		
		menubar.add(fileMenu);
		if( !isSampleMode )	{
			menubar.add(settingMenu);
		}
		menubar.add(languageMenu);
		menubar.add(toolsMenu);
		if( !isSampleMode ) {
			menubar.add(helpMenu);
		}
		
		setJMenuBar(menubar.getMenuBar());
		setVisible(true);
		
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
	
	/**
	 * 引数解析クラス取得.
	 * @return 引数解析クラス
	 */
	public CmdArgEx getCmdArg() {
		return cmdArg;
	}

	/**
	 * メッセージデータ管理クラス取得.
	 * @return メッセージデータ管理クラス
	 */
	public MessageDataManager getMesDataMan() {
		return mesman;
	}

	/**
	 * テーブルメニュー取得.
	 * @return
	 */
	public TableMenu getTableMenu() {
		return tableMenu;
	}
	
	/**
	 * メニューバー取得.
	 * @return メニューバー
	 */
	public MenuBar getMenubar() {
		return menubar;
	}

	/* (非 Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowClosing(WindowEvent arg0) {
		// 終了時処理
		if(cmdArg.getMode() == CmdArgEx.eMode.Normal)
		{
			this.settingMenu.save();
		}
	}
	
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}
