package org.linlinjava.litemall.wx.web;

import com.github.pagehelper.util.StringUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.service.LitemallMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/wx/wxImageUpload")
@Validated
public class WxImageUpload {
    @Autowired
    private LitemallMerchantService litemallMerchantService;

    /*图片的保存路径*/
    public static String UPLOAD_FILE_BASE_PATH = "/data/pic/upload/";

    @PostMapping("/upload")
    public Object doUpload(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "imagefile", required = false) MultipartFile imagefile,
                           @RequestParam(value = "storeId", required = false) Integer storeId,
                           @RequestParam(value = "merchantPic", required = false) String merchantPic
    ) throws Exception {
        try {
            String fileName = imagefile.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀
            if (!suffixName.equals(".jpg") && !suffixName.equals(".png")) {
                return ResponseUtil.fail(401, "请上传正确格式的图片！");
            }
            BufferedImage bufferedImage = ImageIO.read(imagefile.getInputStream());
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            if (height == 0 || width == 0) {
                return ResponseUtil.fail(401, "请上传正确的图片文件！");
            }

            if (!StringUtil.isEmpty(merchantPic)) {

                System.out.println("merchantPic=" + merchantPic);
                String substring = merchantPic.substring(merchantPic.lastIndexOf("/"));
                System.out.println("substring=" + substring);
                fileName = substring.substring(1);

            }
            fileName = UUID.randomUUID() + suffixName; // 新图片进行生产

            //String filePath = "D://images/upload//"; // 上传后的路径
            File dest = new File(UPLOAD_FILE_BASE_PATH + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                imagefile.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            merchantPic = "http://39.97.235.28:8080/pictures/" + fileName;

            litemallMerchantService.updateMerchantPic(storeId, merchantPic);
            return ResponseUtil.ok(merchantPic);
        } catch (Exception e) {
            return "上传失败";
        }
    }


    @PostMapping("/vueUpload")
    public Object vueUpload(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "imagefile", required = false) MultipartFile imagefile,
                            @RequestParam(value = "merchantPic", required = false) String merchantPic,
                            @RequestParam(value = "storeId", required = false) String storeId
    ) throws Exception {
        try {
            String fileName = imagefile.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀
            if (!suffixName.equals(".jpg") && !suffixName.equals(".png")) {
                return ResponseUtil.fail(401, "请上传正确格式的图片！");
            }
            BufferedImage bufferedImage = ImageIO.read(imagefile.getInputStream());
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            if (height == 0 || width == 0) {
                return ResponseUtil.fail(401, "请上传正确的图片文件！");
            }

            if (!StringUtil.isEmpty(merchantPic)) {
                if (merchantPic.equals("undefined")) {
                    fileName = UUID.randomUUID() + suffixName; // 新图片进行生产
                } else {
                    System.out.println("merchantPic=" + merchantPic);
                    String substring = merchantPic.substring(merchantPic.lastIndexOf("/"));
                    System.out.println("substring=" + substring);
                    fileName = substring.substring(1);
                }
            } else {
                fileName = UUID.randomUUID() + suffixName; // 新图片进行生产
            }
            String filePath = "D://images/upload//"; // 上传后的路径
            File dest = new File(UPLOAD_FILE_BASE_PATH + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                imagefile.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!StringUtil.isEmpty(storeId)) {
                litemallMerchantService.updateMerchantPic(Integer.parseInt(storeId), merchantPic);
            }
            fileName = "http://39.97.235.28:8080/pictures/" + fileName;
            return ResponseUtil.ok(fileName);
        } catch (Exception e) {
            return "上传失败";
        }
    }

    @PostMapping("/businessLicensesPicUpload")
    public Object businessLicensesPicUpload(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "imagefile", required = false) MultipartFile imagefile,
                            @RequestParam(value = "merchantPic", required = false) String merchantPic,
                            @RequestParam(value = "storeId", required = false) String storeId
    ) throws Exception {
        try {
            String fileName = imagefile.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀
            if (!suffixName.equals(".jpg") && !suffixName.equals(".png")) {
                return ResponseUtil.fail(401, "请上传正确格式的图片！");
            }
            BufferedImage bufferedImage = ImageIO.read(imagefile.getInputStream());
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            if (height == 0 || width == 0) {
                return ResponseUtil.fail(401, "请上传正确的图片文件！");
            }

            if (!StringUtil.isEmpty(merchantPic)) {
                if (merchantPic.equals("undefined")) {
                    fileName = UUID.randomUUID() + suffixName; // 新图片进行生产
                } else {
                    System.out.println("merchantPic=" + merchantPic);
                    String substring = merchantPic.substring(merchantPic.lastIndexOf("/"));
                    System.out.println("substring=" + substring);
                    fileName = substring.substring(1);
                }
            } else {
                fileName = UUID.randomUUID() + suffixName; // 新图片进行生产
            }
            //String filePath = "D://images/upload//"; // 上传后的路径
            File dest = new File(UPLOAD_FILE_BASE_PATH + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                imagefile.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileName = "http://39.97.235.28:8080/pictures/" + fileName;
//            fileName = filePath + fileName;
            System.out.println("merchantPic="+merchantPic);
            if (!StringUtil.isEmpty(storeId)) {
                litemallMerchantService.updateMerchantCodePic(Integer.parseInt(storeId), fileName);
            }
            return ResponseUtil.ok(fileName);
        } catch (Exception e) {
            return "上传失败";
        }
    }
}
