    import java.awt.*;
    import java.awt.event.*;
    import java.io.*;
    import java.net.*;
     
     
    class WPanel extends Panel {
        Panel innerPanel1;
        Panel innerPanel2;
        int colorNum = 0;
        public WPanel() {
            setLayout(null);
            innerPanel1 = new Panel();
            add(innerPanel1);
            innerPanel1.setBackground(Color.white);
     
            innerPanel2 = new Panel();
            add(innerPanel2);
            innerPanel2.setBackground(Color.white);
        }
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x,y,width,height);
            innerPanel1.setBounds(0,0,width-8,8);
            innerPanel2.setBounds(0,0,8,height-8);
        }
        public void setNColor(int n) {
          colorNum = n;
          if( n == 0 ) { setBackground(Color.gray);
          } else if( n == 1 ) { setBackground(Color.red);
          } else { setBackground(Color.blue); }
        }
        public int getNColor() { return colorNum; }
    }
     
    class rp9_b {
        static Frame myframe;
        static WPanel mypanel1;
        static WPanel mypanel2;
        static WPanel mypanel3;
        static boolean writeflag = false;
        static int player = 1;
        public static void main(String args[]) {
          main2(args);
        }
        public static void main2(String args[]) {
            myframe =  new Frame();
            mypanel1 = new WPanel();
            mypanel2 = new WPanel();
            mypanel3 = new WPanel();
            
            myframe.setLayout(null); // does not use layout manager
            myframe.setSize(400,200); // window size : width = 400, height = 200
            myframe.setVisible(true); // make the window visible
            
            myframe.add(mypanel1);
            mypanel1.setBounds(30,40,100,100);
            mypanel1.setNColor(0);
            
            myframe.add(mypanel2);
            mypanel2.setBounds(150,40,100,100);
            mypanel2.setNColor(0);
            
            myframe.add(mypanel3);
            mypanel3.setBounds(270,40,100,100);
            mypanel3.setNColor(0);
     
            // o ma ji na i !
            mypanel1.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        mypanel1MouseReleased(e);
                    }
                } );
            // o ma ji na i !
            mypanel2.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        mypanel2MouseReleased(e);
                    }
                } );
            // o ma ji na i !
            mypanel3.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        mypanel3MouseReleased(e);
                    }
                } );
            while(true) {
              sleep(100);
              checkStatus();
            }
        }
        public static void mypanel1MouseReleased(MouseEvent e) {
            mypanel1.setNColor(player); writeflag = true;
        }
        public static void mypanel2MouseReleased(MouseEvent e) {
            mypanel2.setNColor(player);writeflag = true;
        }
        public static void mypanel3MouseReleased(MouseEvent e) {
            mypanel3.setNColor(player); writeflag = true;
        }
        public static void checkStatus() {
            int cl[] = new int[3];
            String v;
            if( writeflag ) {
              v = "" + mypanel1.getNColor() + "" + mypanel2.getNColor() + "" + mypanel3.getNColor();
            } else {
              v = "";
            }
            writeflag = false;
            try{
                URL theUrl = new URL("http://127.0.0.1:12354/game?p=" + player +"&d=" + v);
                HttpURLConnection huc = (HttpURLConnection)theUrl.openConnection();
                InputStream instream = huc.getInputStream();
                InputStreamReader isreader = new InputStreamReader(instream);
                BufferedReader bfreader = new BufferedReader(isreader);
                String theLine;
                int i = 0;
                
                do {
                    theLine = bfreader.readLine();
                    if( theLine != null ) {
                          i++;
                          int c = theLine.charAt(0) - '0';
                          if( i == 1 ) {
                            mypanel1.setNColor(c);
                          } else if( i == 2 ) {
                            mypanel2.setNColor(c);
                          } else if( i == 3 ) {
                            mypanel3.setNColor(c);
                          }
                        
                    }
                } while( theLine != null );
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
    class rp9_b2 extends rp9_b {
      public static void main(String args[]) {
        player = 2;
        main2(args);
      }
    }
