package daniel.kisti.AndroidMobile;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
        final TextView txt2 = (TextView)findViewById(R.id.txt2);
        
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.d("[cleint]", "CAF is clicked... ");
				
				Caf_Helper cafHelper = new Caf_Helper();
				
				txt.append("--------------------------------------");
				txt.append(cafHelper.getQueueStatus());
				txt.append("--------------------------------------");
				txt.append(cafHelper.getWorkernodeStatus().toString());
				txt.append("--------------------------------------");
				txt.append(cafHelper.getJobStatus().toString());
				txt.append("--------------------------------------");
				txt.append(cafHelper.getGraphData().toString());
				txt.append("--------------------------------------");
				
//				ch.getGraphData().toString();
				
			}
		});
        
        btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("[cleint]", "SAM is clicked... ");
				
				Sam_Helper samHelper = new Sam_Helper();
				samHelper.reflushData();
				
				
				txt2.append("--------------------------------------\n");
				txt2.append(Double.toString( samHelper.getCpuUse()));
				txt2.append("--------------------------------------\n");
				txt2.append(Double.toString( samHelper.getMemTotal()));
				txt2.append("--------------------------------------\n");
				txt2.append(Double.toString( samHelper.getMemUse()));
				txt2.append("--------------------------------------\n");
				txt2.append(Double.toString( samHelper.getMemUsage()));
				
				
//				txt2.append("--------------------------------------\n");
//				txt2.append(samHelper.getCdfUserDiskUsage());
//				txt2.append("--------------------------------------\n");
//				txt2.append(Double.toString( samHelper.getCdf01DiskSize()));
//				txt2.append("--------------------------------------\n");
//				txt2.append(Double.toString( samHelper.getCdf01DiskUsed()));
//				txt2.append("--------------------------------------\n");
//				txt2.append(samHelper.getCdf02DiskUsage());
//				txt2.append("--------------------------------------\n");
//				txt2.append(Double.toString( samHelper.getCdf02DiskSize()));
//				txt2.append("--------------------------------------\n");
//				txt2.append(Double.toString( samHelper.getCdf02DiskUsed()));
//				txt2.append("--------------------------------------\n");
//				txt2.append(samHelper.getCdfUserDiskUsage());
//				txt2.append("--------------------------------------\n");
//				txt2.append(Double.toString( samHelper.getCdfUserDiskSize()));
//				txt2.append("--------------------------------------\n");
//				txt2.append(Double.toString( samHelper.getCdfUserDiskUsed()));
//				txt2.append("--------------------------------------\n");
				
				;
				
				
				
			}
		});
                
        
        
    }
}