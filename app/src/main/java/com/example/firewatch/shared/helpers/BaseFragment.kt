package com.example.firewatch.shared.helpers

import androidx.fragment.app.Fragment
import com.example.firewatch.services.store.StoreController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import javax.inject.Inject

@AndroidEntryPoint
@WithFragmentBindings
abstract class BaseFragment : Fragment() {
    @Inject
    lateinit var storeController: StoreController

    protected fun reload() {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction();
        fragmentTransaction.detach(this);
        fragmentTransaction.attach(this);
        fragmentTransaction.commit();
    }
}