package com.z32.servicemanual;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class PdfViewerActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
    
    private PDFView pdfView;
    private TextView pageInfoTextView;
    private Button btnGoToPage, btnExit;
    private String fileName;
    private String displayName;
    private int currentPage = 0;
    private int totalPages = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        
        // Get file information from intent
        fileName = getIntent().getStringExtra("PDF_FILE_NAME");
        displayName = getIntent().getStringExtra("PDF_DISPLAY_NAME");
        
        // Initialize views
        pdfView = findViewById(R.id.pdfView);
        pageInfoTextView = findViewById(R.id.pageInfoTextView);
        btnGoToPage = findViewById(R.id.btnGoToPage);
        btnExit = findViewById(R.id.btnExit);
        
        // Set title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(displayName);
        }
        
        // Load PDF with double tap and pinch-to-zoom enabled
        loadPdf();
        
        // Set up button listeners
        btnGoToPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGoToPageDialog();
            }
        });
        
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    
    private void loadPdf() {
        pdfView.fromAsset("PDFs/" + fileName)
                .defaultPage(currentPage)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true) // Double tap to zoom
                .enableAntialiasing(true)
                .load();
        
        // Note: Pinch-to-zoom is enabled by default in this library
    }
    
    private void showGoToPageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Go to Page");
        
        final EditText input = new EditText(this);
        input.setHint("Enter page number (1-" + totalPages + ")");
        input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        
        builder.setPositiveButton("Go", (dialog, which) -> {
            String pageStr = input.getText().toString();
            try {
                int pageNum = Integer.parseInt(pageStr) - 1; // Convert to 0-based index
                if (pageNum >= 0 && pageNum < totalPages) {
                    pdfView.jumpTo(pageNum, true);
                } else {
                    Toast.makeText(this, "Invalid page number", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        });
        
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    
    @Override
    public void onPageChanged(int page, int pageCount) {
        currentPage = page;
        totalPages = pageCount;
        updatePageInfo();
    }
    
    @Override
    public void loadComplete(int nbPages) {
        totalPages = nbPages;
        updatePageInfo();
    }
    
    private void updatePageInfo() {
        pageInfoTextView.setText("Page " + (currentPage + 1) + " of " + totalPages);
    }
    
    @Override
    public void onBackPressed() {
        finish();
    }
}
