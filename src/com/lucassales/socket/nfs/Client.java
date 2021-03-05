package com.lucassales.socket.nfs;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws IOException {
		System.out.println("\n=== LUCAS SILVA - NFS - COMANDOS: ===");
        System.out.println("readdir nomeDiret�rio");
        System.out.println("rename nomeArquivo.txt novoNome.txt");
        System.out.println("create nomeArquivo.txt");
        System.out.println("remove nomeArquivo.txt");

        // configurando o socket
        Socket socket = new Socket("127.0.0.1", 7001);
        // pegando uma refer�ncia do canal de sa�da do socket. Ao escrever nesse canal, est� se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma refer�ncia do canal de entrada do socket. Ao ler deste canal, est� se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // la�o infinito do cliente
        while (true) {
            Scanner teclado = new Scanner(System.in);
            // escrevendo para o servidor
            dos.writeUTF(teclado.nextLine());

            // lendo o que o servidor enviou
            String mensagem = dis.readUTF();
            System.out.println("Servidor falou: \n" + mensagem);
        }
        /*
         * Observe o while acima. Perceba que primeiro se escreve para o servidor (linha 27), depois se l� do canal de
         * entrada (linha 30), vindo do servidor. Agora observe o c�digo while do Servidor2. L�, primeiro se l�,
         * depois se escreve. De outra forma, haveria um deadlock.
         */
    }
}
