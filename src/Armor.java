
public class Armor {
    private String name;

    private int vitesse;

    private double defense;

    public String getDisplayName(StatsAttribute attribute) {
        String result;
        switch (attribute) {
            case DEFENSE ->  result = String.valueOf(defense);
            case PRECISION -> result = String.format("%+d", vitesse);
            case FULL_DESCRIPTION -> result = String.format("%s, (%+d) precision, (%+.1f) def", name, vitesse, defense);
            default ->  result = null;
        }
        return result;
    }

    public static Armor getRandomArmor(int index) {

        return switch (index) {
            case 0 -> Builder.fridgeArmor();
            case 1 -> Builder.bubbleWrapSuit();
            case 2 -> Builder.cardboardArmor();
            case 3 -> Builder.mirrorShield();
            case 4 -> Builder.cactusSuit();
            case 5 -> Builder.rubberDuckVest();
            case 6 -> Builder.tinfoilArmor();
            case 7 -> Builder.stoneAgePlate();
            case 8 -> Builder.jellyArmor();
            case 9 -> Builder.discoBallSuit();
            default -> null;
        };
    }

    public static class Builder {

        public static Armor fridgeArmor() {
            Armor armor = new Armor();
            armor.name = "Fridge Armor";
            armor.vitesse = -10;
            armor.defense = 15;
            return armor;
        }

        public static Armor bubbleWrapSuit() {
            Armor armor = new Armor();
            armor.name = "Bubble Wrap Suit";
            armor.vitesse = 5;
            armor.defense = 15;
            return armor;
        }

        public static Armor cardboardArmor() {
            Armor armor = new Armor();
            armor.name = "Cardboard Armor";
            armor.vitesse = 15;
            armor.defense = 5;
            return armor;
        }

        public static Armor mirrorShield() {
            Armor armor = new Armor();
            armor.name = "Mirror Shield";
            armor.vitesse = -5;
            armor.defense = 10;
            return armor;
        }

        public static Armor cactusSuit () {
            Armor armor = new Armor();
            armor.name = "Cactus Suit";
            armor.vitesse = 5;
            armor.defense = 10;
            return armor;
        }

        public static Armor rubberDuckVest () {
            Armor armor = new Armor();
            armor.name = "Rubber Duck Vest";
            armor.vitesse = 10;
            armor.defense = 5;
            return armor;
        }

        public static Armor tinfoilArmor() {
            Armor armor = new Armor();
            armor.name = "Tinfoil Armor";
            armor.vitesse = 8;
            armor.defense = 9;
            return armor;
        }

        public static Armor stoneAgePlate() {
            Armor armor = new Armor();
            armor.name = "Stone Age Plate";
            armor.vitesse = -15;
            armor.defense = 20;
            return armor;
        }

        public static Armor jellyArmor() {
            Armor armor = new Armor();
            armor.name = "Jelly Armor";
            armor.vitesse = 20;
            armor.defense = 6;
            return armor;
        }

        public static Armor discoBallSuit() {
            Armor armor = new Armor();
            armor.name = "Disco Ball Suit";
            armor.vitesse = -5;
            armor.defense = 10;
            return armor;
        }

        public static Armor theDestroyer() {
            Armor armor = new Armor();
            armor.name = "The Destroyer";
            armor.vitesse = 1000000;
            armor.defense = 1000000;
            return armor;
        }
    }
}
