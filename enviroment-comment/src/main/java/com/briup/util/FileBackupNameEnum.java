package com.briup.util;

/**
 * @author ASUS
 */

@SuppressWarnings("ALL")
public enum FileBackupNameEnum {
	//备份文件的路径
	CLINT_BACKUP_PATH("clint_backup_path","src/main/resources/data.txt");
	private String name;
	private String path;
	private FileBackupNameEnum(String name, String path) {
		this.name = name;
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
