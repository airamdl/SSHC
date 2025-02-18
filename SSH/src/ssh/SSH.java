/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ssh;

/**
 *
 * @author airam
 */
import com.jcraft.jsch.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class SSH {

      // Variables de configuración
    private static final String USERNAME = "alumno";
    private static final String HOST = "localhost";
    private static final int PORT = 22;
    private static final String PASSWORD = "Alumno1234";
    private static final String COMMAND = "ls -la"; 

     public static void main(String[] args) {
        try {
            SSHConnector sshConnector = new SSHConnector();
            sshConnector.connect(USERNAME, PASSWORD, HOST, PORT);
            sshConnector.executeCommand(COMMAND);
            
        } catch (JSchException | IllegalAccessException | IOException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        } 
    }
    
}


class SSHConnector {
    private Session session;
    
    public void connect(String username, String password, String host, int port) throws JSchException, IllegalAccessException {
        if (this.session == null || !this.session.isConnected()) {
            JSch jsch = new JSch();
            this.session = jsch.getSession(username, host, port);
            this.session.setPassword(password);
            this.session.setConfig("StrictHostKeyChecking", "no");
            this.session.connect();
        } else {
            throw new IllegalAccessException("Sesión SSH ya iniciada");
        }
    }
    
    public final void executeCommand(String command) throws IllegalAccessException, JSchException, IOException {
        if (this.session != null && this.session.isConnected()) {
            Channel channel = this.session.openChannel("exec");
            ChannelExec channelExec = (ChannelExec) channel;
            channelExec.setCommand(command);
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect(3 * 1000);
            
        } else {
            throw new IllegalAccessException("No existe sesión SSH iniciada.");

        }
    }
}
