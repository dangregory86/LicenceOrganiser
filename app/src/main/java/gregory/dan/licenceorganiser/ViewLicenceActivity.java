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
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gregory.dan.licenceorganiser.UI.AmmoRecyclerViewAdapter;
import gregory.dan.licenceorganiser.Unit.Ammunition;
import gregory.dan.licenceorganiser.Unit.Licence;
import gregory.dan.licenceorganiser.Unit.viewModels.MyViewModel;

import static gregory.dan.licenceorganiser.AddLicenceActivity.LICENCE_SERIAL_EXTRA;

public class ViewLicenceActivity extends AppCompatActivity implements AmmoRecyclerViewAdapter.ListItemClickListener {

    private MyViewModel myViewModel;
    private Licence mLicence;
    private String mLicenceSerialNo;
    private AmmoRecyclerViewAdapter recyclerViewAdapter;
    private List<Ammunition> ammo;

    @BindView(R.id.view_licence_expiry_date_text_view2)
    public
    TextView mExpiryDateTextView;
    @BindView(R.id.view_licence_issue_date_text_view4)
    public
    TextView mIssueDateTextView;
    @BindView(R.id.view_licence_serial_text_view)
    public
    TextView mSerialTextView;
    @BindView(R.id.view_licence_type_text_view3)
    public
    TextView mLicenceTypeTextView;
    @BindView(R.id.view_licence_ammo_allowed_title_text_view)
    public
    TextView mAmmoAllowedTitleText;
    @BindView(R.id.view_licence_ammo_recycler_view)
    public
    RecyclerView mRecyclerView;
    @BindView(R.id.view_licence_add_ammo_button)
    public
    Button mAddAmmoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_licence);

        Intent intent = getIntent();
        if (!intent.hasExtra(LICENCE_SERIAL_EXTRA)) {
            finish();
        }
        mLicenceSerialNo = intent.getStringExtra(LICENCE_SERIAL_EXTRA);

        ButterKnife.bind(this);
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        mSerialTextView.setText(mLicenceSerialNo);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new AmmoRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(recyclerViewAdapter);


        new GetLicenceAsyncTask(myViewModel).execute(mLicenceSerialNo);
        myViewModel.getAllUnitAmmunition(mLicenceSerialNo).observe(this, new Observer<List<Ammunition>>() {
            @Override
            public void onChanged(@Nullable List<Ammunition> ammunitions) {
                ammo = ammunitions;
                recyclerViewAdapter.setAmmunition(ammo);
            }
        });

    }

    private void instatiateViews(Licence licence) {
        if (licence.licenceType.equals("AQ25")) {
            mAmmoAllowedTitleText.setVisibility(View.VISIBLE);
            mAddAmmoButton.setVisibility(View.VISIBLE);
        }
        long mIssueDateRaw = licence.licenceIssueDate;
        Date mIssueDate = new Date(mIssueDateRaw);
        String issueDate = new SimpleDateFormat("dd-MMMM-YYYY", Locale.getDefault()).format(mIssueDate);
        long mExpiryDateRaw = licence.licenceRenewalDate;
        Date mExpiryDate = new Date(mExpiryDateRaw);
        String expiryDate = new SimpleDateFormat("dd-MMMM-YYYY", Locale.getDefault()).format(mExpiryDate);
        String mLicenceType = licence.licenceType;

        mExpiryDateTextView.setText(expiryDate);
        mIssueDateTextView.setText(issueDate);
        mLicenceTypeTextView.setText(mLicenceType);
    }

    @OnClick(R.id.view_licence_add_ammo_button)
    public void addAmmo() {
        Intent intent = new Intent(this, AddAmmoActivity.class);
        intent.putExtra(LICENCE_SERIAL_EXTRA, mLicenceSerialNo);
        startActivity(intent);
    }

    @Override
    public void onClick(int item) {

    }

    @OnClick(R.id.delete_licence_button)
    public void deleteLicence() {
        myViewModel.deleteUnitLicence(mLicence);
        myViewModel.deleteFromFirebase(mLicence);
        finish();
    }


    private class GetLicenceAsyncTask extends AsyncTask<String, Void, Licence> {

        private MyViewModel viewModel;

        GetLicenceAsyncTask(MyViewModel myViewModel) {
            viewModel = myViewModel;
        }

        @Override
        protected Licence doInBackground(String... strings) {
            return viewModel.getIndividualLicence(strings[0]);
        }

        @Override
        protected void onPostExecute(Licence licence) {
            mLicence = licence;
            instatiateViews(licence);
        }
    }
}
