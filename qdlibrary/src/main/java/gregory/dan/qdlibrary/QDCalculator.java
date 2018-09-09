package gregory.dan.qdlibrary;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.Objects;

public class QDCalculator extends AppCompatActivity {
    private TextView pESDescriptionTv;
    private TextView eSDescriptionTv;
    private TextView mResultTextView;
    private ImageView hdImageView, pEsImageView, eSImageView;
    private EditText mDistanceEditText;
    private int pesPos, esPos;
    private TypedArray pesS, ess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qdcalculator);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setupUi();

        pesS = getResources().obtainTypedArray(R.array.pes_images);
        ess = getResources().obtainTypedArray(R.array.es_image_names);

    }

    private void setupUi(){
        final String[] pesDescription = getResources().getStringArray(R.array.pes_descriptions_full);
        final String[] esDescriptions = getResources().getStringArray(R.array.es_descriptions_long);

        hdImageView = findViewById(R.id.qd_hd_icon);
        pEsImageView = findViewById(R.id.qd_pes_icon);
        eSImageView = findViewById(R.id.qd_es_icon);

        final Spinner pESSpinner = findViewById(R.id.qd_pes_selection_spinner);
        pESDescriptionTv = findViewById(R.id.qd_pes_description_textview);
        pESSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pESDescriptionTv.setText(pesDescription[position]);
                pEsImageView.setImageResource(pesS.getResourceId(position, -1));
                pesPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner eSSpinner = findViewById(R.id.qd_es_selection_spinner);
        eSDescriptionTv = findViewById(R.id.qd_es_description_textview);
        eSSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eSDescriptionTv.setText(esDescriptions[position]);
                eSImageView.setImageResource(ess.getResourceId(position, -1));
                esPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner hdSpinner = findViewById(R.id.qd_hd_spinner);
        hdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        hdImageView.setImageResource(R.drawable.oneone);
                        break;
                    case 1:
                    case 2:
                        hdImageView.setImageResource(R.drawable.onetwo);
                        break;
                    default:
                        hdImageView.setImageResource(R.drawable.onethree);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mResultTextView = findViewById(R.id.qd_result_text_view);
        mDistanceEditText = findViewById(R.id.qd_distance_in_metres_edit_text);
        Button mCalculateButton = findViewById(R.id.qd_calculate_button);
        mCalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = "No code available";
                String maxNEQStart = "The maximum NEQ allowed: ";
                try {
                    code = jsonParser.getQDCode(QDCalculator.this,
                            pesPos,
                            esPos);
                    if(!mDistanceEditText.getText().toString().equals("")) {
                        maxNEQStart += jsonParser.getMaxNEQ(QDCalculator.this,
                                code,
                                Integer.parseInt(mDistanceEditText.getText().toString()));
                    }

                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                mResultTextView.setText(maxNEQStart);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
