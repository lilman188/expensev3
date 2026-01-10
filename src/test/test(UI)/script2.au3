#include <Clipboard.au3>

Opt("MouseCoordMode", 1)
Opt("SendKeyDelay", 10)

; Tételek tömbje: {"Név", "Összeg", "Év", "Hónap", "Nap"}
Local $tetelek[10][5] = [ _
    ["Üzemanyag, Benzin 40L", "22500", @YEAR, @MON, @MDAY], _
    ["Lakástörleszto", "120000", 2025, 12, 30], _
    ["Villanyszámla", "15000", 2025, 1, 5], _
    ["Gázszámla", "10000", 2025, 1, 6], _
    ["Internet", "8000", 2025, 1, 10], _
    ["Telefon", "5000", 2025, 1, 12], _
    ["Élelmiszer", "35000", 2025, 1, 8], _
    ["Bérlet", "12000", 2025, 1, 1], _
    ["Autó biztosítás", "40000", 2025, 2, 15], _
    ["Könyv vásárlás", "6000", 2025, 1, 20] _
]

; Végigmegyünk a tételeken
For $i = 0 To UBound($tetelek) - 1
    ; Jegyzettömb aktiválása és kattintások (példa: a "Új tétel" gomb)
    WinActivate("Jegyzettömb")
    MouseClick("left", 10, 10)
    Sleep(100)
    MouseClick("left", 125, 370)
    Sleep(100)
    MouseClick("left", 170, 200)
    Sleep(200)

    ; Várjuk az Input ablakot
    WinWaitActive("Input")
    $win = WinGetPos("Input")

    ; Kattintás a szövegmezobe
    MouseClick("left", $win[0] + 170, $win[1] + 200)
    Sleep(50)

    ; Szöveg összeállítása: "Név YYYY.MM.DD"
    $szoveg = $tetelek[$i][0] & " " & $tetelek[$i][2] & "." & _
               StringFormat("%02d", $tetelek[$i][3]) & "." & _
               StringFormat("%02d", $tetelek[$i][4])

    _ClipBoard_SetData($szoveg)
    Sleep(50)
    Send("^v")
    Sleep(50)
    Send("{ENTER}")
    Sleep(100)

    ; Várjuk az Összeg (Ft) input ablakot
    WinWaitActive("Input")
    $win = WinGetPos("Input")

    ; Kattintás az Összeg mezobe
    MouseClick("left", $win[0] + 170, $win[1] + 200)
    Sleep(50)

    ; Összeg beillesztése
    _ClipBoard_SetData($tetelek[$i][1])
    Sleep(50)
    Send("^v")
    Sleep(50)
    Send("{ENTER}")
    Sleep(100)
Next
