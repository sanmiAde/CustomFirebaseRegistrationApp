package com.sanmiaderibigbe.customfirebaseregistrationapp.ui.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.sanmiaderibigbe.customfirebaseregistrationapp.R
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.Resource
import com.sanmiaderibigbe.customfirebaseregistrationapp.repo.Status
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class LoginFragment : Fragment() {

    private val viewModels by viewModel<LoginHomeSharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()


        viewModels.getLoginResource().observe(viewLifecycleOwner, Observer { loginResource ->

            loginUser(loginResource, navController)
        })

        login.setOnClickListener {
            viewModels.signIn(txt_name.text.toString(), txt_password.text.toString())
        }


    btn_register.setOnClickListener {
        navController.navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationPersonalFragment())
    }

    }

    private fun loginUser(
        loginResource: Resource<LoginHomeSharedViewModel.AuthenticationState>,
        navController: NavController
    ) {
        when (loginResource.status) {
            Status.LOADING -> {
                Toast.makeText(activity, "Loading", Toast.LENGTH_SHORT).show()
            }

            Status.LOADED -> {
                Toast.makeText(activity, "loaded", Toast.LENGTH_SHORT).show()
            }

            Status.SUCCESS -> {
                Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show()

                when (loginResource.data) {
                    LoginHomeSharedViewModel.AuthenticationState.UNAUTHENTICATED -> {

                    }

                    LoginHomeSharedViewModel.AuthenticationState.AUTHENTICATED -> {
                        navController.navigate(R.id.homeFragment)
                        Toast.makeText(activity, "Authenticated", Toast.LENGTH_SHORT).show()
                    }
                }

            }

            Status.ERROR -> {
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show()

                when (loginResource.data) {
                    LoginHomeSharedViewModel.AuthenticationState.UNAUTHENTICATED -> {
                        Toast.makeText(activity, "${loginResource.message}", Toast.LENGTH_SHORT).show()
                    }
                    LoginHomeSharedViewModel.AuthenticationState.AUTHENTICATED -> TODO()
                    null -> TODO()
                }
            }
        }
    }
}
