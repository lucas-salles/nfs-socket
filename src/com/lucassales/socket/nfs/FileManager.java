package com.lucassales.socket.nfs;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
	private static String HOME = System.getProperty("user.home");
	private Path baseDir = Paths.get(HOME + "/teste");
	
	public String readDir(String dir) throws IOException {
		Path p = Paths.get(baseDir + "/" + dir);
		
		if(Files.notExists(p)) {
			return "Diretório não existe";
		}
		
		DirectoryStream<Path> paths = Files.newDirectoryStream(p);
		
		String result = "";
		for(Path path : paths) {
			if(Files.isDirectory(path)) {
				result += path.getFileName() + "/" + "\n";
			} else {
				result += path.getFileName() + "\n";
			}
		}
		
		return result;
	}
	
	public String renameFile(String fileName, String newName) throws IOException {
		Path oldPath = Paths.get(baseDir + "/" + fileName);
		Path newPath = Paths.get(baseDir + "/" + newName);
		
		if(Files.notExists(oldPath)) {
			return "Arquivo não existe";
		}
		
		Files.move(oldPath, newPath);
		
		return "Arquivo renomeado com sucesso";
	}
	
	public String createFile(String fileName) throws IOException {
		Path p = Paths.get(baseDir + "/" + fileName);
		
		if(Files.exists(p)) {
			return "Arquivo já existe";
		}
		
		Files.createFile(p);
		
		return "Arquivo criado com sucesso";
	}
	
	public String removeFile(String fileName) throws IOException {
		Path p = Paths.get(baseDir + "/" + fileName);
		
		if(Files.notExists(p)) {
			return "Arquivo não existe";
		}
		
		Files.delete(p);
		
		return "Arquivo removido com sucesso";
	}
}
