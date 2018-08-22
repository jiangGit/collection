package com.facetuis.server.service.inviting;

import com.facetuis.server.model.inviting.InvitingImage;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.utils.Base64Util;
import com.facetuis.server.utils.RQCodeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvitingService extends BasicService {

    @Value("${sys.recommend.url}")
    private String recommendUrl;

    @Value("${sys.poster.web.path}")
    private String webPath;//生成路径

    @Value("${sys.poster.file.path}")
    private String posterPath;// 原始图片路径

    @Value("${sys.poster.url}")
    private String posterUrl;



    public Page<InvitingImage> imageList(String recommendCode){
        List<InvitingImage> list = new ArrayList<>();
        for(int i = 1;i < 13; i++){
            InvitingImage image = new InvitingImage();
            image.setUuid(i + "");
            image.setImg(i + "");
            list.add(image);
        }
        for(InvitingImage invitingImage : list){
            String image = getImage(invitingImage.getImg(), recommendCode);
            invitingImage.setImg(image);
        }
        return  new PageImpl<>(list);
    }

    public String getImage(String imagePath,String recommendCode){
        // 获取二维码图片
        String url =String.format(recommendUrl,recommendCode);
        byte[] bytes = null;
        try {
            BufferedImage encode = RQCodeUtils.encode(url);
            File file = new File(posterPath + imagePath + ".jpg");
            BufferedImage image = ImageIO.read(file);
            Graphics2D g = image.createGraphics();
            g.drawImage(encode,263,1001,230,230,null);
            g.dispose();
            String fileName = recommendCode + "-" + imagePath + ".jpg";
            writeImageLocal( webPath + fileName ,image);
            return posterUrl + fileName ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
