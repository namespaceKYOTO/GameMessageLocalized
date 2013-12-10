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
	
	public static void main(String[] args)
	{
		MesMan mesMan = new MesMan();
		
		mesMan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mesMan.setVisible(true);
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
		
		// create message table
		this.mesTable = new MesTable((width / 3) * 2 - 10, height);
		
		// create tab table
		this.tagTable = new TagTable((width / 3) * 1 - 10, height);
		
		BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
		setLayout(layout);
		add(this.tagTable.getPanel());
		add(this.mesTable.getPanel());
		
		// menu bar 
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu help = new JMenu("Help");
		
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
		this.mtblChooser.setFileFilter(new MTblFilter());
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	invoked when an action occurs
	/*---------------------------------------------------------------------*/
	public void actionPerformed(ActionEvent e)
	{
		System.out.println(e.paramString());
		if(e.getActionCommand().equals(TABLE_OPEN))
		{
			int ret = this.mtblChooser.showOpenDialog(this);
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				System.out.println(TABLE_OPEN);
				File inputFile = this.mtblChooser.getSelectedFile();
				MTbl mtbl = new MTbl();
				mtbl.open(inputFile, this.mesTable);
			}
		}
		else if(e.getActionCommand().equals(TABLE_SAVE))
		{
			int ret = this.mtblChooser.showSaveDialog(this);
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				System.out.println(TABLE_SAVE);
				File inputFile = this.mtblChooser.getSelectedFile();
			}
		}
		else if(e.getActionCommand().equals(OUTPUT))
		{
			this.outputChooser.setFileFilter(null);
			int ret = this.outputChooser.showSaveDialog(this);
			if(ret == JFileChooser.APPROVE_OPTION)
			{
				System.out.println(OUTPUT);
				File outFile = this.outputChooser.getSelectedFile();
				OutPuter outPuter = new OutPuter();
				outPuter.outPut(outFile, this.tagTable.getRow(), this.mesTable.getRow());
			}
		}
	}
}
