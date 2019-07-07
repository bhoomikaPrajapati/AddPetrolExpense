package e.dell.addpetrolexpense;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import e.dell.addpetrolexpense.database.DatabaseHelper;
import e.dell.addpetrolexpense.model.Model;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
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

            isUpdate = true;

        }


    }


    private void setListener() {


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
                    Toast.makeText(AddPetrolExpenseActivity.this, "plese enter date", Toast.LENGTH_SHORT).show();
                } else if (tvTimePik.getText().toString().isEmpty()) {
                    Toast.makeText(AddPetrolExpenseActivity.this, "plese enter time", Toast.LENGTH_SHORT).show();

                } else if (etAmount.getText().toString().isEmpty()) {
                    Toast.makeText(AddPetrolExpenseActivity.this, "plese enter Amount", Toast.LENGTH_SHORT).show();
                } else if (etKm.getText().toString().isEmpty()) {
                    Toast.makeText(AddPetrolExpenseActivity.this, "plese enter km", Toast.LENGTH_SHORT).show();
                } else {
                    Model model = new Model();
                    model.setDatepik(tvDatePik.getText().toString());
                    model.setTimepik(tvTimePik.getText().toString());
                    model.setAmount(etAmount.getText().toString());
                    model.setKm(etKm.getText().toString());
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


}
