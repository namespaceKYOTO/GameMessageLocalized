/*---------------------------------------------------------------------*/
/*!
 * @brief	Message Manager
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.lang.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableColumn;

public class MesMan extends JFrame implements ActionListener
{
	private MesTable mesTable;
	private TagTable tagTable;
	private CheckParamPanel checkParam;
	private JFileChooser mtblChooser;
	private JFileChooser outputChooser;
	
	private String FRAME_TITLE = "Message Manager";
	
	private String TABLE_OPEN = "Table Open";
	private String TABLE_SAVE = "Table Save";
	private String OUTPUT = "Output";
	
	private String MENU_FILE = "File";
	private String MENU_HELP = "Help";
	
	public static void main(String[] args)
	{
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
		setVisible(true);
		
		// size
		Dimension size = getContentPane().getSize();
		width = size.width;
		height = size.height;
		System.out.println(String.format("Dimension ** width : %d, height : %d", size.width, size.height));
		
		// create message table
		this.mesTable = new MesTable((size.width / 3) * 2 - 10, size.height);
		
		// create tab table
		this.tagTable = new TagTable((size.width / 3) * 1 - 10, size.height);
		
		BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
		setLayout(layout);
		add(this.tagTable.getPanel());
		add(this.mesTable.getPanel());
		
		// menu bar 
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu(MENU_FILE);
		JMenu help = new JMenu(MENU_HELP);
		
		JMenuItem tableOpen = new JMenuItem(TABLE_OPEN);
		JMenuItem tableSave = new JMenuItem(TABLE_SAVE);
		JMenuItem outputFile = new JMenuItem(OUTPUT);
		tableOpen.addActionListener(this);
		tableSave.addActionListener(this);
		outputFile.addActionListener(this);
		file.add(tableOpen);
		file.add(tableSave);
		file.add(outputFile);
		
		menuBar.add(file);
		menuBar.add(help);
		
		setJMenuBar(menuBar);
		
		// file Chooser
		this.checkParam = new CheckParamPanel();
		this.outputChooser = new JFileChooser();
		this.outputChooser.setAccessory(this.checkParam.getPanel());
		
		this.mtblChooser = new JFileChooser();
		
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
		
		// Table Open
		if(e.getActionCommand().equals(TABLE_OPEN))
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
		// Table Save
		else if(e.getActionCommand().equals(TABLE_SAVE))
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
		// Output Message Table
		else if(e.getActionCommand().equals(OUTPUT))
		{
			this.outputChooser.setFileFilter(null);
			int ret = this.outputChooser.showSaveDialog(this);
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				System.out.println(OUTPUT);
				File outFile = this.outputChooser.getSelectedFile();
				OutPuter outPuter = new OutPuter();
				outPuter.outPut(outFile, this.tagTable, this.mesTable, this.checkParam.getOutFileFlag(), this.checkParam.getCaraCodeFlag());
			}
		}
	}
}
