package com.example.vishnunarayanan.dbapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText inputTime, inputX, inputY, inputZ;
    DBHandler myHandler;
    TextView resultText;
    float[] values_x;
    float[] values_y;
    float[] values_z;
    private static String tablename="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        values_x=new float[10];
        values_y=new float[10];
        values_z=new float[10];

        inputTime=(EditText) findViewById(R.id.inputTime);
        inputX=(EditText) findViewById(R.id.inputX);
        inputY=(EditText) findViewById(R.id.inputY);
        inputZ=(EditText) findViewById(R.id.inputZ);
        resultText=(TextView) findViewById(R.id.resultText);

        tablename="MyTable";
        myHandler=new DBHandler(this,tablename);
        printDatabase();

        Intent it=new Intent(this, SensorHandlerAccelerometer.class);
        startService(it);
    }

    public static String getTablename() {
        return tablename;
    }

    public static void setTablename(){
        //Get patient data and set table name
    }

    public static void setTablename(String tablename) {
        MainActivity.tablename = tablename;
    }

    public void printDatabase(){
        String dbString=myHandler.dbToString();
        resultText.setText(dbString);
        inputTime.setText("");
        inputX.setText("");
        inputY.setText("");
        inputZ.setText("");
    }

    public void addRecordClicked(View v){
        long timestamp=Long.parseLong(inputTime.getText().toString());
        float value_x= (float) Double.parseDouble(inputX.getText().toString());
        float value_y=(float) Double.parseDouble(inputY.getText().toString());
        float value_z=(float) Double.parseDouble(inputZ.getText().toString());
        myHandler.addTuple(timestamp, value_x, value_y, value_z);
        printDatabase();
    }

    public void getTopTenRecords(View v){
        List<AccelerometerTuple> result=myHandler.dbTopTen();
        String currRecords="";
        for(int i=0; i<result.size();++i){
            values_x[i]=result.get(i).getValue_x();
            values_y[i]=result.get(i).getValue_y();
            values_z[i]=result.get(i).getValue_z();
            currRecords+= result.get(i).getTimestamp()+"\t"+values_x[i]+"\t"+values_y[i]+"\t"+values_z[i]+"\n";
        }
        resultText.setText(currRecords);
    }

    public void deleteRecords(View v){
        myHandler.clearTable();
        resultText.setText("");
    }
}
