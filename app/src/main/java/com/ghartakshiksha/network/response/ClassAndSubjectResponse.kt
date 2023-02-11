package com.ghartakshiksha.network.response

import com.ghartakshiksha.network.model.ClassAndSubjectData

data class ClassAndSubjectResponse(
    val `data`: ArrayList<ClassAndSubjectData>,
    val message: String,
    val success: Boolean
)