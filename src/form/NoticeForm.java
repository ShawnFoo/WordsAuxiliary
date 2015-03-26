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
	 * �������ѽ���
	 * @param tasks  ���������б�
	 * @param list   jlabel�б�
	 * @param taskList   �����б��������(JPanel)
	 * @param today    ���������б�
	 * @param tomorrow  ���������б�
	 * @param finish    ����������б�
	 */
	public NoticeForm(final List<Task> tasks,final List<JLabel> list,final JPanel taskList,List<Task> today,
			List<Task> tomorrow,final List<Task> finish,final IsOpen isOpen){
		//���ж���û���������
		if(tasks.size() == 0){
			JOptionPane.showMessageDialog(NoticeForm.this,"��ǰû���������ϰ��������������ĸ�ϰ�ƻ�"
            		,"ע��", JOptionPane.ERROR_MESSAGE);
			return;
		}
		isOpen.setOpen(true);
		this.setTitle("��ϰ����");
		//����Icon	
		this.setLayout(new GridLayout(4,1,5,5));
		this.setIconImage(Picture.getIcon());		
		final NoticeEvent ne = new NoticeEvent();
		
		//�����ʼ��
		JPanel toJp = Picture.getBackGround(1);  //���ո�ϰ����
		toJp.setBorder(BorderFactory.createTitledBorder("���츴ϰ����"));
		JPanel tmJp = Picture.getBackGround(1);  //���ո�ϰ����
		tmJp.setBorder(BorderFactory.createTitledBorder("���츴ϰ����"));
		JPanel fiJp = Picture.getBackGround(1);  //�����һ�μ���ѭ��������
		fiJp.setBorder(BorderFactory.createTitledBorder("�����6�θ�ϰ������һ�μ�������ѭ����"));
		JButton confirm = new JButton("֪����!!!");
		confirm.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		confirm.setBounds(50,30,100,30);
		final JCheckBox delete = new JCheckBox("ɾ������ɵ�����");
		delete.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		if(finish.size() == 0)
			delete.setEnabled(false);
		final JCheckBox alert = new JCheckBox("��������ʱ��������");
		alert.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		if(!ne.isAutoStart())
			alert.setSelected(true);
		//���ݼ���
		ne.loadData(toJp,tmJp,fiJp,today,tomorrow,finish);		
		
		//��������		
		this.add(new JScrollPane(toJp));
		this.add(new JScrollPane(tmJp));
		this.add(new JScrollPane(fiJp));
		JPanel t1 = Picture.getBackGround(1);
		t1.setLayout(null);		
		t1.add(delete);
		delete.setOpaque(false);
		//���Զ�λ
		delete.setBounds(10,20,150,20);
		alert.setBounds(10,50,150,20);
		confirm.setBounds(225, 40, 90,28);
		confirm.setOpaque(false);
		alert.setOpaque(false);
		t1.add(alert);		
		t1.add(confirm);		
		this.add(t1);
		this.getContentPane().setBackground(Color.white);
		
		//�¼�
		confirm.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				//֪���˰�ť�¼�
				if(delete.isSelected()){//ɾ�����������
					ne.deleteFinish(tasks, list, taskList, finish);
				}
				if(alert.isSelected()){//�´�������������
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
				//�����˳��¼�
				if(delete.isSelected()){//ɾ�����������
					ne.deleteFinish(tasks, list, taskList, finish);
				}
				if(alert.isSelected()){//�´�������������
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
		
		//JFrame����		
		this.setAlwaysOnTop(true);
		this.setSize(350, 400);		
		this.setResizable(false);
		this.setLocationRelativeTo(this);
		this.setVisible(true);
		
	}
}
