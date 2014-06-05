package MesMan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Setting {
	
	private String fileName;
	private String defaultDirectory;
	private String defaultLanguage;
	
	private String DefaultDirectoryLabel = "\"defaultDirectory\":";
	private String DefaultLanguageLabel = "\"defaultLanguage\":";

	public Setting(String settingFile)
	{
		try
		{
			fileName = settingFile;
			File file = new File(settingFile);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			// ファイルから情報取得
			
			defaultDirectory = br.readLine().substring(DefaultDirectoryLabel.length());
			defaultLanguage = br.readLine().substring(DefaultLanguageLabel.length());
			
			System.out.println(defaultDirectory + " " + defaultLanguage);
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			reset();
		}
	}
	
	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage)
	{
		// 言語名は正しい?
	}
	
	public String getDefaultDirectory()
	{
		return defaultDirectory;
	}
	public void setDefaultDirectory(String defaultDirectory)
	{
		// directoryが正しい?
//		defaultDirectory = 
	}
	
	public void reset()
	{
		defaultDirectory = new String("C:\\");
		defaultLanguage = new String("ENG");
		
		// ファイルに書き出し
	}
}
