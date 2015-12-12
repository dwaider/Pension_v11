package mvd.pension;


import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {
    private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA30VXRRfhvaGyg/ClKwLdaUAz5Umfd2xL2IA6vh0fCjSuIlIDcvOa7OfUjUGFK0lHnvUB2CGm6tBHM3JC9esCkUiFniC9VbGRAzz8X6ToEBQ97g0f2nfzYBM9byoANwvSEKB9xBBweBjKDbU2TbbUdtcpONdoPClyEQeDYRDeAvYYtO0xF/Cdkog7SHKWIz0/T8mHBEqd7t7km3eUz9YDcFlNrtY3JdAO5Mz/wOfDpNYaEBwIZMAdE2N92oqhdedNENFafe+3vuGLRU0seGuoe8msSbNUGTMqOWhHkh52bBmyNF4wPIAFnadWfg+Mh5JEMzT9WziDU7s5gwx3rIGYwQIDAQAB";
    private LicenseCheckerCallback mLicenseCheckerCallback;
    private LicenseChecker mChecker;
    private Context mContex;


    // Generate your own 20 random bytes, and put them here.
    private static final byte[] SALT = new byte[] {
        -46, 65, 30, -128, -103, -57, 74, -64, 51, 88, -95, -45, 77, -117, -36, -113, -11, 32, -64,
        89
    };
    protected Dialog onCreateDialog(int id) {
        final boolean bRetry = id == 1;
        return new AlertDialog.Builder(this)
            .setTitle(R.string.unlicensed_dialog_title)
            .setMessage(bRetry ? R.string.unlicensed_dialog_retry_body : R.string.unlicensed_dialog_body)
            .setPositiveButton(bRetry ? R.string.retry_button : R.string.buy_button, new DialogInterface.OnClickListener() {
                boolean mRetry = bRetry;
                public void onClick(DialogInterface dialog, int which) {
                    if ( mRetry ) {
                        doCheck();
                    } else {
                       // Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                       //         "http://market.android.com/details?id=" + getPackageName()));
                       //     startActivity(marketIntent);                        
                    }
                }
            })
            .setNegativeButton(R.string.quit_button, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	((Activity)mContex).finish();
                }
            }).create();
    }
	
	protected abstract Fragment createFragment();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pens);
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
			if (fragment == null) {
				fragment = createFragment();
				fm.beginTransaction()
				.add(R.id.fragmentContainer, fragment)
				.commit();
			}
		mContex = this;	
		//–≈¿À»«¿÷»ﬁ À»÷≈Õ«»» Õ¿ œŒ“ŒÃ ¬ —À≈ƒ”ﬁŸ»ﬁ ¬≈–—»ﬁ œ–»ÀŒ∆≈Õ»ﬂ
		/* new Handler();
        // Try to use more data here. ANDROID_ID is a single point of attack.
        String deviceId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

        // Library calls this when it's done.
        mLicenseCheckerCallback = MyLicenseCheckerCallback.get(this);
        // Construct the LicenseChecker with a policy.
        mChecker = new LicenseChecker(
            this, new ServerManagedPolicy(this,
                new AESObfuscator(SALT, getPackageName(), deviceId)),
            BASE64_PUBLIC_KEY);
        doCheck();*/
	}
    private void doCheck() {
        setProgressBarIndeterminateVisibility(true);
        mChecker.checkAccess(mLicenseCheckerCallback);
    }
}
