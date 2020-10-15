package apri.dts.cruddbsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    TextView inName, inMajors;
    Button btnSimpan, btnHapus;
    DBHelper db;
    String extId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        inName = findViewById(R.id.ed_name);
        inMajors = findViewById(R.id.ed_majors);
        btnHapus = findViewById(R.id.btn_delete);
        btnSimpan = findViewById(R.id.btn_submit);

        Bundle b = getIntent().getExtras();
        extId = b.getString("ext_id");
        String extName = b.getString("ext_name");
        String extMajors = b.getString("ext_majors");

        inName.setText(extName);
        inMajors.setText(extMajors);
        db = new DBHelper(this);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.update(Integer.parseInt(extId), inName.getText().toString(), inMajors.getText().toString());
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                String info = getResources().getString(R.string.info_success_add_student);
                Toast.makeText(EditActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tampilkanDialogKonfirmasiHapusCatatan();
            }
        });

    }

    void tampilkanDialogKonfirmasiHapusCatatan (){
        new AlertDialog.Builder(this)
                .setTitle("Hapus Akun ini ?")
                .setMessage("Apakah anda yakin ingin menghapus akun ini?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.delete(Integer.parseInt(extId));
                        Intent intent = new Intent(EditActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }


}