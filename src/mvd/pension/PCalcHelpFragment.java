package mvd.pension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

public class PCalcHelpFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_help, parent, false);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			ActionBar actionBar = getActivity().getActionBar();
			actionBar.setSubtitle(getResources().getString(R.string.pcalc_pod_zago_help));
		}
		TextView txSpravochnik = (TextView) v.findViewById(R.id.txSpravochnikVisl);
		//Html.fromHtml(source)
		txSpravochnik.setText(Html.fromHtml(getStringFromFile(getActivity())));
		return v;
	}

	private String getStringFromFile(FragmentActivity activity) {
		// TODO Auto-generated method stub
		Resources r = activity.getResources();
		InputStream is = r.openRawResource(R.raw.spr_visl_help);
		String myText = null;
		try {
			myText = convertToString(is);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return myText;
	}

	private String convertToString(InputStream is) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream fileRaw = new ByteArrayOutputStream();
		int i = is.read();
		while( i!= -1) {
			fileRaw.write(i);
			i =is.read();
		}
		return fileRaw.toString();
	}
	
}
