package gregory.dan.licenceorganiser;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    //    A list of ranks for the drop down menu
    private static final String[] RANKLIST = new String[]{"LCpl", "Cpl", "Sgt", "SSgt", "WO2", "WO1", "Capt", "Maj", "Lt Col"};
    private ArrayAdapter<String> rankAdapter;
    private String currentRank;
    private String firstName;
    private String surname;

    //    The static strings for sending the intent extras
    public static final String RANK_EXTRA = "RegisterActivity.rankExtra";
    public static final String FIRST_NAME_EXTRA = "RegisterActivity_firstNameExtra";
    public static final String SURNAME_EXTRA = "RegisterActivity.SeurnameExtra";

    //    Binding all the EditText views
    @BindView(R.id.register_popup_first_name)
    EditText mFirstName;
    @BindView(R.id.register_popup_surname)
    EditText mSurname;
    @BindView(R.id.register_popup_rank)
    EditText mRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        rankAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, RANKLIST);

    }

    @OnClick(R.id.register_popup_rank)
    void selectRank() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.rank_spinner_title))
                .setAdapter(rankAdapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentRank = RANKLIST[which];
                        mRank.setText(currentRank);
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @OnClick(R.id.register_popup)
    void register() {
        firstName = mFirstName.getText().toString().trim();
        surname = mSurname.getText().toString().trim();
        //check for valid entries
        if (TextUtils.isEmpty(firstName)) {
            mFirstName.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(surname)) {
            mSurname.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(currentRank)) {
            mRank.setError(getString(R.string.error_field_required));
        } else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(RANK_EXTRA, currentRank);
            returnIntent.putExtra(FIRST_NAME_EXTRA, firstName);
            returnIntent.putExtra(SURNAME_EXTRA, surname);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }

    }
}
