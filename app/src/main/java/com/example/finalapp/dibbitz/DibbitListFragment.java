package com.example.finalapp.dibbitz;

import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalapp.dibbitz.model.Dibbit;
import com.parse.ParseUser;
import java.util.List;

/**
 * Created by user on 08/10/2015.
 */
public class DibbitListFragment extends Fragment {

    private RecyclerView mDibbitRecyclerView;
    private DibbitAdapter mAdapter;
    private boolean mSubtitleVisible;
    private LinearLayout mLinearLayout;
    private Button mAddButton;
    private int mChangedPosition;

    private static final String TAG = "DibbitListFragment";
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dibbit_list, container, false);

        mDibbitRecyclerView = (RecyclerView) view.findViewById(R.id.dibbit_recycler_view);
        mDibbitRecyclerView.setAdapter(mAdapter);
        mDibbitRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mLinearLayout = (LinearLayout) view.findViewById(R.id.empty_dibbit_list);
        mAddButton = (Button) view.findViewById(R.id.add_dibbit_button);

        updateUI();


        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_dibbit_list, menu);
        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_count);
        } else {
            subtitleItem.setTitle(R.string.show_count);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_dibbit:
                addDibbit();
                return true;
            case R.id.menu_item_refresh:
                updateUI();
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            case R.id.menu_item_logout:
            logout();
            return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void updateSubtitle() {
        DibbitLab dibbitLab = DibbitLab.get(getActivity());
        int dibbitCount = dibbitLab.getDibbits().size();

        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, dibbitCount, dibbitCount);
        if (!mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    public DibbitAdapter getAdapter() {
        return mAdapter;
    }


    public void updateUI() {
        DibbitLab dibbitLab = DibbitLab.get(getActivity());
        dibbitLab.updateDibbits();

        List<Dibbit> dibbits = dibbitLab.getDibbits();


        if (mAdapter == null) {
            mAdapter = new DibbitAdapter(dibbits);
            mDibbitRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyItemChanged(mChangedPosition);
            mChangedPosition = RecyclerView.NO_POSITION; //Not sure
        }
        if (dibbits.size() > 0) {
            mLinearLayout.setVisibility(View.GONE);
        } else {
            mLinearLayout.setVisibility(View.VISIBLE);
            mAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addDibbit();
                }
            });
        }

        updateSubtitle();


    }


    private class DibbitHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Dibbit mDibbit;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mDoneCheckBox;
        private ImageButton mShareButton;
        private RelativeLayout mBackground;


        private int mLocation;

        public DibbitHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mBackground = (RelativeLayout) itemView.findViewById(R.id.list_item_background);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_dibbit_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_dibbit_date_text_view);
            mDoneCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_dibbit_done_check_box);
            mShareButton = (ImageButton) itemView.findViewById(R.id.share_button);
        }

        public void bindDibbit(final Dibbit dibbit, final int location) {
            mDibbit = dibbit;
            mLocation = location;
            if (dibbit.isPublic()){
                mBackground.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.dull_light_green));
            }
            mTitleTextView.setText(mDibbit.getTitle());
            mDateTextView.setText(android.text.format.DateFormat.format("EEEE, MMM dd, yyyy, hh:mm a", mDibbit.getDate()));

            mDoneCheckBox.setChecked(mDibbit.isDone());
            mDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mDibbit.setDone(isChecked, getContext());
                }
            });
            if (dibbit.isPublic()){
                mShareButton.setEnabled(false);
                mShareButton.setClickable(false);
                mShareButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray));

            }
            mShareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dibbit.makePublic();
                    Toast.makeText(getContext(), ("Shared"), Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onClick(View v) {
            Intent intent = DibbitPagerActivity.newIntent(getActivity(), mDibbit.getId());
            startActivity(intent);
            mChangedPosition = mLocation;
        }
    }
    public void addDibbit(){
        Dibbit dibbit = new Dibbit();
        DibbitLab.get(getActivity()).addDibbit(dibbit);
        Intent intent = DibbitPagerActivity.newIntent(getActivity(), dibbit.getId());
        startActivity(intent);
    }


    private class DibbitAdapter extends RecyclerView.Adapter<DibbitHolder> {

        private List<Dibbit> mDibbits;

        public DibbitAdapter(List<Dibbit> dibbits) {
            mDibbits = dibbits;
        }


        @Override
        public DibbitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_dibbit, parent, false);
            return new DibbitHolder(view);
        }

        @Override
        public void onBindViewHolder(DibbitHolder holder, int position) {
            Dibbit dibbit = mDibbits.get(position);
            holder.bindDibbit(dibbit, position);
        }

        @Override
        public int getItemCount() {
            return mDibbits.size();
        }
    }
    private void logout() {
        ParseUser.logOut();
        Intent intent = new Intent(getActivity(), LoginSignUpActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


}
