/*---------------------------------------------------------------------*/
/*!
 * @brief	file output
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

import BtoC.BtoC;

public class OutPuter
{
	static int LITTLE_ENDIAN = 1;
	static int BIG_ENDIAN = 2;
	
	private static String LABEL = "Label";
	private static String DESCRIPTION = "Description";

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
		
		boolean binCreate = (outFileFlag & CheckParamPanel.OUT_FILE_BIN) != 0x00;
		boolean cCreate = (outFileFlag & CheckParamPanel.OUT_FILE_C) != 0x00;
		boolean javaCreate = (outFileFlag & CheckParamPanel.OUT_FILE_JAVA) != 0x00;
		String binFile = null;

		System.out.println(String.format("charaCodeFlag] 0x%x", charaCodeFlag));
		if( (outFileFlag & CheckParamPanel.OUT_FILE_BIN) != 0x00 ) {
			String charset[] = {
					"UTF-8",
					"UTF-16LE",
					"UTF-16BE",
			};
			int binFlag = 1;
			int count = 0;
			for (String string : charset) {
				// .bin
				if( binCreate && (charaCodeFlag & (binFlag<<count)) != 0 ) {
					binFile = getOutputFileName(outPutBaseFileName, "_"+string, ".bin", charaCodeFlag);
					outputBinary(file.getParent(), binFile, tagTable, mesTable, string);
				}
				
				// .c
				if( cCreate ) {
					String parent = file.getParent();
					if( binFile == null ) {
						binFile = outPutBaseFileName + ".bin";
						outputBinary(file.getParent(), binFile, tagTable, mesTable, "UTF-8");
					}
					String cFile = binFile.replace(".", "_");
					cFile = cFile.replace("-", "_");
					String args[] = {
						"-o",
						parent + "/" + cFile + ".c",	// Out C File Name
						"-d",
						parent + "/" + binFile,			// data File Name
					};
					BtoC.main(args);
					
					// tag file
					outputCTagFile(parent, cFile, mesTable);
					
				}

				// .java
				if( javaCreate ) {
					String parent = file.getParent();
					if( binFile == null ) {
						binFile = outPutBaseFileName;
					}
					String javaFileName = outPutBaseFileName.replace(".", "_");
					javaFileName = javaFileName.replace("-", "_");
					outputJavaFile(parent, javaFileName, mesTable);
				}

				++count;
				binFile = null;
			}
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
		// To avoid naming fogged
		if( ((charCodeFlag-1) & charCodeFlag) == 0x00 )
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
			
			// Message Num (4Byte)
			byte[] messageNum = this.getByteData(mesTable.getRow().size(), 4, BIG_ENDIAN);
			outputStream.write(messageNum);
			
			// Language Num (4Byte)
			byte[] languageNum = this.getByteData(mesTable.getLanguageNum(), 4, BIG_ENDIAN);
			outputStream.write(languageNum);
			
			// charset
			int charsetNo = 0;
			if(charset.equals("UTF-8"))			{ charsetNo = 0; }
			else if(charset.equals("UTF-16BE"))	{ charsetNo = 1; }
			else if(charset.equals("UTF-16LE"))	{ charsetNo = 2; }
			byte[] charsetByte = this.getByteData(charsetNo, 4, BIG_ENDIAN);
			outputStream.write(charsetByte);
			
			// Message Offset (Message Num * Language Num * 4Byte)
//			for(Stack<String> row : mesTable.getRow())
//			{
//				for(String column : row)
//				{
//					if(columnCount != labelIdx && columnCount != descIdx)
//					{
//						byte[] mesOffset = this.getByteData(messageOffset, 4, BIG_ENDIAN);
//						outputStream.write(mesOffset);
//						outputStream.flush();
//						byte[] message = this.outWrite(column, tagTable, charset);
//						System.out.println(column +  String.format(" %d : %01x %01x %01x %01x", message.length, mesOffset[0], mesOffset[1], mesOffset[2], mesOffset[3]));
//						int size = message.length /*+ newLine.length()*/;
//						messageOffset += size;
//					}
//					++columnCount;
//				}
//				columnCount = 0;
//			}
			outputMessageOffset( outputStream, tagTable, mesTable, charset);
			
			// Messsage
//			columnCount = 0;
//			for(Stack<String> row : mesTable.getRow())
//			{
//				for(String column : row)
//				{
//					if(columnCount != labelIdx && columnCount != descIdx)
//					{
//						outputStream.write(this.outWrite(column, tagTable, charset));
//						//outputStream.write(newLine);	// Message End Code
//						outputStream.flush();
//					}
//					++columnCount;
//				}
//				columnCount = 0;
//			}
			outputMessage( outputStream, tagTable, mesTable, charset);
			
			outputStream.flush();
			outputStream.close();
		}
		catch(IOException e)
		{
			System.out.println("Faild Output Message : " + e.getMessage());
		}
	}
	
	private void outputMessageOffset(FileOutputStream outputStream, TagTable tagTable, MesTable mesTable, String charset)
	{
		int messageOffset = 0;
		int labelIdx = mesTable.getColumnIndex(LABEL);
		int descIdx = mesTable.getColumnIndex(DESCRIPTION);
		int columnCount = 0;
		boolean labelExist = false;
		Stack<String> work = new Stack<String>();
		int languageNum = mesTable.getLanguageNum() + 2;
		work.ensureCapacity(languageNum);
		for(int i = 0; i < languageNum; ++i){
			work.add(i, "");
		}
		try 
		{
			for(Stack<String> row : mesTable.getRow())
			{
				if(labelExist)
				{
					// 現在のWorkを書き出し
					int count = 0;
					for (String string : work)
					{
						if(count != labelIdx && count != descIdx)
						{
							byte[] mesOffset = this.getByteData(messageOffset, 4, BIG_ENDIAN);
							outputStream.write(mesOffset);
							outputStream.flush();
							byte[] message = this.outWrite(string, tagTable, charset);
							System.out.println(string +  String.format(" %d : %01x %01x %01x %01x", message.length, mesOffset[0], mesOffset[1], mesOffset[2], mesOffset[3]));
							int size = message.length /*+ newLine.length()*/;
							messageOffset += size;
						}
						++count;
					}
					labelExist = false;
					work.clear();
					work.ensureCapacity(languageNum);
					for(int i = 0; i < languageNum; ++i){
						work.add(i, "");
					}
				}
				boolean _labelExist = false;
				for(String column : row)
				{
					if(columnCount != labelIdx && columnCount != descIdx)
					{
						if( column != null )
						{
							String str = "";
							str = work.get(columnCount);
							work.remove(columnCount);
							if(labelExist) {
								str = str + "\n";
							}
							work.add(columnCount, str + column);
						}
					}
					if( columnCount == labelIdx && column != null ) {
						_labelExist = true;
					}
					++columnCount;
				}
				labelExist = _labelExist;
				columnCount = 0;	
			}
			int count = 0;
			for (String string : work)
			{
				if(count != labelIdx && count != descIdx)
				{
					byte[] mesOffset = this.getByteData(messageOffset, 4, BIG_ENDIAN);
					outputStream.write(mesOffset);
					outputStream.flush();
					byte[] message = this.outWrite(string, tagTable, charset);
					System.out.println(string +  String.format(" %d : %01x %01x %01x %01x", message.length, mesOffset[0], mesOffset[1], mesOffset[2], mesOffset[3]));
					int size = message.length /*+ newLine.length()*/;
					messageOffset += size;
				}
				++count;
			}
		}
		catch(IOException e)
		{
			System.out.println("Faild Output Message : " + e.getMessage());
		}	
	}
	
	private void outputMessage(FileOutputStream outputStream, TagTable tagTable, MesTable mesTable, String charset)
	{
		boolean labelExist = false;
		int columnCount = 0;
		Stack<String> work = new Stack<String>();
		int labelIdx = mesTable.getColumnIndex(LABEL);
		int descIdx = mesTable.getColumnIndex(DESCRIPTION);
		int languageNum = mesTable.getLanguageNum() + 2;
		work.ensureCapacity(languageNum);
		for(int i = 0; i < languageNum; ++i){
			work.add(i, "");
		}
		try 
		{
			for(Stack<String> row : mesTable.getRow())
			{
				if(labelExist) {
					// 現在のWorkを書き出し
					int count = 0;
					for (String string : work)
					{
						if(count != labelIdx && count != descIdx)
						{
							outputStream.write(this.outWrite(string, tagTable, charset));
							outputStream.flush();	
						}
						++count;
					}
					labelExist = false;
					work.clear();
					work.ensureCapacity(languageNum);
					for(int i = 0; i < languageNum; ++i){
						work.add(i, "");
					}
				}
				boolean _labelExist = false;
				for(String column : row)
				{
					if(columnCount != labelIdx && columnCount != descIdx)
					{
						if( column != null )
						{
							String str = "";
							str = work.get(columnCount);
							work.remove(columnCount);
							if(labelExist) {
								str = str + "\n";
							}
							work.add(columnCount, str + column);
						}
					}
					if( columnCount == labelIdx && column != null ) {
						_labelExist = true;
					}
					++columnCount;
				}
				labelExist = _labelExist;
				columnCount = 0;
			}
			int count = 0;
			for (String string : work)
			{
				if(count != labelIdx && count != descIdx)
				{
					outputStream.write(this.outWrite(string, tagTable, charset));
					outputStream.flush();
				}
				++count;
			}
		}
		catch(IOException e)
		{
			System.out.println("Faild Output Message : " + e.getMessage());
		}
	}
	
	/**
	 * @param parent	Parent directory
	 * @param name		file name
	 * @param mesTable	source data
	 */
	private void outputCTagFile(String parent, String name, MesTable mesTable)
	{
		try
		{
			File file = new File(parent, name + ".h");
			file.createNewFile();
			PrintWriter pw = new PrintWriter(file);
			
			// "-","." replace "_"
			name = name.replace("-", "_");
			name = name.replace(".", "_");
			
			pw.write("#ifndef " + name + "_h_\n");
			pw.write("#define " + name + "_h_\n");
			pw.write("\n");
			
			int labelIdx = mesTable.getColumnIndex("Label");
			Stack<Stack<String>> row = mesTable.getRow();
			
			int index = 0;
			for (Stack<String> strings : row) {
				String string = strings.elementAt(labelIdx);
				pw.write(String.format("#define %s %d\n", string, index));
				++index;
			}
			pw.write("\n");
			
			pw.write(String.format("#define all_label_%s %d\n", name, index));
			pw.write("\n");
			
			pw.write("#endif\t// " + name + "_h_\n");
			
			pw.close();
		}
		catch(IOException e)
		{			
		}
	}
	
	/**
	 * @param parent	parent Directory
	 * @param name		file name
	 * @param mesTable	source data
	 */
	private void outputJavaFile(String parent, String name, MesTable mesTable)
	{
		try
		{
			File file = new File(parent, name + ".java");
			file.createNewFile();
			PrintWriter pw = new PrintWriter(file);
			int labelIdx = mesTable.getColumnIndex("Label");
			Stack<Stack<String>> row = mesTable.getRow();
			
			pw.write("package MesMan;\n");
			pw.write("\n");
			pw.write("public class " + name + "\n");
			pw.write("{\n");
			

			pw.write("\t// Number of Language\n");
			int languageCount = 0;
			for(String column : mesTable.getColumnName())
			{
				if(!"Label".equals(column) && !"Description".equals(column))
				{
					pw.write(String.format("\tstatic int Language_%s = %d;\n", column, languageCount));
					++languageCount;
				}
			}
			pw.write("\n");
			
			pw.write("\t// Number of Message\n");
			int index = 0;
			for (Stack<String> strings : row) {
				String string = strings.elementAt(labelIdx);
				pw.write(String.format("\tstatic int %s = %d;\n", string, index));
				++index;
			}
			

			pw.write("\n}\n");
			pw.close();
		}
		catch(IOException e)
		{
			
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
//						String preTagStr = _str.substring(0, index - 1);
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
//			System.out.println("stack : " + stack.toString());
			
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
			System.out.println(String.format("getByteData %x -> ", value) + ret.toString());
		}
		else if(endian == BIG_ENDIAN)
		{
			int byteBit = 8;
			for(int i = 1; i <= byteSize; ++i) {
				long siftvalue = value >>> ((byteSize - i) * byteBit);
				ret[i - 1] = (byte)(siftvalue & 0x00000000000000FF);
			}
		}
		
		return ret;
	}
}
