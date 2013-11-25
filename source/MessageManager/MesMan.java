/*---------------------------------------------------------------------*/
/*!
 * @brief	Message Manager
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.lang.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class MesMan extends JFrame
{
	// ISO 3166-1
	private String[] country = {"Label", "JPN", "USA", "GBR", "DEU", "FRA", "ITA", "ESP"};
	private Stack<Stack<String>> row;
	private Stack<String> columnName;
	
	private Stack<String> tagRow;
	private Stack<String> tagColumnName;
	
	public static void main(String[] args)
	{
		MesMan mesMan = new MesMan();
		
		mesMan.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mesMan.setVisible(true);
	}
	
	public MesMan()
	{
		setTitle("Message Manager");
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rec = env.getMaximumWindowBounds();
		int width = (int)rec.getWidth() / 2;
		int height = (int)rec.getHeight() / 2;
		int x = ((int)rec.getWidth() / 2) - (width / 2);
		int y = ((int)rec.getHeight() / 2) - (height / 2);
		setBounds( x, y, width, height );
		
		// create list table
		//JTable table = new JTable(this.row, this.columnName);
		this.row = new Stack<Stack<String>>();
		this.columnName = new Stack<String>();
		for(String str : this.country)
		{
			this.columnName.push(str);
			this.row.push(new Stack<String>());
		}
		MesTable mesTable = new MesTable(this.row, this.columnName);
		
		// 
		JScrollPane pane = new JScrollPane(mesTable);
		
		JPanel panel = new JPanel();
		panel.add(pane);
		
		this.tagRow = new Stack<String>();
		this.tagColumnName = new Stack<String>();
		this.tagColumnName.push("Tag Name");
		TagTable tagTable = new TagTable(this.tagRow, this.tagColumnName);
		
		JScrollPane tagPane = new JScrollPane(tagTable);
		
		JPanel tagPanel = new JPanel();
		panel.add(tagPane);
		
		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(tagPanel, BorderLayout.WEST);
		
		
		// menu bar 
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu help = new JMenu("Help");
		
		file.add(new OutPutItem());
		
		menuBar.add(file);
		menuBar.add(help);
		
		setJMenuBar(menuBar);
	}
}
