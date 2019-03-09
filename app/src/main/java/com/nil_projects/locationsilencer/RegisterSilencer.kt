package com.nil_projects.locationsilencer
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register_silencer.*

class RegisterSilencer : AppCompatActivity() {

    val context = this
    var db = databaseHandler(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_silencer)


        autoTransferLongLat()

        btn_save_Silencer.setOnClickListener {
            insertDataRegistrForm()
        }
    }

    fun insertDataRegistrForm()
    {
        var intent = Intent(this, list_view::class.java)
        var user = DataModel(Name_txt!!.text.toString(),Long_txt.text.toString(),Lat_txt.text.toString())
        db.insertData(user)
        startActivity(intent)

    }

            fun autoTransferLongLat() {
                val bundle: Bundle? = intent.extras
                val currentlongitude = bundle!!.getString("longitude_key")
                val currentlatitude = bundle!!.getString("latitude_key")

                Long_txt.setText(currentlongitude)
                Lat_txt.setText(currentlatitude)
            }

        }
