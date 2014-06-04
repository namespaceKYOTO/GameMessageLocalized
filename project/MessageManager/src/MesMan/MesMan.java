/*---------------------------------------------------------------------*/
/*!
 * @brief	Message Manager
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MesMan extends JFrame implements ActionListener
{
	private static MessageManager mesman = null;
	private MesTable mesTable;
	private TagTable tagTable;
	private CheckParamPanel checkParam;
	private JFileChooser mtblChooser;
	private JFileChooser outputChooser;
	
	private String FRAME_TITLE = "Message Manager";
	private String CHAR_SIZE = "Character Size";
	
	JMenu file;
	JMenu setting;
	JMenu language;
	JMenu help;
	JMenu tools;
	JMenu open;
	JMenu save;
	
	private JMenuItem openMesTbl = null;
	private JMenuItem openTabTbl = null;
	private JMenuItem saveMesTbl = null;
	private JMenuItem saveTabTbl = null;
	private JMenuItem output = null;

	private JMenuItem jpn = null;
	private JMenuItem eng = null;
	private JMenuItem deu = null;
	private JMenuItem fra = null;
	private JMenuItem ita = null;
	private JMenuItem spa = null;
	
	private CharacterDialog charDialog;
	
	public static void main(String[] args)
	{
		for(String arg : args)
		{
			System.out.println(arg);
		}
		mesman = new MessageManager("res/MesTableDefine.bin");
		mesman.setLanguageNo(MesTableDefine.Language_ENG);
		for(int i = MesTableDefine.mes_file; i <= MesTableDefine.mes_SPA; ++i ) {
			System.out.println(mesman.getMessage(i));
		}
		MesMan mesMan = new MesMan();
	}
	
	public MesMan()
	{
		setTitle(FRAME_TITLE);
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rec = env.getMaximumWindowBounds();
		int width = (int)rec.getWidth() / 2;
		int height = (int)rec.getHeight() / 2;
		int x = ((int)rec.getWidth() / 2) - (width / 2);
		int y = ((int)rec.getHeight() / 2) - (height / 2);
		setBounds( x, y, width, height );		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// menu bar
		JMenuBar menuBar = new JMenuBar();
		file = new JMenu(mesman.getMessage(MesTableDefine.mes_file));
		setting = new JMenu(mesman.getMessage(MesTableDefine.mes_setting));
		language = new JMenu(mesman.getMessage(MesTableDefine.mes_language));
		help = new JMenu(mesman.getMessage(MesTableDefine.mes_help));
		tools = new JMenu(mesman.getMessage(MesTableDefine.mes_tools));
		
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
		// CharSize
		{
		}
		
		// Language
		{
			jpn = new JMenuItem(mesman.getMessage(MesTableDefine.mes_JPN));
			eng = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ENG));
			deu = new JMenuItem(mesman.getMessage(MesTableDefine.mes_DEU));
			fra = new JMenuItem(mesman.getMessage(MesTableDefine.mes_FRA));
			ita = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ITA));
			spa = new JMenuItem(mesman.getMessage(MesTableDefine.mes_SPA));
			jpn.addActionListener(this);
			eng.addActionListener(this);
			deu.addActionListener(this);
			fra.addActionListener(this);
			ita.addActionListener(this);
			spa.addActionListener(this);
			language.add(jpn);
			language.add(eng);
			language.add(deu);
			language.add(fra);
			language.add(ita);
			language.add(spa);
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
		
		setJMenuBar(menuBar);
		
		setVisible(true);
		
		// size
		Dimension size = getContentPane().getSize();
		width = size.width;
		height = size.height;
		System.out.println(String.format("Dimension ** width : %d, height : %d", size.width, size.height));
		
		// create message table
		String[] languages = {"Label", "Description", "JPN", "ENG", "DEU", "FRA", "ITA", "SPA"};
		this.mesTable = new MesTable(mesman, languages, (size.width / 3) * 2 - 10, size.height);
		
		// create tab table
		String[] tag = {"Tag Name", "Description", "Code"};
		this.tagTable = new TagTable(mesman, tag, (size.width / 3) * 1 - 10, size.height);
		
		BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
		setLayout(layout);
		add(this.tagTable.getPanel());
		add(this.mesTable.getPanel());
		
		// file Chooser
		this.checkParam = new CheckParamPanel();
		this.outputChooser = new JFileChooser();
		this.outputChooser.setAccessory(this.checkParam.getPanel());
		
		this.mtblChooser = new JFileChooser();
		
		this.charDialog = new CharacterDialog(this, mesman, x, y, width, height);
		
		getContentPane().validate();
		
		// resize listener
		addComponentListener(new MyComponentListener(this.mesTable, this.tagTable));
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	invoked when an action occurs
	/*---------------------------------------------------------------------*/
	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.paramString());
		
		if(e.getSource() == openMesTbl)
		{
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
			this.outputChooser.setFileFilter(null);
			int ret = this.outputChooser.showSaveDialog(this);
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				File outFile = this.outputChooser.getSelectedFile();
				OutPuter outPuter = new OutPuter();
				outPuter.outPut(outFile, this.tagTable, this.mesTable, this.checkParam.getOutFileFlag(), this.checkParam.getCaraCodeFlag());
			}
		}
//		// Character Size
//		else if(e.getSource() == )
//		{
//			this.charDialog.show();
//		}
		// Language Change
		else
		{
			LanguageChange(e.getSource());
		}
	}
	
	private void LanguageChange(Object src)
	{
		int languageNo = 0;
			 if(src == jpn){languageNo = MesTableDefine.Language_JPN; }
		else if(src == eng){languageNo = MesTableDefine.Language_ENG; }
		else if(src == deu){languageNo = MesTableDefine.Language_DEU; }
		else if(src == fra){languageNo = MesTableDefine.Language_FRA; }
		else if(src == ita){languageNo = MesTableDefine.Language_ITA; }
		else if(src == spa){languageNo = MesTableDefine.Language_SPA; }
		 
		mesman.setLanguageNo(languageNo);
		

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
}
