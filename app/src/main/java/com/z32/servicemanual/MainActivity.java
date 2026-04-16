package com.z32.servicemanual;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    
    private ListView listView;
    private Map<String, String> pdfMap;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listView = findViewById(R.id.pdfListView);
        
        // Initialize PDF map with display names and file names
        pdfMap = new HashMap<>();
        pdfMap.put("Automatic Transmission", "AT.pdf");
        pdfMap.put("Body & Frame", "bf.pdf");
        pdfMap.put("Brake System", "br.pdf");
        pdfMap.put("Circuit Diagrams", "Circuit.pdf");
        pdfMap.put("Clutch System", "cl.pdf");
        pdfMap.put("Emission & Fuel Control", "efec.pdf");
        pdfMap.put("Electrical System", "el.pdf");
        pdfMap.put("Engine Mechanical", "em.pdf");
        pdfMap.put("Final Assembly", "fa.pdf");
        pdfMap.put("Front End", "fe.pdf");
        pdfMap.put("General Information", "gi.pdf");
        pdfMap.put("Heater & Air Conditioning", "ha.pdf");
        pdfMap.put("Lubrication & Cooling", "lc.pdf");
        pdfMap.put("Manual Transmission", "ma.pdf");
        pdfMap.put("Maintenance", "mt.pdf");
        pdfMap.put("Power Distribution", "pd.pdf");
        pdfMap.put("Rear Axle", "ra.pdf");
        pdfMap.put("Steering System", "st.pdf");
        pdfMap.put("Wiring Diagrams", "wiring.pdf");
        
        // Create a sorted list of display names
        List<String> displayNames = new ArrayList<>(pdfMap.keySet());
        Collections.sort(displayNames);
        
        // Create adapter with sorted display names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_list_item_1,
            displayNames
        );
        
        listView.setAdapter(adapter);
        
        // Set click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String displayName = (String) parent.getItemAtPosition(position);
                String fileName = pdfMap.get(displayName);
                openPdfViewer(fileName, displayName);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Toast.makeText(this, "Z32 Service Manual v1.0", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void openPdfViewer(String fileName, String displayName) {
        Intent intent = new Intent(this, PdfViewerActivity.class);
        intent.putExtra("PDF_FILE_NAME", fileName);
        intent.putExtra("PDF_DISPLAY_NAME", displayName);
        startActivity(intent);
    }
}
