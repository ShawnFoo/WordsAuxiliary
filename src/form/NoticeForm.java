package form;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import event.NoticeEvent;
import event.Picture;

import model.IsOpen;
import model.Task;

/**
 * 
 * @author ShawnFoo 
 * @version  2014.7.17    1.0
 * 
 */
public class NoticeForm extends JFrame{
	/**
	 * 今日提醒界面
	 * @param tasks  所有任务列表
	 * @param list   jlabel列表
	 * @param taskList   任务列表所在面板(JPanel)
	 * @param today    今日任务列表
	 * @param tomorrow  明日任务列表
	 * @param finish    已完成任务列表
	 */
	public NoticeForm(final List<Task> tasks,final List<JLabel> list,final JPanel taskList,List<Task> today,
			List<Task> tomorrow,final List<Task> finish,final IsOpen isOpen){
		//先判断有没有添加任务
		if(tasks.size() == 0){
			JOptionPane.showMessageDialog(NoticeForm.this,"当前没有添加任务复习任务，请先添加您的复习计划"
            		,"注意", JOptionPane.ERROR_MESSAGE);
			return;
		}
		isOpen.setOpen(true);
		this.setTitle("复习提醒");
		//设置Icon	
		this.setLayout(new GridLayout(4,1,5,5));
		this.setIconImage(Picture.getIcon());		
		final NoticeEvent ne = new NoticeEvent();
		
		//组件初始化
		JPanel toJp = Picture.getBackGround(1);  //今日复习任务
		toJp.setBorder(BorderFactory.createTitledBorder("今天复习任务"));
		JPanel tmJp = Picture.getBackGround(1);  //明日复习任务
		tmJp.setBorder(BorderFactory.createTitledBorder("明天复习任务"));
		JPanel fiJp = Picture.getBackGround(1);  //已完成一次记忆循环的任务
		fiJp.setBorder(BorderFactory.createTitledBorder("已完成6次复习的任务（一次记忆曲线循环）"));
		JButton confirm = new JButton("知道了!!!");
		confirm.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		confirm.setBounds(50,30,100,30);
		final JCheckBox delete = new JCheckBox("删除已完成的任务");
		delete.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		if(finish.size() == 0)
			delete.setEnabled(false);
		final JCheckBox alert = new JCheckBox("启动程序时不再提醒");
		alert.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		if(!ne.isAutoStart())
			alert.setSelected(true);
		//数据加载
		ne.loadData(toJp,tmJp,fiJp,today,tomorrow,finish);		
		
		//布局设置		
		this.add(new JScrollPane(toJp));
		this.add(new JScrollPane(tmJp));
		this.add(new JScrollPane(fiJp));
		JPanel t1 = Picture.getBackGround(1);
		t1.setLayout(null);		
		t1.add(delete);
		delete.setOpaque(false);
		//绝对定位
		delete.setBounds(10,20,150,20);
		alert.setBounds(10,50,150,20);
		confirm.setBounds(225, 40, 90,28);
		confirm.setOpaque(false);
		alert.setOpaque(false);
		t1.add(alert);		
		t1.add(confirm);		
		this.add(t1);
		this.getContentPane().setBackground(Color.white);
		
		//事件
		confirm.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//知道了按钮事件
				if(delete.isSelected()){//删除已完成任务
					ne.deleteFinish(tasks, list, taskList, finish);
				}
				if(alert.isSelected()){//下次启动不再提醒
					ne.setNotAutoStart(true);
				}else{
					ne.setNotAutoStart(false);
				}
				NoticeForm.this.dispose();
			}
		});
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {				
				//窗体退出事件
				if(delete.isSelected()){//删除已完成任务
					ne.deleteFinish(tasks, list, taskList, finish);
				}
				if(alert.isSelected()){//下次启动不再提醒
					ne.setNotAutoStart(true);
				}else{
					ne.setNotAutoStart(false);
				}				
				NoticeForm.this.dispose();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				isOpen.setOpen(false);
			}
		});
		
		//JFrame设置		
		this.setAlwaysOnTop(true);
		this.setSize(350, 400);		
		this.setResizable(false);
		this.setLocationRelativeTo(this);
		this.setVisible(true);
		
	}
}
