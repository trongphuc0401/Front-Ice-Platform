package vn.edu.likelion.front_ice.common.utils;
 import java.time.LocalDate;
 import java.time.format.DateTimeFormatter;
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

        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png");

        List<String> allowedMimeTypes = Arrays.asList("image/jpeg", "image/png");

        return allowedExtensions.contains(fileExtension) && allowedMimeTypes.contains(contentType);
    }

    public static String generateChallengeCode(int currentCounter) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return String.format("CHAL-%s-%03d", currentDate, currentCounter);
    }

    public static String generateSolutionCode(Long challengeId, int currentCounter) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return String.format("CHAL%d-SOL-%s-%03d", challengeId, currentDate, currentCounter);
    }


}