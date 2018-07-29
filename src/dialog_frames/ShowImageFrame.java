package dialog_frames;

import utils.RedisUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Andrii Savchuk on 16.07.2018.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class ShowImageFrame extends JFrame {

    public ShowImageFrame(String image_id) {
        setTitle(image_id);
        MyCanvas m = new MyCanvas(image_id);
        add(m);
        setSize(400, 400);
        setVisible(true);
    }
}

class MyCanvas extends Canvas {
    private String imageID;

    MyCanvas(String image_id) {
        imageID = image_id;
    }

    private Image createImage(String imageString) {
        ByteArrayInputStream bis;
        BufferedImage bImage2 = null;
        try {
            byte[] decodedString = new sun.misc.BASE64Decoder().decodeBuffer(imageString);
            bis = new ByteArrayInputStream(decodedString);
            bImage2 = ImageIO.read(bis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bImage2;
    }

    private String getImageFromRedis(String imageID) {
        RedisUtils redis = new RedisUtils();
        return redis.getImageById(imageID);
    }

    public void paint(Graphics g) {
        Image i = createImage(getImageFromRedis(imageID));
//        Image newImage = i.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        g.drawImage(i, 0, 0, 400, 400, this);

    }

}
