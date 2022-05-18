package com.example.albertsonsapp

data class varEntry(
                    val lf:String,
                    val freq:Int,
                    val since:Int)

data class lfEntry(
                    val lf:String,
                    val freq:Int,
                    val since:Int,
                    val vars:Array<varEntry>)

data class responseOneEntry(
                            val sf:String,
                            val lfs:Array<lfEntry>)
