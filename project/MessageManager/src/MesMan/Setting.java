package MesMan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Setting {
	
	private String fileName;
	private String defaultDirectory;
	private Integer defaultLanguage;
	LinkedList<String> buffer;
	
	private String DefaultDirectoryLabel = "\"defaultDirectory\":";
	private String DefaultLanguageLabel = "\"defaultLanguage\":";
	
	private static int directoryIdx = 0;
	private static int languageIdx = 1;

	public Setting(String settingFile)
	{
		buffer = new LinkedList<String>();
		try
		{
			fileName = settingFile;
			File file = new File(settingFile);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			// ファイルから情報取得
			String line = null;
			while((line = br.readLine()) != null) {
				buffer.add(line);
			}
			br.close();
			
			defaultDirectory = buffer.get(directoryIdx).substring(DefaultDirectoryLabel.length());
			defaultLanguage = Integer.valueOf(buffer.get(languageIdx).substring(DefaultLanguageLabel.length()));
			
			System.out.println(defaultDirectory + " " + defaultLanguage);
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			reset();
		}
	}
	
	public Integer getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(int defaultLanguage)
	{
		buffer.remove(languageIdx);
		buffer.add(languageIdx, DefaultLanguageLabel + String.valueOf(defaultLanguage));
		this.defaultLanguage = new Integer(defaultLanguage);
	}
	
	public String getDefaultDirectory()
	{
		return defaultDirectory;
	}
	public void setDefaultDirectory(String defaultDirectory)
	{
		buffer.remove(directoryIdx);
		buffer.add(directoryIdx, DefaultDirectoryLabel + defaultDirectory);
		this.defaultDirectory = defaultDirectory;
	}
	
	public void reset()
	{
		defaultDirectory = new String("C:\\");
		defaultLanguage = MesTableDefine.Language_ENG;
		
		buffer.clear();
		buffer.add(directoryIdx, DefaultDirectoryLabel + defaultDirectory);
		buffer.add(languageIdx, DefaultLanguageLabel + String.valueOf(defaultLanguage));
	}
	
	public void save()
	{
		try
		{
			File file = new File(fileName);
			PrintWriter pw = new PrintWriter(file);
			
			for (String str : this.buffer) {
				pw.write(str);
				pw.write("\n");
			}
			pw.flush();
			pw.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
	}
}
