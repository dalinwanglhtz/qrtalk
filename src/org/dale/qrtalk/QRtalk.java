package org.dale.qrtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class QRtalk extends Activity {
	private EditText textView;
	private String contents, format;

	
	private void updateTextField() {
		textView.setText(this.contents);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrtalk);
        textView = (EditText) findViewById(R.id.decode_string);
        
        //Click on a button to initiate
        final Button button0 = (Button) findViewById(R.id.button1);
        button0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            	intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            	startActivityForResult(intent, 0);

            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_qrtalk, menu);
        return true;
    }
   

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	   if (requestCode == 0) {
    	      if (resultCode == RESULT_OK) {
    	         contents = intent.getStringExtra("SCAN_RESULT");
    	         format = intent.getStringExtra("SCAN_RESULT_FORMAT");
    	         
    	         // Handle successful scan
    	         updateTextField();
    	      } else if (resultCode == RESULT_CANCELED) {
    	         // Handle cancel
    	      }
    	   }
    }

    
}
