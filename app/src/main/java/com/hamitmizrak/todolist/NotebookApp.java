package com.hamitmizrak.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// CLASS
public class NotebookApp extends AppCompatActivity {

    // Global Variable
    private EditText notebookEditTextId;
    private String notebookEditTextToString;
    private Button notebookResetButtonId;
    private Button notebookSubmitButtonId;
    private Button notebookRedirectButtonId;
    private TextView notebookHandleTextViewId;
    private ImageView notebookImageViewId;

    // Other Activity Handling(MainActivity)
    private Intent otherActivityIntent;

    // ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notebook_app);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Loglama
        Log.e("onCreate", "Notebook Create(Oluşturuldu)");

        // Toast
        //Toast.makeText(this, "Anasayfaya Hoşgeldiniz", Toast.LENGTH_SHORT).show();
        //String welcome = getString(R.string.notebook_welcome);
        //Toast.makeText(this, welcome, Toast.LENGTH_SHORT).show();

        // MainActivity gelen veriyi almak (Activityler arasında veri taşımak)
        otherActivityIntent = getIntent();
        String mainActivityPutExtra = "Hoşgediniz ".concat(otherActivityIntent.getStringExtra("key_main"));
        Toast.makeText(NotebookApp.this, mainActivityPutExtra, Toast.LENGTH_SHORT).show();

        // START
        // ID
        notebookEditTextId = findViewById(R.id.notebookEditTextId);
        notebookHandleTextViewId = findViewById(R.id.notebookHandleTextViewId);

        // SUBMIT BUTTON
        notebookSubmitButtonId = findViewById(R.id.notebookSubmitButtonId);
        notebookImageViewId = findViewById(R.id.notebookImageViewId);

        // SUBMIT DATABASE
        // SecretID
        //  --- username: "Hamit"
        // DatabaseReference : Firebase veritabanından referans oluşturmak içindir.
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("todolist").child("message");
        Log.e("Database Key: ", databaseReference.getKey());

        // Firebase Realtime Databasesden veri çekmek istediğimizde  ve verilerdeki herhangi bir değişiklik meydana geldiğinde dinlemek için kullanırız.
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            // onDataChange: Veritabanı dinleyicidir olarak kullanmaktayız. Bu metod veritabanında verilerle bir değişiklik söz konusuysa tetiklenir.
            // DataSnapshot: Metodun parametresindeki olan bu verinin anlamı => Veritabanında belirli bir andaki verileri temsil eder.
            // DataSnapshot: O andaki verileri içerir ve veritabanındaki verileri okumamıza olanak sağlar.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // exists(): Datasnapshoty'ın var olup olmadığını kontrol etmek için yazılır. Eğer veri yoksa veri işlemleri yapılmaz.
                if(snapshot.exists()){
                    // KEY: Veri anahtarı
                    Log.i("KEY: ", snapshot.getKey());

                    // VALUE: Veri değeri
                    // Eğer Veri varsa
                    if(snapshot.getValue()!=null){
                        // VALUE
                        Log.i("VALUE: ", snapshot.getValue().toString());
                    }else{
                        Log.e("VALUE: ","Vo value Found");
                    }
                }else{
                    Log.e("VALUE: ","Snaphot does not exist");
                }
            } //end onDataChange

            // onCancelled: Veri okuma işlemi iptal edildiğinde veya bir hata meydana geldiğinde tetiklenir.
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase Error: ",error.getDetails());
                Log.e("Firebase Error: ",error.getMessage());
            }
        });

        // Bu kod parçasında bir butona tıkladığımız zaman bir olay dinleyicidir.
        notebookSubmitButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TextView Visibility
                notebookHandleTextViewId.setVisibility(view.VISIBLE);

                // EditText'ten verileri alıp String'e çevirdim
                notebookEditTextToString = notebookEditTextId.getText().toString();
                Log.i("EditText", notebookEditTextToString);

                // EditText'ten aldığım datayı TextView'e ekle
                notebookHandleTextViewId.setText(notebookEditTextToString.toLowerCase());

                // Resim Iconu Değiştir
                notebookImageViewId.setImageResource(R.drawable.redirect);
            }
        });

        // RESET BUTTON
        notebookResetButtonId = findViewById(R.id.notebookResetButtonId);
        notebookResetButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notebookEditTextId.setText("");
            }
        });

        // HOME PAGE
        notebookRedirectButtonId = findViewById(R.id.notebookRedirectButtonId);
        notebookRedirectButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Yönlendirme
                //Intent notebookIntent= new Intent(getApplicationContext(),MainActivity.class);
                Intent notebookIntent = new Intent(NotebookApp.this, MainActivity.class);
                Toast.makeText(NotebookApp.this, "NoteBook Sayfasına Yönlendirildi", Toast.LENGTH_SHORT).show();
                startActivity(notebookIntent);
            }
        });

    } //end onCreate
} //end NotebookApp