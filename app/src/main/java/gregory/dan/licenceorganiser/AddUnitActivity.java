package gregory.dan.licenceorganiser;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gregory.dan.licenceorganiser.Unit.Unit;
import gregory.dan.licenceorganiser.Unit.viewModels.MyViewModel;

public class AddUnitActivity extends AppCompatActivity {

    @BindView(R.id.new_unit_title_edit_text)
    EditText unitNameEt;
    @BindView(R.id.new_unit_address_edit_text)
    EditText unitAddressEt;
    @BindView(R.id.new_unit_phone_edit_text)
    EditText unitNumberEt;
    @BindView(R.id.new_unit_co_name)
    EditText unitCoEt;
    MyViewModel myViewModel;

    private boolean alreadySaved = false;
    private Unit mUnit;

    public static final String UNIT_NAME_EXTRA = "gregory.dan.licenceorganiser.unittitleextra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);

        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setTitle(getResources().getString(R.string.add_new_unit));
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        ButterKnife.bind(this);

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
    }

    @OnClick(R.id.new_unit_save_button)
    public void saveButtonClick(View view) {

        if (!formComplete()) {
            Toast.makeText(this, getResources().getString(R.string.form_incomplete), Toast.LENGTH_LONG).show();
        } else if (alreadySaved) {
            updateUnit();
            finish();
        } else {
            saveUnit();
            finish();
        }
    }

    private boolean formComplete(){
        boolean correct = true;
        if(unitNameEt.getText().toString().trim().equals("")){
            unitNameEt.setError(getString(R.string.enter_a_unit_name));
            correct = false;
        }
        if(unitAddressEt.getText().toString().trim().equals("")){
            unitAddressEt.setError(getString(R.string.enter_the_address));
            correct = false;
        }
        if(unitNumberEt.getText().toString().trim().equals("")){
            unitNumberEt.setError(getString(R.string.enter_a_number));
            correct = false;
        }
        if(unitCoEt.getText().toString().trim().equals("")){
            unitCoEt.setError(getString(R.string.enter_co_details));
            correct = false;
        }
        return correct;
    }

    @OnClick(R.id.button_licence_add)
    public void addLicence(View v) {
        if (!formComplete()) {
            Toast.makeText(this, getResources().getString(R.string.form_incomplete), Toast.LENGTH_LONG).show();
        } else if (alreadySaved) {
            Intent intent = new Intent(this, AddLicenceActivity.class);
            intent.putExtra(UNIT_NAME_EXTRA, unitNameEt.getText().toString());
            startActivity(intent);
        } else {
            saveUnit();
            alreadySaved = true;
            Intent intent = new Intent(this, AddLicenceActivity.class);
            intent.putExtra(UNIT_NAME_EXTRA, unitNameEt.getText().toString());
            startActivity(intent);
        }
    }

    @OnClick(R.id.add_unit_add_inspection_button)
    public void addInspectionPoint(View v) {
        if (!formComplete()) {
            Toast.makeText(this, getResources().getString(R.string.form_incomplete), Toast.LENGTH_LONG).show();
        } else if (alreadySaved) {
            Intent intent = new Intent(this, AddInspectionActivity.class);
            intent.putExtra(UNIT_NAME_EXTRA, unitNameEt.getText().toString());
            startActivity(intent);
        } else {
            saveUnit();
            alreadySaved = true;
            Intent intent = new Intent(this, AddInspectionActivity.class);
            intent.putExtra(UNIT_NAME_EXTRA, unitNameEt.getText().toString());
            startActivity(intent);
        }
    }

    public void saveUnit() {
        Unit unit = new Unit(unitNameEt.getText().toString(),
                unitAddressEt.getText().toString(),
                unitNumberEt.getText().toString(),
                unitCoEt.getText().toString());
        mUnit = unit;
        myViewModel.insertUnit(unit);
        myViewModel.insertToFirebase(unit);
    }

    public void updateUnit() {
        myViewModel.updateUnit(mUnit);
        myViewModel.insertToFirebase(mUnit);
    }

    @Override
    public void onBackPressed() {
        if(alreadySaved){
            myViewModel.deleteUnit(mUnit);
        }
        super.onBackPressed();
    }
}
