package employees

import models.Employee
import shared.QueueRecipes
import shared.Warehouse
import kotlin.random.Random

class Pharmacist (
    name : String,
    warehouse: Warehouse,
    private val queueRecipes: QueueRecipes
) : Employee(name, "Farmacéutico", warehouse) {

    override fun work() {
        var assortedRecipes = 0

        while (assortedRecipes < 12) {
            Thread.sleep(Random.nextLong(1000, 2000))

            val recipe = queueRecipes.getNextRecipe()

            if (recipe != null) {
                println("\n[$role] Procesando receta #${recipe.id}")
                println("[$role] Cliente: ${recipe.clientName}")
                println("[$role] Medicamento: ${recipe.medicine} x${recipe.quantity}")

                val medicine = warehouse.getMedicine(recipe.medicine)

                if (medicine != null) {
                    println("[$role] Verificando dosis: ${medicine.dosage}")
                    println("[$role] Verificando caducidad: ${medicine.expirationDate}")
                    Thread.sleep(500)

                    if (warehouse.withdrawMedicine(recipe.medicine, recipe.quantity)) {
                        val remainingStock = warehouse.getStock(recipe.medicine)
                        println("[$role] Retirados ${recipe.quantity} unidades. Quedan: $remainingStock")

                        println("[$role] Etiquetando medicamento...")
                        Thread.sleep(400)
                        println("[$role] Receta #${recipe.id} surtida exitosamente")
                        println("[$role] Medicamento entregado a ${recipe.clientName}")
                        assortedRecipes++
                    } else {
                        println("[$role] ERROR: No hay suficiente stock para surtir")
                    }
                }
            } else {
                Thread.sleep(500)
            }
        }
    }
}