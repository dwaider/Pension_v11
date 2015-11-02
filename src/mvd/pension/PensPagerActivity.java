package mvd.pension;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;


public class PensPagerActivity  extends FragmentActivity{
	private static final int VCLADOK_INT = 3;
	private static final int VCLAD_PENS = 0;
	private static final int VCLAD_NADB = 1;
	private static final int VCLAD_RESULT = 2;
	private FragmentPagerAdapter frPageAdapter;	 
	private ViewPager mViewPager;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// Будет реализовано позднее
			if (NavUtils.getParentActivityName(this) != null) {
				NavUtils.navigateUpFromSameTask(this);	
 	 		    this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			this.getActionBar().setDisplayHomeAsUpEnabled(true);
			ActionBar actionBar = this.getActionBar();
			actionBar.setSubtitle(getResources().getString(R.string.pcalc_pod_zago_vid_pens));
		}
		//Затем мы получаем экземпляр FragmentManager для активности
		FragmentManager fm = getSupportFragmentManager();
		frPageAdapter = new FragmentPagerAdapter(fm) {
			@Override
			public Fragment getItem(int pos) {
				// TODO Auto-generated method stub
				Fragment temp = null;
			    switch (pos) {
				case VCLAD_PENS:{
					 temp = Fragment.instantiate(getApplicationContext(), PCalcPensDataFragment.class.getName());
					 break;
				     }
				case VCLAD_NADB:{
					 temp = Fragment.instantiate(getApplicationContext(), PCalcPensNadbDataFragment.class.getName());
					 break;
			        }
				case VCLAD_RESULT:{
					 temp = Fragment.instantiate(getApplicationContext(), PCalcPensResultFragment.class.getName());
				    break;    
				}
				default:
					break;
				}
				return temp;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return VCLADOK_INT;
			}
		};
		
		
		
		
		mViewPager.setAdapter(frPageAdapter);
	
		//используется для обнаружения изменений в странице
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

}
