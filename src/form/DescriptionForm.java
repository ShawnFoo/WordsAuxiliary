package form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.IsOpen;

import event.Picture;

/**
 * 
 * @author ShawnFoo 
 * @version  2014.7.24    2.0
 * 
 */
public class DescriptionForm extends JFrame{
	public DescriptionForm(final IsOpen isOpen){		
		this.setTitle("˵��");
		//����Icon	
		this.setIconImage(Picture.getIcon());	
		
		//�����ʼ��
		String text = "<html>����:ShawnFoo&nbsp;&nbsp;" +
				"�汾��3.2<br>" +
				"��룺���𽫱���������κ���ҵ��;������<br>" +
				"�������ݣ�<br>1.������ϰ����-1����,��ֹ���(thanks for the advice of Ran)" +
				"<br>2.��������꾭��itemʱ��Ч����" +
				"hoverʱΪ��ɫ��leave�����غ�ɫ" +
				"<br>3.fix��Ӱ�ťģ������һ�κ��ٳ��ֵ�bug<br>" +
				"4.fixɾ���쳣���������</html>";
		String text2 = "<html>�õĽ����뷢������<br>fu4904@gmail.com<br>" +
				"��490461067@qq.com<br>" +
				"</html>";
		String text3 = "                          ���˴�������";
				
		JLabel announcement = new JLabel(text);
		announcement.setBounds(5, 0, 190, 220);
		announcement.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		
		JLabel advice = new JLabel(text2);
		advice.setForeground(Color.BLUE);
		advice.setBounds(5, 210, 190, 70);
		advice.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		
		JLabel update = new JLabel(text3);
		update.setForeground(Color.RED);
		update.setBounds(5, 270, 190, 30);
		update.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		JPanel jp = Picture.getBackGround(1);
		jp.setLayout(null);
		jp.add(announcement);
		jp.add(advice);
		jp.add(update);
		announcement.setOpaque(false);
		
		isOpen.setOpen(true);
		//�¼�
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				isOpen.setOpen(false);
			}
		});
		//�������¼�   ���û�Ĭ�������
		update.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if (java.awt.Desktop.isDesktopSupported()) {  
		            try {  
		                // ����һ��URIʵ��  
		                java.net.URI uri = java.net.URI.create("http://pan.baidu.com/s/1qWsJBOC");  
		                // ��ȡ��ǰϵͳ������չ  
		                java.awt.Desktop dp = java.awt.Desktop.getDesktop() ;   
		                // �ж�ϵͳ�����Ƿ�֧��Ҫִ�еĹ���  
		                if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {  
		                    // ��ȡϵͳĬ�������������   
		                    dp.browse( uri );  
		                }       
		            }catch(Exception ee){   
		                ee.printStackTrace();  
		            }  
		        } 				
			}
			 
		});
		
		this.setAlwaysOnTop(true);
		
		//����
		this.setContentPane(jp);
		
		//JFrame����		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(200, 330);		
		this.setResizable(false);
		this.setLocationRelativeTo(this);
		this.setVisible(true);
	}
}
