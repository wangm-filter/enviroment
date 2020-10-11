package com.briup.util;

/**
 * @author ASUS
 */

public enum FileNamesEnum {
    //记录字节数的文件路径
    CLIENT_COUNT_PATH("client_count_path", "src/main/resources/count.txt");
    private String fileName;
    private String path;

    private FileNamesEnum(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
