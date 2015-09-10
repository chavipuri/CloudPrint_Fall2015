package com.example.developer.cloudprint;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class RegisterUser extends AppCompatActivity {

    private EditText register_username;
    private EditText register_passwd;
    private EditText reregister_passwd;
    private Button register_submit;
    private Button login;
    SQLiteDatabase mysql;
    String DB_CREATE = "CREATE TABLE IF NOT EXISTS USER1(USERNAME TEXT PRIMARY KEY," +
            "PASSWORD TEXT)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_register_user);
        mysql=this.openOrCreateDatabase("user.db", MODE_PRIVATE, null);
        mysql.execSQL(DB_CREATE);
        register_username=(EditText)findViewById(R.id.register_username);
        register_passwd=(EditText)findViewById(R.id.register_password);
        reregister_passwd=(EditText)findViewById(R.id.register_cpassword);
        register_submit=(Button)findViewById(R.id.register_submit);
        login=(Button)findViewById(R.id.login);
        register_username.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(register_username.getText().toString().trim().length()<4){
                        Toast.makeText(RegisterUser.this, "Username should be more then 4 digits.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        register_passwd.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(register_passwd.getText().toString().trim().length()<6){
                        Toast.makeText(RegisterUser.this, "Password should be more then 8 digits.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        reregister_passwd.setOnFocusChangeListener(new OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(!reregister_passwd.getText().toString().trim().equals(register_passwd.getText().toString().trim())){
                        Toast.makeText(RegisterUser.this, "The passwords are not match.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        login.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterUser.this,LoginUser.class);
                startActivity(intent);
            }

        });
        register_submit.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!checkEdit()){
                    return;
                }
                mysql.execSQL("INSERT INTO USER1(USERNAME,PASSWORD) VALUES('"+register_username.getText().toString().trim()+"','"+register_passwd.getText().toString().trim()+"')");
                Intent intent=new Intent(RegisterUser.this,LoginUser.class);
                startActivity(intent);
            }

        });
    }
    private boolean checkEdit(){
        if(register_username.getText().toString().trim().equals("")){
            Toast.makeText(RegisterUser.this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
        }else if(register_passwd.getText().toString().trim().equals("")){
            Toast.makeText(RegisterUser.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
        }else if(!register_passwd.getText().toString().trim().equals(reregister_passwd.getText().toString().trim())){
            Toast.makeText(RegisterUser.this, "The passwords are not match.", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
