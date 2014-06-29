package BtoC;

import java.io.*;
import java.util.Stack;

/**
 * .cファイル出力.
 * @author t_sato
 *
 */
public class ToCFile
{
	private Stack<String> dataList;
	private byte[] dataContent;
	
	/**
	 * コンストラクタ. 
	 */
	public ToCFile()
	{
		this.dataList = new Stack<String>();
	}
	
	/**
	 * .cファイルへ.
	 * @param cmdArg 引数
	 */
	public void toCFile(ExCmdArg cmdArg)
	{
		// data list specified
		if(cmdArg.dataListDir != null)
		{
			getDataLists(cmdArg.dataListDir);
		}
		// individual designation
		else if(cmdArg.datas.size() > 0)
		{
			this.dataList.addAll(cmdArg.datas);
		}
		
		// do .c file
		for(String dataPath : this.dataList)
		{
			// read
			fileRead(dataPath);
			
			// get out path
			String outFileName = getFileName(dataPath);
			
			// out file
			fileWrite(cmdArg.outDir, outFileName, cmdArg.isExtern);
		}
	}
	
	/**
	 * .cファイルにするバイナリファイル一覧を取得
	 * @param path バイナリファイル一覧ファイル名
	 */
	private void getDataLists(String path)
	{
		try
		{
			File file = new File(path);
			file.createNewFile();
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			
			String str = null;
			while((str = fileReader.readLine()) != null)
			{
				this.dataList.push(str);
			}
			
			fileReader.close();
		}
		catch(IOException e)
		{
			System.out.println("Failed : data list read");
		}
	}
	
	/**
	 * 出力ファイル名取得.
	 * @param path 元となるファイル名
	 * @return 出力ファイル名
	 */
	private String getFileName(String path)
	{
		File file = new File(path);
		String name = file.getName();
		return name.replace(".", "_");
	}
	
	/**
	 * バイナリファイル読み込み
	 * @param path バイナリファイルパス
	 */
	private void fileRead(String path)
	{
		try
		{
			// create file
			File file = new File(path);
			FileInputStream fileStream = new FileInputStream(file);
			
			// data read
			this.dataContent = new byte[(int)file.length()];
			fileStream.read(this.dataContent, 0, (int)file.length());
			fileStream.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File Not Found : " + e.getMessage());
		}
		catch(IOException e)
		{
			System.out.println("Failed File Read : " + e.getMessage());
		}
		
	}
	
	/**
	 * ファイル書き出し
	 * @param outDir 書き出しディレクトリ
	 * @param fileName 書き出しファイル名
	 * @param isExtern .cのextern定義の有無
	 */
	private void fileWrite(String outDir, String fileName, boolean isExtern)
	{
		try
		{
			// create file
			File hFile = new File(outDir + fileName + ".h");
			File cFile = new File(outDir + fileName + ".c");
			
			// create new file
			hFile.createNewFile();
			cFile.createNewFile();
			
			// create file writer
			FileWriter hFileWriter = new FileWriter(hFile);
			FileWriter cFileWriter = new FileWriter(cFile);

			String variable = new String("const unsigned char " + fileName + "[" + String.format("0x%x", this.dataContent.length) + "]");
			
			// write .h file
			hFileWriter.write("#ifndef _" + fileName + "_\r\n");
			hFileWriter.write("#define _" + fileName + "_\r\n");
			if(isExtern)
			{
				hFileWriter.write("\r\n");
				hFileWriter.write("#ifdef __cplusplus\r\n");
				hFileWriter.write("extern \"C\" {\r\n");
				hFileWriter.write("#endif\r\n");
				hFileWriter.write("\r\n");
			}
			
			hFileWriter.write("extern " + variable + ";\r\n");
			
			if(isExtern)
			{
				hFileWriter.write("\r\n");
				hFileWriter.write("#ifdef __cplusplus\r\n");
				hFileWriter.write("}\r\n");
				hFileWriter.write("#endif\r\n");
				hFileWriter.write("\r\n");
			}
			hFileWriter.write("#endif\t// _" + fileName + "_\r\n");
			hFileWriter.flush();

			// write .c file
			cFileWriter.write(variable + " = {");
			int counter = 0;
			for(byte value : this.dataContent)
			{
				if(--counter < 0)
				{
					cFileWriter.write("\r\n\t");
					counter = 15;
				}
				
				cFileWriter.write(String.format("0x%02x,", value));
			}
			cFileWriter.write("\r\n};\r\n");
			cFileWriter.flush();
			
			hFileWriter.close();
			cFileWriter.close();
		}
		catch(IOException e)
		{
			System.out.println("Failed file write : " + e.getMessage());
		}
	}
}
