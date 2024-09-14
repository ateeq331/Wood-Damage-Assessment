package com.example.samawia;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class Input extends AppCompatActivity {

    private Spinner spinnerWoodSpecies;
    private String selectedWoodSpecies = "0"; // Default to "0" for the first option
    private EditText etDensity, etWeightLoss, etTemperature, etHumidity,
            etSpecificGravity, etJankaHardness, etModulusOfRupture, etCrushingStrength;
    private TextView tvResult;
    private String url = "https://adeveloper-ateeq.hf.space/predict";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_input);

        // Initialize Spinner
        spinnerWoodSpecies = findViewById(R.id.spinner_wood_species);

        // Initialize EditTexts
        etDensity = findViewById(R.id.et_density);
        etWeightLoss = findViewById(R.id.et_weight_loss);
        etTemperature = findViewById(R.id.et_temperature);
        etHumidity = findViewById(R.id.et_humidity);
        etSpecificGravity = findViewById(R.id.et_specific_gravity);
        etJankaHardness = findViewById(R.id.et_janka_hardness);
        etModulusOfRupture = findViewById(R.id.et_modulus_of_rupture);
        etCrushingStrength = findViewById(R.id.et_crushing_strength);

        // Initialize TextView for result display
        tvResult = findViewById(R.id.tv_result);

        // Initialize Button
        Button btnPredict = findViewById(R.id.btn_predict);

        // Set up the Spinner with an ArrayAdapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.wood_species_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWoodSpecies.setAdapter(adapter);

        // Handle Spinner item selection
        spinnerWoodSpecies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWoodSpecies = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedWoodSpecies = "0"; // Default to "0" if nothing is selected
            }
        });

        // Set onClickListener for the predict button
        btnPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String density = etDensity.getText().toString();
                String weightLoss = etWeightLoss.getText().toString();
                String temperature = etTemperature.getText().toString();
                String humidity = etHumidity.getText().toString();
                String specificGravity = etSpecificGravity.getText().toString();
                String jankaHardness = etJankaHardness.getText().toString();
                String modulusOfRupture = etModulusOfRupture.getText().toString();
                String crushingStrength = etCrushingStrength.getText().toString();

                // Check if any field is empty
                if (density.isEmpty() || weightLoss.isEmpty() || temperature.isEmpty() || humidity.isEmpty() ||
                        specificGravity.isEmpty() || jankaHardness.isEmpty() || modulusOfRupture.isEmpty() || crushingStrength.isEmpty()) {
                    Toast.makeText(Input.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                tvResult.setText(response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(Input.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("wood_specie", selectedWoodSpecies);
                        params.put("density", density);
                        params.put("weight_loss", weightLoss);
                        params.put("temperature", temperature);
                        params.put("humidity", humidity);
                        params.put("specific_gravity", specificGravity);
                        params.put("janka_hardness", jankaHardness);
                        params.put("module_of_rupture", modulusOfRupture);
                        params.put("crushing_strength", crushingStrength);

                        // Log the parameters being sent for debugging
                        Log.d("API Request Params", params.toString());
                        return params;
                    }
                };

                // Add the request to the Volley request queue
                Volley.newRequestQueue(Input.this).add(stringRequest);
            }
        });
    }
}
