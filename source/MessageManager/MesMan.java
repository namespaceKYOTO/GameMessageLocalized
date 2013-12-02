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
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableColumn;

public class MesMan extends JFrame implements ActionListener
{
	private MesTable mesTable;
	private TagTable tagTable;
	private CheckParamPanel checkParam;
	private JFileChooser fileChooser;
	
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
		
		JMenuItem outFile = new JMenuItem("Out File");
		outFile.addActionListener(this);
		file.add(outFile);
		
		menuBar.add(file);
		menuBar.add(help);
		
		setJMenuBar(menuBar);
		
		// file Chooser
		this.checkParam = new CheckParamPanel();
		this.fileChooser = new JFileChooser();
		this.fileChooser.setAccessory(this.checkParam.getPanel());
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	invoked when an action occurs
	/*---------------------------------------------------------------------*/
	public void actionPerformed(ActionEvent e)
	{
		//if(e.getActionCommand().equal(JButton.PRESSED_ICON_CHANGED_PROPERTY))
		{
			System.out.println("Click Button");
//			this.mesTable.outFile();
			this.fileChooser.showSaveDialog(this);
		}
	}
}
