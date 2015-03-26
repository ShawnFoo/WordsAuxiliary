package event;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * ͼƬ·��
 * @author ShawnFoo 
 * @version  2014.7.17    1.0
 * 
 */
public class Picture {
	public static String picDir = System.getProperty("user.dir")+File.separator+"pic"+File.separator;
	private static String icon = "w.png";
	private static String backPic = "back.png";
	
	/**
	 * ����ͼ��
	 * @return  Imageͼ��
	 */
	public static Image getIcon(){
		 ImageIcon i = new ImageIcon(picDir+icon);
		 return i.getImage();
	}
	
	/**
	 * ���ɱ���
	 * @param mode 0==����,1==ƽ��,2==����
	 * @return  JPanel����
	 */
	public static JPanel getBackGround(final int mode){
		JPanel background = new JPanel(){
			private Image backgroundImage=null;
			
		    public void paintComponent(Graphics g){
		        super.paintComponent(g);
		        try{
		        	backgroundImage = ImageIO.read(new File(picDir + backPic));
		            int width = this.getWidth();  
		            int height = this.getHeight();  
		            int imageWidth = backgroundImage.getWidth(this);  
		            int imageHeight = backgroundImage.getHeight(this);  
		            
		            if(mode == 0){//����
		            	 int x = (width - imageWidth) / 2;  
		                 int y = (height - imageHeight) / 2;  
		                 g.drawImage(backgroundImage, x, y, this);		                   
		            }else if(mode == 1){//ƽ��
		            	for(int ix = 0; ix < width; ix += imageWidth)  
	                    {  
	                        for(int iy = 0; iy < height; iy += imageHeight)  
	                        {  
	                            g.drawImage(backgroundImage, ix, iy, this);  
	                        }  
	                    }  
		            }else{//����
		            	g.drawImage(backgroundImage, 0, 0, width, height, this);  
		            }
		        }catch(IOException e){
		            e.printStackTrace();
		        }	        
		    }
		};		
		return background;
	}

}
