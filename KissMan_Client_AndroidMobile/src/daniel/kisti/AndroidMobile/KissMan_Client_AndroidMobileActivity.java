package daniel.kisti.AndroidMobile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import daniel.kisti.Helper.Caf_Helper;
import daniel.kisti.Helper.Sam_Helper;

public class KissMan_Client_AndroidMobileActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        
        final Button btn = (Button)findViewById(R.id.btn1);
        final TextView txt = (TextView)findViewById(R.id.txt1);
        final Button btn2= (Button)findViewById(R.id.btn2);      
        final Button btn3= (Button)findViewById(R.id.btn3);
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
				Sam_Helper samHelper = new Sam_Helper();
//				String status = cafHelper.getStatus();
//				String status = ">"+samHelper.getMemTotal();

//				txt.append(status);
//				samHelper.getStatus();

				txt.append(">"+samHelper.getCpuUse());
				txt.append(">"+samHelper.getMemTotal());
				txt.append(">"+samHelper.getMemUsage());
				txt.append(">"+samHelper.getMemUse());
				txt.append(">"+samHelper.getDiskIn());
				txt.append(">"+samHelper.getDiskOut());				
				txt.append(">"+samHelper.getDiskOut());
				txt.append(">"+samHelper.getNetIn());
				txt.append(">"+samHelper.getNetOut());	

				txt.append(">------------------------------------<\n");
				txt.append(">"+samHelper.getCdf01DiskSize());
				txt.append(">"+samHelper.getCdf01DiskUsed());
				txt.append(">"+samHelper.getCdf01DiskUsage());
				txt.append(">"+samHelper.getCdf02DiskSize());
				txt.append(">"+samHelper.getCdf02DiskUsed());
				txt.append(">"+samHelper.getCdf02DiskUsage());
				txt.append(">"+samHelper.getCdfUserDiskSize());
				txt.append(">"+samHelper.getCdfUserDiskUsed());
				txt.append(">"+samHelper.getCdfUserDiskUsage());

//				samHelper.loadSamDiskData();

			}
		});
        
        
        btn3.setOnClickListener(new OnClickListener() {			
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