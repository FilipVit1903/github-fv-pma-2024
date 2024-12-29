package com.example.myapp007a_fragmentsexample_01

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ConsolePagerAdapter(
    fragmentActivity: FragmentActivity,
    private val consoles: List<Console>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = consoles.size

    override fun createFragment(position: Int): Fragment {
        val console = consoles[position]
        return ConsoleFragment.newInstance(console.name, console.imageResId)
    }
}