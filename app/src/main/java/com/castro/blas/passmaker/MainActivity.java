package com.castro.blas.passmaker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private EditText intext, outtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intext = (EditText) findViewById(R.id.intext);
        outtext = (EditText) findViewById(R.id.outtext);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void generator(View view){
        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        intext = (EditText) findViewById(R.id.intext);
        outtext = (EditText) findViewById(R.id.outtext);
        String s= intext.getText().toString();
        MessageDigest m= null;
        String busqueda = prefe.getString(
                intext.getText().toString(), "");
        if(busqueda.equals("")) {
            try {
                m = MessageDigest.getInstance("MD5");
                m.update(s.getBytes(), 0, s.length());
                BigInteger conversion = new BigInteger(1, m.digest());
                int num1 = 1;
                int num2 = 1001;
                int numAleatorio = (int) Math.floor(Math.random() * (num1 - num2) + num2);
                conversion = conversion.multiply(
                        BigInteger.valueOf(numAleatorio));
                outtext.setText(conversion.toString(16).substring(0, 12));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

        }
        else {
            outtext.setText(busqueda);
        }

    }

    public void savePassword(View view){
        intext = (EditText) findViewById(R.id.intext);
        outtext = (EditText) findViewById(R.id.outtext);
        SharedPreferences preferencias=getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString(intext.getText().toString(), outtext.getText().toString());
        editor.commit();
    }

    public void cleanAll(View view){
        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        preferencias.edit().clear().commit();
    }

}
