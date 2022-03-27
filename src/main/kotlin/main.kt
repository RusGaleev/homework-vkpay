const val minCommissionMir = 35_00
fun main() {
    var transferAmount = 14793_00 //сумма перевода в копейках
    var previousTransferAmount = 8987_00 //сумма предыдущего перевода в копейках
    var cardName = "VK Pay"
    var commissionAmount = doTransfer(cardName, previousTransferAmount, transferAmount)
    printOut(cardName, transferAmount, commissionAmount)
    cardName = "Mir"
    transferAmount = 35000_00
    commissionAmount = doTransfer(cardName, previousTransferAmount, transferAmount)
    printOut(cardName, transferAmount, commissionAmount)
    cardName = "MasterCard"
    transferAmount = 45000_00
    previousTransferAmount = 50000_00
    commissionAmount = doTransfer(cardName, previousTransferAmount, transferAmount)
    printOut(cardName, transferAmount, commissionAmount)
}

fun doTransfer(card: String = "VK Pay", previousTransferAmount: Int = 0, transferAmount: Int): Int {
    if (transferAmount > 150_000_00) throw Exception("Transfer over limits.")
    if (previousTransferAmount + transferAmount > 600_000_00) throw Exception("Transfer over limits.")
    return when (card) {
        "VK Pay" -> {
            if (previousTransferAmount + transferAmount > 40_000_00) throw Exception("Transfer over limits.")
            if (transferAmount > 15_000_00) throw Exception("Transfer over limits.")
            0
        }
        "Mir", "Visa" -> {
            if (transferAmount * 0.75 / 100 <= minCommissionMir) {
                minCommissionMir
            } else {
                (transferAmount * 0.75 / 100).toInt()
            }
        }
        "MasterCard", "Maestro" -> {
            when (transferAmount) {
                in 300_00..75_000_00 -> 0
                else -> (transferAmount * 0.6 / 100 + 20).toInt()
            }
        }
        else -> throw Exception("Transfer unknown.")
    }
}

private fun printOut(card: String, transferAmount: Int, commissionAmount: Int) {
    println("Карта: $card. Сумма перевода: ${transferAmount / 100} рублей ${transferAmount % 100} копеек. Комиссия составит ${commissionAmount / 100} рублей ${commissionAmount % 100} копеек")
}