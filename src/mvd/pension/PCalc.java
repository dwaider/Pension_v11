package mvd.pension;

import java.io.IOException;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.util.SparseArray;

public class PCalc{
	private static final int NADBAVKA_VBD = 1;
	private static final int NADBAVKA_17B = 0; //надбавка на иждевенцев

	private static final String JSON_ID = "id";
	private static final String JSON_OKLAD_ZVAN = "zvan";
	private static final String JSON_OKLAD_ZVAN_STRING = "zvan_string";
	private static final String JSON_OKLAD_DOLGNOST = "dolgnost";
	private static final String JSON_VISLUGA_NADBF_FOR_VISL = "visluga_for_nadbaf";	
	private static final String JSON_VISLUGA_KALENDAR = "visluga_kalendar";	
	private static final String JSON_RAION_KOEFF = "raion_koeff";	
	private static final String JSON_PROCENT_FOR_PENSII = "procent_for_pensii";	//ограничени€ дл€ ден дов
	private static final String JSON_KOLICHESTVO_IGDEV = "kolichestvo_igdev";
	private static final String JSON_VBD = "vbd";
	private static final String JSON_PAY_SAVE_NADB = "pay_save_nadb";
	
	private static final String FILENAME = "penscalc.json";
	
	private float RASM_MIN_PENS;
	private String[] dataPocentRaion; 
	private String[] dataPocentForPensi; 
	private String[] dataKolIgdevency;
	private String[] dataZvanOklad;
	
	private static PCalc pPens; 
	private UUID pId;
	private Context context;
	private ChangeParam chParam;
    private float pOkladZvani = 0;
    private String pOkladZvaniString = "";
    private String pProcentForPensiiString = "";
    private float pOkladDolg = 0;
    private float pVislLet = 0;
    private float pKlandVisl = 0;
    private float pRasmPensiiVProcentah = 0;
    private float pRasVislLet = 0;
    private float pResultPens = 0;
    private float pRaionKoeffRas = 0;//%
    private float pRaionKoeffSum = 0;
    private float pSumDenDov = 0;
    private float psVislLet = 0;
    @SuppressWarnings("unused")
	private String[] pRayonKoef;
    @SuppressWarnings("unused")
	private String[] pProcentForPensi;
	private float pProcentForPensii = 0;
	private float pSumDenDovForRaschPens = 0;
	private float pRasmPensii = 0;
	private float pRasmPensiiRaionKoeff = 0;
	private float pMinPens;
	private boolean pVetBoevDeist;
	private float pNadbSum = 0;
	private float pItogSum = 0;
	private SparseArray<Float> nNadbavka;
	private int pKolIgdev = 0;
	
 	private boolean pBay_save_and_nadbav = false;

 	public void setBay_save_and_nadbav(boolean bb){
 		pBay_save_and_nadbav = bb;
 	}
    
	public boolean ispBay_save_and_nadbav() {
		return pBay_save_and_nadbav;
	}
    
    
	private PCalc(Context context) {
		// √енерирование уникального идентификатора
		pId = UUID.randomUUID();
		this.context = context;
		dataZvanOklad = context.getResources().getStringArray(R.array.pcalc_ar_data_zvan);
		dataPocentRaion = context.getResources().getStringArray(R.array.pcalc_ar_data_pocent_raion);
		dataPocentForPensi = context.getResources().getStringArray(R.array.pcalc_ar_data_pocent_for_pensi);
		dataKolIgdevency = context.getResources().getStringArray(R.array.pcalc_ar_data_kol_igd);
		RASM_MIN_PENS = Float.valueOf(context.getResources().getString(R.string.pcalc_nadb_min_pens));
		nNadbavka = new SparseArray<Float>();
	}
	
	
	public static PCalc get(Context context){
		if (pPens == null) {
			try {
				pPens = PcalcIntentJSONSerializer.get(context).loadPcalc(new PCalc(context),FILENAME);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				pPens = new PCalc(context);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				pPens = new PCalc(context);
			}
		}
		return pPens;
	}
    
	public void setpOkladZvani(float pOkladZvani) {
		RashetAll();
		this.pOkladZvani = round(pOkladZvani,2);
		notifyListener();
	}
	public float getpOkladZvani() {
		return pOkladZvani;
	}
	public String getpOkladZvanString(){
		return pOkladZvaniString;
	}
	public void setpOkladDolg(float pOkladDolg) {
		this.pOkladDolg = round(pOkladDolg,2);
		RashetAll();
		notifyListener();
	}
	public float getpOkladDolg() {
		return pOkladDolg;
	}

	public UUID getpId() {
		return pId;
	}

	public String[] getpZvanOklad() {
		return dataZvanOklad;
	}
	
	public void setpZvanOklad(int position) {
		pOkladZvaniString = dataZvanOklad[position].toString();
        String tStr = pOkladZvaniString;
		tStr = tStr.substring(tStr.indexOf(";")+1);
		this.pOkladZvani = Float.valueOf(tStr);
		RashetAll();
		notifyListener();
	}
	
	public String[] getpRayonKoef() {
		return dataPocentRaion;
	}

	public String[] getpProcentForPensii() {
		return dataPocentForPensi;
	}
	
	public float getpResultPens() {
		return round(pResultPens,2);
	}
    
	private float getProcVislLet() {
		float v = 0;
		if (pVislLet >= 2 && pVislLet < 5) {
			v = 10;
		}
		if (pVislLet >= 5 && pVislLet < 10) {
			v = 15;
		}
		if (pVislLet >= 10 && pVislLet < 15) {
			v = 20;
		}
		if (pVislLet >= 15 && pVislLet < 20) {
			v = 25;
		}
		if (pVislLet >= 20 && pVislLet < 25) {
			v = 30;
		}
		if (pVislLet >= 25) {
			v = 40;
		}
		setPsVislLet(v);
		return v;
	}
	private void RaschetRazmPens_v_Procent() {
		float v = 0;
		if (pKlandVisl >= 20){
			v = ((pKlandVisl - 20)*3)+50;
			if (v > 85) v = 85;
		};
		pRasmPensiiVProcentah = v;
	}
	
	private static float round(float number,int scale){
		int pow = 10;
		for (int i = 1; i < scale; i++) pow *= 10;
		float tmp = number * pow;
		return (float)(int)((tmp - (int) tmp) >= 0.5f ? tmp +1 : tmp) / pow; 
	}
	

//а) 10 процентов Ц при выслуге от 2 до 5 лет;
//б) 15 процентов Ц при выслуге от 5 до 10 лет;
//в) 20 процентов Ц при выслуге от 10 до 15 лет;
//г) 25 процентов Ц при выслуге от 15 до 20 лет;
//д) 30 процентов Ц при выслуге от 20 до 25 лет;
//е) 40 процентов Ц при выслуге 25 лет и более.
	private void RaschetVislLet(){
		pRasVislLet = round((pOkladDolg+pOkladZvani)*(getProcVislLet()/100),2);
	}
	private void RaschetRaionKoeff(){
		pRaionKoeffSum = round(pRasmPensii * (pRaionKoeffRas/100),2);
	}

    private void RaschetDenDov(){
    	//–асчет денежного довольстви€
    	pSumDenDov = round(pOkladDolg+pOkladZvani + pRasVislLet,2);
    }
    
    private void RaschetDenDovForRaschPens(){
    	pSumDenDovForRaschPens = round((pSumDenDov * pProcentForPensii)/100,2);
    }
    private void  RashetRasmPensii(){
    	pRasmPensii = round((pSumDenDovForRaschPens * pRasmPensiiVProcentah)/100,2);
    }
    
    private void RasmPensiiRaionKoeff(){
    	pRasmPensiiRaionKoeff = round(pRasmPensii+pRaionKoeffSum,2);	
    }

    private void RashetAll(){
    	RaschetVislLet();
    	RaschetRazmPens_v_Procent();
    	RaschetDenDov();
    	RaschetDenDovForRaschPens();
		RasMinPensii();
    	RashetRasmPensii();
    	RaschetRaionKoeff();
    	RasmPensiiRaionKoeff();
    	RashetNadavok();
    	RashetItogVipl();
		try {
			//сохранение параметров здесь можно осуществить покупку данной функции 15 руб
			PcalcIntentJSONSerializer.get(this.context).savePensCalc(pPens, FILENAME);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	/**
	 * @return the pRasmPensii
	 */
	public float getpRasmPensii() {
		RashetAll();
		return pRasmPensii;
	}	

	public float getpRasmPensiiRaionKoeff() {
		RashetAll();
		return 	pRasmPensiiRaionKoeff;
	}	


	public float getVislLetPoln() {
		return this.pVislLet;
	}
    
	public float getpVislLet() {
		return pRasVislLet;
	}

	/**
	 * @param psVislLet the psVislLet to set
	 */
	private void setPsVislLet(float psVislLet) {
		this.psVislLet = psVislLet;
		// RashetAll();
	}

	/**
	 * @return the psVislLet
	 */
	public float getPsVislLet() {
		return psVislLet;
	}

	public void setpVislLet(float pVislLet) {
		this.pVislLet = pVislLet;
		
		RashetAll();
		notifyListener();
	}
	
	public void setpRayonKoeff(int position) {
		pRaionKoeffRas = Float.valueOf(dataPocentRaion[position].toString());
		RashetAll();
		notifyListener();
	}
	
	public float getRaionKoeffSum(){
		return pRaionKoeffSum;
	}

	public void setpProcentForPensii(int position) {
		pProcentForPensiiString = dataPocentForPensi[position].toString();
        String tStr = pProcentForPensiiString;
		tStr = tStr.substring(tStr.indexOf(";")+1);
		this.pProcentForPensii = Float.valueOf(tStr);
		RashetAll();
		notifyListener();
	}

	public String[] getDataKolIgdevency() {
		return dataKolIgdevency;
	}
	
	
	public float getProcentForPensii() {
		return pProcentForPensii;
	}
	
	public float getpRaionKoeffRas() {
		return pRaionKoeffRas;
	}
	
	public float getpSumDenDovForRashen() {
	    RashetAll();
		return pSumDenDovForRaschPens;		
	}

	public float getpSumDenDov() {
		 RashetAll();
		 return pSumDenDov;
	}
	
	public void setpKlandVisl(float pKlandVisl) {
		RashetAll();
		this.pKlandVisl = pKlandVisl;
		notifyListener();
	}

	public float getpKlandVisl() {
		return pKlandVisl;
	}
	
	/**
	 * @return the pRasmPensiiVProcentah
	 */
	public float getpRasmPensiiVProcentah() {
		return pRasmPensiiVProcentah;
	}

	/**
	 * @return the pMinPens
	 */
	public float getpMinPens() {
		RasMinPensii();
		return pMinPens;
	}

	private void RasMinPensii() {
		if (pRaionKoeffRas==0) pMinPens = RASM_MIN_PENS;
		else pMinPens = round((RASM_MIN_PENS * ((pRaionKoeffRas/100)+1)),2);
	}

	public void setpVetBoevDeist(boolean pVBD) {
		pVetBoevDeist = pVBD;
        if (pVetBoevDeist==true) {
        	 float koeff = Float.valueOf(this.context.getResources().getString(R.string.pcalc_nadb_32));
        	 float pVetBoevDeist_sum = (round(RASM_MIN_PENS * (koeff/100),2));
        	 nNadbavka.put(NADBAVKA_VBD, pVetBoevDeist_sum);
        }
        else 
        	 nNadbavka.delete(NADBAVKA_VBD);
        RashetAll();
		notifyListener();
	}
	
	public String getKolIgdevencev(){
		String kolS = null;
		switch (pKolIgdev) {
			case 1: 
				kolS = "1";
				break;
			case 2: 
				kolS = "2";
				break;
			case 3: 
				kolS = "3 и более";
				break;
			default:
				break;				
		}
		return kolS;
	}
	
	public void setpIgdevency(int position) {
		if (dataKolIgdevency[position].toString().equals("3 и более")) pKolIgdev = 3;//т.к в массиве не 3 а 3 и более
		else pKolIgdev = Integer.parseInt(dataKolIgdevency[position].toString());
		//Rascht17B();
        RashetAll();
		notifyListener();
	}


	private void Rascht17B() {
		float koeff;
		float pIgdev_sum;
		switch (pKolIgdev) {
		case 0:
       	    nNadbavka.delete(NADBAVKA_17B);
			break;
		case 1:
       	    koeff = Float.valueOf(this.context.getResources().getString(R.string.pcalc_nadb_32));
       	    pIgdev_sum = (round((RASM_MIN_PENS * (koeff/100))*(1+pRaionKoeffRas/100),2));
       	    nNadbavka.put(NADBAVKA_17B, pIgdev_sum);
       	 	break;
		case 2:
       	    koeff = Float.valueOf(this.context.getResources().getString(R.string.pcalc_nadb_64));
       	    pIgdev_sum = (round((RASM_MIN_PENS * (koeff/100))*(1+pRaionKoeffRas/100),2));
       	    nNadbavka.put(NADBAVKA_17B, pIgdev_sum);
			break;
		case 3:
      	    koeff = Float.valueOf(this.context.getResources().getString(R.string.pcalc_nadb_100));
       	    pIgdev_sum = (round((RASM_MIN_PENS * (koeff/100))*(1+pRaionKoeffRas/100),2));
       	    nNadbavka.put(NADBAVKA_17B, pIgdev_sum);
			break;
		default:
			break;
		}
	}


	public String getpNadbavki_string() {
		String st ="";
		if (nNadbavka.get(NADBAVKA_VBD)!=null){
			st = st + String.format(
					context.getResources().getString(R.string.pcalc_nadb_VBD),
					String.format("%.2f",nNadbavka.get(NADBAVKA_VBD))) + ";";
		}	
		if (nNadbavka.get(NADBAVKA_17B)!=null){//иждевенцы
			st = st + String.format(
					context.getResources().getString(R.string.pcalc_nadb_17B),
					String.format("%.2f",nNadbavka.get(NADBAVKA_17B)))+ ";";
		}	
		return st; 
	}

	/**
	 * @return the pVetBoevDeist
	 */
	public boolean ispVetBoevDeist() {
		return pVetBoevDeist;
	}
	
    private void RashetNadavok(){
    	Rascht17B();
    	pNadbSum = 0;
    	for (int i = 0; i < nNadbavka.size(); i++) {
    		try {
   			    int key = nNadbavka.keyAt(i);
        		pNadbSum = pNadbSum + nNadbavka.get(key);			
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
    }
    private void RashetItogVipl(){
    	pItogSum = pRasmPensiiRaionKoeff + pNadbSum;
    	if (pMinPens > pItogSum) pItogSum = pMinPens;
    }


	/**
	 * @return the pItogSum
	 */
	public float getpItogSum() {
		return pItogSum;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_PAY_SAVE_NADB,pBay_save_and_nadbav);
		json.put(JSON_ID,  pId.toString());
		json.put(JSON_OKLAD_DOLGNOST, pOkladDolg);
		json.put(JSON_OKLAD_ZVAN, pOkladZvani);
		json.put(JSON_RAION_KOEFF, pRaionKoeffRas);
		json.put(JSON_PROCENT_FOR_PENSII, pProcentForPensii);
		json.put(JSON_VISLUGA_KALENDAR, pKlandVisl);
		json.put(JSON_VISLUGA_NADBF_FOR_VISL, pVislLet);
		json.put(JSON_VBD, pVetBoevDeist);
		json.put(JSON_KOLICHESTVO_IGDEV, pKolIgdev);
		json.put(JSON_OKLAD_ZVAN_STRING,pOkladZvaniString);
		return json;
	}
	public void loadPCalc(JSONObject json) throws JSONException {
		pBay_save_and_nadbav = json.getBoolean(JSON_PAY_SAVE_NADB);
		if (pBay_save_and_nadbav) {//если куплено сохранение и надбавки тогда загружать данные
			pId = UUID.fromString(json.getString(JSON_ID));
			pOkladDolg = (float) json.getDouble(JSON_OKLAD_DOLGNOST);
			pOkladZvani = (float) json.getDouble(JSON_OKLAD_ZVAN);
			pRaionKoeffRas = (float) json.getDouble(JSON_RAION_KOEFF);
			pProcentForPensii = (float) json.getDouble(JSON_PROCENT_FOR_PENSII);
			pKlandVisl = json.getInt(JSON_VISLUGA_KALENDAR);
			pVislLet = json.getInt(JSON_VISLUGA_NADBF_FOR_VISL);
			pVetBoevDeist = json.getBoolean(JSON_VBD);
			pKolIgdev = json.getInt(JSON_KOLICHESTVO_IGDEV);
			pOkladZvaniString = json.getString(JSON_OKLAD_ZVAN_STRING);
		}
	}


	
	//*****************************************************************************	
  	public interface ChangeParam {
  		/** @param которые изменились */
  		   void onChangeParam();
	}
  	/** @param l нового слушател€ изменений */
  	public void setChangeParam(ChangeParam l) {
  		chParam = l;
  	}
  	
  	private void notifyListener() {
  		if (null != chParam) {
  			chParam.onChangeParam();
  		}
  	}



}
