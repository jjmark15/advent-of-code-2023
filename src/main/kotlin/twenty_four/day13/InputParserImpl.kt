package twenty_four.day13

import InputParser
import parser4k.Parser
import parser4k.commonparsers.Tokens
import parser4k.inOrder
import parser4k.map
import parser4k.oneOf
import parser4k.oneOrMore
import parser4k.parseWith
import parser4k.str
import utils.grids.twodee.Point2D
import utils.lineGroups

class InputParserImpl : InputParser<List<ClawMachineSettings>> {
    private val number: Parser<Int> = oneOrMore(Tokens.digit).map { it.joinToString("").toInt() }
    private val buttonName: Parser<ButtonName> = oneOf('A', 'B').map {
        when (it) {
            'A' -> ButtonName.A
            'B' -> ButtonName.B
            else -> TODO()
        }
    }
    private val displacement: Parser<Int> =
        inOrder(oneOf('X', 'Y'), oneOf('+', '='), number).map { (_, _, number) -> number }
    private val buttonSettingsParser: Parser<ButtonSettings> = inOrder(
        str("Button "), buttonName, str(": "), displacement, str(", "), displacement
    ).map { (_, buttonName, _, xDisplacement, _, yDisplacement) ->
        ButtonSettings(
            buttonName, xDisplacement, yDisplacement
        )
    }
    private val newLineParser: Parser<String> = str("\n")
    private val prizeLocationParser: Parser<Point2D> =
        inOrder(str("Prize: "), displacement, str(", "), displacement).map { (_, xPosition, _, yPosition) ->
            Point2D(
                yPosition, xPosition
            )
        }
    private val clawMachineSettingsParser: Parser<ClawMachineSettings> = inOrder(
        buttonSettingsParser, newLineParser, buttonSettingsParser, newLineParser, prizeLocationParser
    ).map { (buttonA, _, buttonB, _, location) -> ClawMachineSettings(buttonA, buttonB, location) }

    override fun parse(lines: List<String>) =
        lines.lineGroups().map { group -> group.joinToString("\n").parseWith(clawMachineSettingsParser) }
}
