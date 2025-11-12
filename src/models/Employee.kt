package models

import shared.Warehouse

abstract class Employee (
    val name: String,
    val role: String,
    protected val warehouse: Warehouse
) : Runnable {
    abstract fun work()

    fun startShift() {
        println("[$role] $name ha iniciado su turno a las 10:00 a.m.")
    }

    fun finishShift() {
        println("[$role] $name ha finalizado su turno a las 10:00 p.m.")
    }

    override fun run() {
        startShift()
        work()
        finishShift()
    }
}