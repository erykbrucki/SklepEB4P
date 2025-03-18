package com.example.sklep;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sklep.Database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName;
    private SeekBar seekBarQuantity;
    private Spinner spinnerMice, spinnerKeyboard, spinnerWebcam, spinnerPC;
    private ImageView imageViewMouse, imageViewKeyboard, imageViewWebcam, imageViewPC;
    private TextView textViewTotalPrice;
    private double totalPrice = 0.0;
    private final double[] pcPrices = {3000.0, 4000.0, 5000.0};
    private final double[] micePrices = {100.0, 150.0, 120.0, 200.0};
    private final double[] keyboardPrices = {80.0, 130.0, 120.0, 150.0};
    private final double[] webcamPrices = {250.0, 300.0, 350.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        spinnerMice = findViewById(R.id.spinnerMice);
        spinnerKeyboard = findViewById(R.id.spinnerKeyboard);
        spinnerWebcam = findViewById(R.id.spinnerWebcam);
        spinnerPC = findViewById(R.id.spinnerPC);
        imageViewPC = findViewById(R.id.imageViewPC);
        imageViewMouse = findViewById(R.id.imageViewMouse);
        imageViewKeyboard = findViewById(R.id.imageViewKeyboard);
        imageViewWebcam = findViewById(R.id.imageViewWebcam);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        editTextName = findViewById(R.id.editTextName);
        seekBarQuantity = findViewById(R.id.seekBarQuantity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupAdapters();
        setupListeners();
    }

    private void setupAdapters() {
        ArrayAdapter<CharSequence> pcAdapter = ArrayAdapter.createFromResource(this,
                R.array.pc_options, android.R.layout.simple_spinner_item);
        pcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPC.setAdapter(pcAdapter);

        ArrayAdapter<CharSequence> miceAdapter = ArrayAdapter.createFromResource(this,
                R.array.mice_options, android.R.layout.simple_spinner_item);
        miceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMice.setAdapter(miceAdapter);

        ArrayAdapter<CharSequence> keyboardAdapter = ArrayAdapter.createFromResource(this,
                R.array.keyboard_options, android.R.layout.simple_spinner_item);
        keyboardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKeyboard.setAdapter(keyboardAdapter);

        ArrayAdapter<CharSequence> webcamAdapter = ArrayAdapter.createFromResource(this,
                R.array.webcam_options, android.R.layout.simple_spinner_item);
        webcamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWebcam.setAdapter(webcamAdapter);
    }

    private void setupListeners() {
        Button btnSendOrder = findViewById(R.id.btnSendOrder);
        btnSendOrder.setOnClickListener(view -> saveOrderToList());

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateImagesAndPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        };

        spinnerMice.setOnItemSelectedListener(itemSelectedListener);
        spinnerKeyboard.setOnItemSelectedListener(itemSelectedListener);
        spinnerWebcam.setOnItemSelectedListener(itemSelectedListener);
        spinnerPC.setOnItemSelectedListener(itemSelectedListener);

        seekBarQuantity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTotalPrice(progress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void updateImagesAndPrice() {
        switch (spinnerPC.getSelectedItemPosition()) {
            case 0:
                imageViewPC.setImageResource(R.drawable.pc2);
                break;
            case 1:
                imageViewPC.setImageResource(R.drawable.pc);
                break;
            case 2:
                imageViewPC.setImageResource(R.drawable.pc3);
                break;
        }

        switch (spinnerMice.getSelectedItemPosition()) {
            case 0:
                imageViewMouse.setImageResource(R.drawable.mouse_logitech);
                break;
            case 1:
                imageViewMouse.setImageResource(R.drawable.mouse_razer);
                break;
            case 2:
                imageViewMouse.setImageResource(R.drawable.mouse_steelseries);
                break;
            case 3:
                imageViewMouse.setImageResource(R.drawable.mouse_corsair);
                break;
        }

        switch (spinnerKeyboard.getSelectedItemPosition()) {
            case 0:
                imageViewKeyboard.setImageResource(R.drawable.keyboard_logitech);
                break;
            case 1:
                imageViewKeyboard.setImageResource(R.drawable.keyboard_razer);
                break;
            case 2:
                imageViewKeyboard.setImageResource(R.drawable.keyboard_corsair);
                break;
            case 3:
                imageViewKeyboard.setImageResource(R.drawable.keyboard_steelseries);
                break;
        }

        switch (spinnerWebcam.getSelectedItemPosition()) {
            case 0:
                imageViewWebcam.setImageResource(R.drawable.webcam_logitech);
                break;
            case 1:
                imageViewWebcam.setImageResource(R.drawable.webcam_razer);
                break;
            case 2:
                imageViewWebcam.setImageResource(R.drawable.webcam_logitech_streamcam);
                break;
        }

        updateTotalPrice(seekBarQuantity.getProgress() + 1);
    }

    private void updateTotalPrice(int quantity) {
        double pcPrice = pcPrices[spinnerPC.getSelectedItemPosition()];
        double mousePrice = micePrices[spinnerMice.getSelectedItemPosition()];
        double keyboardPrice = keyboardPrices[spinnerKeyboard.getSelectedItemPosition()];
        double webcamPrice = webcamPrices[spinnerWebcam.getSelectedItemPosition()];

        totalPrice = (pcPrice + mousePrice + keyboardPrice + webcamPrice) * quantity;
        textViewTotalPrice.setText("Całkowita cena: " + totalPrice + " zł");
    }

    private void saveOrderToList() {
        String name = editTextName.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Najpierw wprowadź dane zamówienia", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = seekBarQuantity.getProgress() + 1;
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
        int productId = getSelectedProductId();

        Order order = new Order(name, totalPrice, date, quantity, productId);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.addOrder(order);

        Toast.makeText(this, "Zamówienie zapisane!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list_orders:
                startActivity(new Intent(this, OrderListActivity.class));
                return true;
            case R.id.menu_send_sms:
                sendOrderSMS();
                return true;
            case R.id.menu_share:
                shareOrder();
                return true;
            case R.id.menu_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private int getSelectedProductId() {
        int mouseId = spinnerMice.getSelectedItemPosition();
        int keyboardId = spinnerKeyboard.getSelectedItemPosition();
        int webcamId = spinnerWebcam.getSelectedItemPosition();

        if (mouseId >= 0) {
            return mouseId + 1;
        } else if (keyboardId >= 0) {
            return keyboardId + 5; //
        } else if (webcamId >= 0) {
            return webcamId + 9;
        }

        return -1;
    }
    private void sendSMS(Order order) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"));
        smsIntent.putExtra("sms_body", "Zamówienie: " +
                "\nImię: " + order.getName() +
                "\nCena: " + order.getTotalPrice() + " zł" +
                "\nLiczba sztuk: " + order.getQuantity() + "\n" +
                "\nData: " + order.getDate());
        startActivity(smsIntent);
    }
    private void sendOrderSMS() {
        String name = editTextName.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Najpierw wprowadź dane zamówienia", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = seekBarQuantity.getProgress() + 1;
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
        int productId = getSelectedProductId();

        Order order = new Order(name, totalPrice, date, quantity, productId);

        sendSMS(order);
    }

    private void shareOrder() {
        String name = editTextName.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "Najpierw wprowadź dane zamówienia", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = seekBarQuantity.getProgress() + 1;
        String date = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());

        String orderDetails = "Zamówienie:\n" +
                "Imię: " + name + "\n" +
                "Liczba sztuk: " + quantity + "\n" +
                "Cena: " + totalPrice + " zł\n" +
                "Data: " + date;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, orderDetails);
        startActivity(Intent.createChooser(shareIntent, "Udostępnij za pomocą"));
    }

}
