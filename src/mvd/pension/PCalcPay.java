package mvd.pension;

import com.util.IabHelper;
import com.util.IabResult;
import com.util.Purchase;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class PCalcPay {
	private String TAG = "myLogPCalcPay";
	
	private Context mContext;
	private static PCalcPay pPay;
	IabHelper mHelper;
	int RC_REQUEST = 10001;
    String ID_DAR_1 = "id_dar_1";
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
	
	private PCalcPay(Context context) {
		this.mContext = context;
	}

	public static PCalcPay get(Context context){
		if (pPay == null) {
			pPay = new PCalcPay(context);
		}
		return pPay;
	}	
	
    private void InstPay() {
		// TODO Auto-generated method stub
		String base64EncodedPublicKey = "";
		mHelper = new IabHelper(mContext, base64EncodedPublicKey);
        //Log.d(TAG, "mHelper = new IabHelper(getApplicationContext(), base64EncodedPublicKey);");
		mHelper.enableDebugLogging(true); 
        Log.d(TAG, "mHelper.enableDebugLogging(true);");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {

            @Override
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) 
                {                       
                    // Произошла ошибка авторизации библиотеки     
                	Log.d(TAG, "mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener()");
                    return; 
                }  
            	Log.d(TAG, result.getMessage());
            }           
         });

		
		mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
			@Override
			public void onIabPurchaseFinished(IabResult result,
					Purchase purchase) {
				// TODO Auto-generated method stub
	            Log.d(TAG, "IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener");
	            if (mHelper == null) return;

	            if (result.isFailure()) {
//	                complain("Error purchasing: " + result);
///		                setWaitScreen(false);
	                return;
	            }
	            if (!verifyDeveloperPayload(purchase)) {
	      //          complain("Error purchasing. Authenticity verification failed.");
	     //           setWaitScreen(false);
	                return;
	            }					
			}
	    };		
	}
 
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    } 
    
	private void bayPay() {
		// TODO Auto-generated method stub
		// Объект Хелпера для взаимодействия с биллингом.
		try {
			mHelper.launchPurchaseFlow((Activity)mContext, ID_DAR_1 , RC_REQUEST, mPurchaseFinishedListener, "");	
            Log.d(TAG, "mHelper.launchPurchaseFlow(this, ID_DAR_1 , RC_REQUEST, mPurchaseFinishedListener, );");

		} catch (Exception e) {
			// TODO: handle exception
	           Log.d(TAG, e.getLocalizedMessage());
		}
	}   
	

}
