package com.example.developer.cloudprint;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginUser extends AppCompatActivity implements View.OnClickListener{

    private EditText login_username;
    private EditText login_password;
    private Button user_login_button;
    private Button user_register_button;
    SQLiteDatabase mysql;
    String DB_QUERY;
    String QUERY_result;
    Cursor result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_login_user);
        initWidget();
    }
    private void initWidget()
    {
        login_username=(EditText)findViewById(R.id.login_username);
        login_password=(EditText)findViewById(R.id.login_password);
        user_login_button=(Button)findViewById(R.id.user_login_button);
        user_register_button=(Button)findViewById(R.id.user_register_button);
        mysql=this.openOrCreateDatabase("user.db", MODE_PRIVATE, null);
        DB_QUERY = "SELECT * FROM USER1 WHERE USERNAME=? AND PASSWORD=?";
        user_login_button.setOnClickListener(this);
        user_register_button.setOnClickListener(this);
        login_username.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(!hasFocus){
                    String username=login_username.getText().toString().trim();
                    if(username.length()<4){
                        Toast.makeText(LoginUser.this, "Username should be more then 4 digits.", Toast.LENGTH_SHORT);
                    }
                }
            }

        });
        login_password.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(!hasFocus){
                    String password=login_password.getText().toString().trim();
                    if(password.length()<4){
                        Toast.makeText(LoginUser.this, "Password should be more then 8 digits.", Toast.LENGTH_SHORT);
                    }
                }
            }

        });
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId())
        {
            case R.id.user_login_button:
                if(checkEdit())
                {
                    login();
                }
                break;
            case R.id.user_register_button:
                Intent intent2=new Intent(LoginUser.this,RegisterUser.class);
                startActivity(intent2);
                break;
        }
    }

    private boolean checkEdit(){
        if(login_username.getText().toString().trim().equals("")){
            Toast.makeText(LoginUser.this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
        }else if(login_password.getText().toString().trim().equals("")){
            Toast.makeText(LoginUser.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }
        return false;
    }

    private void login(){
        result=mysql.rawQuery(DB_QUERY,new String []{login_username.getText().toString().trim(),login_password.getText().toString().trim()});
        //QUERY_result=result.getString(0);
        if(result.getCount()>0){
            Intent intent;
            intent = new Intent(LoginUser.this,LoggedUser.class);
            Bundle bundle = new Bundle();
            bundle.putString("UserName", login_username.getText().toString().trim());
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Toast.makeText(LoginUser.this,"Your password or username is wrong", Toast.LENGTH_SHORT).show();
        }
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
