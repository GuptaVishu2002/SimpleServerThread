    import java.io.*;
    import java.net.*;
     
    public class ex9_00 {
        public static void main(String args[]) {
            try{
                InputStream instream = System.in;
                InputStreamReader isreader = new InputStreamReader(instream);
                BufferedReader bfreader = new BufferedReader(isreader);
                String theLine;
                
                do {
                    theLine = bfreader.readLine();
                    if( theLine != null ) {
                        System.out.println(theLine);
                    }
                } while( theLine != null );
                
                
            } catch (Exception e) {
                System.err.print(e);
            }
        }
    }
