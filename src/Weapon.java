public class Weapon {
    private String name;

    private double damage;

    private boolean blade;

    private int ammo;

    public String getDisplayName(StatsAttribute statsAttribute) {
        String result = name;
        switch (statsAttribute) {
            case NAME -> result = name;
            case DAMAGE -> result = String.valueOf(damage);
            case BLADE -> result = String.valueOf(blade);
            case AMMO -> result = String.valueOf(ammo);
            case FULL_DESCRIPTION -> result = name + ", (+" + damage + ") att" + ( blade ? "" : ", (" + ammo + ") ammo" );
        }
        return result;
    }

    public void fireAmmo() {
        if (!blade) {
            ammo--;
        }
    }

    public boolean canAttack() {
        boolean result = true;
        if (ammo <= 0 && !blade) {
            return false;
        }
        return result;
    }

    public static Weapon getRandomWeapon(int index) {
        return switch (index) {
            case 0 -> Weapon.Builder.bananaBlaster();
            case 1 -> Weapon.Builder.toasterThrower();
            case 2 -> Weapon.Builder.spoonOfDoom();
            case 3 -> Weapon.Builder.boomerangBlade();
            case 4 -> Weapon.Builder.rubberChicken();
            case 5 -> Weapon.Builder.bubbleGun();
            case 6 -> Weapon.Builder.angryGoose();
            case 7 -> Weapon.Builder.slapGlove();
            case 8 -> Weapon.Builder.laserPointer();
            case 9 -> Weapon.Builder.explodingPizza();
            default -> null;
        };
    }

    public static class Builder {

        public static Weapon bananaBlaster() {
            Weapon weapon = new Weapon();
            weapon.name = "Banana Blaster";
            weapon.blade = false;
            weapon.damage = 20;
            weapon.ammo = 15;
            return weapon;
        }

        public static Weapon toasterThrower() {
            Weapon weapon = new Weapon();
            weapon.name = "Toaster Thrower";
            weapon.blade = false;
            weapon.damage = 35;
            weapon.ammo = 3;
            return weapon;
        }

        public static Weapon spoonOfDoom() {
            Weapon weapon = new Weapon();
            weapon.name = "Spoon of Doom";
            weapon.blade = true;
            weapon.damage = 20;
            return weapon;
        }

        public static Weapon boomerangBlade() {
            Weapon weapon = new Weapon();
            weapon.name = "Boomerang Blade";
            weapon.blade = false;
            weapon.damage = 25;
            weapon.ammo = 1;
            return weapon;
        }

        public static Weapon rubberChicken() {
            Weapon weapon = new Weapon();
            weapon.name = "Rubber Chicken";
            weapon.blade = true;
            weapon.damage = 20;
            return weapon;
        }

        public static Weapon bubbleGun() {
            Weapon weapon = new Weapon();
            weapon.name = "Bubble Gun";
            weapon.blade = false;
            weapon.damage = 20;
            weapon.ammo = 40;
            return weapon;
        }

        public static Weapon angryGoose() {
            Weapon weapon = new Weapon();
            weapon.name = "Angry Goose";
            weapon.blade = true;
            weapon.damage = 15;
            return weapon;
        }

        public static Weapon slapGlove() {
            Weapon weapon = new Weapon();
            weapon.name = "Slap Glove";
            weapon.blade = true;
            weapon.damage = 20;
            return weapon;
        }

        public static Weapon laserPointer() {
            Weapon weapon = new Weapon();
            weapon.name = "Laser Pointer";
            weapon.blade = false;
            weapon.damage = 15;
            weapon.ammo = 15;
            return weapon;
        }

        public static Weapon explodingPizza () {
            Weapon weapon = new Weapon();
            weapon.name = "Exploding Pizza";
            weapon.blade = false;
            weapon.damage = 40;
            weapon.ammo = 2;
            return weapon;
        }

        public static Weapon theDestroyer() {
            Weapon weapon = new Weapon();
            weapon.name = "The Destroyer";
            weapon.blade = true;
            weapon.damage = 1000000;
            return weapon;
        }
    }
}
