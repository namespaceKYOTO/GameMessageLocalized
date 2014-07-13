package MesMan;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PreSetting extends JFrame implements ActionListener, WindowListener
{
	private static final long serialVersionUID = -2734118271704301329L;
	
	private JComboBox<String> comboBox;
	private JLabel label;
	private JButton okButton;

	/**
	 * コンストラクタ.
	 */
	public PreSetting()
	{

		MessageDataManager mesDataMan = new MessageDataManager("/res/MesTableDefine.bin");
		mesDataMan.setLanguageNo(MesTableDefine.Language_JPN);
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rec = env.getMaximumWindowBounds();
		int width = (int)rec.getWidth() / 3;
		int height = (int)rec.getHeight() / 3;
		int x = ((int)rec.getWidth() / 2) - (width / 2);
		int y = ((int)rec.getHeight() / 2) - (height / 2);
		setBounds( x, y, width, height );

		setTitle("Setting");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(this);
		
		String[] element = {
			mesDataMan.getMessage(MesTableDefine.mes_JPN),
			mesDataMan.getMessage(MesTableDefine.mes_ENG),
			mesDataMan.getMessage(MesTableDefine.mes_DEU),
			mesDataMan.getMessage(MesTableDefine.mes_FRA),
			mesDataMan.getMessage(MesTableDefine.mes_ITA),
			mesDataMan.getMessage(MesTableDefine.mes_SPA),
			mesDataMan.getMessage(MesTableDefine.mes_RUS),
			mesDataMan.getMessage(MesTableDefine.mes_THA),
		};
		comboBox = new JComboBox<String>(element);
		label = new JLabel("Default Language");
		okButton = new JButton("OK");
		
		JPanel panel = new JPanel();
		panel.add(label);
		panel.add(comboBox);
		panel.add(okButton);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		add(panel);
		setVisible(true);
	}
	
	/**
	 * コンフィグファイルを作成したか.
	 * @return 有無
	 */
	public boolean isCreate()
	{
		return false;
	}
	
	/**
	 * コンフィグファイルの作成
	 */
	public void createConfigFile()
	{
		
	}

	/* (非 Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{	
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowClosing(WindowEvent arg0) {
		createConfigFile();
	}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}
