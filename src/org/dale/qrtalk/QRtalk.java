package org.dale.qrtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class QRtalk extends Activity {
	private EditText textView;
	private String contents, format;
	private static final int NEW_MENU_ITEM = Menu.FIRST;
	private static final int SAVE_MENU_ITEM = NEW_MENU_ITEM + 1;
	private static final int QR_MENU_ITEM = SAVE_MENU_ITEM + 1;

	
	private void updateTextField() {
		textView.setText(this.contents);
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrtalk);
        textView = (EditText) findViewById(R.id.decode_string);
        
        //Context menu
        registerForContextMenu(textView);
        
        //Click on a button to decode
        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            	intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            	startActivityForResult(intent, 0);

            }
        });
        
      //Click on a button to decode
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String qrInputText = textView.getText().toString();
                Intent intent = new Intent("com.google.zxing.client.android.ENCODE");
        		intent.addCategory(Intent.CATEGORY_DEFAULT);
        		intent.putExtra("ENCODE_TYPE", "TEXT_TYPE");
        		intent.putExtra("ENCODE_DATA", qrInputText);
        		startActivity(intent);
            }
        });
        
    }

    @Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case NEW_MENU_ITEM:
			showMsg("New");
			break;
		case SAVE_MENU_ITEM:
			showMsg("Save");
			break;
		case QR_MENU_ITEM:
			String qrInputText = textView.getText().toString();
            Intent intent = new Intent("com.google.zxing.client.android.ENCODE");
    		intent.addCategory(Intent.CATEGORY_DEFAULT);
    		intent.putExtra("ENCODE_TYPE", "TEXT_TYPE");
    		intent.putExtra("ENCODE_DATA", qrInputText);
    		startActivity(intent);
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("My Context Menu");
		menu.add(0, NEW_MENU_ITEM, 0, "New");
		menu.add(0, SAVE_MENU_ITEM, 1, "Save");
		menu.add(0, QR_MENU_ITEM, 2, "Get QR");
	}

	private void showMsg(String message) {
		Toast msg = Toast.makeText(QRtalk.this, message, Toast.LENGTH_LONG);
		msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2,
				msg.getYOffset() / 2);
		msg.show();
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
