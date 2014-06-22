package MesMan;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * 出力に関する項目.
 * @author t_sato
 *
 */
public class CheckParamPanel implements ItemListener
{
	private JPanel panel;
	
	private String OUT_FILE = "Out File";
	private String CHARACTOR_CODE = "Charactor Code";
	private String SUFFIX_BIN = ".bin";
	private String SUFFIX_C = ".c";
	private String SUFFIX_JAVA = ".java";
	private String UTF8 = "UTF-8";
	private String UTF16BE = "UTF-16BE";
	private String UTF16LE = "UTF-16LE";
	
	private JCheckBox binCheck;
	private JCheckBox cCheck;
	private JCheckBox javaCheck;
	private JCheckBox utf8;
	private JCheckBox utf16BE;
	private JCheckBox utf16LE;
	
	private int outFileFlag;
	private int charactorCodeFlag;
	
	public static int OUT_FILE_BIN = 0x00000001;
	public static int OUT_FILE_C   = 0x00000002;
	public static int OUT_FILE_JAVA = 0x00000004;
	
	public static int CHARA_CODE_MASK    = 0x0000000F;
	public static int CHARA_CODE_UTF8    = 0x00000001;
	public static int CHARA_CODE_UTF16BE = 0x00000002;
	public static int CHARA_CODE_UTF16LE = 0x00000004;
	
	/**
	 * コンストラクタ.
	 */
	public CheckParamPanel()
	{
		outFileFlag = 0;
		charactorCodeFlag = 0;
		
		// suffix
		this.binCheck = new JCheckBox(SUFFIX_BIN);
		this.cCheck = new JCheckBox(SUFFIX_C);
		this.javaCheck = new JCheckBox(SUFFIX_JAVA);
		this.binCheck.addItemListener(this);
		this.cCheck.addItemListener(this);
		this.javaCheck.addItemListener(this);
		
		JPanel suffix = new JPanel();
		suffix.add(this.binCheck);
		suffix.add(this.cCheck);
		suffix.add(this.javaCheck);
		
		BoxLayout suffixLayout = new BoxLayout(suffix, BoxLayout.X_AXIS);
		suffix.setLayout(suffixLayout);
		TitledBorder suffixBorder = new TitledBorder(new LineBorder(Color.BLACK, 2), OUT_FILE);
		suffix.setBorder(suffixBorder);
		
		// language code
		this.utf8 = new JCheckBox(UTF8);
		this.utf16BE = new JCheckBox(UTF16BE);
		this.utf16LE = new JCheckBox(UTF16LE);
		this.utf8.addItemListener(this);
		this.utf16BE.addItemListener(this);
		this.utf16LE.addItemListener(this);
		
		JPanel code = new JPanel();
		code.add(this.utf8);
		code.add(this.utf16BE);
		code.add(this.utf16LE);
		
		BoxLayout codeLayout = new BoxLayout(code, BoxLayout.X_AXIS);
		code.setLayout(codeLayout);
		TitledBorder codeBorder = new TitledBorder(new LineBorder(Color.BLACK, 2), CHARACTOR_CODE);
		code.setBorder(codeBorder);
		
		// panel
		this.panel = new JPanel();
		this.panel.add(suffix);
		this.panel.add(code);
		BoxLayout panelLayout = new BoxLayout(this.panel, BoxLayout.Y_AXIS);
		this.panel.setLayout(panelLayout);
	}
	
	/**
	 * @return パネル.
	 */
	public JPanel getPanel()
	{
		return this.panel;
	}
	
	/**
	 * @return　出力するファイル形式.
	 */
	public int getOutFileFlag()
	{
		return this.outFileFlag;
	}
	
	/**
	 * @return 出力する文字コード.
	 */
	public int getCaraCodeFlag()
	{
		return this.charactorCodeFlag;
	}
	
	public void itemStateChanged(ItemEvent e)
	{
		Object source = e.getItem();
		
		if(this.binCheck == source)
		{
			if( this.binCheck.isSelected() ){ outFileFlag |= OUT_FILE_BIN; }
			else							{ outFileFlag &= ~OUT_FILE_BIN;}
		}
		else if(this.cCheck == source)
		{
			if( this.cCheck.isSelected() )	{ outFileFlag |= OUT_FILE_C; }
			else							{ outFileFlag &= ~OUT_FILE_C;}
		}
		else if(this.javaCheck == source)
		{
			if( this.javaCheck.isSelected() ) { outFileFlag |= OUT_FILE_JAVA; }
			else							  { outFileFlag &= ~OUT_FILE_JAVA; }
		}
		else if(this.utf8 == source)
		{
			if( this.utf8.isSelected() ){ charactorCodeFlag |= CHARA_CODE_UTF8; }
			else						{ charactorCodeFlag &= ~CHARA_CODE_UTF8;}
		}
		else if(this.utf16BE == source)
		{
			if( this.utf16BE.isSelected() )	{ charactorCodeFlag |= CHARA_CODE_UTF16BE; }
			else							{ charactorCodeFlag &= ~CHARA_CODE_UTF16BE;}
		}
		else if(this.utf16LE == source)
		{
			if( this.utf16LE.isSelected() )	{ charactorCodeFlag |= CHARA_CODE_UTF16LE; }
			else							{ charactorCodeFlag &= ~CHARA_CODE_UTF16LE;}
		}
	}
}
