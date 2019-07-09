package e.dell.addpetrolexpense;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import e.dell.addpetrolexpense.adapter.DisplayListAdapter;
import e.dell.addpetrolexpense.database.DatabaseHelper;
import e.dell.addpetrolexpense.listener.OnItemClickListenr;
import e.dell.addpetrolexpense.model.Model;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListPetrolExpenseActivity extends AppCompatActivity implements OnItemClickListenr {

    private RecyclerView rvDetail;
    private DatabaseHelper databaseHelper;
    private List<Model> mUserData = new ArrayList<>();
    private TextView tvTotal;
    private TextView tvSelect;
    private ArrayList<String> userList = new ArrayList<>();
    private ArrayList<Model> tempList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petrol_expense);
        init();
    }

    private void init() {
        databaseHelper = new DatabaseHelper(this);
        mUserData.clear();
        tempList.clear();
        tvTotal = findViewById(R.id.tvTotal);
        tvSelect = findViewById(R.id.tvSelect);
        mUserData.addAll(databaseHelper.getUserData());
        tempList.addAll(mUserData);
        rvDetail = findViewById(R.id.rvDetail);
        rvDetail.setLayoutManager(new LinearLayoutManager(this));
        rvDetail.setAdapter(new DisplayListAdapter(mUserData, this, this));
        userList.clear();
        userList.add("ALL");
        userList.add("Chirag");
        userList.add("Bhoomika");
        getTotal();
        setListener();

    }

    private void setListener() {
        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupWindow popUp = popupWindowsort();
                popUp.showAsDropDown(tvSelect, 0, 0);
            }
        });
    }

    private void getTotal() {
        int total = 0;
        if (mUserData.size() > 0) {
            for (int i = 0; i < mUserData.size(); i++) {

                total = total + Integer.parseInt(mUserData.get(i).getAmount());
                if (i == (mUserData.size() - 1)) {
                    tvTotal.setText("Total Amount : " + total);
                }
            }
        } else {
            tvTotal.setText("Total Amount : " + total);
        }
    }

    @Override
    public void onItemClickLister(View view, final int pos) {
        if (view.getId() == R.id.btnDelete) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert");
            builder.setMessage("Are you Sure you want to delete?");
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    databaseHelper.deleteExpenses(mUserData.get(pos).getId());
                    mUserData.remove(pos);
                    rvDetail.getAdapter().notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            builder.show();


        } else if (view.getId() == R.id.btnEdit) {

            Intent intent = new Intent(this, AddPetrolExpenseActivity.class);
            intent.putExtra(Model.DATA, mUserData.get(pos));
            startActivity(intent);
            finish();

        }
        getTotal();
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

                setData(userList.get(i));

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

    private void setData(String name) {
        mUserData.clear();
        if (name.equalsIgnoreCase("all")) {
            mUserData.addAll(tempList);
        } else {

            for (int i = 0; i < tempList.size(); i++) {
                if (name.equalsIgnoreCase(tempList.get(i).getPay_user())) {
                    mUserData.add(tempList.get(i));
                }
            }

        }

        rvDetail.getAdapter().notifyDataSetChanged();
        getTotal();

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
