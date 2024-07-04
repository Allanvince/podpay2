package com.vince.tucash.fragments

import com.vince.tucash.view_model.MenuViewModel
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.vince.tucash.R
import com.vince.tucash.data.LoginData
import com.vince.tucash.data.LoginResponse
import de.hdodenhof.circleimageview.CircleImageView

class MenuBtnFragment : Fragment() {

    private lateinit var menuViewModel: MenuViewModel
    private lateinit var loginResponse: LoginResponse



    private var selectedImgUri: Uri? = null
    private var imagePick = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_menu_btn, container, false)

        val backButton = view.findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener{
            val homeFragment = HomeFragment()
            val transition : FragmentTransaction = requireFragmentManager().beginTransaction()
            transition.replace(R.id.flFragment, homeFragment)
            transition.commit()
          }

        menuViewModel = ViewModelProvider(this)[MenuViewModel::class.java]

        loginResponse = LoginData.LoginResponse

        menuViewModel.walletBalanceData.observe(viewLifecycleOwner, Observer { balance ->
            val menuBalanceView = view.findViewById<TextView>(R.id.menuBalanceView)
            menuBalanceView.text = balance

        })

        menuViewModel.fetchUserId(loginResponse)




        val whatsappIcon = view.findViewById<TextView>(R.id.whatsappIcon)
        whatsappIcon.setOnClickListener {
            val phoneNumber = "254798120240"
            val whatsappUrl = "https://api.whatsapp.com/send?phone=$phoneNumber"

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(whatsappUrl)
            startActivity(intent)
        }

        val emailIcon = view.findViewById<TextView>(R.id.emailText)
        emailIcon.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("tuc50850@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject of the email")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body of the  email")
            startActivity(Intent.createChooser(emailIntent, "send email"))
        }


        val profilePicBtn = view.findViewById<ImageView>(R.id.cameraProfile)

        profilePicBtn.setOnClickListener{
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, imagePick)
        }

        val loadWalletBtn = view.findViewById<Button>(R.id.loadWalletButton)
        loadWalletBtn.setOnClickListener {
            val loadWalletFragment = LoadWalletFragment()
            val transition: FragmentTransaction = requireFragmentManager().beginTransaction()
            transition.replace(R.id.flFragment, loadWalletFragment)
            transition.commit()
        }
        val logOutBtn = view.findViewById<Button>(R.id.logoutBtn)
        logOutBtn.setOnClickListener {
            //TODO
        }

        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data !=null){
            if (data.data != null){
                selectedImgUri = data.data

                val picHolder = view?.findViewById<ImageView>(R.id.cameraProfile)
                picHolder?.setImageURI(selectedImgUri)

                uploadProfilePic()
            }
        }
    }

    private fun uploadProfilePic() {

        //
    }

    private fun storeData(imageUrl: Uri?) {
      //
    }
}