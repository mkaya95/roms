package ba.edu.ibu.roms;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    RomsProvider rp = new RomsProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout myRoot = findViewById(R.id.default_layout);

        String myLoginEmailAddress = getLoginEmailAddress();
        TextView loginInformation = (TextView)findViewById(R.id.textViewEmail);
        if(myLoginEmailAddress != null || !myLoginEmailAddress.equals("")){
            loginInformation.setText("Welcome!!! You have logged in as " + myLoginEmailAddress);
        }else {
            loginInformation.setText("Your login email is missing");
        }


        Intent intent = new Intent(this, Overview.class);
        startActivity(intent);




       /*

       Button btn1 = new Button(this);
        btn1.setText("Button_text");

        gridLayout.addView(btn1);



                ConstraintLayout myRoot = findViewById(R.id.default_layout);


        LinearLayout linlayout = new LinearLayout(this);
        linlayout.setOrientation(LinearLayout.VERTICAL);
        myRoot.addView(linlayout);
*/
//
//        Button btn2 = new Button(this);
//        btn1.setText("Button_text");
//
//        gridLayout.addView(btn2);
//
//        Button btn3 = new Button(this);
//        btn1.setText("Button_2_text");
//
//        gridLayout.addView(btn3);


    }

    private String getLoginEmailAddress(){
        String storedEmail = "";
        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getExtras();
        if(mBundle != null){
            storedEmail = mBundle.getString("EMAIL");
        }
        return storedEmail;
    }




}
