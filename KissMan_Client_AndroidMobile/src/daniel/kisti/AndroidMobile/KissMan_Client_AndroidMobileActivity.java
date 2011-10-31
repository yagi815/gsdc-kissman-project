package daniel.kisti.AndroidMobile;

import daniel.kisti.Helper.Caf_Helper;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class KissMan_Client_AndroidMobileActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        
         Button btn = (Button)findViewById(R.id.btn1);
        final TextView txt = (TextView)findViewById(R.id.txt1);
        
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.d("[cleint]", "button is clicked... ");
				
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
                
        
        
    }
}