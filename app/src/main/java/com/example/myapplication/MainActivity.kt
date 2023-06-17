package com.example.myapplication

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.example.myapplication.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastnumaric = false
    var stateerror = false
    var lastdot = false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }



    fun onclearclick(view: View) {
        binding.dataTv.text = ""
        lastnumaric = false
    }

    fun onbackclick(view: View) {
        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)

        try {
            val lastchar = binding.dataTv.text.toString().last()
            if(lastchar.isDigit())
                onequal()
        }catch (e : Exception){
            binding.resultTv.text = ""
            binding.resultTv.visibility = View.GONE
            Log.e("last char error" , e.toString())
        }

    }

    fun onopclick(view: View) {
        if(!stateerror && lastnumaric){
            binding.dataTv.append((view as Button).text)
            lastdot = false
            lastnumaric = false
            onequal()
        }
    }

    fun digionclick(view: View) {
        if (stateerror){
            binding.dataTv.text = (view as Button).text
            stateerror = false
        }else{
            binding.dataTv.append((view as Button).text)
        }
        lastnumaric = true
        onequal()
    }

    fun onallclearclick(view: View) {
        binding.dataTv.text  = ""
        binding.resultTv.text = ""
        stateerror = false
        lastdot = false
        lastnumaric = false
        binding.resultTv.visibility = View.GONE
    }

    fun onequalclick(view: View) {
        onequal()
        binding.dataTv.text= binding.resultTv.text.toString().drop(1)
    }

    fun onequal(){
        if (lastnumaric && !stateerror){
            val txt = binding.dataTv.text.toString()
            expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                binding.resultTv.visibility = View.VISIBLE
                binding.resultTv.text = "=" + result.toString()
            }catch (ex : ArithmeticException){
                Log.e("evaluate error", ex.toString())
                binding.resultTv.text = "Error"
                stateerror = true
                lastnumaric = false
            }
        }
    }
}