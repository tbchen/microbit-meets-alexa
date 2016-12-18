let weather = 0
control.onEvent(1104, 0, () => {
    weather = control.eventValue()
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
    if (weather == 4) {
        led.setBrightness(255)
        basic.showLeds(`
            . # . # .
            # # # # #
            . # . # .
            . . . . .
            . . . . .
            `)
    }
    if (weather == 5) {
        led.setBrightness(255)
        basic.showLeds(`
            . # . # .
            # # # # #
            . # . # .
            # . # . #
            . # . # .
            `)
    }
    if (weather == 6) {
        led.setBrightness(198)
        basic.showLeds(`
            . # . # .
            # # # # #
            . # . # .
            # . # . #
            . # . # .
            `)
    }
    if (weather == 7) {
        led.setBrightness(255)
        basic.showLeds(`
            # . . # .
            . # . . #
            . . # . .
            . # . . #
            # . . # .
            `)
    }
    if (weather == 8) {
        led.setBrightness(198)
        basic.showLeds(`
            # . # . #
            . # . # .
            # . # . #
            . # . # .
            # . # . #
            `)
    }
    if (weather == 9) {
        led.setBrightness(57)
        basic.showLeds(`
            # # # # #
            # # # # #
            # # # # #
            # # # # #
            # # # # #
            `)
    }
})
bluetooth.onBluetoothConnected(() => {
    basic.showString("C")
})
bluetooth.onBluetoothDisconnected(() => {
    basic.showString("D")
})
