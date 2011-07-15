package test_code;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

public class HTML_TEXTPANEL extends JFrame{

	
	
	public HTML_TEXTPANEL() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Desc :
	 * @Method Name : main
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 String desc;
         desc = "<html>";
         desc += 	"<head>";
         desc += 	"<body>";
         desc += 	"<center>";
         desc += 	"<img src=\'http://cdfcaf.fnal.gov/cgi-bin/caf/namgrid/graphs/system_info_bytype.py?period=86400&width=455&height=182&colors=std&class=site&type=Claimed&absolute=1&detail=full&el=FNAL-CDF&el=FNAL-GP&el=KISTI&el=MIT'>";
         //http://cdfcaf.fnal.gov/cgi-bin/caf/namgrid/graphs/system_info_bytype.py?period=86400&width=455&height=182&colors=std&class=site&type=Claimed&absolute=1&detail=full&el=FNAL-CDF&el=FNAL-GP&el=KISTI&el=MIT
         desc += 	"</b> hellow </b>";
         desc += 	"</center>";
         desc += 	"</body>";
         desc += 	"</head>";
         desc += "</html>";
         
         
        
         
         JEditorPane editorPane = new JEditorPane("text/html",  desc);
         HTML_TEXTPANEL h = new HTML_TEXTPANEL();
         h.setSize(200,200);
         h.add(editorPane);
         h.setVisible(true);

	}
}
