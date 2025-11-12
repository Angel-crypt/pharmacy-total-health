package core

import employees.Cashier
import employees.Pharmacist
import employees.Stocker
import shared.QueueRecipes
import shared.Warehouse
import kotlin.concurrent.thread

class Pharmacy {
    fun runSimulation(){
        println("═══════════════════════════════════════════════════════")
        println("   FARMACIA SALUD TOTAL - SIMULACIÓN DE OPERACIONES")
        println("   Horario: 10:00 a.m. - 10:00 p.m.")
        println("═══════════════════════════════════════════════════════\n")

        // Crear recursos compartidos
        val warehouse = Warehouse()
        val queueRecipes = QueueRecipes()

        // Crear empleados
        val ana = Cashier("Ana", warehouse, queueRecipes)
        val luis = Pharmacist("Luis", warehouse, queueRecipes)
        val carlos = Stocker("Carlos", warehouse)

        // Iniciar hilos
        val hiloAna = thread { ana.run() }
        val hiloLuis = thread { luis.run() }
        val hiloCarlos = thread { carlos.run() }

        // Esperar finalización
        hiloAna.join()
        hiloLuis.join()
        hiloCarlos.join()

        println("\n═══════════════════════════════════════════════════════")
        println("   FIN DE LA JORNADA - FARMACIA CERRADA")
        println("═══════════════════════════════════════════════════════")
    }
}

