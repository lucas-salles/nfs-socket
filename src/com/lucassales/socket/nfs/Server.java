package com.lucassales.socket.nfs;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");
        
        FileManager manager = new FileManager();

        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        // pegando uma refer�ncia do canal de sa�da do socket. Ao escrever nesse canal, est� se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma refer�ncia do canal de entrada do socket. Ao ler deste canal, est� se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // la�o infinito do servidor
        while (true) {
            System.out.println("Cliente: " + socket.getInetAddress());

            String message = dis.readUTF();
            
            String[] command = message.split(" ");
            String operation = "";
            String fileName = "";
            String newName = "";
            if(command.length >= 2 && command.length < 4) {
            	operation = command[0];
                fileName = command[1];
                
                if(operation.equals("rename") && command.length == 3) {
                	newName = command[2];
                }
            }
            
            switch (operation) {
				case "readdir": {
					dos.writeUTF(manager.readDir(fileName));
					break;
				}
				case "rename": {
					if(newName == "") {
						dos.writeUTF("Comando " + message + " inv�lido");
						break;
					}
					
					dos.writeUTF(manager.renameFile(fileName, newName));
					break;
				}
				case "create": {
					dos.writeUTF(manager.createFile(fileName));
					break;
				}
				case "remove": {
					dos.writeUTF(manager.removeFile(fileName));
					break;
				}
				default:
					dos.writeUTF("Comando " + message + " inv�lido");
			}
        }
        /*
         * Observe o while acima. Perceba que primeiro se l� a mensagem vinda do cliente (linha 29, depois se escreve
         * (linha 32) no canal de sa�da do socket. Isso ocorre da forma inversa do que ocorre no while do Cliente2,
         * pois, de outra forma, daria deadlock (se ambos quiserem ler da entrada ao mesmo tempo, por exemplo,
         * ningu�m evoluiria, j� que todos estariam aguardando.
         */
	}
}
