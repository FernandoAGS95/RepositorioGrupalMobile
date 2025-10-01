package com.example.app_grupo7.repository

import com.example.app_grupo7.model.Perfume

object PerfumeRepository {
    fun getCatalogo(): List<Perfume> = listOf(
        Perfume("p1","Bleu de Chanel","Chanel",129990,100,"Aromático amaderado, versátil."),
        Perfume("p2","Sauvage","Dior",119990,100,"Fresco especiado, muy proyectón."),
        Perfume("p3","Azzaro The Most Wanted Parfum","Azzaro",99990,100,"Dulce especiado, nocturno."),
        Perfume("p4","Acqua di Gio Profumo","Armani",134990,75,"Acuático–incienso, elegante.")
    )
}
