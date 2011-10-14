package testCode;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


/**
 * <pre>
 * kisti.gui.CDF
 *   |_ MemoryMonitor.java
 *
 * </pre>
 *
 * Desc :  Tracks Memory allocated & used, displayed in graph form.
 * @Company : KISTI
 * @Author :grkim
 * @Date   :2011. 7. 4. PM 4:18:40
 * @Version:
 *
 */
public class CpuMemoryMonitor extends JPanel {

    public static final String MonitoringSurface = null;
	static JCheckBox dateStampCB = new JCheckBox("Output Date Stamp");
    public MonitoringSurface  monitoring   ;
    JPanel controls;
    boolean doControls;
    JTextField tf;
    
    public static int DIMENSION_WIDTH=100;
    public static int DIMENSION_HEIGHT=300;
    
	/**
	 * 
	 * Desc : Constructor of MemoryMonitor.java class
	 */
    public CpuMemoryMonitor() {
        this.setLayout(new BorderLayout());
        //this.setBorder(new TitledBorder(new EtchedBorder(), "Memory Monitor"));
        this.add(monitoring = new MonitoringSurface());
        controls = new JPanel();
        controls.setPreferredSize(new Dimension(DIMENSION_WIDTH,DIMENSION_HEIGHT));
        Font font = new Font("serif", Font.PLAIN, 10);
        JLabel label = new JLabel("Sample Rate");
        label.setFont(font);
        label.setForeground(Color.black);
        controls.add(label);
        tf = new JTextField("1000");
        tf.setPreferredSize(new Dimension(45,20));
        controls.add( tf );
        controls.add( label = new JLabel("ms") );
        label.setFont(font);
        label.setForeground(Color.black);
        controls.add(dateStampCB);
        dateStampCB.setFont(font);
        
        
//        this.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//               removeAll();
//               if ((doControls = !doControls)) {
//                   surf.stop();
//                   add(controls);
//               } else {
//                   try { 
//                       surf.sleepAmount = Long.parseLong(tf.getText().trim());
//                   } catch (Exception ex) {}
//                   surf.start();
//                   add(surf);
//               }
//               validate();
//               repaint();
//            }
//        });
        
        
    }


    public class MonitoringSurface extends JPanel implements Runnable {

        public Thread thread;
        public long sleepAmount = 500;
        private int w, h;
        private BufferedImage bimg;
        private Graphics2D big;
        private Font font = new Font("Times New Roman", Font.PLAIN, 11);
        private Runtime r = Runtime.getRuntime();
        private int columnInc;
        private int pts[];
        private int ptNum;
        private int ascent, descent;
        private float freeMemory, totalMemory;
        private Rectangle graphOutlineRect = new Rectangle();
        private Rectangle2D mfRect = new Rectangle2D.Float();
        private Rectangle2D muRect = new Rectangle2D.Float();
        private Line2D graphLine = new Line2D.Float();
        private Color graphColor = new Color(46, 139, 87);//green
        private Color mfColor = new Color(0, 100, 0); //dark green
        private String usedStr;
      

        /**
         * 
         * Desc : Constructor of MemoryMonitor.java class
         */
        public MonitoringSurface() {
            setBackground(Color.black);
//            addMouseListener(new MouseAdapter() {
//                public void mouseClicked(MouseEvent e) {
//                    if (thread == null) start(); else stop();
//                }
//            });
        }
              
        /**
         * 
         */
        public void paint(Graphics g) {
            if (big == null) {
                return;
            }

            big.setBackground(getBackground());
            
            /*Clears the specified rectangle by filling it with the background color 
             * of the current drawing surface. This operation does not use the current paint mode*/
            big.clearRect(0,0,w,h);

            float freeMemory = (float) r.freeMemory();
            float totalMemory = (float) r.totalMemory();  
            
            System.out.println("paint");
            System.out.println(freeMemory+"-"+totalMemory);

            
            // .. Draw allocated and used strings ..
            big.setColor(Color.red);//font color	
            big.drawString(String.valueOf((int) totalMemory/1024) + "K allocated",  4.0f, (float) ascent+0.5f);
            usedStr = String.valueOf(((int) (totalMemory - freeMemory))/1024) 
                + "K used";
            
            //draw used string to bottom 
            big.drawString(usedStr, 4, h-descent);

            // Calculate remaining size
            float ssH = ascent + descent;
            float remainingHeight = (float) (h - (ssH*2) - 0.5f);
            float blockHeight = remainingHeight/10;
            float blockWidth = 20.0f;
            float remainingWidth = (float) (w - blockWidth - 10);

            // .. Memory Free ..
            big.setColor(mfColor);
            int MemUsage = (int) ((freeMemory / totalMemory) * 10);
            
            //CPU �׷���
            int i = 0, ii = 0;
            for ( ; i < MemUsage ; i++) { 
                mfRect.setRect(5,(float) ssH+i*blockHeight,
                                blockWidth,(float) blockHeight-1);
                big.fill(mfRect);
            }
            // �޸� �׷���
            for (; ii < MemUsage ; ii++) { 
                mfRect.setRect(30,(float) ssH+i*blockHeight,
                                blockWidth,(float) blockHeight-1);
                big.fill(mfRect);
            }

            // .. Memory Used ..
            big.setColor(Color.green);
            for (; i < 10; i++)  {
                muRect.setRect(5,(float) ssH+i*blockHeight,
                                blockWidth,(float) blockHeight-1);
                big.fill(muRect);
            }


            g.drawImage(bimg, 0, 0, this);
        }


        public void start() {
            thread = new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.setName("CpuMemoryMonitor");
            thread.start();
        }


        public synchronized void stop() {
            thread = null;
            notify();
        }
        
        ;


        public void run() {

            Thread me = Thread.currentThread();

            while (thread == me && !isShowing() || getSize().width == 0) {
                try {
                    thread.sleep(500);
                } catch (InterruptedException e) { return; }
            }
    
            while (thread == me && isShowing()) {
            	System.out.println("in while ---- ");
                Dimension d = getSize();
                if (d.width != w || d.height != h) {
                    w = d.width;
                    h = d.height;
                    bimg = (BufferedImage) createImage(w, h);
                    big = bimg.createGraphics();
                    big.setFont(font);
                    FontMetrics fm = big.getFontMetrics(font);
                    ascent = (int) fm.getAscent();
                    descent = (int) fm.getDescent();
                }
                validate();
                repaint();
                try {
                    thread.sleep(sleepAmount);
                } catch (InterruptedException e) { break; }
//                if (CpuMemoryMonitor.dateStampCB.isSelected()) {
//                     System.out.println(new Date().toString() + " " + usedStr);
//                }
            }
            thread = null;
        }
    }


    /**
     * 
     * Desc :
     * @Method Name : main
     * @param s
     *
     */
    public static void main(String s[]) {
        final CpuMemoryMonitor demo = new CpuMemoryMonitor();
//        WindowListener l = new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {	System.exit(0);	}
//            public void windowDeiconified(WindowEvent e) { demo.monitoringMem.start(); }
//            public void windowIconified(WindowEvent e) { demo.monitoringMem.stop(); }
//        };
//        
        JFrame f = new JFrame("Java2D Demo - MemoryMonitor");
//        f.addWindowListener(l);
        f.getContentPane().add("Center", demo);
        f.pack();
//       
//        
        f.setSize(new Dimension(DIMENSION_WIDTH, DIMENSION_HEIGHT));
		f.setVisible(true);
//		
//        demo.monitoringMem.start();
    }
}
