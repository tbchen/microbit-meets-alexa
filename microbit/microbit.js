let weather = 1
if (weather == 1) {
    led.setBrightness(255)
    basic.showLeds(`
        . . . . .
        . . # . .
        . # # # .
        . . # . .
        . . . . .
        `)
}
if (weather == 2) {
    led.setBrightness(113)
    basic.showLeds(`
        . # . # .
        # # # # #
        . # . # .
        . . . . .
        . . . . .
        `)
}
if (weather == 3) {
    led.setBrightness(170)
    basic.showLeds(`
        . # . # .
        # # # # #
        . # . # .
        . . . . .
        . . . . .
        `)
}

bluetooth.onBluetoothConnected(() => {
    basic.showString("C")
})
bluetooth.onBluetoothDisconnected(() => {
    basic.showString("D")
})
control.onEvent(0, 0, () => {
    if (control.eventValue() == 1) {
        basic.showLeds(`
            # # # # #
            # # # # #
            # # # # #
            # # # # #
            # # # # #
            `)
    } else if (control.eventValue() == 2) {
        basic.showLeds(`
            . . . . .
            . . . . .
            . . . . .
            . . . . .
            . . . . .
            `)
    } else if (control.eventValue() == 3) {
        pins.digitalWritePin(DigitalPin.P0, 1)
    } else if (control.eventValue() == 4) {
        pins.digitalWritePin(DigitalPin.P0, 0)
    }
})
