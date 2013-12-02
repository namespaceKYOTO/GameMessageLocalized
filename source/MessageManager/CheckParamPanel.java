/*---------------------------------------------------------------------*/
/*!
 * @brief	Out file param
 * @author	t_sato
 */
/*---------------------------------------------------------------------*/
package MesMan;

import java.lang.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class CheckParamPanel
{
	private JPanel panel;
	
	/*---------------------------------------------------------------------*/
	//*!brief	constructor
	/*---------------------------------------------------------------------*/
	public CheckParamPanel()
	{
		// suffix
		JCheckBox binCheck = new JCheckBox("bin");
		JCheckBox cCheck = new JCheckBox(".c");
		JPanel suffix = new JPanel();
		suffix.add(binCheck);
		suffix.add(cCheck);
		BoxLayout suffixLayout = new BoxLayout(suffix, BoxLayout.X_AXIS);
		suffix.setLayout(suffixLayout);
		TitledBorder suffixBorder = new TitledBorder(new LineBorder(Color.BLACK, 2), "Out File");
		suffix.setBorder(suffixBorder);
		
		// language code
		JCheckBox utf8 = new JCheckBox("UTF-8");
		JCheckBox utf16BE = new JCheckBox("UTF-16BE");
		JCheckBox utf16LE = new JCheckBox("UTF-16LE");
		JPanel code = new JPanel();
		code.add(utf8);
		code.add(utf16BE);
		code.add(utf16LE);
		BoxLayout codeLayout = new BoxLayout(code, BoxLayout.X_AXIS);
		code.setLayout(codeLayout);
		TitledBorder codeBorder = new TitledBorder(new LineBorder(Color.BLACK, 2), "Charactor Code");
		code.setBorder(codeBorder);
		
		// panel
		this.panel = new JPanel();
		this.panel.add(suffix);
		this.panel.add(code);
		BoxLayout panelLayout = new BoxLayout(this.panel, BoxLayout.Y_AXIS);
		this.panel.setLayout(panelLayout);
	}
	
	/*---------------------------------------------------------------------*/
	//*!brief	getter
	/*---------------------------------------------------------------------*/
	public JPanel getPanel()
	{
		return this.panel;
	}
}
