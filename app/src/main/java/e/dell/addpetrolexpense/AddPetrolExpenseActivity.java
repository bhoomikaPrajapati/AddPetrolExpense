package e.dell.addpetrolexpense;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import e.dell.addpetrolexpense.database.DatabaseHelper;
import e.dell.addpetrolexpense.model.Model;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddPetrolExpenseActivity extends AppCompatActivity {

    public TextView tvDatePik;
    public TextView tvTimePik;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private EditText etAmount;
    private EditText etKm;
    private Button btnSubmit;
    private Button btnDisplay;
    private DatabaseHelper databaseHelper;
    private boolean isUpdate = false;
    private Model updateModel;
    private TextView tvSelect;
    private String mSelectedName = "";
    private ArrayList<String> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        userList.clear();
        userList.add("Bhoomika");
        userList.add("Chirag");


        init();
        setListener();

    }

    private void init() {

        tvDatePik = findViewById(R.id.tvDatePik);
        tvTimePik = findViewById(R.id.tvTimePik);
        etAmount = findViewById(R.id.etAmount);
        etKm = findViewById(R.id.etKm);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnDisplay = findViewById(R.id.btnDisplay);
        tvSelect = findViewById(R.id.tvSelect);
        tvSelect.setText(userList.get(0));
        mSelectedName = userList.get(0);

        long date = System.currentTimeMillis();

        //dd/mm/yy

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM, yyyy");
        tvDatePik.setText(simpleDateFormat.format(date));

        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("h:mm");
        tvTimePik.setText(simpleTimeFormat.format(date));


        if (getIntent().hasExtra(Model.DATA)) {
            updateModel = (Model) getIntent().getSerializableExtra(Model.DATA);
            etAmount.setText(updateModel.getAmount());
            etKm.setText(updateModel.getKm());
            tvDatePik.setText(updateModel.getDatepik());
            tvTimePik.setText(updateModel.getTimepik());
            mSelectedName = updateModel.getPay_user();
            tvSelect.setText(updateModel.getPay_user());
            isUpdate = true;

        }


    }


    private void setListener() {

        etKm.addTextChangedListener(new TextWatcher() {
            boolean isEdiging;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (s.length() > 0) {
                    if (isEdiging) return;
                    isEdiging = true;
                    String str = s.toString().replaceAll("[^\\d]", "");
                    double s1 = Double.parseDouble(str);

                    NumberFormat nf2 = NumberFormat.getInstance(Locale.ENGLISH);
                    ((DecimalFormat) nf2).applyPattern("###,###.###");
                    s.replace(0, s.length(), nf2.format(s1));

                    isEdiging = false;
                }
            }
        });

        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupWindow popUp = popupWindowsort();
                popUp.showAsDropDown(tvSelect, 0, 0);
            }
        });

        tvDatePik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddPetrolExpenseActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvDatePik.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }


        });
        tvTimePik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(AddPetrolExpenseActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        tvTimePik.setText(hourOfDay + "-" + minute);

                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();


            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvDatePik.getText().toString().isEmpty()) {
                    Toast.makeText(AddPetrolExpenseActivity.this, "Enter date", Toast.LENGTH_SHORT).show();
                } else if (tvTimePik.getText().toString().isEmpty()) {
                    Toast.makeText(AddPetrolExpenseActivity.this, "Enter time", Toast.LENGTH_SHORT).show();

                } else if (etAmount.getText().toString().isEmpty()) {
                    Toast.makeText(AddPetrolExpenseActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (etKm.getText().toString().isEmpty()) {
                    Toast.makeText(AddPetrolExpenseActivity.this, "Enter km", Toast.LENGTH_SHORT).show();
                } else if (mSelectedName.equalsIgnoreCase("")) {
                    Toast.makeText(AddPetrolExpenseActivity.this, "Select name", Toast.LENGTH_SHORT).show();
                } else {
                    Model model = new Model();
                    model.setDatepik(tvDatePik.getText().toString());
                    model.setTimepik(tvTimePik.getText().toString());
                    model.setAmount(etAmount.getText().toString());
                    model.setKm(etKm.getText().toString());
                    model.setPay_user(mSelectedName);
                    long i;
                    if (isUpdate) {
                        model.setId(updateModel.getId());
                        i = databaseHelper.updateData(model);
                    } else {
                        i = databaseHelper.insertUserData(model);
                        etAmount.getText().clear();
                        etKm.getText().clear();
                    }


                    if (i != 0) {
                        Toast.makeText(AddPetrolExpenseActivity.this, "data insert sucessfully...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddPetrolExpenseActivity.this, ListPetrolExpenseActivity.class);
                        startActivity(intent);
                        if (isUpdate)
                            finish();
                    }


                }
            }
        });


        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPetrolExpenseActivity.this, ListPetrolExpenseActivity.class);
                startActivity(intent);
            }
        });

    }

    private PopupWindow popupWindowsort() {


        final PopupWindow popupWindow = new PopupWindow(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.popup_layout, userList);
        final ListView listViewSort = new ListView(this);
        listViewSort.setAdapter(adapter);

        listViewSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tvSelect.setText(userList.get(i));
                mSelectedName = userList.get(i);
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setWidth(300);
        popupWindow.getAnimationStyle();

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(listViewSort);
        return popupWindow;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
