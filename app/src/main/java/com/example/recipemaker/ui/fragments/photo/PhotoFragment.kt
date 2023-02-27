package com.example.recipemaker.ui.fragments.photo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.recipemaker.ObjectDetectionHelper
import com.example.recipemaker.R
import com.example.recipemaker.databinding.FragmentPhotoBinding
import com.example.recipemaker.ui.rview.adapter.FoodAdapter

import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.ui.activities.LogIn
import com.example.recipemaker.ui.fragments.detailfood.DetailViewModel
import com.example.recipemaker.utils.FoodProvider
import com.example.recipemaker.utils.snackBar
import com.example.recipemaker.utils.toast
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetector
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.Detection
import java.io.IOException



@AndroidEntryPoint
class PhotoFragment : Fragment() {



    companion object {
        const val TAG = "TFLite - ODT"
        const val REQUEST_IMAGE_CAPTURE: Int = 1
        private const val MAX_FONT_SIZE = 96F
    }


    private var imageAnalyzer: ImageAnalysis? = null
    private lateinit var objectDetectorHelper: ObjectDetectionHelper
    private lateinit var bitmapBuffer: Bitmap

    lateinit var binding : FragmentPhotoBinding
    lateinit var adapter: FoodAdapter
    private var listResult: MutableList<Recipe> = mutableListOf()

    lateinit var objectDetector : ObjectDetector
    lateinit var image : InputImage
    lateinit var imageu : Uri

    private val detailFood : DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FoodAdapter(listResult,525,333){
            onItemSelected(it);
        }
        binding.rvResult.adapter = adapter
        initListeners()
    }


    private fun initListeners(){
        binding.photoDetect.setOnClickListener{



            if (ContextCompat.checkSelfPermission(activity as LogIn, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Request the permission
                activity?.snackBar("No se han concedido permisos de acceso",binding.rvResult)
                ActivityCompat.requestPermissions(activity as LogIn,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    FoodProvider.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
            } else {
                // Permission has already been granted
                // Access the gallery
                uploadImg()
            }

        }
       // manageVisibility()
    }

    fun onItemSelected(food: Recipe){
        detailFood.selectedItem(food)
        FoodProvider.itemSelected = food
        val result = "result"
        // Use the Kotlin extension in the fragment-ktx artifact
        //setFragmentResult("requestKey", bundleOf("bundleKey" to food.title))
        findNavController().navigate(R.id.detailFood)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            FoodProvider.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    // Access the gallery
                } else {
                    // Permission denied
                    // Handle the denied permission
                }
                return
            }
        }
    }


    private fun manageVisibility(){
        if(listResult.size == 0){
            activity?.snackBar("No se encontraron recetas",binding.rvResult)
            binding.rvResult.visibility = View.INVISIBLE
            binding.messageInfo.visibility = View.VISIBLE
        } else {
            binding.rvResult.visibility = View.VISIBLE
            binding.messageInfo.visibility = View.INVISIBLE
        }
    }


    private fun imageFromPath(context: Context, uri: Uri) {
        // [START image_from_path]

        try {
            image = InputImage.fromFilePath(context, uri)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        // [END image_from_path]
    }


    private fun uploadImg() {
        var intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1) {
            imageu = data?.data!!
            binding.photoDetect.setImageURI(imageu)
            setViewAndDetect(getSampleImage(R.drawable.apple))
        }
    }


    private fun detectObjects(image: ImageProxy) {
        // Copy out RGB bits to the shared bitmap buffer
        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }

        val imageRotation = image.imageInfo.rotationDegrees
        // Pass Bitmap and rotation to the object detector helper for processing and detection
        objectDetectorHelper.detect(bitmapBuffer, imageRotation)
    }



    private fun getSampleImage(drawable: Int): Bitmap {
        /* return BitmapFactory.decodeResource(resources, drawable, BitmapFactory.Options().apply {
            inMutable = true
        })*/

        return MediaStore.Images.Media.getBitmap(activity?.getContentResolver(), imageu)
    }

    private fun setViewAndDetect(bitmap: Bitmap) {
        // Display capture image
        //inputImageView.setImageBitmap(bitmap)
        //tvPlaceholder.visibility = View.INVISIBLE
        binding.photoDetect.setImageBitmap(bitmap)
        // Run ODT and display result
        // Note that we run this in the background thread to avoid blocking the app UI because
        // TFLite object detection is a synchronised process.
        lifecycleScope.launch(Dispatchers.Default) { runObjectDetection(bitmap) }
    }


    private fun runObjectDetection(bitmap: Bitmap) {
        // Step 1: Create TFLite's TensorImage object
        val image = TensorImage.fromBitmap(bitmap)

        // Step 2: Initialize the detector object
        val options = org.tensorflow.lite.task.vision.detector.ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(5)
            .setScoreThreshold(0.3f)
            .build()
        val detector = org.tensorflow.lite.task.vision.detector.ObjectDetector.createFromFileAndOptions(
            activity as LogIn,
            "efficientdet-lite0.tflite",
            options
        )

        // Step 3: Feed given image to the detector
        val results = detector.detect(image)

        // Step 4: Parse the detection result and show it
        val resultToDisplay = results.map {
            // Get the top-1 category and craft the display text
            val category = it.categories.first()
            val text = "${category.label}, ${category.score.times(100).toInt()}%"
            println("******************************")
            println(category.label)
            // Create a data object to display the detection result
            activity?.runOnUiThread{
                filtrarFood(category.label)
            }

            DetectionResult(it.boundingBox, text)
        }
        // Draw the detection result on the bitmap and show it.
        val imgWithResult = drawDetectionResult(bitmap, resultToDisplay)
        activity?.runOnUiThread {
            binding.photoDetect.setImageBitmap(imgWithResult)
        }

    }


    private fun filtrarFood(ingredient: String){

        listResult = mutableListOf()
        for(receta in FoodProvider.food){
            if(receta.ingredients.contains(ingredient)){
                listResult.add(receta)
            }
        }

        manageVisibility()
        adapter.setData(listResult)
        adapter.notifyDataSetChanged()

    }

    private fun debugPrint(results : List<Detection>) {
        for ((i, obj) in results.withIndex()) {
            val box = obj.boundingBox

            Log.d(TAG, "Detected object: ${i} ")
            Log.d(TAG, "  boundingBox: (${box.left}, ${box.top}) - (${box.right},${box.bottom})")

            for ((j, category) in obj.categories.withIndex()) {
                Log.d(TAG, "    Label $j: ${category.label}")
                val confidence: Int = category.score.times(100).toInt()
                Log.d(TAG, "    Confidence: ${confidence}%")
            }
        }
    }

    private fun drawDetectionResult(
        bitmap: Bitmap,
        detectionResults: List<DetectionResult>
    ): Bitmap {
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)
        val pen = Paint()
        pen.textAlign = Paint.Align.LEFT

        detectionResults.forEach {
            // draw bounding box
            pen.color = Color.RED
            pen.strokeWidth = 8F
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)


            val tagSize = Rect(0, 0, 0, 0)

            // calculate the right font size
            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = Color.YELLOW
            pen.strokeWidth = 2F

            pen.textSize = MAX_FONT_SIZE
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)
            val fontSize: Float = pen.textSize * box.width() / tagSize.width()

            // adjust the font size so texts are inside the bounding box
            if (fontSize < pen.textSize) pen.textSize = fontSize

            var margin = (box.width() - tagSize.width()) / 2.0F
            if (margin < 0F) margin = 0F
            canvas.drawText(
                it.text, box.left + margin,
                box.top + tagSize.height().times(1F), pen
            )
        }
        return outputBitmap
    }

}

data class DetectionResult(val boundingBox: RectF, val text: String)
