package fullGambling;

public class Dice {

    private static enum DICE_FACE {
        BLANK("│     │"),
        LEFT_DOT("│ •   │"),
        MIDDLE_DOT("│  •  │"),
        RIGHT_DOT("│   • │"),
        TWO_DOTS("│ • • │");

        private final String value;

        DICE_FACE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    private String top, middle, bottom;



    // System.out.println("┌─────┐");
    // System.out.println("└─────┘");

    public Dice( int number){

        switch (number) {
            case 1:
                top    = DICE_FACE.BLANK.getValue();
                middle = DICE_FACE.MIDDLE_DOT.getValue();
                bottom = DICE_FACE.BLANK.getValue();
                break;

            case 2:
                top    = DICE_FACE.LEFT_DOT.getValue();
                middle = DICE_FACE.BLANK.getValue();
                bottom = DICE_FACE.RIGHT_DOT.getValue();
                break;

            case 3:
                top    = DICE_FACE.LEFT_DOT.getValue();
                middle = DICE_FACE.MIDDLE_DOT.getValue();
                bottom = DICE_FACE.RIGHT_DOT.getValue();    
                break;

            case 4:  
                top    = DICE_FACE.TWO_DOTS.getValue();
                middle = DICE_FACE.BLANK.getValue();
                bottom = DICE_FACE.TWO_DOTS.getValue();
                break;

            case 5:
                top    = DICE_FACE.TWO_DOTS.getValue();
                middle = DICE_FACE.MIDDLE_DOT.getValue();
                bottom = DICE_FACE.TWO_DOTS.getValue();
                break;

            case 6:
                top    = DICE_FACE.TWO_DOTS.getValue();
                middle = DICE_FACE.TWO_DOTS.getValue();
                bottom = DICE_FACE.TWO_DOTS.getValue();
                break;

            default:
                System.out.println("Invalid number. Please provide a number between 1 and 6.");
        }
    }


}
