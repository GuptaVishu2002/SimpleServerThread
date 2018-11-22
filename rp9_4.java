    import java.io.*;
    import java.net.*;
     
    public class rp9_4 {
        public static void main(String args[]) {
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
    }
     
    class ServerThreadSample implements Runnable {
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
                    if( theLine.startsWith("game?") ) {
                      gameServerSample.process(s,theLine.substring(5));
                    } else {
                      File theFile = new File(theLine);
                      if( theFile.exists() == false ) {
                        System.err.print("File does not exist:" + theLine + "\n");
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
                      do {
                        theLine = fbfreader.readLine();
                        if( theLine != null ) {
                            sleep(100);
                            pwriter.println(theLine);
                            pwriter.flush();
                        }
                      } while( theLine != null );
                    
                      pwriter.flush();
                      pwriter.close();
                    }
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
    class gameServerSample {
      public static int[] a = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
      public static synchronized void process(Socket s, String param) {
        System.err.println("param: " + param);
        if( param.charAt(0) == 'p' || param.charAt(1) == '=') {
          int p = (int)(param.charAt(2) - '0');
          if( param.charAt(3) == '&' && param.charAt(4) == 'd' && param.charAt(5) == '=') {
            int i;
            for( i = 0; i < a.length && i+6 < param.length(); i++ ) {
              a[i] = (int)(param.charAt(i+6) - '0');
            }
          }
        }
        // output
        try{
          OutputStream outstream = s.getOutputStream();
          OutputStreamWriter oswriter = new OutputStreamWriter(outstream);
          BufferedWriter bfwriter = new BufferedWriter(oswriter);
          PrintWriter pwriter = new PrintWriter(bfwriter);
         
          pwriter.print("HTTP/1.0 200 OK\r\nContent-Type: text/html\r\n\r\n");
          int j;
          for( j = 0; j < a.length; j++ ){
            pwriter.println(a[j]);
            System.err.print(a[j]);
          }
          pwriter.flush();
          pwriter.close();
        } catch (Exception e) {
          System.err.print(e);
        }
        System.err.print("\n");
      }
    }
