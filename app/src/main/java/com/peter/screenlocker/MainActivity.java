package com.peter.screenlocker;


import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

public class MainActivity extends Activity {

	public DevicePolicyManager policyManager;
	
	public ComponentName componentName;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		policyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
		componentName = new ComponentName(this, AdminReceiver.class);
		lockScreen();
	}

	public void lockScreen() {
		if(policyManager.isAdminActive(componentName)) {
			policyManager.lockNow();
			finish();
		}else {
			activeManage();
		}
	}

    private void activeManage() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.lock_str));
        startActivityForResult(intent, 19);
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 19 && resultCode == Activity.RESULT_OK) {
			Toast toast = Toast.makeText(getApplicationContext(), R.string.activite_success, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}else {
			Toast toast = Toast.makeText(getApplicationContext(), R.string.activite_fail, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
		
		finish();
	}
}
