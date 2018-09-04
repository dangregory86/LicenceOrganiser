package gregory.dan.licenceorganiser;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gregory.dan.licenceorganiser.Unit.Inspection;
import gregory.dan.licenceorganiser.Unit.viewModels.MyViewModel;

import static gregory.dan.licenceorganiser.AddUnitActivity.UNIT_NAME_EXTRA;

public class AddInspectionActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    public static final String INSPECTION_EXTRA = "gregory.dan.licenceorganiser.inspectiondateextra";

    @BindView(R.id.add_inspction_date_edit_text)
    EditText inspectedDateEditText;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    private long inspectionDateInMillis, remindByDateInMillis, nextInspectionDueInMillis;
    private String mUnitTitle;
    private String user;
    private MyViewModel viewModel;
    private boolean alreadySaved = false;
    private int hasPoints = 0;
    private Inspection inspection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inspection);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        user = firebaseUser.getEmail();

        Intent intent = getIntent();
        if (!intent.hasExtra(UNIT_NAME_EXTRA)) {
            finish();
        }
        mUnitTitle = intent.getStringExtra(UNIT_NAME_EXTRA);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @OnClick(R.id.add_inspection_save_inspection_button)
    public void buttonClicked(){
        if(!alreadySaved){
            saveInspection();
            finish();
        }else{
            updateInspection();
            finish();
        }
    }

    public void saveInspection(){
        long time = System.currentTimeMillis();
        if(!inspectedDateEditText.getText().toString().trim().equals("")){
            alreadySaved = true;
            inspection = new Inspection(mUnitTitle,
                    hasPoints,
                     (inspectionDateInMillis),
                     (remindByDateInMillis),
                     (nextInspectionDueInMillis),
                    user,
                    time);
            viewModel.insertInspection(inspection);
            viewModel.insertToFirebase(inspection);
        }else{
            Toast.makeText(this, getText(R.string.complete_all_boxes), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateInspection(){
        inspection.hasPoints = hasPoints;
        viewModel.updateInspection(inspection);
        viewModel.insertToFirebase(inspection);
    }

    @OnClick(R.id.add_inspection_new_point_button)
    public void addPoint(){

        if(alreadySaved){
            Intent intent = new Intent(this, AddInspectionPointActivity.class);
            intent.putExtra(INSPECTION_EXTRA, inspection._id);
            startActivity(intent);
        }else if(!inspectedDateEditText.getText().toString().trim().equals("")){
            saveInspection();
            Intent intent = new Intent(this, AddInspectionPointActivity.class);
            intent.putExtra(INSPECTION_EXTRA, inspection._id);
            startActivity(intent);
        }else{
            saveInspection();
        }
    }

    @OnClick(R.id.add_inspction_date_edit_text)
    public void getDate(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int generic = 12;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth,
                generic, generic, 0);
        inspectionDateInMillis = calendar.getTimeInMillis();

        remindByDateInMillis = inspectionDateInMillis + 1000 * 60 * 60 * 24 * 28;
        nextInspectionDueInMillis = inspectionDateInMillis + 1000 * 60 * 60 * 24 * 365;

        String date = "Licence issue date:  " + dayOfMonth + "/" + month + "/" + year;
        inspectedDateEditText.setText(date);
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), (AddInspectionActivity) getActivity(), year, month, day);
        }

    }
}
