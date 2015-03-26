package form;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import event.MainEvent;
import event.NoticeEvent;
import event.Picture;

import model.IsOpen;
import model.Task;

/**
 * 
 * @author ShawnFoo 
 * @version  2014.8.7    3.2
 * 
 */
public class MainForm extends JFrame{
	//����list
	final List<Task> tasks = new ArrayList<Task>();
	//jlabel�б�
	final List<JLabel> list = new ArrayList<JLabel>();
	//���ո�ϰ�����б�
	final List<Task> today = new ArrayList<Task>();
	//���ո�ϰ�����б�
	final List<Task> tomorrow = new ArrayList<Task>();
	
	//ֻ�ܴ�һ������,���࿪
	IsOpen addForm = new IsOpen();
	IsOpen desForm = new IsOpen();
	IsOpen noticeForm = new IsOpen();
	
	public MainForm(){
		this.setTitle("ѧϰ�ƻ����Ÿ�������");

		final MainEvent mainevent = new MainEvent();
		
		//����Icon	
		this.setIconImage(Picture.getIcon());		
	    
		//��ʼ����������ݼ��ص�		
		JMenuItem description = new JMenuItem("˵��");
		description.setOpaque(false);
		description.setAccelerator(KeyStroke.getKeyStroke('Z', Event.CTRL_MASK));
		description.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		
		JMenuItem notice = new JMenuItem("��������");
		notice.setOpaque(false);
		notice.setAccelerator(KeyStroke.getKeyStroke('X', Event.CTRL_MASK));
		notice.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		
		final JMenuItem selectAll = new JMenuItem("ѡ��ȫ��");
		selectAll.setOpaque(false);
		selectAll.setAccelerator(KeyStroke.getKeyStroke('A', Event.CTRL_MASK));
		selectAll.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		
		JMenuBar jMenuBar = new JMenuBar();
		jMenuBar.setBackground(Color.WHITE);
		jMenuBar.add(description);
		jMenuBar.add(notice);
		jMenuBar.add(selectAll);		
		this.setJMenuBar(jMenuBar);
		
		final JPanel taskList = Picture.getBackGround(1);
		taskList.setBorder(BorderFactory.createTitledBorder("�������� ---- �ϴ�ѧϰ���� ---- �Ѹ�ϰ����"));
		//��������
		if(!mainevent.loadTask().equals("0")){
			mainevent.getTasksInfo(mainevent.loadTask(), tasks,list);
			taskList.setLayout(new GridLayout(list.size(),1));
			for (JLabel jl : list) {				
				taskList.add(jl);
			}
		}		
		
		JButton add = new JButton("���");//���
		add.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		JButton delete = new JButton("ɾ��");//ɾ��
		delete.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		JButton review = new JButton("��ϰ+1");//��ϰ+1
		review.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		JButton revoke = new JButton("��ϰ-1");//��ϰ-1
		revoke.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		JButton reset = new JButton("ȡ��ѡ��");//"ȡ��ѡ��"
		reset.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		
		//����
		JPanel down = new JPanel();
		down.setBackground(Color.WHITE);
		down.add(add);
		down.add(delete);
		down.add(review);
		down.add(revoke);
		down.add(reset);
		
		this.add(taskList,BorderLayout.CENTER);
		this.add(down,BorderLayout.SOUTH);
		
		//�¼�
		description.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// ˵��
				if(!desForm.isOpen()){
					new DescriptionForm(desForm);
				}				
			}
		});
		add.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//��Ӱ�ť�¼�
				if(!addForm.isOpen()){
					new AddForm(tasks,taskList,list,addForm);
				}								
			}
		});		
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				//�˳�ʱ������Ϣ
				mainevent.saveTask(tasks);
				System.exit(1);
			}
		});
		delete.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//ɾ������
				mainevent.deleteTasks(tasks, list, taskList);
			}
		});
		review.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//��ϰ������1
				mainevent.reviewTasks(tasks, list, taskList);
			}
		});
		revoke.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//��ϰ������1
				mainevent.revokeTasks(tasks, list, taskList);
			}
		});
		reset.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//ȡ��ѡ����
				mainevent.cancelSelect(list, taskList);
			}
		});
		selectAll.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectAll.getText().equals("ѡ��ȫ��")){
					mainevent.selectAll(list, taskList);
					selectAll.setText("ȡ��ѡ����");
				}else{
					mainevent.selectNull(list, taskList);
					selectAll.setText("ѡ��ȫ��");
				}
				
			}
		});
		notice.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!noticeForm.isOpen()){
					mainevent.getNeedToReview(list,taskList,tasks, today, tomorrow,noticeForm);
				}							
			}
		});
		//JFrame����	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(370, 320);		
		this.setResizable(false);
		this.setLocationRelativeTo(this);
		this.setVisible(true);
		
		NoticeEvent ne = new NoticeEvent();
		//ÿ�����ѹ���
		if(tasks.size() > 0 && ne.isAutoStart()){
			mainevent.getNeedToReview(list,taskList,tasks, today, tomorrow,noticeForm);
		}
	}
	
	public static void main(String args[]){
		new MainForm();
	}
}
