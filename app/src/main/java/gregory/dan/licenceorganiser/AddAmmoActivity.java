package gregory.dan.licenceorganiser;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gregory.dan.licenceorganiser.Unit.Ammunition;
import gregory.dan.licenceorganiser.Unit.viewModels.MyViewModel;

import static gregory.dan.licenceorganiser.AddLicenceActivity.LICENCE_SERIAL_EXTRA;

public class AddAmmoActivity extends AppCompatActivity {

    @BindView(R.id.add_ammo_adac_edit_text)
    public
    EditText mAdacEditText;
    @BindView(R.id.add_ammo_hcc_edit_text)
    public
    EditText mHCCEditText;
    @BindView(R.id.add_ammo_designation_edit_text)
    public
    EditText mDesignationEditText;
    @BindView(R.id.add_ammo_max_quantity_edit_text)
    public
    EditText mMaxQuatityEditText;

    private MyViewModel mViewModel;

    private ArrayAdapter<String> hdAdapter, compatAdapter;
    private String mHazardDivision, mCompatabilityGroup, mLicenceSerial;
    private String[] hdArray, compatArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ammo);

        ButterKnife.bind(this);
        startTextChangeListenener();

        Intent intent = getIntent();
        if (!intent.hasExtra(LICENCE_SERIAL_EXTRA)) {
            finish();
        }

        mLicenceSerial = intent.getStringExtra(LICENCE_SERIAL_EXTRA);
        mViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        hdArray = getResources().getStringArray(R.array.hazard_division_array);
        hdAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, hdArray);

        compatArray = getResources().getStringArray(R.array.compatibility_group_array);
        compatAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, compatArray);

    }

    @OnClick(R.id.add_ammo_button)
    public void clicked() {
        saveAmmo();
    }

    private void saveAmmo() {
        if (mAdacEditText.getText().toString().length() == 8 &&
                !mHCCEditText.getText().toString().trim().equals("") &&
                !mDesignationEditText.getText().toString().trim().equals("") &&
                !mMaxQuatityEditText.getText().toString().trim().equals("")) {
            Ammunition ammunition = new Ammunition(mLicenceSerial,
                    mAdacEditText.getText().toString(),
                    mDesignationEditText.getText().toString(),
                    mHCCEditText.getText().toString(),
                    Integer.parseInt(mMaxQuatityEditText.getText().toString()),
                    System.currentTimeMillis());
            mViewModel.insertAmmunition(ammunition);
            mViewModel.insertToFirebase(ammunition);
            finish();
        }
    }

    @OnClick(R.id.add_ammo_hcc_edit_text)
    public void clickedHCCEditText() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.hazard_division_spinner_title))
                .setAdapter(hdAdapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHazardDivision = hdArray[which];
                        selectCompatabilityGroupDropdown();

                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void selectCompatabilityGroupDropdown() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.compatibility_group_spinner_title))
                .setAdapter(compatAdapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCompatabilityGroup = compatArray[which];
                        mHCCEditText.setText(new StringBuilder(mHazardDivision).append(mCompatabilityGroup));
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void startTextChangeListenener() {
        mAdacEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int textLength = s.length();

                if (textLength == 5) {
                    CharSequence newText = new StringBuilder(s).append("-");
                    mAdacEditText.setText(newText);
                    mAdacEditText.setSelection(newText.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
