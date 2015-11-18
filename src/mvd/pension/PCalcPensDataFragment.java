package mvd.pension;


import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;


public class PCalcPensDataFragment extends Fragment {
	private static final String DIALOG_DATA = "datahelp";
	
	private PCalc pens;
	private Spinner spOkladZvan;
	private EditText pOkladDolg;
	private EditText pProcentNadbv;
	private EditText pKalendVisl;
	private ImageButton ibtHelp1;
	private ImageButton ibtHelp2;
	private ImageButton ibtHelp3;
	private ImageButton ibtHelp4;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pens = PCalc.get(getActivity());
		setHasOptionsMenu(true);
    }
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_pens_calc, parent, false);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		spOkladZvan = (Spinner)v.findViewById(R.id.spPOklad_zvan);
		pOkladDolg = (EditText)v.findViewById(R.id.edPOklad_dolg);
		pProcentNadbv = (EditText)v.findViewById(R.id.edPProcent_nadb);
		pKalendVisl = (EditText)v.findViewById(R.id.edKalendVisl);

		// адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pens.getpZvanOklad());
		spOkladZvan.setAdapter(adapter);
        //загрузка сохраненных данных
		spOkladZvan.setSelection(adapter.getPosition(pens.getpOkladZvanString()));
		spOkladZvan.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				 pens.setpZvanOklad(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		if (pens.getpOkladDolg() != 0) pOkladDolg.setText(String.valueOf((int)pens.getpOkladDolg()));
		if (pens.getVislLetPoln() != 0) pProcentNadbv.setText(String.valueOf((int)pens.getVislLetPoln()));
		if (pens.getpKlandVisl() != 0) pKalendVisl.setText(String.valueOf((int)pens.getpKlandVisl()));
		
		pOkladDolg.addTextChangedListener(new GenericTextWatcher(pOkladDolg));
		pProcentNadbv.addTextChangedListener(new GenericTextWatcher(pProcentNadbv));
		pKalendVisl.addTextChangedListener(new GenericTextWatcher(pKalendVisl));
		pKalendVisl.setOnFocusChangeListener(new GenFocus());
		OnClickListener d = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DataMessageFragmnet iDialog = null;
				switch(v.getId()){
	            case R.id.ibtHelp1:
	            	iDialog = DataMessageFragmnet.newInstance(getString(R.string.pcalc_help_oklad_zvan),getString(R.string.pcalc_oklad_zavan));
	            	break;
	            case R.id.ibtHelp2:
	            	iDialog = DataMessageFragmnet.newInstance(getString(R.string.pcalc_help_oklad_dolg),getString(R.string.pcalc_oklad_dolg));
	            	break;	
	            case R.id.ibtHelp3:
	            	iDialog = DataMessageFragmnet.newInstance(getString(R.string.pcalc_help_kal_or_lgot_visl),getString(R.string.pcalc_calend_visluga));
	            	break;	
		        case R.id.ibtHelp4:
		        	iDialog = DataMessageFragmnet.newInstance(getString(R.string.pcalc_help_visl_for_procent_nadbavki),getString(R.string.pcalc_visl_for_procent_nadb));
		        	break;	
				}				
				if (iDialog != null) iDialog.show(fm, DIALOG_DATA);
			}
		};
		ibtHelp1 = (ImageButton)v.findViewById(R.id.ibtHelp1);//оклад по званию
		ibtHelp1.setOnClickListener(d);
		ibtHelp2 = (ImageButton)v.findViewById(R.id.ibtHelp2);//оклад по должности
		ibtHelp2.setOnClickListener(d);
		ibtHelp3 = (ImageButton)v.findViewById(R.id.ibtHelp3);//календ. или льгот выслуга
		ibtHelp3.setOnClickListener(d);
		ibtHelp4 = (ImageButton)v.findViewById(R.id.ibtHelp4);//выслуга дл€ % надбавки
		ibtHelp4.setOnClickListener(d);
		
		return v;
	}	
	//Declaration
	private class GenericTextWatcher implements TextWatcher{
	    private View view;
	    private GenericTextWatcher(View view) {
	        this.view = view;
	    }
	    @Override
	    public void beforeTextChanged(CharSequence ch, int i, int i1, int i2) {
	
	    }
	    public void onTextChanged(CharSequence ch, int i, int i1, int i2) {
	    	try {
		            Integer TextToFloat = Integer.parseInt(ch.toString());
			        switch(view.getId()){
			            case R.id.edPOklad_dolg:
			                pens.setpOkladDolg(TextToFloat);
			                break;
			            case R.id.edPProcent_nadb:
			                pens.setpVislLet(TextToFloat);
			                break;
			            case R.id.edKalendVisl:
			                pens.setpKlandVisl(TextToFloat);
			                break;			                
		        }
			} catch (Exception e) {
				// TODO: handle exception
				//Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
			}

	    }
	    @Override
	    public void afterTextChanged(Editable edTt) {
	    	
	    }
	}
	
	
	private class GenFocus implements View.OnFocusChangeListener{
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if (!hasFocus) {
		        switch(v.getId()){
	                case R.id.edKalendVisl:
	                   if (pens.getpKlandVisl()< 20) {
	                	   //((EditText)v).setError("ѕраво на пенсию не наступило.");
	                   }
	                   break;
		        }
			}
		}
	}
}
