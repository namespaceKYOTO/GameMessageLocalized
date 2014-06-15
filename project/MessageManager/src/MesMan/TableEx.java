/*---------------------------------------------------------------------*/
/*!
 * @brief	Message Table
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableColumnModel;

public class TableEx implements MouseListener, ActionListener, MenuListener
{
	MessageManager messageMan;
	
	private Stack<Stack<String>> row;
	private Stack<String> columnName;
	
	private String POPUP_NUM_1 = "1";
	private String POPUP_NUM_10 = "10";
	private String POPUP_NUM_100 = "100";
	
	private JPanel				panel;
	private JScrollPane			scrollPane;
	private JTable				table;
	private DefaultTableModel	tableModel;
	private JPopupMenu			popup;
	private JMenu				addItem;
	private JMenu				insertItem;
	private JMenuItem			removeItem;
	private JMenuItem			addColumn;
	private JMenuItem			removeColumn;
	private Object				selectMenu;
	
	protected int SIZE_OFFSET_X = 10;
	protected int SIZE_OFFSET_Y = 40;
	
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public TableEx(MessageManager messageMan, String[] columns, int width, int height)
	{
		this.messageMan = messageMan;
		this.row = new Stack<Stack<String>>();
		this.columnName = new Stack<String>();
		this.selectMenu = null;
		for(String str : columns)
		{
			this.columnName.push(str);
		}
		
		Dimension preferredSize = new Dimension(width - this.SIZE_OFFSET_X, height - this.SIZE_OFFSET_Y);
		this.tableModel = new DefaultTableModel(this.row, this.columnName);
		this.tableModel.addRow(new Stack<String>());
		this.table = new JTable(this.tableModel);
		//this.table.setPreferredSize(preferredMesTable));
		this.table.setSize(preferredSize);
		this.table.addMouseListener(this);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		this.scrollPane = new JScrollPane(this.table);
		this.scrollPane.setPreferredSize(preferredSize);
		
		this.panel = new JPanel();
		this.panel.add(this.scrollPane);
		//this.panel.setPreferredSize(preferredMesTable);
//		this.panel.setSize(width, height);
		
		this.addItem = new JMenu(messageMan.getMessage(MesTableDefine.mes_add_row));
		this.insertItem = new JMenu(messageMan.getMessage(MesTableDefine.mes_intert_row));
		this.removeItem = new JMenuItem(messageMan.getMessage(MesTableDefine.mes_remove_row));
		this.addColumn = new JMenuItem(messageMan.getMessage(MesTableDefine.mes_add_column));
		this.removeColumn = new JMenuItem(messageMan.getMessage(MesTableDefine.mes_remove_column));
		this.addItem.addActionListener(this);
		this.insertItem.addActionListener(this);
		this.removeItem.addActionListener(this);
		this.addColumn.addActionListener(this);
		this.removeColumn.addActionListener(this);
		this.popup = new JPopupMenu();
		this.popup.add(this.addItem);
		this.popup.add(this.insertItem);
		this.popup.add(removeItem);
		this.popup.add(addColumn);
		this.popup.add(removeColumn);
		
		// add,intert popup menu
		{
			JMenuItem Item1 = new JMenuItem(POPUP_NUM_1);
			JMenuItem Item10 = new JMenuItem(POPUP_NUM_10);
			JMenuItem Item100 = new JMenuItem(POPUP_NUM_100);
			Item1.addActionListener(this);
			Item10.addActionListener(this);
			Item100.addActionListener(this);
			this.addItem.add(Item1);
			this.addItem.add(Item10);
			this.addItem.add(Item100);
			this.addItem.addMenuListener(this);
		}
		{
			JMenuItem Item1 = new JMenuItem(POPUP_NUM_1);
			JMenuItem Item10 = new JMenuItem(POPUP_NUM_10);
			JMenuItem Item100 = new JMenuItem(POPUP_NUM_100);
			Item1.addActionListener(this);
			Item10.addActionListener(this);
			Item100.addActionListener(this);
			this.insertItem.add(Item1);
			this.insertItem.add(Item10);
			this.insertItem.add(Item100);
			this.insertItem.addMenuListener(this);
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	getter
	/*---------------------------------------------------------------------*/
	public JPanel getPanel()
	{
		return this.panel;
	}
	
	public JTable getTable()
	{
		return this.table;
	}
	
	public DefaultTableModel getTableModel()
	{
		return this.tableModel;
	}
	
	public Stack<String> getColumnName()
	{
		return this.columnName;
	}
	
	public Stack<Stack<String>> getRow()
	{
		return this.row;
	}
	
	public int getColumnIndex(String column)
	{
		return this.columnName.size() - this.columnName.search(column);
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	Mouse event
	/*---------------------------------------------------------------------*/
	public void mousePressed(MouseEvent e)
	{
		if(e.isPopupTrigger())
		{
			this.popup.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		if(e.isPopupTrigger())
		{
			this.popup.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
	/*---------------------------------------------------------------------*/
	//*!brief	action event
	/*---------------------------------------------------------------------*/
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
//		System.out.println("Command : " + command);
		Object source = e.getSource();
		
		// remove row
		if(source == removeItem)
		{
			//int selectedRow = this.table.getSelectedRow();
			//if(selectedRow != -1)
			int[] selectedRows = this.table.getSelectedRows();
			if(selectedRows.length > 0)
			{
				int removeCount = 0;
				for(int i = 0; i < selectedRows.length; ++i)
				{
					this.tableModel.removeRow(selectedRows[i] + removeCount);
					--removeCount;
				}
				if(this.tableModel.getRowCount() == 0)
				{
					this.addRow(1);
				}
				int rc = this.tableModel.getRowCount() - 1;
				this.table.scrollRectToVisible(this.table.getCellRect(rc, 0, true));
			}
		}
		// add column
		else if(source == this.addColumn)
		{
			JOptionPane pane = new JOptionPane("add column name", JOptionPane.YES_OPTION);
			String input = pane.showInputDialog("add column name");
			if(input.length() > 0)
			{
				this.tableModel.addColumn(input);
			}
		}
		// remove column
		else if(source == this.removeColumn)
		{
			JOptionPane pane = new JOptionPane("remove column name", JOptionPane.YES_OPTION);
			String input = pane.showInputDialog("remove column name");
			DefaultTableColumnModel model = (DefaultTableColumnModel)this.table.getColumnModel();
			try
			{
				model.removeColumn(model.getColumn(model.getColumnIndex(input)));
			}
			catch(IllegalArgumentException exce)
			{
				System.out.println("not found column");
			}
		}
		// add, insert 1
		else if(command.equals(POPUP_NUM_1) || command.equals(POPUP_NUM_10) || command.equals(POPUP_NUM_100))
		{
			if(this.selectMenu == this.addItem)
			{
				this.addRow(Integer.valueOf(command).intValue());
				this.selectMenu = null;
			}
			else if(this.selectMenu == this.insertItem)
			{
				this.insertRow(Integer.valueOf(command).intValue());
				this.selectMenu = null;
			}
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	action event
	/*---------------------------------------------------------------------*/
	public void menuCanceled(MenuEvent e){}
	public void menuDeselected(MenuEvent e){}
	
	public void menuSelected(MenuEvent e)
	{
		System.out.println("Menu Selected");
		if(e.getSource() == this.addItem)
		{
			this.selectMenu = this.addItem;
		}
		else if(e.getSource() == this.insertItem)
		{
			this.selectMenu = this.insertItem;
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	resize
	/*---------------------------------------------------------------------*/
	public void resize(Dimension dimension)
	{
		Dimension tabelDimension = new Dimension(dimension.width - this.SIZE_OFFSET_X, dimension.height - this.SIZE_OFFSET_Y);
		System.out.println(String.format("Resize width : %d, height : %d", dimension.width, dimension.height));
		
//		this.table.setPreferredSize(tabelDimension);
		this.scrollPane.setPreferredSize(tabelDimension);
//		this.panel.setPreferredSize(dimension);
		
		this.table.setSize(tabelDimension);
		this.scrollPane.setSize(tabelDimension);
		this.panel.setSize(dimension);
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	add row
	/*---------------------------------------------------------------------*/
	public void addRow(int num)
	{
		for(int i = 0; i < num; ++i)
		{
			this.tableModel.addRow(new Stack<String>());
		}
		int rc = this.tableModel.getRowCount();
		this.table.scrollRectToVisible(this.table.getCellRect(rc, 0, true));
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	insert row
	/*---------------------------------------------------------------------*/
	public void insertRow(int num)
	{
		int selectedRow = this.table.getSelectedRow();
		if(selectedRow != -1)
		{
			for(int i = 0; i < num; ++i)
			{
				this.tableModel.insertRow(selectedRow, new Stack<String>());
			}
			int rc = this.tableModel.getRowCount();
			this.table.scrollRectToVisible(this.table.getCellRect(rc, 0, true));
		}
		else
		{
			System.out.println("not select row");
		}
	}
	
	/**
	 * 言語設定
	 */
	public void LanguageChange()
	{
		addItem.setText(messageMan.getMessage(MesTableDefine.mes_add_row));
		insertItem.setText(messageMan.getMessage(MesTableDefine.mes_intert_row));
		removeItem.setText(messageMan.getMessage(MesTableDefine.mes_remove_row));
		addColumn.setText(messageMan.getMessage(MesTableDefine.mes_add_column));
		removeColumn.setText(messageMan.getMessage(MesTableDefine.mes_remove_column));
	}
}
