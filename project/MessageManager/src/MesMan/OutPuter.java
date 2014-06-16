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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Stack;

import BtoC.BtoC;

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
			byte[] messageNum = this.getByteData(mesTable.getMessageNum(), 4, BIG_ENDIAN);
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
			outputMessage( 0, outputStream, tagTable, mesTable, charset);
			
			// Messsage
			outputMessage( 1, outputStream, tagTable, mesTable, charset);
			
			outputStream.flush();
			outputStream.close();
		}
		catch(IOException e)
		{
			System.out.println("Faild Output Message : " + e.getMessage());
		}
	}

	private void outputMessageOffset(FileOutputStream outputStream, int messageOffset)
	{
		byte[] mesOffset = this.getByteData(messageOffset, 4, BIG_ENDIAN);
		try {
			outputStream.write(mesOffset);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void outputMessageData(FileOutputStream outputStream, byte[] data)
	{
		try {
			outputStream.write(data);
			outputStream.flush();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void outputMessage(int type, FileOutputStream outputStream, TagTable tagTable, MesTable mesTable, String charset)
	{
		int labelIdx = mesTable.getColumnLabelIndex();
		int descIdx = mesTable.getColumnDescriptionIndex();
		System.out.println(String.format("Label : %d, Desc : %d", labelIdx, descIdx));
		Stack<String> work = new Stack<String>();
		int messageOffset = 0;
		int columnCount = 0;
		int languageNum = mesTable.getColumnNum();
		boolean first = true;
		
		work.ensureCapacity(languageNum);
		for(int i = 0; i < languageNum; ++i){
			work.add(i, "");
		}
		
		for(Stack<String> row : mesTable.getRow())
		{
			if(row.get(labelIdx) != null && row.get(labelIdx).length() > 0)
			{
				// 現在のWorkを書き出し
				int count = 0;
				if(!first)
				{
					for (String string : work)
					{
						if(count != labelIdx && count != descIdx)
						{
							if(type == 0) {
								this.outputMessageOffset(outputStream, messageOffset);
								byte[] message = this.outWrite(new String(string), tagTable, charset);
								int size = message.length;
								messageOffset += size;
								System.out.println("Out Message : " + string);
							}
							else {
								this.outputMessageData(outputStream, this.outWrite(new String(string), tagTable, charset));
							}
						}
						++count;
					}
				}
				work.clear();
				work.ensureCapacity(languageNum);
				first = false;
				for(int i = 0; i < languageNum; ++i){
					work.add(i, "");
				}
			}
			for(String column : row)
			{
				if(columnCount != labelIdx && columnCount != descIdx)
				{
					if( column != null )
					{
						String str = work.get(columnCount);
						work.remove(columnCount);
						if(str.length() > 0) {
							str = str + "\r\n";
						}
						work.add(columnCount, new String(str + column));
					}
				}
				++columnCount;
			}
			columnCount = 0;
		}
		int count = 0;
		for (String string : work)
		{
			if(count != labelIdx && count != descIdx)
			{
				if(type == 0) {
					this.outputMessageOffset(outputStream, messageOffset);
					byte[] message = this.outWrite(new String(string), tagTable, charset);
					int size = message.length;
					messageOffset += size;
					System.out.println("Out Message : " + string);
				}
				else {
					this.outputMessageData(outputStream, this.outWrite(new String(string), tagTable, charset));
				}
			}
			++count;
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
			pw.write(String.format("\tstatic int LanguageNum = %d;\n", languageCount));
			pw.write("\n");
			
			pw.write("\t// Number of Message\n");
			int index = 0;
			for (Stack<String> strings : row) {
				String string = strings.elementAt(labelIdx);
				if(string != null && string.length() > 0) {
					pw.write(String.format("\tstatic int %s = %d;\n", string, index));
					++index;
				}
			}
			pw.write(String.format("\tstatic int MessageNum = %d;\n", index));
			

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
		ArrayList<Byte> stack = new ArrayList<Byte>();
		Stack<Stack<String>> row = tagTable.getRow();
		String[] split = null;
		Byte[] tagCode = null;
		int count = 0;
		
		if(str == null) {
			return new byte[0];
		}

		// tag replace
		for(Stack<String> column : row )
		{
			int index = -1;
			String tagName = column.get(0);
			if(tagName == null) continue;
			index = str.indexOf(tagName);
			if(index != -1)
			{
				System.out.println(str + " : " + tagName);
				split = str.split(tagName);
				tagCode = tagTable.getTagCode(count, charset);
				break;
			}
			++count;
		}
		boolean first = true;
		if(split != null) {
			for (String string : split) {
				System.out.println("split string : " + string);
				if(string.length() == 0) continue;
				byte[] splitByte = this.outWrite(new String(string), tagTable, charset);

				if( !first ) {
					stack.addAll(Arrays.asList(tagCode));
				}
				for (byte b : splitByte) {
					stack.add(b);
				}
				first = false;
			}
		}
		else {
			try {
				byte[] byteData = str.getBytes(charset);
				for (byte b : byteData) {
					stack.add(b);
				}
			} catch (UnsupportedEncodingException e) {
				System.out.println(e.getMessage());
			}
			
		}
		
		byte[] ret = new byte[stack.size()];
		int i = 0;
		for (byte b : stack) {
			ret[i++] = b;
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
