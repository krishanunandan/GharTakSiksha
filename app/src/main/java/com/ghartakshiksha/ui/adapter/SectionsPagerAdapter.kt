package com.ghartakshiksha.ui.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ghartakshiksha.ui.fragment.AllLeadsFragment
import com.ghartakshiksha.ui.fragment.ForYouLeadsFragment

class SectionsPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllLeadsFragment()
            else -> ForYouLeadsFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}