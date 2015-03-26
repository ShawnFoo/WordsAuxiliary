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
		this.setTitle("����µĸ�ϰ��");		
		isOpen.setOpen(true);
		//����Icon	
		this.setIconImage(Picture.getIcon());		
		//��ʼ�����
		final AddEvent event = new AddEvent();
		final JTextField description = new JTextField(12);
		JButton add = new JButton("ȷ��");
		add.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		this.getRootPane().setDefaultButton(add);
		
		//����
		JPanel jp = new JPanel();
		JLabel jl = new JLabel("��ϰ�����ƣ�");
		jl.setFont(new Font("Dialog",Font.ROMAN_BASELINE,12));
		jp.add(jl);
		jp.add(description);
		jp.add(add);
		this.add(jp);
		
		//�¼�
		add.addActionListener(new ActionListener() {//ȷ����ť�¼�			
			@Override
			public void actionPerformed(ActionEvent e) {				
				String d = description.getText().trim();
				if(d.contains("&")||d.contains("/")||d.length()==0||d.length()>15){				
					JOptionPane.showMessageDialog(AddForm.this,"���Ʋ��ܴ���'&'��'/'�ַ�������������1~15λ�ַ�����"
		            		,"ע��", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(event.isExisted(tasks, d)){//�ж��Ƿ����ͬ��
					JOptionPane.showMessageDialog(AddForm.this,"������ͬ��������������������"
		            		,"ע��", JOptionPane.INFORMATION_MESSAGE);
					description.requestFocus();//��ý���
					description.selectAll();//ѡ��ȫ������
					return;
				}
				Task t = new Task();
				t.setDescription(d);
				//�������ڸ�ʽ
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String time = df.format(new Date());				
				t.setLastTime(time);
				t.setSum(0);
				tasks.add(0,t);				
				//���б��һ�м��ϸոմ�������������
				StringBuffer newOne = new StringBuffer();
				Task nt = tasks.get(0);
				newOne.append(nt.getDescription()+"   ----   "+nt.getLastTime()+"   ----   "+nt.getSum());
				final JLabel jb = new JLabel(newOne.toString());
				jb.setForeground(Color.BLACK);
				jb.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//�����JLabelʱ	
						if(jb.getForeground() == Color.BLACK || jb.getForeground().equals(Color.BLUE))
							jb.setForeground(Color.red);
						else
							jb.setForeground(Color.BLACK);			
					}
					@Override
					public void mouseEntered(MouseEvent e) {
						//�����JLabel�Ϸ�ʱ
						if(jb.getForeground().equals(Color.red))
							return;//��ѡ���򲻱�ɫ
						jb.setForeground(Color.BLUE);
					}
					@Override
					public void mouseExited(MouseEvent e) {
						//����뿪JLabel�Ϸ�ʱ
						if(jb.getForeground().equals(Color.red))
							return;//��ѡ���򲻱�ɫ
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
		
		//JFrame����
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(320, 70);		
		this.setResizable(false);
		this.setLocationRelativeTo(this);
		this.setVisible(true);
	}
}
