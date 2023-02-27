package com.example.recipemaker.data.remote

import com.example.recipemaker.di.FirebaseModule
import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.domain.interfaces.FoodRepository
import com.example.recipemaker.utils.DataState
import com.example.recipemaker.utils.FoodProvider
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    @FirebaseModule.FoodCollection private val foodCollection: CollectionReference
) : FoodRepository {


    //Nos obtiene todas las recetas en la base de datos
    override suspend fun getAllFood(): Flow<DataState<List<Recipe>>> = flow {
        emit(DataState.Loading)
        val listFood : MutableList<Recipe> = mutableListOf();
        try {
                foodCollection.get()
                .addOnSuccessListener { result ->
                    var counter = 0
                    for (document in result) {
                        // Log.d(TAG, "${document.id} => ${document.data}")
                        counter++

                        var recipe1 = document.toObject(Recipe::class.java)
                        //println(recipe1.id)
                        listFood.add(recipe1)


                    }
                }
                .addOnFailureListener { exception ->

                }.await()
            FoodProvider.food = listFood
            emit(DataState.Success(listFood))
            emit(DataState.Finished)

        } catch(e: Exception) {
            emit(DataState.Error(e))
            emit(DataState.Finished)
        }
    }
    //Se le pasa una lista de ids de recetas, y devuelva dichas recetas de la base de datos
    override suspend fun getFavoriteFood(lista: List<String>): Flow<DataState<List<Recipe>>> = flow {
        emit(DataState.Loading)
        val listFavoriteFood : MutableList<Recipe> = mutableListOf<Recipe>()
        //val listFood : MutableList<Recipe> = mutableListOf();
        try {
            for(id in lista){
                foodCollection.whereEqualTo("id",id).get()
                    .addOnSuccessListener {
                        for(document in it ){
                            var recipe: Recipe = document.toObject(Recipe::class.java)
                            listFavoriteFood.add(recipe)
                            println("recuperado")
                        }
                    }
                    .addOnFailureListener{
                        println("fallo ")
                    }.await()
            }


            emit(DataState.Success(listFavoriteFood))
            emit(DataState.Finished)
        }catch (e: Exception) {
            println("fallo")
            emit(DataState.Error(e))
            emit(DataState.Finished)
        }

    }
    //guarda una receta
    override suspend fun saveFood(food: Recipe): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        println(food)
        try {
            var uploadSuccessful: Boolean = false
            foodCollection.document(food.id).set(food, SetOptions.merge())
                .addOnSuccessListener {
                    uploadSuccessful = true
                }.addOnFailureListener {
                    uploadSuccessful = false
                }.await()
            emit(DataState.Success(uploadSuccessful))
            emit(DataState.Finished)
        } catch (e: Exception){
            emit(DataState.Error(e))
            emit(DataState.Finished)
        }
    }
}