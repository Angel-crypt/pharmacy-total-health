package shared

import models.Medicine
import java.util.concurrent.locks.ReentrantLock
import kotlin.collections.set
import kotlin.concurrent.withLock
import kotlin.random.Random

class Warehouse {
    private val inventory = mutableMapOf<String, Int>()
    private val medicinesCatalog = mutableMapOf<String, Medicine>()
    private val lock = ReentrantLock()

    init {
        val medicines = listOf(
            Medicine("Paracetamol", "500mg", "2026-03-15", "Analgésico", 120),
            Medicine("Ibuprofeno", "400mg", "2027-01-22", "Antiinflamatorio", 95),
            Medicine("Amoxicilina", "875mg", "2025-09-10", "Antibiótico", 180),
            Medicine("Loratadina", "10mg", "2026-11-05", "Antihistamínico", 250),
            Medicine("Omeprazol", "20mg", "2027-06-30", "Antiácido", 150),
            Medicine("Metformina", "850mg", "2028-02-18", "Antidiabético", 85),
            Medicine("Losartán", "50mg", "2027-09-12", "Antihipertensivo", 110),
            Medicine("Simvastatina", "20mg", "2028-04-25", "Hipolipemiante", 45),
            Medicine("Diazepam", "5mg", "2026-08-30", "Ansiolítico", 120),
            Medicine("Salbutamol", "100µg/dosis", "2026-12-14", "Broncodilatador", 95),
            Medicine("Azitromicina", "500mg", "2025-10-02", "Antibiótico macrólido", 180),
            Medicine("Cetirizina", "10mg", "2026-07-08", "Antihistamínico", 250),
            Medicine("Clonazepam", "2mg", "2027-05-20", "Ansiolítico", 150),
            Medicine("Diclofenaco", "75mg", "2026-09-15", "Antiinflamatorio", 85),
            Medicine("Ranitidina", "150mg", "2026-03-10", "Antiácido", 110),
            Medicine("Prednisona", "5mg", "2027-04-11", "Corticosteroide", 45)
        )

        medicines.forEach { med ->
            medicinesCatalog[med.name] = med
            inventory[med.name] = Random.nextInt(30, 60)
        }
    }

    fun getAvailableMedicines() : List<Medicine> {
        return lock.withLock {
            medicinesCatalog.values.filter { (inventory[it.name] ?: 0) > 0 }.toList()
        }
    }

    fun getMedicine(name: String) : Medicine? {
        return medicinesCatalog[name]
    }

    fun checkAvailability (medicineName : String, quantity : Int) : Boolean {
        return lock.withLock {
            (inventory[medicineName] ?: 0) >= quantity
        }
    }

    fun withdrawMedicine (medicineName : String, quantity : Int) : Boolean {
        return lock.withLock {
            val available = inventory[medicineName] ?: 0
            if (available >= quantity) {
                inventory[medicineName] = available - quantity
                true
            } else {
                false
            }
        }
    }

    fun restock(medicineName: String, quantity: Int) {
        lock.withLock {
            if (!medicinesCatalog.containsKey(medicineName)) {
                println("[ERROR] El medicamento '$medicineName' no existe en el catálogo.")
                return
            }

            val actual = inventory[medicineName] ?: 0
            inventory[medicineName] = actual + quantity
        }
    }


    fun getLowStock() : List<Pair<String, Int>> {
        return lock.withLock {
            inventory.filter { it.value < 20 }.toList()
        }
    }

    fun getStock(medicineName : String) : Int {
        return lock.withLock {
            inventory[medicineName] ?: 0
        }
    }
}

