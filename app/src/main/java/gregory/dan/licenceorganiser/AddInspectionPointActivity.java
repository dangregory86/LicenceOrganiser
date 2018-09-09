package gregory.dan.licenceorganiser;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gregory.dan.licenceorganiser.Unit.OutstandingPoints;
import gregory.dan.licenceorganiser.Unit.viewModels.MyViewModel;

import static gregory.dan.licenceorganiser.AddInspectionActivity.INSPECTION_EXTRA;

public class AddInspectionPointActivity extends AppCompatActivity {

    @BindView(R.id.add_inspection_point_point_edit_text)
    public
    EditText inspectionPointEditText;

    private long inspection;

    private MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inspection_point);

        Intent intent = getIntent();
        if (!intent.hasExtra(INSPECTION_EXTRA)) {
            finish();
        }
        inspection = intent.getLongExtra(INSPECTION_EXTRA, 0);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @OnClick(R.id.add_inspection_point_save_button)
    public void saveInspectionPoint(View view) {
        if (inspectionPointEditText.getText().toString().equals("")) {
            Toast.makeText(this, getText(R.string.complete_all_boxes), Toast.LENGTH_SHORT).show();
        } else {
            long time = System.currentTimeMillis();
            OutstandingPoints point = new OutstandingPoints(inspection,
                    inspectionPointEditText.getText().toString(),
                    0,
                    time);
            viewModel.insertPoint(point);
            viewModel.insertToFirebase(point);
            finish();
        }
    }


}
