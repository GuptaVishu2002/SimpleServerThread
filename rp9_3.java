import java.io.*;
import java.net.*;
 
public class rp9_3 {
    public static void main(String args[]) {
    	FilePermission();
        try{
            ServerSocket ss = new ServerSocket(12354);
            while(true) {
                Socket s = ss.accept();
                ServerThreadSample sts = new ServerThreadSample(s);
                Thread theThread = new Thread(sts);
                theThread.start();
            }
        } catch (Exception e) {
            System.err.print(e);
        }
    }
    
    static void FilePermission(){
        FilePermission p = new FilePermission("/home/cs15701/ex9/*", "read,write");
    }
}
 
class ServerThreadSample implements Runnable {
	static int count = 0;
    Socket s;
    public ServerThreadSample(Socket socket) {
        this.s = socket;
    }
    public void run() {
        try {
                InputStream instream = s.getInputStream();
                InputStreamReader isreader = new InputStreamReader(instream);
                BufferedReader bfreader = new BufferedReader(isreader);
                
                String theLine;
                theLine = bfreader.readLine();
                theLine = theLine.substring(theLine.indexOf(" ")+1);
                theLine = theLine.substring(0,theLine.indexOf(" "));
                theLine = theLine.substring(theLine.indexOf("/")+1);
                System.out.print("filename: " + theLine + "\n");
                FileWriter fw = new FileWriter("count.txt",true);
				fw.write("A");
			    fw.close();
                File theFile = new File(theLine);
                count++;
                
                if( theFile.exists() == false ) {
                    System.err.print("File does not exist:" + theLine + " HTTP/1.0 404 NotFound\r\n\r\n");
                    s.close();
                    return;
                }
                FileInputStream finstream = new FileInputStream(theFile);
                InputStreamReader fisreader = new InputStreamReader(finstream);
                BufferedReader fbfreader = new BufferedReader(fisreader);
                
                OutputStream outstream = s.getOutputStream();
                OutputStreamWriter oswriter = new OutputStreamWriter(outstream);
                BufferedWriter bfwriter = new BufferedWriter(oswriter);
                PrintWriter pwriter = new PrintWriter(bfwriter);
                
                pwriter.print("HTTP/1.0 200 OK\r\nContent-Type: text/html\r\n\r\n");
               
                pwriter.println(count);
                pwriter.flush();
                pwriter.close();
        } catch (Exception e) {
            System.err.print(e);
        }
    }
    
    static void sleep(long msec) {
        try{
            Thread.sleep(msec);
        }catch(InterruptedException ie) {
        }
    }
    

    
}
