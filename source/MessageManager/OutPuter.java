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
	static int LITTLE_ENDIAN = 1;
	static int BIG_ENDIAN = 2;

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
				outputBinary(file.getParent(), getOutputFileName(outPutBaseFileName, "_UTF8", ".bin", charaCodeFlag), tagTable, mesTable, "UTF-8");
			}
			if((charaCodeFlag & CheckParamPanel.CHARA_CODE_UTF16LE) != 0x00)
			{
				outputBinary(file.getParent(), getOutputFileName(outPutBaseFileName, "_UTF16LE", ".bin", charaCodeFlag), tagTable, mesTable, "UTF-16LE");
			}
			if((charaCodeFlag & CheckParamPanel.CHARA_CODE_UTF16BE) != 0x00)
			{
				outputBinary(file.getParent(), getOutputFileName(outPutBaseFileName, "_UTF16BE", ".bin", charaCodeFlag), tagTable, mesTable, "UTF-16BE");
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
		
		// To avoid naming fogged
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
	private void outputBinary(String parent, String name, TagTable tagTable, MesTable mesTable, String charset)
	{
		try
		{
			File file = new File(parent, name);
			file.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(file);
			byte[] newLine;
			
			// new line
			if(charset.equals("UTF-8") == true)
			{
				newLine = new byte[1];
				newLine[0] = 0;
			}
			else
			{
				newLine = new byte[2];
				newLine[0] = 0;
				newLine[1] = 0;
			}
			
			// signature (4Byte)
			byte[] signature = {0x54, 0x45, 0x58, 0x54};	// TEXT
			outputStream.write(signature);
			
			// creation date  YYYY MM DD (2Byte 1Byte 1Byte)
			Calendar rightNow = Calendar.getInstance();
			int year = rightNow.get(Calendar.YEAR);
			int month = rightNow.get(Calendar.MONTH) + 1;
			int date = rightNow.get(Calendar.DATE);
			System.out.println(String.format("Date : %d/%d/%d",year,month,date));
			outputStream.write(this.getByteData(year, 2, BIG_ENDIAN));
			outputStream.write(this.getByteData(month, 1, BIG_ENDIAN));
			outputStream.write(this.getByteData(date, 1, BIG_ENDIAN));
			//outputStream.write(year / 1000); year %= 1000;
			//outputStream.write(year / 100 ); year %= 100;
			//outputStream.write(year / 10  ); year %= 10;
			//outputStream.write(year / 1   );
			//outputStream.write(month / 10); month %= 10;
			//outputStream.write(month / 1 );
			//outputStream.write(date / 10); date %= 10;
			//outputStream.write(date / 1 );
			
			// Message Num (4Byte)
			byte[] messageNum = this.getByteData(mesTable.getRow().size(), 4, BIG_ENDIAN);
			outputStream.write(messageNum);
			
			// Language Num (4Byte)
			byte[] languageNum = this.getByteData(mesTable.getLanguageNum(), 4, BIG_ENDIAN);
			outputStream.write(languageNum);
			
			// Message Offset (Message Num * Language Num * 4Byte)
			int messageSize = 0;
			for(Stack<String> row : mesTable.getRow())
			{
				for(String column : row)
				{
					outputStream.write(this.getByteData(messageSize, 4, BIG_ENDIAN));
					byte[] message = this.outWrite(column, tagTable, charset);
					int size = message.length /*+ newLine.length()*/;
					messageSize += size;
				}
			}
			
			
			// Messsage
			for(Stack<String> row : mesTable.getRow())
			{
				for(String column : row)
				{
					outputStream.write(this.outWrite(column, tagTable, charset));
					//outputStream.write(newLine);	// Message End Code
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
		byte[] ret = null;
		
		try
		{
			// tag replace
			for(Stack<String> column : row )
			{
				int index = -1;
				String tagName = column.get(0);
				if(tagName != null && (index = _str.indexOf(tagName)) != -1)
				{
					// character befor of tag
					if(index != 0)
					{
						String preTagStr = _str.substring(0, index - 1);
						byte[] strByte = _str.getBytes(charset);
						for(byte b : strByte)
						{
							stack.add(new Byte(b));
						}
					}
					
					// tag 
					Byte[] tagCode = tagTable.getTagCode(count, charset);
					for(Byte code : tagCode)
					{
						stack.add(code);
					}
					
					// character after of tag
					_str = _str.substring(index + tagName.length());
				}
				++count;
			}
			if(_str.length() > 0)
			{
				byte[] strByte = _str.getBytes(charset);
				for(byte b : strByte)
				{
					stack.add(new Byte(b));
				}
			}
			
			Byte[] stackData = new Byte[stack.size()];
			stack.toArray(stackData);
			System.out.println("stack : " + stack.toString());
			
			int index = 0;
			ret = new byte[stack.size()];
			for(Byte data : stackData)
			{
				ret[index++] = data.byteValue();
			}
		}
		catch(UnsupportedEncodingException e)
		{
			System.out.println("Faild Output Message : " + e.getMessage());
		}
		return ret;
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	get byte data
	//*!note	Only in little endian still
	/*---------------------------------------------------------------------*/
	private byte[] getByteData(long value, int byteSize, int endian)
	{
		byte[] ret = new byte[byteSize];
		
		if(endian == LITTLE_ENDIAN)
		{
			for(int i = 0; i < byteSize; ++i) {
				ret[i] = (byte)(value & 0x00000000000000FF);
				value >>>= 8;
			}
		}
		else if(endian == BIG_ENDIAN)
		{
			for(int i = 1; i <= byteSize; ++i) {
				ret[i - 1] = (byte)(value & (0x00000000000000FF << ((byteSize - i) * 8)));
			}
		}
		
		return ret;
	}
}
