package alimama.bme.center.thread;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class LogSocketServer implements Runnable  
{  
    private static final Logger logger = Logger.getLogger(LogSocketServer.class);  
      
    public void run()  
    {  
        logger.debug("begin call listen log info .....");  
        ServerSocket serverSocket;  
        Socket socket = null;  
        try  
        {     
            //如果没有配置端口  则抛出异常  
            serverSocket = new ServerSocket(4445);  
              
            while (true) {     
                  
             logger.info("Waiting to accept a new client.");     
     
                socket = serverSocket.accept();     
     
                InetAddress inetAddress = socket.getInetAddress();     
     
                logger.debug("Connected to client at " + inetAddress);     
     
                logger.debug("Starting new socket node.");     
     
                new Thread(new LogSocketNode(socket)).start();     
     
            }     
        }  
        catch (Exception e)   
        {     
            logger.error("error in liston info  ", e);    
        }     
     
    }  
}  