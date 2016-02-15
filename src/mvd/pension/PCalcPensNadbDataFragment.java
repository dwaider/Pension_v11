package mvd.pension;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;

public class PCalcPensNadbDataFragment  extends Fragment {
	private static final String DIALOG_DATA = "datahelp";


	private PCalc pens;
	private ImageButton ibtHelp5;
	private ImageButton ibtHelp6;
	private ImageButton ibtHelp7;
	private ImageButton ibtHelp8;	
    
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
				//if (position==1) bayPay();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
        CheckBox chVetBoevDest = (CheckBox) v.findViewById(R.id.chVetBoevDest);
        if (!pens.ispBay_save_and_nadbav()) {chVetBoevDest.setEnabled(false);}//если куплено
        else chVetBoevDest.setEnabled(true);
        chVetBoevDest.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				pens.setpVetBoevDeist(isChecked);
			}
		});
       	chVetBoevDest.setChecked(pens.ispVetBoevDeist());
        
       	MySppinerAdapterForProcentPens adapter1 = new MySppinerAdapterForProcentPens(getActivity(),pens.getpProcentForPensii());
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
        if (!pens.ispBay_save_and_nadbav()) {spKolIgdev.setEnabled(false);}//если куплено
        else spKolIgdev.setEnabled(true);
        spKolIgdev.setAdapter(adapter2);
        spKolIgdev.setSelection(adapter2.getPosition(pens.getKolIgdevencev()));
        spKolIgdev.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//попробуем здесь сделать биллинг на рассчет пенсии с учетом 
				   pens.setpIgdevency(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		OnClickListener d = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DataMessageFragmnet iDialog = null;
				switch(v.getId()){
	            case R.id.ibtHelpNadb1:
	            	iDialog = DataMessageFragmnet.newInstance(getString(R.string.pcalc_help_procent_for_pensi),getString(R.string.pcalc_procent_for_pensi));
	            	break;
	            case R.id.ibtHelpNadb2:
	            	iDialog = DataMessageFragmnet.newInstance(getString(R.string.pcalc_help_raion_koeff),getString(R.string.pcalc_nadb_raion_koeff_1));
	            	break;	
	            case R.id.ibtHelpNadb3:
	            	iDialog = DataMessageFragmnet.newInstance(getString(R.string.pcalc_help_nadbavka_for_igdev),getString(R.string.pcalc_nadb_17B_text));
	            	break;	
		        case R.id.ibtHelpNadb4:
		        	iDialog = DataMessageFragmnet.newInstance(getString(R.string.pcalc_help_nadbavka_vbd),getString(R.string.pcalc_nadb_vet_war));
		        	break;	
				}				
				if (iDialog != null) iDialog.show(fm, DIALOG_DATA);
			}
		};
		ibtHelp5 = (ImageButton)v.findViewById(R.id.ibtHelpNadb1);//понижающий коэффицент
		ibtHelp5.setOnClickListener(d);
		ibtHelp6 = (ImageButton)v.findViewById(R.id.ibtHelpNadb2);//районный коэффициент
		ibtHelp6.setOnClickListener(d);
		ibtHelp7 = (ImageButton)v.findViewById(R.id.ibtHelpNadb3);//иждевенцы
		ibtHelp7.setOnClickListener(d);
		ibtHelp8 = (ImageButton)v.findViewById(R.id.ibtHelpNadb4);//вбд
		ibtHelp8.setOnClickListener(d);
        return v;
	}

}
