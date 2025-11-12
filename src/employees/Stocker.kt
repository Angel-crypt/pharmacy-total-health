package employees

import models.Employee
import shared.Warehouse
import kotlin.random.Random

class Stocker(
    name: String,
    warehouse: Warehouse
) : Employee(name, "Reponedor", warehouse) {

    override fun work() {
        repeat(6) { round ->
            Thread.sleep(Random.nextLong(2500, 4000))

            println("\n[$role] Ronda de revisión #${round + 1}")
            println("[$role] Inspeccionando estantes y contando inventario...")
            Thread.sleep(800)

            val lowStock = warehouse.getLowStock()

            if (lowStock.isEmpty()) {
                println("[$role] Todos los medicamentos tienen niveles óptimos")
            } else {
                println("[$role] Se detectaron ${lowStock.size} medicamentos con stock bajo:")
                lowStock.forEach { (med, cant) ->
                    println("     • $med: $cant unidades")
                }

                Thread.sleep(600)
                println("[$role] Dirigiéndose al almacén principal...")
                Thread.sleep(400)

                lowStock.forEach { (med, cantActual) ->
                    val quantityReplace = Random.nextInt(25, 45)
                    println("[$role] Cargando $quantityReplace unidades de $med")
                    Thread.sleep(500)
                    warehouse.restock(med, quantityReplace)
                    val nuevoStock = warehouse.getStock(med)
                    println("[$role] $med reabastecido. Stock actualizado: $nuevoStock unidades")
                }

                println("[$role] Reposición completada.")
            }
        }
    }
}