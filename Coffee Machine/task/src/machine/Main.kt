package machine

private val coffeeMachine = CoffeeMachine()
fun main() {
    while (!coffeeMachine.shutDownSignalOn) {
        while (coffeeMachine.inMainMenu) {
            println()
            println("Write action (buy, fill, take, remaining, exit):")
            coffeeMachine.receiveCustomerInput(readln())
        }
    }
}
