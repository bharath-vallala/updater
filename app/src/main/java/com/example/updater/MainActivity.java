package com.example.updater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> countries = new ArrayList<>();

        // Get ISO countries, create Country object and
        // store in the collection.
        String[] isoCountries = Locale.getISOCountries();
        for (String country : isoCountries) {
            Locale locale = new Locale("en", country);
            String name = locale.getDisplayCountry();
           // Log.d("iso", "onCreate: "+locale.getCountry());
            countries.add(name);


        }
        final Map<String, Object> city = new HashMap<>();
        final TextView total=findViewById(R.id.total);
        TextView rec=findViewById(R.id.recovered);
        TextView deaths=findViewById(R.id.deaths);
        final Spinner spinner=findViewById(R.id.spinner);

        ArrayAdapter<String> a =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countries);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(a);



        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city.put("Confirmed_cases", Integer.parseInt(total.getText().toString()));
                city.put("Deaths", Integer.parseInt(total.getText().toString()));
                city.put("Recovered", Integer.parseInt(total.getText().toString()));

                db.collection("reports").document(spinner.getSelectedItem().toString()).set(city).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(getApplicationContext(),"inserted",Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(),"not inserted",Toast.LENGTH_LONG).show();


                    }
                });




            }
        });

      /*  Map<String, String> countries1 = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries1.put(l.getDisplayCountry(), iso);
        }

        for (String c:countries) {
            Log.d("listsss", "onCreate: "+ countries1.get(c));

        }*/

        //Log.d("countries", "onCreate: "+countries);
    }
}
