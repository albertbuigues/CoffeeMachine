package machine

private var shutDownSignalOn = false
private var inMainMenu = true
private val coffeeMachine = CoffeeMachine()


fun main() {
    while (!shutDownSignalOn) {
        while (inMainMenu) {
            println()
            println("Write action (buy, fill, take, remaining, exit):")
            coffeeMachine.receiveCustomerInput(readln())
        }
    }
}

class CoffeeMachine {
    var machineWater = 400
    var machineMilk = 540
    var machineBeans = 120
    var machineMoney = 550
    var disposableCups = 9

    enum class CoffeeTypes {
        ESPRESSO, LATTE, CAPUCCINO
    }

    private fun printMachineStatus() {
        println(
            """
            The coffee machine has:
            $machineWater ml of water
            $machineMilk ml of milk
            $machineBeans g of coffee beans
            $disposableCups disposable cups
            $$machineMoney of money 
            """.trimIndent()
    )
}

    private fun reactionToChoice(choice: String) {
        when (choice) {
            "buy" -> {
                println()
                println("What do you want to buy? 1 - espresso, 2 - latte, 3 - capuccino, back - to main menu:")
                val clientChoice = readln()
                if (clientChoice != "back") {
                    when(clientChoice) {
                        "1" -> subtractFromMachine(CoffeeTypes.ESPRESSO)
                        "2" -> subtractFromMachine(CoffeeTypes.LATTE)
                        "3" -> subtractFromMachine(CoffeeTypes.CAPUCCINO)
                    }
                } else {
                    return
                }
            }
            "fill" -> {
                println()
                println("Write how many ml of water you want to add:")
                val waterToAdd = readln().toInt()
                machineWater += waterToAdd
                println("Write how many ml of milk you want to add:")
                val milkToAdd = readln().toInt()
                machineMilk += milkToAdd
                println("Write how many grams of coffee beans you want to add:")
                val beans = readln().toInt()
                machineBeans += beans
                println("Write how many disposable cups you want to add:")
                val cups = readln().toInt()
                disposableCups += cups
            }
            "take" -> {
                println()
                println("I gave you $$machineMoney")
                machineMoney = 0
            }
            "remaining" -> {
                println()
                printMachineStatus()
            }
            "exit" -> {
                inMainMenu = false
                shutDownSignalOn = true
            }
        }
    }

    fun receiveCustomerInput(choice: String) {
        reactionToChoice(choice)
    }

    private fun checkIfResources(water: Int, milk: Int, beans: Int): Boolean {
        var capable = true
        if (machineWater < water) {
            capable = false
            println("Sorry, not enough water!")
        } else if (machineMilk < milk) {
            capable = false
            println("Sorry, not enough milk!")
        } else if (machineBeans < beans) {
            capable = false
            println("Sorry, not enough coffee beans!")
        } else if (disposableCups == 0) {
            capable = false
            println("Sorry, not enough disposable cups!")
        } else {
            println("I have enough resources, making you a coffee!")
        }
        return capable
    }

    private fun subtractFromMachine(clientChoice: CoffeeTypes) {
        when(clientChoice) {
            CoffeeTypes.ESPRESSO -> {
                val capable = checkIfResources(250, 16, 4)
                if (capable) {
                    machineWater -= 250
                    machineBeans -= 16
                    machineMoney += 4
                    disposableCups -= 1
                }
            }
            CoffeeTypes.LATTE -> {
                val capable = checkIfResources(350, 75, 20)
                if (capable) {
                    machineWater -= 350
                    machineMilk -= 75
                    machineBeans -= 20
                    machineMoney += 7
                    disposableCups -= 1
                }
            }
            CoffeeTypes.CAPUCCINO -> {
                val capable = checkIfResources(200, 100, 12)
                if (capable) {
                    machineWater -= 200
                    machineMilk -= 100
                    machineBeans -= 12
                    machineMoney += 6
                    disposableCups -= 1
                }
            }
        }
    }
}
