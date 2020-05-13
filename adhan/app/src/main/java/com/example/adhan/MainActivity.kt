package com.example.adhan


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {
    //button objects
    private var buttonStart: Button? = null
    private var buttonStop: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //getting buttons from xml
        System.out.println("oooooooooook"+timePicker.currentHour)
        buttonStart = findViewById<View>(R.id.buttonStart) as Button
        buttonStop = findViewById<View>(R.id.buttonStop) as Button
        //attaching onclicklistener to buttons
        buttonStart!!.setOnClickListener(this)
        buttonStop!!.setOnClickListener(this)



    }


    override fun onClick(view: View) {
        if (view === buttonStart) { //starting service
           var input :  String= "حان الان موعد الصلاه"
            val alarmHour = timePicker.currentHour
            val alarmMinute = timePicker.currentMinute
           System.out.println("oooooooooook"+alarmHour)

             var intent : Intent =Intent(this, MyService::class.java)
            intent.putExtra("inputExtra", input);
            intent.putExtra("alarmHour", alarmHour)
            intent.putExtra("alarmMinute", alarmMinute)
            startService(intent)
        } else if (view === buttonStop) { //stopping service
            stopService(Intent(this, MyService::class.java))
        }
    }
}
