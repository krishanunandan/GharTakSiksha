package com.ghartakshiksha.network.model

data class ClassAndSubjectData(
    val className: String,
    val id: Int,
    val subjects: ArrayList<Subject>
) {
    override fun toString(): String = className
}