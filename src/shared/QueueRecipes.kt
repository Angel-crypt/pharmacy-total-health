package shared

import models.Recipe
import java.util.concurrent.ConcurrentLinkedQueue

class QueueRecipes {
    private val queue = ConcurrentLinkedQueue<Recipe>()

    fun addRecipe(recipe : Recipe) {
        queue.offer(recipe)
        println("Receta #${recipe.id} agregada a la cola para surtir")
    }

    fun getNextRecipe() : Recipe? {
        return queue.poll()
    }

    fun hasPendingRecipes(): Boolean {
        return queue.isNotEmpty()
    }
}
