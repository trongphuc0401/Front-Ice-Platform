package vn.edu.likelion.front_ice.common.utils;
 import java.util.Arrays;
 import java.util.List;
 import java.util.UUID;


public class HelperUtil {
    /**
     * 128 bit UUID
     */
    public static synchronized String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static boolean isImageFile(String fileName, String contentType) {
        // Kiểm tra phần mở rộng của file
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png");

        // Kiểm tra mime type (loại file)
        List<String> allowedMimeTypes = Arrays.asList("image/jpeg", "image/png");

        // Kiểm tra phần mở rộng và mime type của file
        return allowedExtensions.contains(fileExtension) && allowedMimeTypes.contains(contentType);
    }


}