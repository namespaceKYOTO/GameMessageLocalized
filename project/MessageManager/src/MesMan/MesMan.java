/*---------------------------------------------------------------------*/
/*!
 * @brief	Message Manager
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.io.*;
import java.util.Stack;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MesMan extends JFrame implements ActionListener, MenuListener, WindowListener
{
	private MessageManager mesman;
	private Setting set;
	private MesTable mesTable;
	private TagTable tagTable;
	private CharacterSizeTable charSizeTable;
	private CheckParamPanel checkParam;
	private JFileChooser mtblChooser;
	private JFileChooser outputChooser;
	private JFileChooser directoryChooser;
	
	private String FRAME_TITLE = "Message Manager";
	
	private JMenu file;
	private JMenu setting;
	private JMenu language;
	private JMenu help;
	private JMenu tools;
	private JMenu open;
	private JMenu save;

	private JTabbedPane tab;
	
	private JMenuItem openMesTbl;
	private JMenuItem openTabTbl;
	private JMenuItem saveMesTbl;
	private JMenuItem saveTabTbl;
	private JMenuItem output;
	
	private JMenuItem defaulttDirectory;
	private JMenu defaultLanguage;
	private JMenuItem setting_jpn;
	private JMenuItem setting_eng;
	private JMenuItem setting_deu;
	private JMenuItem setting_fra;
	private JMenuItem setting_ita;
	private JMenuItem setting_spa;
	private JMenuItem setting_rus;
	private JMenuItem setting_tha;
	private JMenuItem reset;

	private JMenuItem jpn;
	private JMenuItem eng;
	private JMenuItem deu;
	private JMenuItem fra;
	private JMenuItem ita;
	private JMenuItem spa;
	private JMenuItem rus;
	private JMenuItem tha;
	
	private Object selectMenu = null;
	
	public static void main(String[] args)
	{
		for(String arg : args)
		{
			System.out.println(arg);
		}
		
		MesMan mesMan = new MesMan();
	}
	
	public MesMan()
	{
		addWindowListener(this);
		
		set = new Setting("res/config.txt");
		mesman = new MessageManager("res/MesTableDefine.bin");
		mesman.setLanguageNo(set.getDefaultLanguage());
		
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
		JMenuBar menuBar = new JMenuBar();
		{
			file = new JMenu(mesman.getMessage(MesTableDefine.mes_file));
			setting = new JMenu(mesman.getMessage(MesTableDefine.mes_setting));
			language = new JMenu(mesman.getMessage(MesTableDefine.mes_language));
			help = new JMenu(mesman.getMessage(MesTableDefine.mes_help));
			tools = new JMenu(mesman.getMessage(MesTableDefine.mes_tools));
			language.addMenuListener(this);
		}
		
		//// 
		{
			tab = new JTabbedPane();
		}
		
		//// File
		// Open
		{
			open = new JMenu(mesman.getMessage(MesTableDefine.mes_open));
			openMesTbl = new JMenuItem(mesman.getMessage(MesTableDefine.mes_mtbl));
			openTabTbl = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ttbl));
			openMesTbl.addActionListener(this);
			openTabTbl.addActionListener(this);
			open.add(openMesTbl);
			open.add(openTabTbl);
			file.add(open);
		}
		
		// Save
		{
			save = new JMenu(mesman.getMessage(MesTableDefine.mes_save));
			saveMesTbl = new JMenuItem(mesman.getMessage(MesTableDefine.mes_mtbl));
			saveTabTbl = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ttbl));
			saveMesTbl.addActionListener(this);
			saveTabTbl.addActionListener(this);
			save.add(saveMesTbl);
			save.add(saveTabTbl);
			file.add(save);
		}
		
		// Output
		{
			output = new JMenuItem(mesman.getMessage(MesTableDefine.mes_output));
			output.addActionListener(this);
			file.add(output);
		}
		
		//// Setting
		{
			defaulttDirectory = new JMenuItem ("Default Directory");
			defaultLanguage = new JMenu("Default Language");
			reset = new JMenuItem("reset");
			defaulttDirectory.addActionListener(this);
			defaultLanguage.addActionListener(this);
			defaultLanguage.addMenuListener(this);
			reset.addActionListener(this);
			
			setting_jpn = new JMenuItem(mesman.getMessage(MesTableDefine.mes_JPN));
			setting_eng = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ENG));
			setting_deu = new JMenuItem(mesman.getMessage(MesTableDefine.mes_DEU));
			setting_fra = new JMenuItem(mesman.getMessage(MesTableDefine.mes_FRA));
			setting_ita = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ITA));
			setting_spa = new JMenuItem(mesman.getMessage(MesTableDefine.mes_SPA));
			setting_rus = new JMenuItem(mesman.getMessage(MesTableDefine.mes_RUS));
			setting_tha = new JMenuItem(mesman.getMessage(MesTableDefine.mes_THA));
			setting_jpn.addActionListener(this);
			setting_eng.addActionListener(this);
			setting_deu.addActionListener(this);
			setting_fra.addActionListener(this);
			setting_ita.addActionListener(this);
			setting_spa.addActionListener(this);
			setting_rus.addActionListener(this);
			setting_tha.addActionListener(this);
			defaultLanguage.add(setting_jpn);
			defaultLanguage.add(setting_eng);
			defaultLanguage.add(setting_deu);
			defaultLanguage.add(setting_fra);
			defaultLanguage.add(setting_ita);
			defaultLanguage.add(setting_spa);
			defaultLanguage.add(setting_rus);
			defaultLanguage.add(setting_tha);

			
			setting.add(defaulttDirectory);
			setting.add(defaultLanguage);
			setting.add(reset);
		}
		
		// Language
		{
			jpn = new JMenuItem(mesman.getMessage(MesTableDefine.mes_JPN));
			eng = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ENG));
			deu = new JMenuItem(mesman.getMessage(MesTableDefine.mes_DEU));
			fra = new JMenuItem(mesman.getMessage(MesTableDefine.mes_FRA));
			ita = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ITA));
			spa = new JMenuItem(mesman.getMessage(MesTableDefine.mes_SPA));
			rus = new JMenuItem(mesman.getMessage(MesTableDefine.mes_RUS));
			tha = new JMenuItem(mesman.getMessage(MesTableDefine.mes_THA));
			jpn.addActionListener(this);
			eng.addActionListener(this);
			deu.addActionListener(this);
			fra.addActionListener(this);
			ita.addActionListener(this);
			spa.addActionListener(this);
			rus.addActionListener(this);
			tha.addActionListener(this);
			language.add(jpn);
			language.add(eng);
			language.add(deu);
			language.add(fra);
			language.add(ita);
			language.add(spa);
			language.add(rus);
			language.add(tha);
		}
		
		// tools
		{
//			JMenuItem item = new JMenuItem(CHAR_SIZE);
//			item.addActionListener(this);
//			tools.add(item);
		}
		
		menuBar.add(file);
		menuBar.add(setting);
		menuBar.add(language);
		menuBar.add(help);
		menuBar.add(tools);
		
		add(tab, BorderLayout.CENTER);
		
		setJMenuBar(menuBar);
		
		setVisible(true);
		
		// size
		Dimension size = getContentPane().getSize();
//		width = size.width;
//		height = size.height;
//		System.out.println(String.format("Dimension ** width : %d, height : %d", size.width, size.height));
		
		// create message table
		String[] languages = {"Label", "Description", "JPN", "ENG", "DEU", "FRA", "ITA", "SPA"};
		this.mesTable = new MesTable(mesman, languages, size.width, size.height);
		
		// create tab table
		String[] tag = {"Tag Name", "Description", "Code"};
		this.tagTable = new TagTable(mesman, tag, size.width, size.height);
		
		String[] character = {"Character", "Size"};
		this.charSizeTable = new CharacterSizeTable(mesman, character, width, height);
		
		BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
		setLayout(layout);
		tab.addTab("Tag", this.tagTable.getPanel());
		tab.addTab("Message", this.mesTable.getPanel());
		tab.addTab("CharacterSize", this.charSizeTable.getPanel());
		
		// file Chooser
		this.checkParam = new CheckParamPanel();
		this.outputChooser = new JFileChooser();
		this.directoryChooser = new JFileChooser();
		
		this.outputChooser.setAccessory(this.checkParam.getPanel());
		this.directoryChooser.setDialogType(JFileChooser.CUSTOM_DIALOG);
		this.directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		this.mtblChooser = new JFileChooser();
		
		getContentPane().validate();
		
		// resize listener
		{
			Stack<TableEx> tables = new Stack<TableEx>();
			tables.push(this.tagTable);
			tables.push(this.mesTable);
			tables.push(this.charSizeTable);
			addComponentListener(new MyComponentListener(getContentPane(), tables));
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	invoked when an action occurs
	/*---------------------------------------------------------------------*/
	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.paramString());
		
		if(e.getSource() == openMesTbl)
		{
			File file = new File(set.getDefaultDirectory());
			this.mtblChooser.setCurrentDirectory(file);
			this.mtblChooser.setFileFilter(new FileFilterEx(".mtbl","Message Table(.mtbl)"));
			int ret = this.mtblChooser.showOpenDialog(this);
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				File inputFile = this.mtblChooser.getSelectedFile();
				MTbl mtbl = new MTbl();
				mtbl.open(inputFile, this.mesTable);
			}
		}
		else if(e.getSource() == openTabTbl)
		{
			File file = new File(set.getDefaultDirectory());
			this.mtblChooser.setCurrentDirectory(file);
			this.mtblChooser.setFileFilter(new FileFilterEx(".ttbl","Tag Table(.ttbl)"));
			int ret = this.mtblChooser.showOpenDialog(this);
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				File inputFile = this.mtblChooser.getSelectedFile();
				TTbl ttbl = new TTbl();
				ttbl.open(inputFile, this.tagTable);
			}
		}
		else if(e.getSource() == saveMesTbl)
		{
			File file = new File(set.getDefaultDirectory());
			this.mtblChooser.setCurrentDirectory(file);
			this.mtblChooser.setFileFilter(new FileFilterEx(".mtbl","Message Table(.mtbl)"));
			int ret = this.mtblChooser.showSaveDialog(this);
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				File inputFile = this.mtblChooser.getSelectedFile();
				MTbl mtbl = new MTbl();
				mtbl.save(inputFile, this.mesTable);
			}	
		}
		else if(e.getSource() == saveTabTbl) 
		{
			File file = new File(set.getDefaultDirectory());
			this.mtblChooser.setCurrentDirectory(file);
			this.mtblChooser.setFileFilter(new FileFilterEx(".ttbl","Tag Table(.ttbl)"));
			int ret = this.mtblChooser.showSaveDialog(this);
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				File inputFile = this.mtblChooser.getSelectedFile();
				TTbl ttbl = new TTbl();
				ttbl.open(inputFile, this.tagTable);
			}
		}
		else if(e.getSource() == output)
		{
			File file = new File(set.getDefaultDirectory());
			this.outputChooser.setCurrentDirectory(file);
			this.outputChooser.setFileFilter(null);
			int ret = this.outputChooser.showSaveDialog(this);
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				File outFile = this.outputChooser.getSelectedFile();
				OutPuter outPuter = new OutPuter();
				outPuter.outPut(outFile, this.tagTable, this.mesTable, this.checkParam.getOutFileFlag(), this.checkParam.getCaraCodeFlag());
			}
		}
		else if(e.getSource() == this.defaulttDirectory)
		{
			int ret = this.directoryChooser.showOpenDialog(this);
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				File file = this.directoryChooser.getSelectedFile();
				this.set.setDefaultDirectory(file.getPath());
			}
		}
		else if(e.getSource() == this.reset)
		{
			this.set.reset();
		}
//		// Character Size
//		else if(e.getSource() == )
//		{
//			this.charDialog.show();
//		}
		// Language Change
		else
		{
			if(this.selectMenu == this.language) {
				LanguageChange(e.getSource());
			}
			else if(this.selectMenu == this.defaultLanguage) {
				int languageNo = this.getLanguageNo(e.getSource());
				this.set.setDefaultLanguage(languageNo);
			}
		}
	}
	
	private int getLanguageNo(Object src)
	{
			 if(src == jpn || src == setting_jpn){return MesTableDefine.Language_JPN; }
		else if(src == eng || src == setting_eng){return MesTableDefine.Language_ENG; }
		else if(src == deu || src == setting_deu){return MesTableDefine.Language_DEU; }
		else if(src == fra || src == setting_fra){return MesTableDefine.Language_FRA; }
		else if(src == ita || src == setting_ita){return MesTableDefine.Language_ITA; }
		else if(src == spa || src == setting_spa){return MesTableDefine.Language_SPA; }
		else if(src == rus || src == setting_rus){return MesTableDefine.Language_RUS; }
		else if(src == tha || src == setting_tha){return MesTableDefine.Language_THA; }
		return MesTableDefine.Language_ENG;
	}
	
	private void LanguageChange(Object src)
	{
		mesman.setLanguageNo(this.getLanguageNo(src));
		

		file.setText(mesman.getMessage(MesTableDefine.mes_file));
		setting.setText(mesman.getMessage(MesTableDefine.mes_setting));
		language.setText(mesman.getMessage(MesTableDefine.mes_language));
		help.setText(mesman.getMessage(MesTableDefine.mes_help));
		tools.setText(mesman.getMessage(MesTableDefine.mes_tools));
		
		open.setText(mesman.getMessage(MesTableDefine.mes_open));
		openMesTbl.setText(mesman.getMessage(MesTableDefine.mes_mtbl));
		openTabTbl.setText(mesman.getMessage(MesTableDefine.mes_ttbl));
		
		save.setText(mesman.getMessage(MesTableDefine.mes_save));
		saveMesTbl.setText(mesman.getMessage(MesTableDefine.mes_mtbl));
		saveTabTbl.setText(mesman.getMessage(MesTableDefine.mes_ttbl));

		output.setText(mesman.getMessage(MesTableDefine.mes_output));
		
		mesTable.LanguageChange();
		tagTable.LanguageChange();
	}

	public void menuCanceled(MenuEvent arg0) {}
	public void menuDeselected(MenuEvent arg0) {}
	public void menuSelected(MenuEvent arg0) {
		this.selectMenu = arg0.getSource();
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowClosing(WindowEvent arg0) {
		// 終了時処理
		this.set.save();
	}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}
