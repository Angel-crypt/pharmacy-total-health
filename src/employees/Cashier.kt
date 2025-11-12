package employees

import models.Employee
import models.Recipe
import shared.QueueRecipes
import shared.Warehouse
import kotlin.random.Random

class Cashier (
    name: String,
    warehouse: Warehouse,
    private val queueRecipes: QueueRecipes
) : Employee(name, "Cajera", warehouse) {

    private val clientsNames = listOf(
        "Juan Pérez", "María García", "Carlos López", "Ana Martínez",
        "Pedro Sánchez", "Laura Rodríguez", "José Hernández", "Carmen Díaz",
        "Miguel Torres", "Isabel Ruiz"
    )

    override fun work() {
        repeat(12) { clientNum ->
            Thread.sleep(Random.nextLong(800, 1500))

            val medicationsAvailable = warehouse.getAvailableMedicines()

            if (medicationsAvailable.isEmpty()) {
                println("\n[$role] No hay medicamentos disponibles en este momento")
            }

            val medicine = medicationsAvailable.random()
            val quantity = Random.nextInt(1, 4)
            val clientName = clientsNames.random()

            println("\n[$role] Atendiendo a $clientName")
            println("[$role] Cliente solicita: $quantity unidades de ${medicine.name}")

            if (warehouse.checkAvailability(medicine.name, quantity)) {
                val total = medicine.price * quantity
                println("[$role] Total a cobrar: $$total")

                Thread.sleep(400)
                println("[$role] Pago recibido")

                val recipe = Recipe(
                    clientName = clientName,
                    medicine = medicine.name,
                    quantity = quantity,
                    totalAmount = total
                )

                queueRecipes.addRecipe(recipe)

            } else {
                val stockActual = warehouse.getStock(medicine.name)
                println("[$role] Lo siento, solo hay $stockActual unidades disponibles")
            }
        }
    }
}
