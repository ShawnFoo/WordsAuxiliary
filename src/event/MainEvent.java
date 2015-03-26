package event;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import form.NoticeForm;
import model.IsOpen;
import model.Task;

/**
 * 
 * @author ShawnFoo 
 * @version  2014.7.17    1.0
 * 1.文件中的信息格式为：
 * 不同任务之间用'^'分隔，单个任务中任务描述、任务开始时间、已复习次数各项间
 * 用'$'分隔
 * 2.任务信息文件目录在第一次保存时会自动设置在
 * C:\Users\Administrator\reciteWords下
 */
public class MainEvent {
	final String sep = File.separator;	
	final String cachePath = System.getProperty("user.dir")+sep+"cache";
	final String filePath = System.getProperty("user.dir")+sep+"cache"+sep+"list.txt";
	
	/**
	 * 从文件中加载任务
	 * @return    任务信息
	 */
	public String loadTask(){
		try {
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){//判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader br = new BufferedReader(read);
                String tasks = br.readLine();                
                read.close();
                if(tasks == null)//文件为空
                	return "0";
                return tasks;
		    }else{//如果文件不存在
		        return "0";
		    }
	    } catch (Exception e) {
	        System.out.println("读取文件内容出错");
	        e.printStackTrace();
	        return "0";
	    }
	}
		
	/**
	 * 处理文件信息，并生成对应列表
	 * @param tasks   任务信息
	 * @return	任务列表
	 */
	public void getTasksInfo(String taskInfo,List<Task> tasks,final List<JLabel> list){
		String each[] = taskInfo.split("&");		
		for(int i = 0;i < each.length;i++){
			String items[] = each[i].split("/");			
			String one = "";			
			for(int j = 0;j < items.length;j++){
				if(j < 2){
					one += items[j] + "  ----  ";
				}else{
					if(items[j].equals("6"))
						one += "已完成";
					else
						one += items[j];
				}					
			}			
			//Jlabel初始化，设置字体颜色
			final JLabel jb = new JLabel(one);			
			jb.setForeground(Color.BLACK);
			
			//以下事件若更改，则AddForm里边的事件也要更改，需保持一致
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
//					jb.setFont(new Font("Dialog",Font.BOLD,12));
					if(jb.getForeground().equals(Color.red))
						return;//已选中则不变色
					jb.setForeground(Color.BLUE);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					//鼠标离开JLabel上方时
//					jb.setFont(new Font("Dialog",Font.BOLD,12));
					if(jb.getForeground().equals(Color.red))
						return;//已选中则不变色
					jb.setForeground(Color.BLACK);
				}
			});			
			list.add(jb);
			Task t = new Task();
			t.setDescription(items[0]);
			t.setLastTime(items[1]);
			t.setSum(Integer.valueOf(items[2]));
			tasks.add(t);
		}
	}
	
	/**
	 * 将任务信息写入文件中保存
	 * @param tasks  任务列表
	 */
	public void saveTask(List<Task> tasks){		
        try {
        	File file = new File(filePath);
            if(!file.exists()){//不存在则创建
            	if(tasks.size() == 0)
        			return;
            	File directory = new File(cachePath);
	            if(!directory.exists())//不存在目录则创建
	            	directory.mkdir();	
            	file.createNewFile();
            }else{//如果存在，则判断当前是否有任务，没有则删除此文件
        		if(tasks.size() == 0){
        			file.delete();
        			return;
        		}	
            }       				
    		//处理写入内容
    		StringBuffer content = new StringBuffer();
    		for (Task task : tasks) {
    			content.append(task.getDescription()+"/"+task.getLastTime()
    						+"/"+task.getSum()+"&");
    		}
            
            //定义编码
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            BufferedWriter output = new BufferedWriter(write);
            output.write(content.toString());
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	

	/**
	 * 删除任务
	 * @param tasks 任务信息
	 * @param list  任务表
	 * @param taskList  任务列表对象
	 */
	public void deleteTasks(List<Task> tasks,List<JLabel> list,JPanel taskList){
		for (int i = 0;i < list.size();i++) {//删除选中项
			if(list.get(i).getForeground() == Color.red){				
				taskList.remove(list.get(i));
				tasks.remove(i);	
				list.remove(i);
				i--;
			}
		}
		taskList.updateUI();
	}
	
	/**
	 * 增加1次复习次数,并更新上次学习日期
	 * @param tasks 任务信息
	 * @param list  任务表
	 * @param taskList  任务列表对象
	 */
	public void reviewTasks(List<Task> tasks,List<JLabel> list,JPanel taskList){
		for (int i = 0;i < list.size();i++) {//对选中项增加1次复习次数
			if(list.get(i).getForeground() == Color.red){				
				Task t = tasks.get(i);
				int lastSum = t.getSum();
				if(lastSum == 5){//5次就已经完成了任务					
					return;
				}
				//更新复习次数和学习日期				
				t.setSum(lastSum+1);//复习次数加1
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String time = df.format(new Date());	
				t.setLastTime(time);//更新学习日期
				//拼凑新的labelText
				String LabelText = t.getDescription()+"  ----  "
						+t.getLastTime()+"  ----  ";				
				
				//更新面板数据
				if(lastSum == 4)
					list.get(i).setText(LabelText+"已完成");
				else
					list.get(i).setText(LabelText+(lastSum+1));
			}
		}
		taskList.updateUI();
	}
	
	/**
	 * 减少1次复习次数,并更新上次学习日期
	 * @param tasks 任务信息
	 * @param list  任务表
	 * @param taskList  任务列表对象
	 */
	public void revokeTasks(List<Task> tasks,List<JLabel> list,JPanel taskList){
		for (int i = 0;i < list.size();i++) {//对选中项减少1次复习次数
			if(list.get(i).getForeground() == Color.red){				
				Task t = tasks.get(i);
				int lastSum = t.getSum();
				if(lastSum == 0){					
					return;
				}
				//更新复习次数和学习日期				
				t.setSum(lastSum-1);//复习次数-1
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String time = df.format(new Date());	
				t.setLastTime(time);//更新学习日期
				//拼凑新的labelText
				String LabelText = t.getDescription()+"  ----  "
						+t.getLastTime()+"  ----  ";			
				list.get(i).setText(LabelText+(lastSum-1));
			}
		}
		taskList.updateUI();
	}
	
	/**
	 * 取消选中项
	 * @param list  任务表
	 * @param taskList  任务列表对象
	 */
	public void cancelSelect(List<JLabel> list,JPanel taskList){
		for (JLabel jl : list) {
			jl.setForeground(Color.BLACK);
		}
		taskList.updateUI();
	}
	
	/**
	 * 选中全部
	 * @param list  任务表
	 * @param taskList  任务列表对象
	 */
	public void selectAll(List<JLabel> list,JPanel taskList){
		for (JLabel jl : list) {
			jl.setForeground(Color.red);
		}
		taskList.updateUI();
	}
	
	/**
	 * 选中全部
	 * @param list  任务表
	 * @param taskList  任务列表对象
	 */
	public void selectNull(List<JLabel> list,JPanel taskList){
		for (JLabel jl : list) {
			jl.setForeground(Color.BLACK);
		}
		taskList.updateUI();
	}
	
	/**
	 * 计算需要复习事项
	 * @param list   jlabel列表
	 * @param taskList   任务列表所在面板(JPanel)
	 * @param tasks   总的任务列表
	 * @param today   今日复习任务列表
	 * @param tomorrow   明日复习任务列表
	 */
	public void getNeedToReview(List<JLabel> list,JPanel taskList,
			List<Task> tasks,List<Task> today,List<Task> tomorrow,IsOpen isOpen){
		//先清空 today tomorrow  finish列表
		today.clear();
		tomorrow.clear();		
		//int reviewGap[] = {1,2,4,7,15,25};
		int reviewGap[] = {1,1,2,3,8};//复习时间间隔
		
		List<Task> finish = new ArrayList<Task>();//完成的任务列表
		//设置日期格式
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String tmTime = df.format(calendar.getTime());//明天日期
		String toTime = df.format(new Date());//今天日期
		try {
			Date toDate = df.parse(toTime);
			Date tmDate = df.parse(tmTime);
			getReview(toDate, reviewGap, tasks, today, finish);	
			getReview(tmDate, reviewGap, tasks, tomorrow, null);
			//明日任务列表中和今日任务列表中的重复项删除
			for (int i = 0;i < tomorrow.size();i++) {
				for (Task to : today) {
					if(tomorrow.get(i).equals(to)){
						tomorrow.remove(i);//出现重复项则删除
						i--;
						break;
					}
				}
			}
			new NoticeForm(tasks,list,taskList,today, tomorrow, finish,isOpen);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	/**
	 * 判断某个任务是否需要复习,核心算法
	 * @param d1    今日或明日日期
	 * @param gap  复习提醒间隔天数
	 * @param tasks   任务列表
	 * @param TList   提醒列表
	 * @param finish   已完成的任务列表
	 * @throws ParseException   转换日期失败
	 */	 
	private void getReview(Date d1,int[] gap,List<Task> tasks,List<Task> TList,
			List<Task> finish) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long down = 1000*60*60*24;
		long daysGap = 0;
		Date temp = null;	
		for (Task task : tasks) {//遍历找出需要复习的任务
			if(task.getSum() > 4 && finish != null){//完成了一次记忆曲线的任务
				finish.add(task);   
				continue;
			}
			for(int i = 0;i < 5;i++){//复习次数遍历
				if(task.getSum() == i){
					temp = df.parse(task.getLastTime());
					if(d1.compareTo(temp) > 0){//如果d1日期大于任务起始日期
						daysGap = d1.getTime() - temp.getTime();
						daysGap /= down;//相差天数
						if(daysGap >= gap[i])//差值大于或等于间隔天数时，需要复习
							TList.add(task);
					}
					break;
				}											
			}
		}		
	}
}
