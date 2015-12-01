package com.example.finalapp.dibbitz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by user on 15/10/15.
 */
public class DibbitPagerActivity extends AppCompatActivity {
    private static final String EXTRA_DIBBIT_ID =
            "com.example.finalapp.dibbitz.dibbit_id";

    private ViewPager mViewPager;
    private List<Dibbit> mDibbits;

    public static Intent newIntent(Context packageContext, UUID dibbitId) {
        Intent intent = new Intent(packageContext, DibbitPagerActivity.class);
        intent.putExtra(EXTRA_DIBBIT_ID, dibbitId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dibbit_pager);

        UUID dibbitId = (UUID) getIntent().getSerializableExtra(EXTRA_DIBBIT_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_dibbit_pager_view_pager);

        mDibbits = DibbitLab.get(this).getDibbits();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Dibbit dibbit = mDibbits.get(position);
                return DibbitFragment.newInstance(dibbit.getId());
            }

            @Override
            public int getCount() {
                return mDibbits.size();
            }
        });
        for (int i = 0; i < mDibbits.size(); i++) {
            if (mDibbits.get(i).getId().equals(dibbitId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}



