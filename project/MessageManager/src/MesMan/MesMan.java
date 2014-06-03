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
	
	private JMenuItem openMesTbl = null;
	private JMenuItem openTabTbl = null;
	private JMenuItem saveMesTbl = null;
	private JMenuItem saveTabTbl = null;
	private JMenuItem output = null;
	
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
		JMenu file = new JMenu(mesman.getMessage(MesTableDefine.mes_file));
		JMenu setting = new JMenu(mesman.getMessage(MesTableDefine.mes_setting));
		JMenu language = new JMenu(mesman.getMessage(MesTableDefine.mes_language));
		JMenu help = new JMenu(mesman.getMessage(MesTableDefine.mes_help));
		JMenu tools = new JMenu(mesman.getMessage(MesTableDefine.mes_tools));
		
		//// File
		// Open
		{
			JMenu menu = new JMenu(mesman.getMessage(MesTableDefine.mes_open));
			openMesTbl = new JMenuItem(mesman.getMessage(MesTableDefine.mes_mtbl));
			openTabTbl = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ttbl));
			openMesTbl.addActionListener(this);
			openTabTbl.addActionListener(this);
			menu.add(openMesTbl);
			menu.add(openTabTbl);
			file.add(menu);
		}
		
		// Save
		{
			JMenu menu = new JMenu(mesman.getMessage(MesTableDefine.mes_save));
			saveMesTbl = new JMenuItem(mesman.getMessage(MesTableDefine.mes_mtbl));
			saveTabTbl = new JMenuItem(mesman.getMessage(MesTableDefine.mes_ttbl));
			saveMesTbl.addActionListener(this);
			saveTabTbl.addActionListener(this);
			menu.add(saveMesTbl);
			menu.add(saveTabTbl);
			file.add(menu);
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
		}
		
		// tools
		{
			JMenuItem item = new JMenuItem(CHAR_SIZE);
			item.addActionListener(this);
			tools.add(item);
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
	}
}
