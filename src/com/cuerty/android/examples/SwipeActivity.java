package com.cuerty.android.examples;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class SwipeActivity extends SherlockFragmentActivity {
	private static final String TAG = "com.cuerty.android.examples.SwipeActivity";
	protected ViewPager viewPager;
	protected TabsAdapter tabsAdapter;
	protected MyFragment fragment1;
	protected MyFragment fragment2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		viewPager = new ViewPager(this);
		viewPager.setId(R.id.activity_swipe_pager);

		setContentView(viewPager);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(R.string.activity_swipe_title);

		fragment1 = new MyFragment();
		Bundle fragment1args = new Bundle();
		fragment1args.putInt("label_resource", R.string.fragment_myfragment_1_label);
		fragment1.setArguments(fragment1args);

		fragment2 = new MyFragment();
		Bundle fragment2args = new Bundle();
		fragment2args.putInt("label_resource", R.string.fragment_myfragment_2_label);
		fragment2.setArguments(fragment2args);

		tabsAdapter = new TabsAdapter(this, viewPager);

		// Tab 1
		Tab tab1 = getSupportActionBar().newTab();
		tab1.setText(getResources().getString(R.string.fragment_myfragment_1_label));
		tab1.setTag("tab_1");
		tab1.setTabListener(tabsAdapter);
		getSupportActionBar().addTab(tab1);

		// Tab 2
		Tab tab2 = getSupportActionBar().newTab();
		tab2.setText(getResources().getString(R.string.fragment_myfragment_2_label));
		tab2.setTag("tab_2");
		tab2.setTabListener(tabsAdapter);
		getSupportActionBar().addTab(tab2);

		Log.d(TAG, "onCreate");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		}
		return false;
	}

	public class MyFragment extends SherlockFragment {
		private int labelResource;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			this.labelResource = getArguments().getInt("label_resource");
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_myfragment, container, false);
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			TextView label = (TextView) view.findViewById(R.id.fragment_myfragment_label);
			label.setText(this.labelResource);
			super.onViewCreated(view, savedInstanceState);
		}
	}

	private static class TabsAdapter extends FragmentPagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
		private final SwipeActivity context;
		private final ActionBar actionBar;
		private final ViewPager viewPager;

		public TabsAdapter(SwipeActivity activity, ViewPager viewPager) {
			super(activity.getSupportFragmentManager());
			context = activity;
			actionBar = activity.getSupportActionBar();
			this.viewPager = viewPager;
			this.viewPager.setAdapter(this);
			this.viewPager.setOnPageChangeListener(this);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return context.fragment1;
			case 1:
				return context.fragment2;
			}
			return context.fragment1;
		}

		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		public void onPageSelected(int position) {
			actionBar.setSelectedNavigationItem(position);
		}

		public void onPageScrollStateChanged(int state) {
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Object tag = (String) tab.getTag();
			if (tag.equals("tab_1")) {
				viewPager.setCurrentItem(0);
			} else if (tag.equals("tab_2")) {
				viewPager.setCurrentItem(1);
			} else {
				viewPager.setCurrentItem(0);
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
	}
}
