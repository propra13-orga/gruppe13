package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

class NetChatPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField 	inputField;
	private JTextArea  	chatArea;
	
	private NetClient	client;
	private String 		username;
	
	private int			areaheight = 80;
	private int 		chatheight = 20;
	
	NetChatPanel(Dimension frameDimension, NetClient client, int clientNo) {
		// set dimension of the panel
		this.setPreferredSize(new Dimension((int)(frameDimension.getWidth()-60), (int)(areaheight+chatheight)));
		this.client = client;
		this.username = "user "+clientNo;
	}
	
	void sendText(String toSend) {
		String s = "01234567890123456789"+toSend;
		client.setChatText(s);
	}
	
	void addText(String toAdd) {
		if (chatArea.getLineCount() > 4) {
			System.out.println(chatArea.getLineCount());
			int offset;
			String s = "";
			try {
				offset = chatArea.getLineEndOffset(1);
				s = chatArea.getText(offset, chatArea.getText().length());
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			chatArea.setText(s);
		}
		chatArea.append(toAdd);
		chatArea.append(System.getProperty("line.separator"));
	}
	
	void setUsername (String username) {
		this.username = username;
	}
	
	void initPanel() {
		// set the layout manager
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.red);
		
		// initialize the input field and make it listen to ENTER
		inputField 	= new JTextField();
		inputField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					sendText(inputField.getText());
					inputField.setText(username+": ");
				}
			}
		});
		
		inputField.setPreferredSize(new Dimension(this.getWidth(), chatheight));
		inputField.setBackground(Color.lightGray);
		inputField.setText(username+": ");
		inputField.setVisible(true);
		
		// initialize the text area
		chatArea = new JTextArea();
		chatArea.setPreferredSize(new Dimension(this.getWidth(), areaheight));
		chatArea.setBackground(Color.darkGray);
		chatArea.setForeground(Color.white);
		chatArea.setText("blub");
		chatArea.setVisible(true);
		
		// set the layout
		GridBagConstraints layout = new GridBagConstraints();
		
		layout.fill = GridBagConstraints.BOTH;
		layout.gridx = 0;
		layout.gridy = 0;
		
		layout.weightx = 1;
		layout.weighty = 1.0*(areaheight/(chatheight+areaheight));
		
		this.add(chatArea, layout);
		
		layout.weighty = 1.0*(chatheight/(chatheight+areaheight));
		layout.gridy = 1;
		this.add(inputField, layout);
	}
}