/*---------------------------------------------------------------------*/
/*!
 * @brief	file output
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.lang.*;
import java.io.*;
import java.util.*;

public class OutPuter
{
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public OutPuter()
	{
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	file output
	/*---------------------------------------------------------------------*/
	public void outPut(File file, TagTable tagTable, MesTable mesTable, int outFileFlag, int charaCodeFlag)
	{
		String outPutBaseFileName = getOutputBaseFileName(file);
		
		// .bin
		if( (outFileFlag & CheckParamPanel.OUT_FILE_BIN) != 0x00 )
		{
			System.out.println("Output File : " + outPutBaseFileName);
			
			if((charaCodeFlag & CheckParamPanel.CHARA_CODE_UTF8) != 0x00)
			{
				OutputBinary(file.getParent(), getOutputFileName(outPutBaseFileName, "_UTF8", ".bin", charaCodeFlag), tagTable, mesTable, "UTF-8");
			}
			if((charaCodeFlag & CheckParamPanel.CHARA_CODE_UTF16LE) != 0x00)
			{
				OutputBinary(file.getParent(), getOutputFileName(outPutBaseFileName, "_UTF16LE", ".bin", charaCodeFlag), tagTable, mesTable, "UTF-16LE");
			}
			if((charaCodeFlag & CheckParamPanel.CHARA_CODE_UTF16BE) != 0x00)
			{
				OutputBinary(file.getParent(), getOutputFileName(outPutBaseFileName, "_UTF16BE", ".bin", charaCodeFlag), tagTable, mesTable, "UTF-16BE");
			}
		}
		
		// .c
		if( (outFileFlag & CheckParamPanel.OUT_FILE_C) != 0x00 )
		{
			String outPutCFileName = outPutBaseFileName  + ".c";
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	get Output File Name
	/*---------------------------------------------------------------------*/
	private String getOutputBaseFileName(File file)
	{
		System.out.println(file.getName());
		String ret = file.getName();
		
		// splits directory
		String[] split = ret.split("/");
		ret = split[split.length - 1];
		
		// splits suffix
		if(ret.endsWith(".bin"))
		{
			ret = ret.substring(0,ret.lastIndexOf(".bin"));
		}
		// splits suffix
		else if(ret.endsWith(".c"))
		{
			ret = ret.substring(0,ret.lastIndexOf(".c"));
		}
		
		ret = ret.replace(".","_");
		return ret;
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	get Output File Name
	/*---------------------------------------------------------------------*/
	private String getOutputFileName(String baseName, String charset, String suffix, int charCodeFlag)
	{
		int count = 0;
		while((charCodeFlag & 0xFFFFFFFF) != 0x00)
		{
			charCodeFlag = charCodeFlag & (charCodeFlag - 1);
			++count;
		}
		
		if(count <= 1)
		{
			return baseName + suffix;
		}
		else
		{
			return baseName + charset + suffix;
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	output file
	/*---------------------------------------------------------------------*/
	private void OutputBinary(String parent, String name, TagTable tagTable, MesTable mesTable, String charset)
	{
		try
		{
			File file = new File(parent, name);
			file.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(file);
			
			// out put
			for(Stack<String> row : mesTable.getRow())
			{
				for(String column : row)
				{
//					outputStream.write(column.getBytes(charset));
					outWrite(column, tagTable, charset);
				}
			}
			
			outputStream.flush();
			outputStream.close();
		}
		catch(IOException e)
		{
			System.out.println("Faild Output Message : " + e.getMessage());
		}
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	out write
	/*---------------------------------------------------------------------*/
	private byte[] outWrite(String str, TagTable tagTable, String charset)
	{
		String _str = str;
		Stack<Stack<String>> row = tagTable.getRow();
		ArrayList<Byte> stack = new ArrayList<Byte>();
		int count = 0;
		for(Stack<String> column : row )
		{
			int index = -1;
			if((index = _str.indexOf(column.indexOf(0))) != -1)
			{
				Byte[] tagCode = tagTable.getTagCode(count, charset);
				stack.add(tagCode);
//				String str_column = column.indexOf(0);
//				_str = _str.substring(index, index + str_column.length());
			}
			++count;
		}
		byte[] strByte = _str.getBytes(charset);
		for(byte b : strByte)
		{
			stack.add(new Byte(b));
		}
		
		byte[] ret;
		stack.toArray(ret);
		return ret;
	}
}
