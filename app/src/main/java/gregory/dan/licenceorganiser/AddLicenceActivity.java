package gregory.dan.licenceorganiser;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gregory.dan.licenceorganiser.Unit.Licence;
import gregory.dan.licenceorganiser.Unit.viewModels.MyViewModel;

import static gregory.dan.licenceorganiser.AddUnitActivity.UNIT_NAME_EXTRA;

public class AddLicenceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public static final String LICENCE_SERIAL_EXTRA = "gregory.dan.licenceorganiser.licenceserialextra";

    private String unitTitle;
    private MyViewModel mMyViewModel;
    @BindView(R.id.new_licence_serial_edit_text) EditText mLicenceSerialEditText;
    @BindView(R.id.new_licence_issue_date_edit_text)
    EditText issueDateEditText;
    @BindView(R.id.new_licence_add_ammo_button)
    Button addAmmoButton;
    @BindView(R.id.new_licence_radio_group)
    RadioGroup radioGroupView;
    private long issueDateTimeInMillies, expiryDateTimeInMillies;
    private String licenceType = "Standard";
    private boolean alreadyExists = false;
    private Licence mLicence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_licence);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        if(intent.hasExtra(UNIT_NAME_EXTRA)){
            unitTitle = intent.getStringExtra(UNIT_NAME_EXTRA);
        }

        radioGroupView.check(R.id.new_licence_standard_radio_button);
        radioGroupView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.new_licence_aq25_radio_button){
                    addAmmoButton.setVisibility(View.VISIBLE);
                    licenceType = "AQ25";
                }else if(checkedId == R.id.new_licence_saa_radio_button){
                    addAmmoButton.setVisibility(View.GONE);
                    licenceType = "SAA";
                }else{
                    addAmmoButton.setVisibility(View.GONE);
                    licenceType = "Standard";
                }

            }
        });

        mMyViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @OnClick(R.id.new_licence_save_button)
    public void clickSaveButton(){
        if(!alreadyExists){
            saveLicence();
            finish();
        }else{
            updateLicence();
            finish();
        }
    }



    private void updateLicence(){
        if(!mLicenceSerialEditText.getText().toString().equals("") &&
                !issueDateEditText.getText().toString().equals("")){
            mLicence.licenceIssueDate = issueDateTimeInMillies;
            mLicence.licenceRenewalDate =  expiryDateTimeInMillies;
            mLicence.licenceType = licenceType;
            mMyViewModel.updateLicence(mLicence);
            mMyViewModel.insertToFirebase(mLicence);
        }else{
            Toast.makeText(this, "Ensure you complete all sections", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveLicence(){
        if(!mLicenceSerialEditText.getText().toString().equals("") &&
                !issueDateEditText.getText().toString().equals("")){
            alreadyExists = true;
            Licence licence = new Licence(mLicenceSerialEditText.getText().toString(),
                    unitTitle,
                    licenceType,
                     issueDateTimeInMillies,
                     expiryDateTimeInMillies);
            mLicence = licence;
            mMyViewModel.insertLicence(licence);
            mMyViewModel.insertToFirebase(licence);
        }else{
            Toast.makeText(this, "Ensure you complete all sections", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.new_licence_issue_date_edit_text)
    public void showDatePickerDialog(View view) {

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    @OnClick(R.id.new_licence_add_ammo_button)
    public void addAmmo(){
        if (!alreadyExists){
            saveLicence();
        }
        Intent intent = new Intent(this, AddAmmoActivity.class);
        intent.putExtra(LICENCE_SERIAL_EXTRA, mLicence.licenceSerial);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month + 1, dayOfMonth,
                5, month + 1, 0);
        issueDateTimeInMillies = calendar.getTimeInMillis();
        calendar.set(year + 5, month + 1, dayOfMonth,
                0, month + 1, 0);
        expiryDateTimeInMillies = calendar.getTimeInMillis();

        String date = "Licence issue date:  " + dayOfMonth + "/" + (month + 1) + "/" + year;
        issueDateEditText.setText(date);
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
            return new DatePickerDialog(getActivity(), (AddLicenceActivity) getActivity(), year, month, day);
        }

    }

}
