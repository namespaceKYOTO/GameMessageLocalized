package MesMan;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableColumnModel;

/**
 * テーブルクラス.
 * @author t_sato
 *
 */
public class TableEx implements MouseListener, ActionListener, MenuListener
{
	MessageManager messageMan;
	
	private Stack<Stack<String>>	row;
	private Stack<String>			columnName;
	private Stack<String>			notDeleteColumn;
	
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
	
	protected JPopupMenu getPopup() {
		return this.popup;
	}
	
	/**
	 * 初期化.
	 * @param messageMan メッセージ管理
	 * @param width UI幅
	 * @param height UI高さ
	 */
	private void init(MessageManager messageMan, int width, int height)
	{
		this.messageMan = messageMan;
		this.row = new Stack<Stack<String>>();
		this.columnName = new Stack<String>();
		this.notDeleteColumn = new Stack<String>();
		this.selectMenu = null;
		
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
	
	/**
	 * コンストラクタ.
	 * @param messageMan メッセージ管理
	 * @param width UI幅
	 * @param height UI高さ
	 */
	public TableEx(MessageManager messageMan, int width, int height)
	{
		init( messageMan, width, height);
	}
	
	/**
	 * コンストラクタ.
	 * @param messageMan メッセージ管理
	 * @param columns 列文字列
	 * @param width UI幅
	 * @param height UI高さ
	 */
	public TableEx(MessageManager messageMan, String[] columns, int width, int height)
	{
		init( messageMan, width, height);
		addColumnNames(columns);
	}
	
	/**
	 * 列追加.
	 * @param str 列名
	 */
	protected void addColumnName(String str)
	{
		this.tableModel.addColumn(str);
	}
	
	/**
	 * 列追加.
	 * @param strs　列名s
	 */
	protected void addColumnNames(String[] strs)
	{
		for (String string : strs) {
			addColumnName(string);
		}
	}
	
	/**
	 * すべての行削除.
	 */
	public void removeAll()
	{
		int length = this.row.size();
		for(int i = 0; i < length; ++i)
		{
			this.tableModel.removeRow(0);
		}
		if(this.tableModel.getRowCount() == 0)
		{
			this.addRow(1);
		}
		int rc = this.tableModel.getRowCount() - 1;
		this.table.scrollRectToVisible(this.table.getCellRect(rc, 0, true));
	}
	
	/**
	 * 行削除.
	 * @param row 削除する行番号の配列
	 */
	public void remove(int[] row)
	{
		int removeCount = 0;
		for(int i = 0; i < row.length; ++i)
		{
			this.tableModel.removeRow(row[i] + removeCount);
			--removeCount;
		}
		if(this.tableModel.getRowCount() == 0)
		{
			this.addRow(1);
		}
		int rc = this.tableModel.getRowCount() - 1;
		this.table.scrollRectToVisible(this.table.getCellRect(rc, 0, true));
	}
	
	/**
	 * パネル取得.
	 * @return パネル
	 */
	public JPanel getPanel()
	{
		return this.panel;
	}
	
	/**
	 * テーブル取得.
	 * @return テーブル
	 */
	public JTable getTable()
	{
		return this.table;
	}
	
	/**
	 * テーブルモデルの取得.
	 * @return テーブルモデル
	 */
	public DefaultTableModel getTableModel()
	{
		return this.tableModel;
	}
	
	/**
	 * 列の名前取得.
	 * @return 列の名前
	 */
	public Stack<String> getColumnName()
	{
		return this.columnName;
	}
	
	/**
	 * 行取得.
	 * @return 行
	 */
	public Stack<Stack<String>> getRow()
	{
		return this.row;
	}
	
	/**
	 * 指定した列名の列番号を取得.
	 * @param column 列名
	 * @return 列番号
	 */
	public int getColumnIndex(String column)
	{
		return this.columnName.size() - this.columnName.search(column);
	}
	
	/**
	 * 列数の取得.
	 * @return 列数
	 */
	public int getColumnNum()
	{
		return this.columnName.size();
	}
	
	/**
	 * 削除できない列の追加.
	 * @param str 列名
	 */
	public void addNotDeleteColumn(String str)
	{
		this.notDeleteColumn.push(str);
	}
	
	/**
	 * 指定列を削除できるようにする.
	 * @param str 列名
	 */
	public void removeNotDeleteColumn(String str)
	{
		this.notDeleteColumn.remove(str);
	}
	
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
	
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
//		System.out.println("Command : " + command);
		Object source = e.getSource();
		
		// remove row
		if(source == removeItem)
		{
			int[] selectedRows = this.table.getSelectedRows();
			if(selectedRows.length > 0)
			{
				this.remove(selectedRows);
			}
		}
		// add column
		else if(source == this.addColumn)
		{
			String input = JOptionPane.showInputDialog("add column name");
			if(input.length() > 0)
			{
				this.tableModel.addColumn(input);
			}
		}
		// remove column
		else if(source == this.removeColumn)
		{
			String input = JOptionPane.showInputDialog("remove column name");
			
			for (String str : notDeleteColumn) {
				if(str.equals(input)) { return; }
			}
			
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
	
	/**
	 * テーブルのリサイズ.
	 * @param dimension リサイズ時の大きさ
	 */
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
	
	/**
	 * 行の追加.
	 * @param num 追加行数
	 */
	public void addRow(int num)
	{
		for(int i = 0; i < num; ++i)
		{
			this.tableModel.addRow(new Stack<String>());
		}
		int rc = this.tableModel.getRowCount();
		this.table.scrollRectToVisible(this.table.getCellRect(rc, 0, true));
	}
	
	/**
	 * 行の挿入.
	 * @param num 挿入数
	 */
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
	 * UIの表示言語変更.
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
