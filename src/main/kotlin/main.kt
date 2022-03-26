var commissionAmount = 0 //сумма комиссии
const val minCommissionMir = 35_00
fun main() {
    var transferAmount = 66793_00 //сумма перевода в копейках
    var previousTransferAmount = 8987300 //сумма предыдущего перевода в копейках
    var cardName = "VK Pay"
    printOut(cardName, previousTransferAmount, transferAmount)
    printOut("Mir", previousTransferAmount, 35000_00)
    printOut("MasterCard", 40000_00, 50000_00)
}

fun doTransfer(card: String = "VK Pay", previousTransferAmount: Int = 0, transferAmount: Int): Boolean {
    if (card == "VK Pay" && previousTransferAmount + transferAmount > 40_000_00) return false
    if (card == "VK Pay" && transferAmount > 15_000_00) return false
    if (transferAmount > 150_000_00) return false
    if (previousTransferAmount + transferAmount > 600_000_00) return false
    when (card) {
        "VK Pay" -> commissionAmount = 0
        "Mir", "Visa" -> {
            commissionAmount = if (transferAmount * 0.75 / 100 <= minCommissionMir) {
                minCommissionMir
            } else {
                (transferAmount * 0.75 / 100).toInt()
            }
        }
        "MasterCard", "Maestro" -> {
            commissionAmount = when (transferAmount) {
                in 300_00..75_000_00 -> 0
                else -> (transferAmount * 0.6 / 100 + 20).toInt()
            }

        }
    }
    return true
}

private fun printOut(card: String, previousTransferAmount: Int, transferAmount: Int) {
    if (!doTransfer(card, previousTransferAmount, transferAmount)) println("Перевод отклонен")
    println("Карта: $card. Сумма перевода: ${transferAmount/100} рублей ${transferAmount%100} копеек. Комиссия составит ${commissionAmount/100} рублей ${commissionAmount%100} копеек")
}