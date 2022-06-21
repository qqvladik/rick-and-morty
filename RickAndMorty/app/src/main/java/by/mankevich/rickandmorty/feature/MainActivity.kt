package by.mankevich.rickandmorty.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.mankevich.rickandmorty.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isContainerFragmentEmpty = savedInstanceState == null
        if(isContainerFragmentEmpty) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, MainFragment())
                commit()
            }
        }
    }
}