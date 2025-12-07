package uk.chaoticgoose.adventofcode.twentyfive.day7;

enum DiagramElement {
    Start,
    Beam,
    Splitter,
    Empty;

    public String debug() {
        return switch (this) {
            case Start -> "S";
            case Beam -> "|";
            case Splitter -> "^";
            case Empty -> ".";
        };
    }
}
