package gregory.dan.licenceorganiser;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gregory.dan.licenceorganiser.UI.PointRecyclerViewAdapter;
import gregory.dan.licenceorganiser.Unit.OutstandingPoints;
import gregory.dan.licenceorganiser.Unit.viewModels.MyViewModel;

import static gregory.dan.licenceorganiser.AddInspectionActivity.INSPECTION_DATE_EXTRA;

public class ViewInspectionActivity extends AppCompatActivity implements PointRecyclerViewAdapter.ListItemClickListener{

    private final static String TITLE_START = "Inspection carried out on: ";

    @BindView(R.id.view_inspection_date_title_text)TextView mInspectionDate;
    @BindView(R.id.view_inspection_points_recycler_view)RecyclerView mRecyclerView;
    private long mInspectionDateFromIntent;
    private List<OutstandingPoints> mOutstandingPoints;

    private PointRecyclerViewAdapter mRecyclerViewAdapter;

    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inspection);

        Intent intent = getIntent();
        if(!intent.hasExtra(INSPECTION_DATE_EXTRA)){
            finish();
        }
        mInspectionDateFromIntent = intent.getLongExtra(INSPECTION_DATE_EXTRA, 1);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewAdapter = new PointRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        myViewModel.getAllUnitPoints(mInspectionDateFromIntent).observe(this, new Observer<List<OutstandingPoints>>() {
            @Override
            public void onChanged(@Nullable List<OutstandingPoints> outstandingPoints) {
                mOutstandingPoints = outstandingPoints;
                mRecyclerViewAdapter.setPoints(outstandingPoints);
            }
        });
    }

    @Override
    public void completedClick(int item) {
        OutstandingPoints point = mOutstandingPoints.get(item);
        point.complete = 1;
        myViewModel.updatePoint(point);
    }
}
