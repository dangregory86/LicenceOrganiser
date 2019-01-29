package gregory.dan.licenceorganiser;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

//    A list of ranks for the drop down menu
    private static final String[] RANKLIST = new String[]{"LCpl","Cpl","Sgt","SSgt","WO2","WO1","Capt","Maj","Lt Col"};
    private ArrayAdapter<String> rankAdapter;
    private String currentRank;
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
    private void selectRank() {
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
}
