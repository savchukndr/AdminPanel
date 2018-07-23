package dialog_frames;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Andrii Savchuk on 16.07.2018.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class ShowImageFrame extends JFrame{

    public ShowImageFrame(){
        setTitle("Image");
        MyCanvas m = new MyCanvas();
        add(m);
        setSize(400, 400);
        setVisible(true);
    }
}

class MyCanvas extends Canvas {

    public void paint(Graphics g) {

        Toolkit t=Toolkit.getDefaultToolkit();
        Image i=t.getImage("C:\\Users\\savch\\Pictures\\admin\\shelf.PNG");
//        Image newImage = i.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        g.drawImage(i, 0,0, 400, 400,this);

    }
    public static void main(String[] args) {
        MyCanvas m=new MyCanvas();
        JFrame f=new JFrame();
        f.add(m);
        f.setSize(400,400);
        f.setVisible(true);
    }

}
