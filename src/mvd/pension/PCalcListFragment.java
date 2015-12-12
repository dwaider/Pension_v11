package mvd.pension;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PCalcListFragment extends ListFragment {
	private String[] VidPensii;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setHasOptionsMenu(true);
	  VidPensii =getResources().getStringArray(R.array.pcalc_ar_vid_pensii);
	  PCalcAdapter adapter = new PCalcAdapter(VidPensii);
	  setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		 String sPosition = (String) this.getListAdapter().getItem(position);
		 if (sPosition == VidPensii[0]) { //в ресурсах должна быть ссылка на выслугу
 			 Intent i = new Intent(getActivity(), PensPagerActivity.class);
 	 		 startActivity(i);
 	 		 getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		 }
		 if (sPosition == VidPensii[1]) { //в ресурсах должна быть ссылка на помощь
 			 Intent i = new Intent(getActivity(), PCalcHelpActivity.class);
 	 		 startActivity(i);
 	 		 getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		 } 
		 if (sPosition == VidPensii[2]) { //в ресурсах должна быть ссылка на помощь
 			 Intent i = new Intent(getActivity(), PCalcPayActivity.class);
 	 		 startActivity(i);
 	 		 getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		 } 
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		//inflater.inflate(R.menu.pens, menu);
	}
	
	private class PCalcAdapter extends ArrayAdapter<String> {
		public PCalcAdapter(String[] vidPensii) {
			super(getActivity(), 0, vidPensii);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Если мы не получили представление, заполняем его
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater()
				.inflate(R.layout.list_item_pcalc, null);
			}
			String c = VidPensii[position];
			TextView titleTextView = (TextView) convertView.findViewById(R.id.pcalc_list_item_titleTextView);
			titleTextView.setText(c);
			return convertView;
		}
	}
	

}
