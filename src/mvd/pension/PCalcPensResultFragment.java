package mvd.pension;

import mvd.pension.PCalc.ChangeParam;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PCalcPensResultFragment  extends Fragment {
//	private PcalcIntentJSONSerializer mSerializer;
	
	private TextView txRaionKoeff;
	private TextView txDolOklad;
	private TextView txZvanOklad;
	private TextView txProcNadb;
	private TextView txProcNadbName;
	private TextView txSumDenDov;
	private TextView txRazmPensVProcent;
	private TextView txRaionKoeffName;
	private TextView txDenDovForIschislPensii;
	private TextView txDenDovForIschName;
	private TextView txRasmPensii;
	private TextView txRasmPensiiUchetRaionKoeff;
	private TextView txRasmMinPensii;
	private TextView txNadb;
	private TextView txItog;
	
	
	

	private PCalc pens;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pens = PCalc.get(getActivity());
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_result_calc, parent, false);
		txDolOklad = (TextView)v.findViewById(R.id.txDolOklad);
		txZvanOklad = (TextView)v.findViewById(R.id.txZvanOklad);
		txProcNadb = (TextView)v.findViewById(R.id.txVislProcNadb);
		txRaionKoeff = (TextView)v.findViewById(R.id.txRaionKoeff);
		txSumDenDov = (TextView)v.findViewById(R.id.txSumDov);
		txRazmPensVProcent = (TextView)v.findViewById(R.id.txRazmVProcent);
		txProcNadbName = (TextView)v.findViewById(R.id.txNameProcNadb);
		txRaionKoeffName = (TextView)v.findViewById(R.id.txNameRaionKoeff);
		txDenDovForIschislPensii = (TextView)v.findViewById(R.id.txDenDovForIschislPensii);
		txDenDovForIschName = (TextView)v.findViewById(R.id.txDenDovForIsch); 
		txRasmPensii = (TextView)v.findViewById(R.id.txRasmPensii);
		txRasmPensiiUchetRaionKoeff = (TextView)v.findViewById(R.id.txRazmUchetRaionKoeff);
		txRasmMinPensii = (TextView)v.findViewById(R.id.txMinPensii);
		txItog = (TextView)v.findViewById(R.id.txItog);
	//	lstNadbavki = (ListView)v.findViewById(R.id.lstNadbvka);
		//layNadbavka = (LinearLayout)v.findViewById(R.id.layNadbvka1);
		//lstNadbavki.se
		txNadb = (TextView)v.findViewById(R.id.txNadbavka);
		UpdateTX();
		pens.setChangeParam(new ChangeParam() {
			
			@Override
			public void onChangeParam() {
				// TODO Auto-generated method stub
				UpdateTX();
			}
		});
		return v;
	}
	
	
	public void UpdateTX(){
		try {
			txRasmMinPensii.setText(String.format("%.2f",pens.getpMinPens()));
			txSumDenDov.setText(String.format("%.2f",pens.getpSumDenDov()));
			txDolOklad.setText(String.format("%.2f",pens.getpOkladDolg()));
			txZvanOklad.setText(String.format("%.2f",pens.getpOkladZvani()));
			txProcNadb.setText(String.format("%.2f",pens.getpVislLet()));
			txProcNadbName.setText(String.valueOf(
					String.format(getResources().getString(R.string.pcalc_nadb_visl_for_pensi),
							String.format("%.0f",pens.getPsVislLet()))));
			txRaionKoeffName.setText(String.valueOf(
					String.format(getResources().getString(R.string.pcalc_nadb_raion_koeff),
							String.format("%.0f",pens.getpRaionKoeffRas()))));
			txDenDovForIschislPensii.setText(String.format("%.2f",pens.getpSumDenDovForRashen()));		
			txRazmPensVProcent.setText(String.format("%.0f%%",pens.getpRasmPensiiVProcentah()));
			txRaionKoeff.setText(String.format("%.2f",pens.getRaionKoeffSum()));
			txDenDovForIschName.setText(String.valueOf(
					String.format(getResources().getString(R.string.pcalc_sum_den_dov_for_isch),
							String.format("%.2f",pens.getProcentForPensii()))));
			txRasmPensii.setText(String.format("%.2f",pens.getpRasmPensii()));
			txRasmPensiiUchetRaionKoeff.setText(String.format("%.2f",pens.getpRasmPensiiRaionKoeff()));
			txItog.setText(String.format("%.2f",pens.getpItogSum()));
			txItog.setText(String.valueOf(
					String.format(getResources().getString(R.string.pcalc_result_text),
							String.format("%.2f",pens.getpItogSum()))));
			txNadb.setText(pens.getpNadbavki_string());
		} catch (Exception e) {
			// TODO: handle exception
			//Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
}
