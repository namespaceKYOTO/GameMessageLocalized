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
			
			br.close();
			
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
	public int getDefaultLanguageNo() {
			 if(defaultLanguage.equals("JPN")) { return MesTableDefine.Language_JPN; }
		else if(defaultLanguage.equals("ENG")) { return MesTableDefine.Language_ENG; }
		else if(defaultLanguage.equals("DEU")) { return MesTableDefine.Language_DEU; }
		else if(defaultLanguage.equals("FRA")) { return MesTableDefine.Language_FRA; }
		else if(defaultLanguage.equals("ITA")) { return MesTableDefine.Language_ITA; }
		else if(defaultLanguage.equals("SPA")) { return MesTableDefine.Language_SPA; }
		else if(defaultLanguage.equals("RUS")) { return MesTableDefine.Language_RUS; }
		else if(defaultLanguage.equals("THA")) { return MesTableDefine.Language_THA; }
		return MesTableDefine.Language_ENG;
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
