package gregory.dan.licenceorganiser;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gregory.dan.licenceorganiser.UI.AmmoRecyclerViewAdapter;
import gregory.dan.licenceorganiser.Unit.Ammunition;
import gregory.dan.licenceorganiser.Unit.Licence;
import gregory.dan.licenceorganiser.Unit.viewModels.MyViewModel;
import gregory.dan.licenceorganiser.notifications.NotificationService;

import static gregory.dan.licenceorganiser.AddUnitActivity.UNIT_NAME_EXTRA;
import static gregory.dan.licenceorganiser.notifications.NotificationService.ALERT_NOTIFICATION_EXTRA;
import static gregory.dan.licenceorganiser.notifications.NotificationService.ID_EXTRA;
import static gregory.dan.licenceorganiser.notifications.NotificationService.MESSAGE_NOTIFICATION_EXTRA;
import static gregory.dan.licenceorganiser.notifications.NotificationService.NOTIFICATION_ID;
import static gregory.dan.licenceorganiser.notifications.NotificationService.UNIT_TITLE_NOTIFICATION_EXTRA;

public class AddLicenceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AmmoRecyclerViewAdapter.ListItemClickListener {

    public static final String LICENCE_SERIAL_EXTRA = "gregory.dan.licenceorganiser.licenceserialextra";

    private String unitTitle;
    private MyViewModel mMyViewModel;
    @BindView(R.id.new_licence_serial_edit_text)
    public
    EditText mLicenceSerialEditText;
    @BindView(R.id.new_licence_issue_date_edit_text)
    public
    EditText issueDateEditText;
    @BindView(R.id.new_licence_add_ammo_button)
    public
    Button addAmmoButton;
    @BindView(R.id.new_licence_radio_group)
    public
    RadioGroup radioGroupView;
    @BindView(R.id.add_licence_ammo_recycler_view)
    public RecyclerView mRecyclerView;
    private long issueDateTimeInMillies, expiryDateTimeInMillies;
    private String licenceType = getString(R.string.standard_licence_string);
    private boolean alreadyExists = false;
    private Licence mLicence;

    private AmmoRecyclerViewAdapter ammoRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_licence);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent.hasExtra(UNIT_NAME_EXTRA)) {
            unitTitle = intent.getStringExtra(UNIT_NAME_EXTRA);
        }

        radioGroupView.check(R.id.new_licence_standard_radio_button);
        radioGroupView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.new_licence_aq25_radio_button:
                        addAmmoButton.setVisibility(View.VISIBLE);
                        licenceType = getString(R.string.aq25_string);
                        break;
                    case R.id.new_licence_saa_radio_button:
                        addAmmoButton.setVisibility(View.GONE);
                        licenceType = getString(R.string.saa_string);
                        break;
                    default:
                        addAmmoButton.setVisibility(View.GONE);
                        licenceType = getString(R.string.standard_licence_string);
                        break;
                }

            }
        });

        ammoRecyclerViewAdapter = new AmmoRecyclerViewAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(ammoRecyclerViewAdapter);

        mMyViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        if (mLicence != null) {
            showRecyclerView();
        }
    }

    @OnClick(R.id.new_licence_save_button)
    public void clickSaveButton() {
        if (!alreadyExists) {
            saveLicence();
            finish();
        } else {
            updateLicence();
            finish();
        }
    }


    private void updateLicence() {
        if (!mLicenceSerialEditText.getText().toString().equals("") &&
                !issueDateEditText.getText().toString().equals("")) {
            mLicence.licenceIssueDate = issueDateTimeInMillies;
            mLicence.licenceRenewalDate = expiryDateTimeInMillies;
            mLicence.licenceType = licenceType;
            mMyViewModel.updateLicence(mLicence);
            mMyViewModel.insertToFirebase(mLicence);
        } else {
            Toast.makeText(this, getString(R.string.complete_all_boxes), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveLicence() {
        if (!mLicenceSerialEditText.getText().toString().equals("") &&
                !issueDateEditText.getText().toString().equals("")) {
            alreadyExists = true;
            Licence licence = new Licence(mLicenceSerialEditText.getText().toString(),
                    unitTitle,
                    licenceType,
                    issueDateTimeInMillies,
                    expiryDateTimeInMillies);
            mLicence = licence;
            showRecyclerView();
            mMyViewModel.insertLicence(licence);
            mMyViewModel.insertToFirebase(licence);

            setReminder();
        } else {
            Toast.makeText(this, getString(R.string.complete_all_boxes), Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.new_licence_issue_date_edit_text)
    public void showDatePickerDialog(View view) {

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    @OnClick(R.id.new_licence_add_ammo_button)
    public void addAmmo() {
        if (!mLicenceSerialEditText.getText().toString().equals("") &&
                !issueDateEditText.getText().toString().equals("")) {
            if (!alreadyExists) {
                saveLicence();
            }
            Intent intent = new Intent(this, AddAmmoActivity.class);
            intent.putExtra(LICENCE_SERIAL_EXTRA, mLicence.licenceSerial);
            startActivity(intent);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-YYYY", Locale.getDefault());
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.HOUR, 11);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        issueDateTimeInMillies = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, 5);
        expiryDateTimeInMillies = calendar.getTimeInMillis();

        String date = getString(R.string.licence_issue_date_start_string) + sdf.format(issueDateTimeInMillies);
        issueDateEditText.setText(date);
    }

    @Override
    public void onClick(int item) {


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
            return new DatePickerDialog(Objects.requireNonNull(getActivity()), (AddLicenceActivity) getActivity(), year, month, day);
        }

    }

    private void showRecyclerView() {

        mMyViewModel.getAllUnitAmmunition(mLicence.licenceSerial).observe(this, new Observer<List<Ammunition>>() {
            @Override
            public void onChanged(@Nullable List<Ammunition> ammunitions) {
                ammoRecyclerViewAdapter.setAmmunition(ammunitions);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (alreadyExists) {
            mMyViewModel.deleteFromFirebase(mLicence);
            mMyViewModel.deleteUnitLicence(mLicence);
        }
        super.onBackPressed();
    }

    private void setReminder() {

        String message = getString(R.string.licence_renewal_reminder);
        message = message + mLicence.licenceSerial;

        String alert = getString(R.string.lecence_renewal_alert);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mLicence.licenceRenewalDate);
        calendar.add(Calendar.DATE, -60);

        long alertTime = calendar.getTimeInMillis();

        Intent alertIntent = new Intent(this, NotificationService.class);
        alertIntent.putExtra(UNIT_TITLE_NOTIFICATION_EXTRA, unitTitle);
        alertIntent.putExtra(MESSAGE_NOTIFICATION_EXTRA, message);
        alertIntent.putExtra(ALERT_NOTIFICATION_EXTRA, alert);
        alertIntent.putExtra(ID_EXTRA, mLicence.licenceSerial);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Objects.requireNonNull(alarmManager).setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getService(this, NOTIFICATION_ID, alertIntent, 0));
        } else {
            Objects.requireNonNull(alarmManager).set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getService(this, NOTIFICATION_ID, alertIntent, 0));
        }
    }
}
