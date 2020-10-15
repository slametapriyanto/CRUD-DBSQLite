package apri.dts.cruddbsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DBHelper db;
    EditText inName, inMajor;
    Button btnAdd, btnShow;
    ArrayList<HashMap<String, String>> listData;
    ArrayList<HashMap<String, String>> listData1;
    ListView listView;
    Context mContext;
    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        inName = findViewById(R.id.in_name);
        inMajor = findViewById(R.id.in_majors);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addStudent(inName.getText().toString(), inMajor.getText().toString());
                inName.setText("");
                inMajor.setText("");

                String info = getResources().getString(R.string.info_success_add_student);
                Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });

        btnShow = findViewById(R.id.btn_show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listData = db.getStudentList();
                inName.setText("");
                inMajor.setText("");
                showData();
            }
        });

        listView = findViewById(R.id.listview);
    }
    void showData(){
        SimpleAdapter adapter = new SimpleAdapter(this, listData, android.R.layout.simple_list_item_2,
                new String[]{"name", "majors"}, new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String idx = listData.get(position).get("id");
                final String name = listData.get(position).get("name");
                final String majors = listData.get(position).get("majors");
                                Map<String, String> item = listData.get(position);
                                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                                intent.putExtra("ext_id", idx);
                                intent.putExtra("ext_name", name);
                                intent.putExtra("ext_majors", majors);
                                startActivity(intent);

////
                Log.d("LOG_TAG", "Click Id: " + item.get("id"));
                Log.d("LOG_TAG", "Click Name: " + item.get("name"));
                Log.d("LOG_TAG", "Click Majors: " + item.get("majors"));
            }
        });
    }
}