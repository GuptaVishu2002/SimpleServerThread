    import java.io.*;
    import java.net.*;
     
    public class ex9_0b {
        public static void main(String args[]) {
            URL theUrl;
            try{
                theUrl = new URL(args[0]);
            } catch( MalformedURLException mue ) {
                return;
            }
            try{
                HttpURLConnection huc = (HttpURLConnection)theUrl.openConnection();
                InputStream instream = huc.getInputStream();
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
