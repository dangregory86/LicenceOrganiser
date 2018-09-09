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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import gregory.dan.licenceorganiser.UI.PointRecyclerViewAdapter;
import gregory.dan.licenceorganiser.Unit.Inspection;
import gregory.dan.licenceorganiser.Unit.OutstandingPoints;
import gregory.dan.licenceorganiser.Unit.viewModels.MyViewModel;

import static gregory.dan.licenceorganiser.AddInspectionActivity.INSPECTION_EXTRA;

public class ViewInspectionActivity extends AppCompatActivity implements PointRecyclerViewAdapter.ListItemClickListener {

    private final static String TITLE_START = "Inspection carried out on: ";
    private final static String INSPECTED_BY_TEXT = "Inspection conducted by: ";

    @BindView(R.id.view_inspection_date_title_text)
    public TextView mInspectionDate;
    @BindView(R.id.view_inspection_points_recycler_view)
    public RecyclerView mRecyclerView;
    @BindView(R.id.inspected_by_text_view)
    public TextView mInspectedByTextView;
    private List<OutstandingPoints> mOutstandingPoints;

    private PointRecyclerViewAdapter mRecyclerViewAdapter;

    private MyViewModel myViewModel;

    //firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inspection);

        Intent intent = getIntent();
        if (!intent.hasExtra(INSPECTION_EXTRA)) {
            finish();
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mUser = mFirebaseAuth.getCurrentUser();

        long mInspectionIdFromIntent = intent.getLongExtra(INSPECTION_EXTRA, 1);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new PointRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        myViewModel.getAllUnitPoints(mInspectionIdFromIntent).observe(this, new Observer<List<OutstandingPoints>>() {
            @Override
            public void onChanged(@Nullable List<OutstandingPoints> outstandingPoints) {
                mOutstandingPoints = outstandingPoints;
                mRecyclerViewAdapter.setPoints(outstandingPoints);
            }
        });

        new GetInspectionAsyncTask(myViewModel).execute(mInspectionIdFromIntent);

        String inspectedByText = INSPECTED_BY_TEXT + mUser.getEmail();
        mInspectedByTextView.setText(inspectedByText);

        mInspectionDate.setText(TITLE_START);
    }

    private void setTitleText(long date) {
        Date mDate = new Date(date);
        String newInspectedDate = new SimpleDateFormat("dd-MMMM-YYYY", Locale.getDefault()).format(mDate);
        String completeTitle = TITLE_START + newInspectedDate;
        mInspectionDate.setText(completeTitle);
    }

    @Override
    public void completedClick(int item) {
        OutstandingPoints point = mOutstandingPoints.get(item);
        point.complete = 1;
        myViewModel.updatePoint(point);
        myViewModel.insertToFirebase(point);
    }

    private class GetInspectionAsyncTask extends AsyncTask<Long, Void, Inspection> {
        MyViewModel viewModel;

        GetInspectionAsyncTask(MyViewModel myViewModel) {
            viewModel = myViewModel;
        }

        @Override
        protected Inspection doInBackground(Long... id) {
            return viewModel.getInspection(id[0]);
        }

        @Override
        protected void onPostExecute(Inspection inspection) {
            setTitleText(inspection.inspectionDate);
        }
    }
}
