package com.example.finalapp.dibbitz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import java.util.List;
import java.util.Vector;


public class MainActivity extends AppCompatActivity implements
		OnTabChangeListener, OnPageChangeListener {

	public static FragmentManager fragmentManager;
	private TabHost tabHost;
	private ViewPager viewPager;
	private MyFragmentPagerAdapter myViewPagerAdapter;
	int i = 0;

	private String[] tabs = {"List", "Calendar", "Map"};


	// fake content for tabhost
	class FakeContent implements TabContentFactory {
		private final Context mContext;

		public FakeContent(Context context) {
			mContext = context;
		}

		@Override
		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumHeight(0);
			v.setMinimumWidth(0);
			return v;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fragmentManager = getSupportFragmentManager();
		i++;

		// init tabhost
		this.initializeTabHost(savedInstanceState);
		// init ViewPager
		this.initializeViewPager();



	}

	// Add fragments to tabhost
	private void initializeViewPager() {
		List<Fragment> fragments = new Vector<Fragment>();

		fragments.add(new DibbitListFragment());
		fragments.add(new CalendarActivity());
		fragments.add(new MapActivity());


		this.myViewPagerAdapter = new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragments);
		this.viewPager = (ViewPager) super.findViewById(R.id.view_pager);
		this.viewPager.setAdapter(this.myViewPagerAdapter);
		this.viewPager.setOnPageChangeListener(this);

		onRestart();

	}

	private void initializeTabHost(Bundle args) {

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();

		for (String tabName : tabs) {

			TabHost.TabSpec tabSpec;
			tabSpec = tabHost.newTabSpec(tabName);
			tabSpec.setIndicator(tabName);
			tabSpec.setContent(new FakeContent(this));
			tabHost.addTab(tabSpec);
		}
		tabHost.setOnTabChangedListener(this);


	}

	@Override
	public void onTabChanged(String tabId) {
		int pos = this.tabHost.getCurrentTab();
//		if (pos == 2) {
//			this.myViewPagerAdapter.fragments.remove(pos);
//			this.myViewPagerAdapter.fragments.add(new MapActivity());
//		}
//		if (pos == 0) {
//			this.myViewPagerAdapter.fragments.remove(pos);
//			this.myViewPagerAdapter.fragments.add(pos, new DibbitListFragment());
//		}

		this.viewPager.setCurrentItem(pos);


		HorizontalScrollView hScrollView = (HorizontalScrollView) findViewById(R.id.h_scroll_view);
		View tabView = tabHost.getCurrentTabView();
		int scrollPos = tabView.getLeft()
				- (hScrollView.getWidth() - tabView.getWidth()) / 2;
		hScrollView.smoothScrollTo(scrollPos, 0);

	}


	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		this.tabHost.setCurrentTab(position);

	}


	}


