package gregory.dan.licenceorganiser;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import gregory.dan.licenceorganiser.Unit.Unit;
import gregory.dan.licenceorganiser.Unit.viewModels.MyViewModel;

public class AddUnitActivity extends AppCompatActivity {

    EditText unitNameEt, unitAddressEt, unitNumberEt, unitCoEt;
    CheckBox addLicenceCheckbox, addInspectionCheckbox;
    Button saveButton;
    MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);

        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setTitle(getResources().getString(R.string.add_new_unit));
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        instantiateViews();
    }

    private void instantiateViews(){
        unitNameEt = findViewById(R.id.new_unit_title_edit_text);
        unitAddressEt = findViewById(R.id.new_unit_address_edit_text);
        unitNumberEt = findViewById(R.id.new_unit_phone_edit_text);
        unitCoEt = findViewById(R.id.new_unit_co_name);
        addInspectionCheckbox = findViewById(R.id.new_unit_add_points_check_box);
        addLicenceCheckbox = findViewById(R.id.new_unit_add_licences_checkbox);
        saveButton = findViewById(R.id.new_unit_save_button);
    }

    public void saveUnit(View view){

        Boolean addLicences = addLicenceCheckbox.isChecked();
        Boolean addInspectionPoint = addInspectionCheckbox.isChecked();

        if(unitNameEt.getText().toString().equals("") ||
                unitAddressEt.getText().toString().equals("") ||
                unitNumberEt.getText().toString().equals("") ||
                unitCoEt.getText().toString().equals("")){
            Toast.makeText(this, "Ensure you complete all boxes!!", Toast.LENGTH_SHORT).show();
        }else{
            Unit unit = new Unit(unitNameEt.getText().toString(),
            unitAddressEt.getText().toString(),
            unitNumberEt.getText().toString(),
            unitCoEt.getText().toString());
            myViewModel.insertUnit(unit);
            finish();
        }

    }
}
