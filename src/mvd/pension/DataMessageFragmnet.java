package mvd.pension;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DataMessageFragmnet extends DialogFragment {
	public static final String EXTRA_DATE =	"mvd.pension.datahelp";
	public static final String EXTRA_DATE_NAME ="mvd.pension.datahelp_name";

	private String mData;
	private String mData_name;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	mData = (String)getArguments().getSerializable(EXTRA_DATE);
	mData_name = (String)getArguments().getSerializable(EXTRA_DATE_NAME);
	return new AlertDialog.Builder(getActivity())
		.setTitle(mData_name).setMessage(mData)
		.setPositiveButton(android.R.string.ok, null)
		.create();
	}
	
	public static DataMessageFragmnet newInstance(String dataHelp,String dataHelp_name) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_DATE, dataHelp);
		args.putSerializable(EXTRA_DATE_NAME, dataHelp_name);
		
		DataMessageFragmnet fragment = new DataMessageFragmnet();
		fragment.setArguments(args);
		return fragment;
	}

}
