package MesMan.MenuBar;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import MesMan.CheckParamPanel;
import MesMan.CmdArgEx;
import MesMan.FileFilterEx;
import MesMan.MesMan;
import MesMan.MesTableDefine;
import MesMan.MessageDataManager;
import MesMan.OutPuter;
import MesMan.TableEx;
import MesMan.TableFile;
import MesMan.TableMenu;


/**
 * ファイルメニュー
 * @author t_sato
 *
 */
public class FileMenu extends MenuBarBase
{
	private TableMenu tableMenu;
	private SettingMenu settingMenu;
	private CheckParamPanel checkParam;
	private JFileChooser tblChooser;
	private JFileChooser outputChooser;
	private JMenu open;
	private JMenu save;
	private JMenuItem openMesTbl;
	private JMenuItem openTabTbl;
	private JMenuItem openCharTbl;
	private JMenuItem saveMesTbl;
	private JMenuItem saveTabTbl;
	private JMenuItem saveCharTbl;
	private JMenuItem output;

	
	/**
	 * コンストラクタ.
	 * @param mesman メッセージ管理
	 * @param tableMenu テーブルメニュー
	 * @param settingMenu 設定メニュー
	 */
	public FileMenu(MesMan mesman, TableMenu tableMenu, SettingMenu settingMenu)
	{
		super(mesman, mesman.getMesDataMan().getMessage(MesTableDefine.mes_file));
		
		MessageDataManager mesDataMan =  mesman.getMesDataMan();
		
		this.tableMenu = tableMenu;
		this.settingMenu = settingMenu;
		
		this.tblChooser = new JFileChooser();
		this.checkParam = new CheckParamPanel();
		this.outputChooser = new JFileChooser();
		this.outputChooser.setAccessory(this.checkParam.getPanel());
		
		JMenu menu = getMenu();
		
		open = new JMenu(mesDataMan.getMessage(MesTableDefine.mes_open));
		openMesTbl = new JMenuItem(mesDataMan.getMessage(MesTableDefine.mes_mtbl));
		openTabTbl = new JMenuItem(mesDataMan.getMessage(MesTableDefine.mes_ttbl));
		openCharTbl = new JMenuItem("ctbl");
		openMesTbl.addActionListener(this);
		openTabTbl.addActionListener(this);
		openCharTbl.addActionListener(this);
		open.add(openMesTbl);
		open.add(openTabTbl);
		open.add(openCharTbl);
		
		save = new JMenu(mesDataMan.getMessage(MesTableDefine.mes_save));
		saveMesTbl = new JMenuItem(mesDataMan.getMessage(MesTableDefine.mes_mtbl));
		saveTabTbl = new JMenuItem(mesDataMan.getMessage(MesTableDefine.mes_ttbl));
		saveCharTbl = new JMenuItem("ctbl");
		saveMesTbl.addActionListener(this);
		saveTabTbl.addActionListener(this);
		saveCharTbl.addActionListener(this);
		save.add(saveMesTbl);
		save.add(saveTabTbl);
		save.add(saveCharTbl);

		output = new JMenuItem(mesDataMan.getMessage(MesTableDefine.mes_output));
		output.addActionListener(this);
		
		if(mesman.getCmdArg().getMode() != CmdArgEx.eMode.Sample) {
			menu.add(open);
			menu.add(save);
		}
		menu.add(output);
	}
	
	/**
	 * UI表示言語変更.
	 */
	public void LanguageChange()
	{
		MessageDataManager mesDataMan = getMesman().getMesDataMan(); 
		getMenu().setText(mesDataMan.getMessage(MesTableDefine.mes_file));
		open.setText(mesDataMan.getMessage(MesTableDefine.mes_open));
		openMesTbl.setText(mesDataMan.getMessage(MesTableDefine.mes_mtbl));
		openTabTbl.setText(mesDataMan.getMessage(MesTableDefine.mes_ttbl));
		save.setText(mesDataMan.getMessage(MesTableDefine.mes_save));
		saveMesTbl.setText(mesDataMan.getMessage(MesTableDefine.mes_mtbl));
		saveTabTbl.setText(mesDataMan.getMessage(MesTableDefine.mes_ttbl));
		output.setText(mesDataMan.getMessage(MesTableDefine.mes_output));
	}
	
	/* (非 Javadoc)
	 * @see MesMan.MenuBar.MenuBarBase#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		System.out.println(arg0.paramString());
		Object obj = arg0.getSource();
		
		if(obj == openMesTbl || obj == openTabTbl || obj == openCharTbl)
		{
			String suffix = null;
			String description =null;
			String charset = "UTF-16";
			TableEx table = null;
			int tabIdx = 0;

			     if(obj == openMesTbl ) { suffix = ".mtbl"; description = "Message Table(.mtbl)"; table = this.tableMenu.getMesTable(); tabIdx = 1; }
			else if(obj == openTabTbl ) { suffix = ".ttbl"; description = "Tag Table(.ttbl)"; table = this.tableMenu.getTagTable(); tabIdx = 0; }
			else if(obj == openCharTbl) { suffix = ".ctbl"; description = "Character Size Table(.ctbl)"; table = this.tableMenu.getCharsizeTable(); tabIdx = 2; }
			
			File file = new File(settingMenu.getDefaultDirectory());
			this.tblChooser.setCurrentDirectory(file);
			this.tblChooser.setFileFilter(new FileFilterEx(suffix,description));
			int ret = this.tblChooser.showOpenDialog(this.getParent());
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				File inputFile = this.tblChooser.getSelectedFile();
				TableFile tableFile = new TableFile(suffix, charset);
				tableFile.open(inputFile, table);
				this.tableMenu.setTabSelectedIndex(tabIdx);
			}
		}
		else if(obj == saveMesTbl || obj == saveTabTbl || obj == saveCharTbl)
		{
			String suffix = null;
			String description =null;
			String charset = "UTF-16";
			TableEx table = null;

			     if(obj == saveMesTbl ) { suffix = ".mtbl"; description = "Message Table(.mtbl)"; table = this.tableMenu.getMesTable(); }
			else if(obj == saveTabTbl ) { suffix = ".ttbl"; description = "Tag Table(.ttbl)"; table = this.tableMenu.getTagTable(); }
			else if(obj == saveCharTbl) { suffix = ".ctbl"; description = "Character Size Table(.ctbl)"; table = this.tableMenu.getCharsizeTable(); }
			     
			File file = new File(settingMenu.getDefaultDirectory());
			this.tblChooser.setCurrentDirectory(file);
			this.tblChooser.setFileFilter(new FileFilterEx(suffix, description));
			int ret = this.tblChooser.showSaveDialog(this.getParent());
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				File inputFile = this.tblChooser.getSelectedFile();
				TableFile tableFile = new TableFile(suffix, charset);
				tableFile.save(inputFile, table);
			}	
		}
		else if(obj == output)
		{
			File file = new File(settingMenu.getDefaultDirectory());
			this.outputChooser.setCurrentDirectory(file);
			this.outputChooser.setFileFilter(null);
			int ret = this.outputChooser.showSaveDialog(this.getParent());
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				File outFile = this.outputChooser.getSelectedFile();
				OutPuter outPuter = new OutPuter();
				outPuter.outPut(outFile, this.tableMenu.getTagTable(), this.tableMenu.getMesTable(), this.checkParam.getOutFileFlag(), this.checkParam.getCaraCodeFlag());
			}
		}
	}
}
