package daniel.kisti.AndroidMobile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import daniel.kisti.Helper.Caf_Helper;

public class KissMan_Client_AndroidMobileActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        
        final Button btn = (Button)findViewById(R.id.btn1);
        final TextView txt = (TextView)findViewById(R.id.txt1);
        final Button btn2= (Button)findViewById(R.id.btn2);      
//        final TextView txt2 = (TextView)findViewById(R.id.txt2);
        final ImageView imgView = (ImageView)findViewById(R.id.imageView1);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.d("[cleint]", "CAF is clicked... ");
				
				
				Caf_Helper cafHelper = new Caf_Helper();
				
				txt.append("--------------------------------------");
				txt.append(cafHelper.getQueueStatus());
				txt.append("--------------------------------------");
				String[][] wnStatus = cafHelper.getWorkernodeStatus();
				for (int i = 0; i < wnStatus.length; i++) {
					txt.append(wnStatus[i][0]);
					txt.append(wnStatus[i][1]);
					txt.append(wnStatus[i][2]);
					txt.append(wnStatus[i][3]);
				}
				txt.append("--------------------------------------");
				String[][] jobStatus = cafHelper.getJobStatus();
				for (int i = 0; i < wnStatus.length; i++) {
					txt.append(jobStatus[i][0]+"\n");
					txt.append(jobStatus[i][1]+"\n");
					txt.append(jobStatus[i][2]+"\n");
					txt.append(jobStatus[i][3]+"\n");
					txt.append(jobStatus[i][4]+"\n");
					txt.append(jobStatus[i][5]+"\n");
					txt.append(jobStatus[i][6]+"\n");
					txt.append(jobStatus[i][7]+"\n");
				}
				
				
				txt.append("--------------------------------------");
				int[] jobGraph = cafHelper.getJobGraphData();
				for (int i = 0; i < jobGraph.length; i++) {
					txt.append( jobGraph[i]+"\n");					
				}
				
				
				
				
				
				String imgPath = cafHelper.getCeMonGraphImage();
				Bitmap img = null;      
				
				img = BitmapFactory.decodeFile(imgPath);//				
				imgView.setImageBitmap(img);				
			}
		});
        
        btn2.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("[cleint]", "SAM is clicked... ");
				txt.setText("");
				

				imgView.setImageBitmap(null);
				
			}
		});
        
     
        
        
    }
}