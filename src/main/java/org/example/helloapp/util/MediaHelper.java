package org.example.helloapp.util;

import org.example.helloapp.models.FileType;



public class MediaHelper {

    public static FileType getMediaType(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();
        return switch (extension) {
            case "jpg", "jpeg", "png", "gif" -> FileType.IMAGE;
            case "mp4", "avi", "mov" -> FileType.VIDEO;
            case "mp3", "wav" -> FileType.AUDIO;
            case "pdf", "doc", "docx" -> FileType.DOCUMENT;
            default -> FileType.OTHER;
        };
    }

    private static String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex > 0 && lastIndex < fileName.length() - 1) {
            return fileName.substring(lastIndex + 1);
        }
        return "";
    }

}
