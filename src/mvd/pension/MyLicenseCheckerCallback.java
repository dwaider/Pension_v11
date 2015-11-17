package mvd.pension;


import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;

import android.app.Activity;
import android.content.Context;

public class MyLicenseCheckerCallback  implements LicenseCheckerCallback {
	private static final String DIALOG_RETRY = null;
	private Context mContext;
	private static MyLicenseCheckerCallback pLicense;

	private MyLicenseCheckerCallback(Context context) {
		this.mContext = context;
	}
	

	
	public static MyLicenseCheckerCallback get(Context context){
		if (pLicense == null) {
			pLicense = new MyLicenseCheckerCallback(context);
		}
		return pLicense;
	}
	
	@Override
    public void allow(int reason) {
        if (((Activity)mContext).isFinishing()) {
            // Don't update UI if Activity is finishing.
           
            return;
        }

        // Should allow user access.
        //displayResult(mContext.getResources().getString(R.string.allow));
    }
	@SuppressWarnings("deprecation")
	@Override
    public void dontAllow(int reason) {
        if (((Activity)mContext).isFinishing()) {
            // Don't update UI if Activity is finishing.
            return;
        }
      //  displayResult(getString(R.string.dont_allow));
        
        if (reason == Policy.RETRY) {
            // If the reason received from the policy is RETRY, it was probably
            // due to a loss of connection with the service, so we should give the
            // user a chance to retry. So show a dialog to retry.
        	((Activity)mContext).showDialog(1);
        } else {
            // Otherwise, the user is not licensed to use this app.
            // Your response should always inform the user that the application
            // is not licensed, but your behavior at that point can vary. You might
            // provide the user a limited access version of your app or you can
            // take them to Google Play to purchase the app.
        	((Activity)mContext).showDialog(0);
        }
    }
	


	@Override
	public void applicationError(int errorCode) {
		// TODO Auto-generated method stub
		
	}
}
