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
	private MesTable mesTable;
	private TagTable tagTable;
	private CheckParamPanel checkParam;
	private JFileChooser mtblChooser;
	private JFileChooser outputChooser;
	
	private String FRAME_TITLE = "Message Manager";
	
	private String MENU_FILE = "File";
	private String MENU_SETTING = "Setting";
	private String MENU_LANGUAGE = "Language";
	private String MENU_HELP = "Help";
	private String MENU_CHECK = "Check";
	
	private String OPEN = "Open";
	private String SAVE = "Save";
	private String OUTPUT = "Output";
	private String MES_TBL = "Message Table";
	private String TAG_TBL = "Tag Table";
	private String CHAR_SIZE = "Character Size";
	
	private JMenuItem openMesTbl = null;
	private JMenuItem openTabTbl = null;
	private JMenuItem saveMesTbl = null;
	private JMenuItem saveTabTbl = null;
	
	private CharacterDialog charDialog;
	
	public static void main(String[] args)
	{
		try
		{
			byte[] code = {0x00, 0x20};
			for(int i = 0; i < 0x0FFF; ++i) {
				System.out.println(new String(code, "UTF-16") + ",");
				
				++code[1];
				if(code[1] == 0) {
					++code[0];
				}
			}
		}
		catch(UnsupportedEncodingException e)
		{
			
		}
		
		System.out.println("== Args ==");
		for(String arg : args)
		{
			System.out.println(arg);
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
		JMenu file = new JMenu(MENU_FILE);
		JMenu setting = new JMenu(MENU_SETTING);
		JMenu language = new JMenu(MENU_LANGUAGE);
		JMenu help = new JMenu(MENU_HELP);
		JMenu check = new JMenu(MENU_CHECK);
		
		//// File
		// Open
		{
			JMenu menu = new JMenu(OPEN);
			openMesTbl = new JMenuItem(MES_TBL);
			openTabTbl = new JMenuItem(TAG_TBL);
			openMesTbl.addActionListener(this);
			openTabTbl.addActionListener(this);
			menu.add(openMesTbl);
			menu.add(openTabTbl);
			file.add(menu);
		}
		
		// Save
		{
			JMenu menu = new JMenu(SAVE);
			saveMesTbl = new JMenuItem(MES_TBL);
			saveTabTbl = new JMenuItem(TAG_TBL);
			saveMesTbl.addActionListener(this);
			saveTabTbl.addActionListener(this);
			menu.add(saveMesTbl);
			menu.add(saveTabTbl);
			file.add(menu);
		}
		
		// Output
		{
			JMenuItem item = new JMenuItem(OUTPUT);
			item.addActionListener(this);
			file.add(item);
		}
		
		//// Setting
		// CharSize
		{
			JMenuItem item = new JMenuItem(CHAR_SIZE);
			item.addActionListener(this);
			setting.add(item);
		}
		
		// Language
		{
		}
		
		menuBar.add(file);
		menuBar.add(setting);
		menuBar.add(language);
		menuBar.add(help);
		menuBar.add(check);
		
		setJMenuBar(menuBar);
		
		setVisible(true);
		
		// size
		Dimension size = getContentPane().getSize();
		width = size.width;
		height = size.height;
		System.out.println(String.format("Dimension ** width : %d, height : %d", size.width, size.height));
		
		// create message table
		String[] languages = {"Label", "Description", "JPN", "ENG", "DEU", "FRA", "ITA", "SPA"};
		this.mesTable = new MesTable(languages, (size.width / 3) * 2 - 10, size.height);
		
		// create tab table
		String[] tag = {"Tag Name", "Description", "Code"};
		this.tagTable = new TagTable(tag, (size.width / 3) * 1 - 10, size.height);
		
		BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
		setLayout(layout);
		add(this.tagTable.getPanel());
		add(this.mesTable.getPanel());
		
		// file Chooser
		this.checkParam = new CheckParamPanel();
		this.outputChooser = new JFileChooser();
		this.outputChooser.setAccessory(this.checkParam.getPanel());
		
		this.mtblChooser = new JFileChooser();
		
		this.charDialog = new CharacterDialog(this, x, y, width, height);
		
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
		
		if(e.getActionCommand().equals(MES_TBL))
		{
			Object source = e.getSource();
			String suffix = ".mtbl";
			this.mtblChooser.setFileFilter(new FileFilterEx(suffix,"Message Table(.mtbl)"));
			
			// Open
			if(openMesTbl == source) {
				int ret = this.mtblChooser.showOpenDialog(this);
				if(ret == JFileChooser.APPROVE_OPTION)
				{
					File inputFile = this.mtblChooser.getSelectedFile();
					MTbl mtbl = new MTbl();
					mtbl.open(inputFile, this.mesTable);
				}
			}
			// Save
			else if(saveMesTbl == source) {
				int ret = this.mtblChooser.showSaveDialog(this);
				if(ret == JFileChooser.APPROVE_OPTION)
				{
					File inputFile = this.mtblChooser.getSelectedFile();
					MTbl mtbl = new MTbl();
					mtbl.save(inputFile, this.mesTable);
				}
			}
		}
		else if(e.getActionCommand().equals(TAG_TBL))
		{
			Object source = e.getSource();
			String suffix = ".ttbl";
			this.mtblChooser.setFileFilter(new FileFilterEx(suffix,"Tag Table(.ttbl)"));
			
			// Open
			if(openTabTbl == source)
			{
				int ret = this.mtblChooser.showOpenDialog(this);
				if(ret == JFileChooser.APPROVE_OPTION)
				{
					File inputFile = this.mtblChooser.getSelectedFile();
					TTbl ttbl = new TTbl();
					ttbl.open(inputFile, this.tagTable);
				}
			}
			// Save
			else if(saveTabTbl == source)
			{
				int ret = this.mtblChooser.showSaveDialog(this);
				if(ret == JFileChooser.APPROVE_OPTION)
				{
					File inputFile = this.mtblChooser.getSelectedFile();
					TTbl ttbl = new TTbl();
					ttbl.open(inputFile, this.tagTable);
				}
			}
		}
		// Output Message Table
		else if(e.getActionCommand().equals(OUTPUT))
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
		// Character Size
		else if(e.getActionCommand().equals(CHAR_SIZE))
		{
			this.charDialog.show();
		}
	}
}
