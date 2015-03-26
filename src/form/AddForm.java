package form;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import event.AddEvent;
import event.Picture;

import model.IsOpen;
import model.Task;

/**
 * 
 * @author ShawnFoo 
 * @version  2014.7.17    1.0
 * 
 */
public class AddForm extends JFrame{
	public AddForm(final List<Task> tasks,final JPanel taskList,final List<JLabel>list
			,final IsOpen isOpen){
		this.setTitle("添加新的复习项");		
		isOpen.setOpen(true);
		//设置Icon	
		this.setIconImage(Picture.getIcon());		
		//初始化组件
		final AddEvent event = new AddEvent();
		final JTextField description = new JTextField(12);
		JButton add = new JButton("确定");
		add.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		this.getRootPane().setDefaultButton(add);
		
		//布局
		JPanel jp = new JPanel();
		JLabel jl = new JLabel("复习项名称：");
		jl.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		jp.add(jl);
		jp.add(description);
		jp.add(add);
		this.add(jp);
		
		//事件
		add.addActionListener(new ActionListener() {//确定按钮事件			
			@Override
			public void actionPerformed(ActionEvent e) {				
				String d = description.getText().trim();
				if(d.contains("&")||d.contains("/")||d.length()==0||d.length()>15){				
					JOptionPane.showMessageDialog(AddForm.this,"名称不能带有'&'和'/'字符，并且名称在1~15位字符以内"
		            		,"注意", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(event.isExisted(tasks, d)){//判断是否存在同名
					JOptionPane.showMessageDialog(AddForm.this,"存在相同描述的任务，请重新输入"
		            		,"注意", JOptionPane.INFORMATION_MESSAGE);
					description.requestFocus();//获得焦点
					description.selectAll();//选中全部文字
					return;
				}
				Task t = new Task();
				t.setDescription(d);
				//设置日期格式
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String time = df.format(new Date());				
				t.setLastTime(time);
				t.setSum(0);
				tasks.add(0,t);				
				//在列表第一行加上刚刚创建的新提醒项
				StringBuffer newOne = new StringBuffer();
				Task nt = tasks.get(0);
				newOne.append(nt.getDescription()+"   ----   "+nt.getLastTime()+"   ----   "+nt.getSum());
				final JLabel jb = new JLabel(newOne.toString());
				jb.setForeground(Color.BLACK);
				jb.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//鼠标点击JLabel时	
						if(jb.getForeground() == Color.BLACK || jb.getForeground().equals(Color.BLUE))
							jb.setForeground(Color.red);
						else
							jb.setForeground(Color.BLACK);			
					}
					@Override
					public void mouseEntered(MouseEvent e) {
						//鼠标在JLabel上方时
						if(jb.getForeground().equals(Color.red))
							return;//已选中则不变色
						jb.setForeground(Color.BLUE);
					}
					@Override
					public void mouseExited(MouseEvent e) {
						//鼠标离开JLabel上方时
						if(jb.getForeground().equals(Color.red))
							return;//已选中则不变色
						jb.setForeground(Color.BLACK);
					}
				});			
				list.add(0,jb);
				taskList.setLayout(new GridLayout(list.size(),1));				
				taskList.add(list.get(0),0);				
				taskList.updateUI();		
				isOpen.setOpen(false);
				AddForm.this.dispose();				
			}
		});
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				isOpen.setOpen(false);
			}
		});
		this.setAlwaysOnTop(true);
		
		//JFrame设置
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(320, 70);		
		this.setResizable(false);
		this.setLocationRelativeTo(this);
		this.setVisible(true);
	}
}
