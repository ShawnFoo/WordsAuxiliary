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
		this.setTitle("说明");
		//设置Icon	
		this.setIconImage(Picture.getIcon());	
		
		//组件初始化
		String text = "<html>作者:ShawnFoo&nbsp;&nbsp;" +
				"版本：3.2<br>" +
				"告诫：请勿将本软件用于任何商业用途！！！<br>" +
				"更新内容：<br>1.新增复习次数-1功能,防止点错(thanks for the advice of Ran)" +
				"<br>2.更改了鼠标经过item时的效果，" +
				"hover时为蓝色，leave后则变回黑色" +
				"<br>3.fix添加按钮模块运行一次后不再出现的bug<br>" +
				"4.fix删除异常的特殊情况</html>";
		String text2 = "<html>好的建议请发送至：<br>fu4904@gmail.com<br>" +
				"或490461067@qq.com<br>" +
				"</html>";
		String text3 = "                          请点此处检查更新";
				
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
		//事件
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				isOpen.setOpen(false);
			}
		});
		//检查更新事件   打开用户默认浏览器
		update.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if (java.awt.Desktop.isDesktopSupported()) {  
		            try {  
		                // 创建一个URI实例  
		                java.net.URI uri = java.net.URI.create("http://pan.baidu.com/s/1qWsJBOC");  
		                // 获取当前系统桌面扩展  
		                java.awt.Desktop dp = java.awt.Desktop.getDesktop() ;   
		                // 判断系统桌面是否支持要执行的功能  
		                if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {  
		                    // 获取系统默认浏览器打开链接   
		                    dp.browse( uri );  
		                }       
		            }catch(Exception ee){   
		                ee.printStackTrace();  
		            }  
		        } 				
			}
			 
		});
		
		this.setAlwaysOnTop(true);
		
		//布局
		this.setContentPane(jp);
		
		//JFrame设置		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(200, 330);		
		this.setResizable(false);
		this.setLocationRelativeTo(this);
		this.setVisible(true);
	}
}
