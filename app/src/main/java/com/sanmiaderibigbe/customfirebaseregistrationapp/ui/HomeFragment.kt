package com.sanmiaderibigbe.customfirebaseregistrationapp.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.sanmiaderibigbe.customfirebaseregistrationapp.R
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.Status
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    private val viewModels: LoginHomeSharedViewModel by activityViewModels()
    private lateinit var  navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()


        btn_sign_out.setOnClickListener {
            viewModels.signOut()
        }

        viewModels.getLoginResource().observe(viewLifecycleOwner, Observer {
                loginResource ->

            when(loginResource.status){
                Status.LOADING -> {

                }

                Status.LOADED -> {
                    Toast.makeText(activity, "loaded", Toast.LENGTH_SHORT).show()

                    when( loginResource.data) {
                        LoginHomeSharedViewModel.AuthenticationState.UNAUTHENTICATED -> {
                            navController.navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
                            Toast.makeText(activity, "unauthenticated", Toast.LENGTH_SHORT).show()
                        }

                        LoginHomeSharedViewModel.AuthenticationState.AUTHENTICATED -> {

                        }
                    }
                }

                Status.SUCCESS -> {

                }

                Status.ERROR -> {

                }
            }
        })


    }
}


