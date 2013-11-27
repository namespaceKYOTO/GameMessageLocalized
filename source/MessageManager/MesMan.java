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
	
	private Stack<Stack<String>> tagRow;
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
		
		// create message table
		this.row = new Stack<Stack<String>>();
		this.columnName = new Stack<String>();
		for(String str : this.country)
		{
			this.columnName.push(str);
			this.row.push(new Stack<String>());
		}
		Dimension preferredMesTable = new Dimension((width / 3) * 2 - 10, height);
		MesTable mesTable = new MesTable(this.row, this.columnName);
		mesTable.setPreferredSize(preferredMesTable);
		
		JScrollPane pane = new JScrollPane(mesTable);
		pane.setPreferredSize(preferredMesTable);
		
		JPanel panel = new JPanel();
		panel.add(pane);
		panel.setPreferredSize(preferredMesTable);
		
		// create tag table
		this.tagRow = new Stack<Stack<String>>();
		this.tagColumnName = new Stack<String>();
		this.tagRow.push(new Stack<String>());
		this.tagColumnName.push("Tag Name");
		this.tagColumnName.push("Description");
		Dimension preferredTagTable = new Dimension((width / 3) * 1 - 10, height);
		TagTable tagTable = new TagTable(this.tagRow, this.tagColumnName);
		tagTable.setPreferredSize(preferredTagTable);
		
		JScrollPane tagPane = new JScrollPane(tagTable);
		tagPane.setPreferredSize(preferredTagTable);
		
		JPanel tagPanel = new JPanel();
		tagPanel.add(tagPane);
		tagPanel.setPreferredSize(preferredTagTable);
		
		//getContentPane().add(panel, BorderLayout.CENTER);
		//getContentPane().add(tagPanel, BorderLayout.WEST);
		BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.X_AXIS);
		setLayout(layout);
		add(tagPanel);
		add(panel);
		//FlowLayout layout = new FlowLayout(/*FlowLayout.LEFT*/);
		//layout.addLayoutComponent("TagPanel", tagPanel);
		//layout.addLayoutComponent("MessagPanel", panel);
		//setLayout(layout);
		//tagPanel.setLayout(layout);
		//panel.setLayout(layout);
		//add(tagPanel);
		//add(panel);
		
		
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
