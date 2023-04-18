package com.hadirahimi.iphonephoneforrecord

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.hadirahimi.iphonephoneforrecord.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    //binding
    private lateinit var binding : ActivityMainBinding
    private val CALL_PHONE_PERMISSION_REQUEST_CODE = 100
    private lateinit var telephonyManager : TelephonyManager
    
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }
    
    fun funClick(view : View)
    {
        binding.apply {
            val phoneNumber = etPhoneNumber.text.toString().trim()
            when(view.id)
            {
                in listOf(R.id.num1,R.id.num2,R.id.num3,R.id.num4,R.id.num5,R.id.num6,R.id.num7,R.id.num8,R.id.num9,R.id.num0,R.id.numHashtag,R.id.numStar) ->{
                    val number = when(view.id)
                    {
                        R.id.num1 -> "1"
                        R.id.num2 -> "2"
                        R.id.num3 -> "3"
                        R.id.num4 -> "4"
                        R.id.num5 -> "5"
                        R.id.num6 -> "6"
                        R.id.num7 -> "7"
                        R.id.num8 -> "8"
                        R.id.num9 -> "9"
                        R.id.num0 -> "0"
                        R.id.numHashtag -> "#"
                        R.id.numStar -> "*"
                        else -> ""
                    }
                    etPhoneNumber.text.append(number)
                }
                R.id.ivBackSpace ->{
                    if (etPhoneNumber.text.isNotEmpty())
                        removeLastCharacter()
                    
                }
                R.id.ivCall ->{
                    if (phoneNumber.isNotEmpty())
                    {
                        if (ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                        {
                            startCall(phoneNumber)
                        }else
                        {
                            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.CALL_PHONE),CALL_PHONE_PERMISSION_REQUEST_CODE)
                            
                        }
                    }else Toast.makeText(this@MainActivity , "empty" , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    override fun onRequestPermissionsResult(requestCode : Int , permissions : Array<out String> , grantResults : IntArray)
    {
        super.onRequestPermissionsResult(requestCode , permissions , grantResults)
        if (requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //permission Granted, start call
                val phoneNumber = binding.etPhoneNumber.text.toString().trim()
                if (phoneNumber.isNotEmpty()){
                    startCall(phoneNumber)
                }
            }else
            {
                //Permission Denied,show error message
                Toast.makeText(this@MainActivity , "permission required" , Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun startCall(phoneNumber:String){
        val intent = Intent(Intent.ACTION_CALL,Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    }
    private fun removeLastCharacter()
    {
        val text = binding.etPhoneNumber.text.toString()
        if (text.isNotEmpty())
        {
            val newText = text.substring(0,text.length-1)
            binding.etPhoneNumber.setText(newText)
            binding.etPhoneNumber.setSelection(newText.length)
        }
    }
}











