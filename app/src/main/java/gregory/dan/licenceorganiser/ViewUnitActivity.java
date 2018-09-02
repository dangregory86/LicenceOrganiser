package gregory.dan.licenceorganiser;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gregory.dan.licenceorganiser.UI.LicenceRecyclerAdapter;
import gregory.dan.licenceorganiser.Unit.Licence;
import gregory.dan.licenceorganiser.Unit.Unit;
import gregory.dan.licenceorganiser.Unit.viewModels.MyViewModel;

import static gregory.dan.licenceorganiser.AddLicenceActivity.LICENCE_SERIAL_EXTRA;
import static gregory.dan.licenceorganiser.AddUnitActivity.UNIT_NAME_EXTRA;

public class ViewUnitActivity extends AppCompatActivity implements LicenceRecyclerAdapter.ListItemClickListener{

    @BindView(R.id.view_unit_title_text_view)
    TextView mUnitTitleTextView;
    @BindView(R.id.view_unit_address_text_view)
    TextView mUnitAddressTextView;
    @BindView(R.id.view_unit_co_text_view)
    TextView mUnitCoTextView;
    @BindView(R.id.view_unit_contact_number)
    TextView mUnitContactNumber;
    @BindView(R.id.view_unit_licence_recycler_view)
    RecyclerView mUnitLicenceRecyclerView;

    private MyViewModel mMyViewModel;
    private String mUnitTitle;
    private Unit mUnit;
    private LicenceRecyclerAdapter recyclerAdapter;
    private List<Licence> mLicences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_unit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mMyViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        mUnitLicenceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new LicenceRecyclerAdapter(this);
        mUnitLicenceRecyclerView.setAdapter(recyclerAdapter);
        Intent intent = getIntent();


        if(intent.hasExtra(UNIT_NAME_EXTRA)){
            mUnitTitle = intent.getStringExtra(UNIT_NAME_EXTRA);
            getSupportActionBar().setTitle(mUnitTitle);
            new GetUnitAsyncTask(mMyViewModel).execute(mUnitTitle);
            mMyViewModel.getAllUnitLicences(mUnitTitle).observe(this, new Observer<List<Licence>>() {
                @Override
                public void onChanged(@Nullable List<Licence> licences) {
                    mLicences = licences;
                    recyclerAdapter.setLicences(licences);
                }
            });
        }else{
            finish();
        }
    }


    private void setViews(Unit unit){
        mUnit = unit;
        mUnitTitleTextView.setText(unit.unitTitle);
        mUnitAddressTextView.setText(unit.unitAddress);
        mUnitContactNumber.setText(unit.unitContactNumber);
        mUnitCoTextView.setText(unit.unitCO);

    }

    @Override
    public void onClick(int item) {
        Intent intent = new Intent(this, ViewLicenceActivity.class);
        intent.putExtra(LICENCE_SERIAL_EXTRA, mLicences.get(item).licenceSerial);
        startActivity(intent);
    }

    @OnClick(R.id.view_unit_add_licence_button)
    public void addAnotherLicence(View v){
        Intent intent = new Intent(this, AddLicenceActivity.class);
        intent.putExtra(UNIT_NAME_EXTRA, mUnitTitle);
        startActivity(intent);
    }


    private class GetUnitAsyncTask extends AsyncTask<String, Void, Unit>{
        private MyViewModel viewModel;

        GetUnitAsyncTask(MyViewModel myViewModel){
            viewModel = myViewModel;
        }


        @Override
        protected Unit doInBackground(String... strings) {
            return viewModel.loadUnitWithName(strings[0]);
        }

        @Override
        protected void onPostExecute(Unit unit) {
            mUnit = unit;
            setViews(unit);
        }
    }
}
