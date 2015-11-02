package mvd.pension;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;

public class PCalcPensNadbDataFragment  extends Fragment {
	private PCalc pens;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pens = PCalc.get(getActivity());
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_nadb_calc, parent, false);
		// адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pens.getpRayonKoef());
        Spinner spNadbRaionKoeff = (Spinner) v.findViewById(R.id.spNadbRaionKoeff);
        spNadbRaionKoeff.setAdapter(adapter);
        //загрузка сохраненных данных
        spNadbRaionKoeff.setSelection(adapter.getPosition(String.valueOf((int)pens.getpRaionKoeffRas())));
        spNadbRaionKoeff.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				pens.setpRayonKoeff(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
        CheckBox chVetBoevDest = (CheckBox) v.findViewById(R.id.chVetBoevDest);
        chVetBoevDest.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				pens.setpVetBoevDeist(isChecked);
			}
		});
       	chVetBoevDest.setChecked(pens.ispVetBoevDeist());
        
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pens.getpProcentForPensii());
        Spinner spProcentForPensi = (Spinner) v.findViewById(R.id.spProcentForPensi);
        spProcentForPensi.setAdapter(adapter1);
        spProcentForPensi.setSelection(adapter1.getPosition(String.valueOf(pens.getProcentForPensii())));
        spProcentForPensi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				pens.setpProcentForPensii(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pens.getDataKolIgdevency());
        Spinner spKolIgdev = (Spinner) v.findViewById(R.id.spIgdevency);
        spKolIgdev.setAdapter(adapter2);
        spKolIgdev.setSelection(adapter2.getPosition(pens.getKolIgdevencev()));
        spKolIgdev.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				   pens.setpIgdevency(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        return v;
	}

}
