package MesMan.Table;

import java.io.*;
import java.util.*;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * テーブルのファイル操作関係.
 * @author t_sato
 *
 */
public class TableFile
{
	static private String DELIMITER = "\t";
	static private String NEWLINE = "\r\n";
	
	private String suffix;
	private String charset;
	
	/**
	 * コンストラクタ.
	 * @param suffix 拡張子
	 * @param charset 文字コード
	 */
	public TableFile(String suffix, String charset)
	{
		this.suffix = suffix;
		this.charset = charset;
	}
	
	/**
	 * ファイルオープン.
	 * @param file オープンするファイル
	 * @param tableEx 読み込み結果格納先テーブル
	 */
	public void open(File file, TableEx tableEx)
	{
		FileInputStream fileStrem = null;
		try {
			fileStrem = new FileInputStream(file);
			open(new InputStreamReader(fileStrem, this.charset), tableEx);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ファイルオープン.
	 * @param isr オープンするファイル
	 * @param tableEx 読み込み結果格納先テーブル
	 */
	public void open(InputStreamReader isr, TableEx tableEx)
	{
		JTable table = tableEx.getTable();
		DefaultTableModel tableModel = tableEx.getTableModel();
		tableModel.setColumnCount(0);
		tableModel.setRowCount(0);
		
		try
		{
			BufferedReader bufferedReader = new BufferedReader(isr);
			
			String line;
			
			// Column comment
			line = bufferedReader.readLine();
			// Column
			line = bufferedReader.readLine();
			String[] column = line.split(DELIMITER);
			for(String val : column)
			{
				tableModel.addColumn(val);
			}
			
			// Row comment
			line = bufferedReader.readLine();
			// Row
			while((line = bufferedReader.readLine()) != null)
			{
				String[] rowData = line.split(DELIMITER);
				Stack<String>row = new Stack<String>();
				for(String val : rowData)
				{
					row.push(val);
				}
				tableModel.addRow(row);
			}
			
			// redisplay
			int rc = tableModel.getRowCount();
			table.scrollRectToVisible(table.getCellRect(rc, 0, false));
			
			bufferedReader.close();
		}
		catch(IOException e)
		{
			System.out.println("Failed File Open : " + e.getMessage());
		}
	}
	
	/**
	 * テーブル内容の保存.
	 * @param file 保存ファイル名
	 * @param table 保存テーブル
	 */
	public void save(File file, TableEx table)
	{
		try
		{
			if(file.getName().endsWith(this.suffix) == false)
			{
				File newFile = new File(file.getParent(), file.getName() + this.suffix);
				file = newFile;
			}
			file.createNewFile();
			PrintWriter pw = new PrintWriter(file, this.charset);
			
			// Column Comment
			pw.println("# Column Data");
			
			// Column
			for(int i = 0; i < table.getColumnName().size(); ++i)
			{
				pw.print(table.getColumnName().get(i));
				if(i + 1 < table.getColumnName().size())
				{
					pw.print(DELIMITER);
				}
			}
			pw.print(NEWLINE);
			
			// Row Comment
			pw.println("# Row Data");
			
			// Row
			Stack<Stack<String>> rowData = table.getRow();
			for(int row = 0; row < rowData.size(); ++row)
			{
				Stack<String> columnData = rowData.get(row);
				for(int column = 0; column < columnData.size(); ++column)
				{
					String str = columnData.get(column);
					if(str == null) str = "";
					pw.print(str);
					if(column + 1 < columnData.size())
					{
						pw.print(DELIMITER);
					}
				}
				pw.print(NEWLINE);
			}
			
			// finish
			pw.flush();
			pw.close();
		}
		catch(IOException e)
		{
			System.out.println("Failed File Save : " + e.getMessage());
		}
	}
}
