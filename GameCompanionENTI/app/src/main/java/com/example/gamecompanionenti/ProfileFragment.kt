package com.example.gamecompanionenti


import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_event_one.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        initUI()
        handlePopup()
    }

    private fun initUI(){
        if(FirebaseAuth.getInstance().currentUser!=null){
            //TODO: Show profile
            signoutbutton.setOnClickListener {

                FirebaseAuth.getInstance().signOut()

                val fragmentTransaction = fragmentManager?.beginTransaction()
                val fragment = HomeFragment()
                fragmentTransaction?.replace(R.id.fragment_container, fragment)
                fragmentTransaction?.commit()
                val context = this
            }

            takePicture.setOnClickListener {
                dispatchTakePictureIntent()
            }

            val user = FirebaseAuth.getInstance().currentUser
            FirebaseFirestore.getInstance()
                .collection("users")
                .document((user?.uid).toString())
                .get()
                .addOnSuccessListener {documentSnapshot->
                    val usuario = documentSnapshot.toObject(com.example.gamecompanionenti.UserModel::class.java)
                    contnetName.text = usuario?.username
                    contnetEmail.text = usuario?.email
                    if(usuario?.avatarUrl.toString()!="") {
                        Glide
                            .with(this)
                            .load(usuario?.avatarUrl)
                            .apply(
                                RequestOptions()
                                    .transforms(CenterCrop())
                                    .placeholder(R.drawable.ic_profile)
                            )
                            .into(imageView2)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun handlePopup(){
        usersettings.setOnClickListener {
            val builder = AlertDialog.Builder(view?.context)
            val builderView = layoutInflater.inflate(R.layout.custom_dialog,null)
            builder.setView(builderView)
            builder.setCancelable(false)
            builder.setPositiveButton("OK",{ dialogInterface: DialogInterface, i: Int -> })
            builder.show()
        }

    }

    //Creamos un archivo temporal donde guardar la imagen:
    var currentPhotoPath: String? = null
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    //Lanzamos un Intent para capturar una foto:
    val REQUEST_IMAGE_CAPTURE = 1
    private fun dispatchTakePictureIntent() {
        activity?.let { activity ->
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(activity.packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        ex.printStackTrace()
                        Log.e("ProfileFragment", "Error creating camera file")
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            activity,
                            "com.example.gamecompanionenti.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    //Cogemos la imagen que nos devuelve la cámara y la ponemos en nuestra ImageView!
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val thumbnailBitmap = data?.extras?.get("data") as? Bitmap
            currentPhotoPath?.let {

                val fullImage = BitmapFactory.decodeFile(currentPhotoPath)
                imageView2.setImageBitmap(fullImage)

                val file = File(currentPhotoPath.toString())
                //Modificamos la referencia para que apunte al nuevo archivo que crearemos:
                val avatarStorageReference = FirebaseStorage.getInstance().getReference("images/users/${file.name}.jpg")
                //Creamos un objeto Uri
                val uri = Uri.fromFile(file)
                //Ahora ya subimos el archivo a Storage y guardamos la variable de la task:
                val uploadTask = avatarStorageReference.putFile(uri)
                //Y por supuesto, añadimos nuestros listeners de success/fail
                uploadTask .addOnSuccessListener {
                    // All good!
                    Log.i("ProfileFragment", "File upload success!")
                }
                    .addOnFailureListener {
                        // Handle unsuccessful uploads
                        Log.w("ProfileFragment", "Error uploading file :(")
                    }

                val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation avatarStorageReference.downloadUrl
                }).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Got URL!!
                        val downloadUri = task.result
                        // TODO: Save to user profile
                        // Obtenemos el id de usuario
                        FirebaseAuth.getInstance().currentUser?.uid?.let {uid ->
                            // Actualizamos el documento del usuario
                            FirebaseFirestore.getInstance().collection("users").document(uid).update("avatarUrl", downloadUri.toString())
                        }


                    } else {
                        // Handle failures
                        Log.w("ProfileFragment", "Error getting download url :( " + task.exception?.message)
                    }
                }


            }
        }
    }


}
