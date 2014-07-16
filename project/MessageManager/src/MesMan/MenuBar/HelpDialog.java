package MesMan.MenuBar;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import MesMan.MesMan;
/**
 * ヘルプのダイアログ.
 * @author t_sato
 *
 */
public class HelpDialog implements ActionListener
{
	private MesMan mesman;
	
	private JDialog dialog;
	
	private JTextArea textArea;
	
	private JPanel buttonPanel;
	private JButton buttonMessage;
	private JButton buttonTag;
	private JButton buttonCharSize;
	private JButton buttonOutput;
	private JButton buttonSuffix;
	
	
	/**
	 * コンストラクタ. 
	 */
	public HelpDialog(MesMan mesman)
	{
		this.mesman = mesman;
		
		dialog = new JDialog(mesman, "Help");
		Rectangle rectangle = mesman.getBounds();
		dialog.setBounds(rectangle);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setSize(100, 100);
		textArea.setColumns(50);
		
		buttonMessage = new JButton("buttonMessage");
		buttonMessage.setContentAreaFilled(false);
		buttonMessage.setBorderPainted(false);
		buttonMessage.addActionListener(this);
		buttonTag = new JButton("buttonTag");
		buttonTag.setContentAreaFilled(false);
		buttonTag.setBorderPainted(false);
		buttonTag.addActionListener(this);
		buttonCharSize = new JButton("buttonCharSize");
		buttonCharSize.setContentAreaFilled(false);
		buttonCharSize.setBorderPainted(false);
		buttonCharSize.addActionListener(this);
		buttonOutput = new JButton("buttonOutput");
		buttonOutput.setContentAreaFilled(false);
		buttonOutput.setBorderPainted(false);
		buttonOutput.addActionListener(this);
		buttonSuffix = new JButton("buttonSuffix");
		buttonSuffix.setContentAreaFilled(false);
		buttonSuffix.setBorderPainted(false);
		buttonSuffix.addActionListener(this);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(buttonMessage);
		buttonPanel.add(buttonTag);
		buttonPanel.add(buttonCharSize);
		buttonPanel.add(buttonOutput);
		buttonPanel.add(buttonSuffix);
		
//		dialog.add(buttonPanel);
//		dialog.add(textArea);
		JPanel panel = new JPanel();
		panel.add(buttonPanel);
		panel.add(textArea);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		dialog.add(panel);
	}
	
	/**
	 * ダイアログの表示. 
	 */
	public void show()
	{
		this.dialog.setVisible(true);
	}
	
	/**
	 * ダイアログの非表示
	 */
	public void hidden()
	{
		this.dialog.setVisible(false);
	}

	/* (非 Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		Object obj = arg0.getSource();
		String language = getNowLanguage();
		
		if(obj == this.buttonMessage)
		{
			textRead("/help/" + language + "/help_message.txt");
		}
		else if(obj == this.buttonTag)
		{
			textRead("/help/" + language + "/help_tag.txt");
		}
		else if(obj == this.buttonCharSize)
		{
			textRead("/help/" + language + "/help_charasize.txt");
		}
		else if(obj == this.buttonOutput)
		{
			textRead("/help/" + language + "/help_output.txt");
		}
		else if(obj == this.buttonSuffix)
		{
			textRead("/help/" + language + "/help_suffix.txt");
		}
	}
	
	/**
	 * テキストの読み込み
	 * @param src
	 */
	private void textRead(String data)
	{
		// 現在のテキストを削除
		textArea.setText("");

		InputStream fileStream = this.getClass().getResourceAsStream(data);
		BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));
		String readLine = null;
		try {
			while((readLine = br.readLine()) != null)
			{
				textArea.append(readLine + "\n");
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	/**
	 * 現在の表示言語取得.
	 * @return 言語文字
	 */
	private String getNowLanguage()
	{
		// TODO：現在はJPNのヘルプしかないので July.13.2014 t_sato
		String language = ((LanguageMenu)mesman.getMenubar().get(2)).getLanguage(); 
		return language;
	}
}
