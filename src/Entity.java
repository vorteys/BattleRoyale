import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Entity {
    protected static final int MOI_INDEX = 0;
    protected static final int ENEMY_INDEX = 1;

    protected Weapon[] weapon = new Weapon[2];
    protected Armor[] armor = new Armor[2];

    protected String[] fullNames = new String[50];

    protected double[] health =  new double[2];
    protected double[] defense =  new double[2];
    protected double[] attack =  new double[2];
    protected double[] finalAttack =  new double[2];
    protected double[] finalDefense =  new double[2];

    protected int[] precision =  new int[2];
    protected int[] finalPrecision =  new int[2];
    protected int[] luck =  new int[2];
    protected int[] rations =  new int[2];
    protected int[] bonusRations =  new int[2];

    protected ArrayList<Weapon> inventoryWeapons;
    protected ArrayList<Armor> inventoryArmors;
    protected Random rand = new Random();

    public void getYourBag(int type) {
        getRandomItems(type);
        getRandomStats(type);
    }

    private void getRandomStats(int type) {
        health[type] = 100;
        defense[type] = rand.nextInt(13);
        finalDefense[type] = defense[type] + Double.parseDouble(armor[type].getDisplayName(StatsAttribute.DEFENSE));
        attack[type] =  rand.nextInt(1, 6);
        if (Objects.equals(weapon[type].getDisplayName(StatsAttribute.BLADE), "true")) {
            finalAttack[type] = attack[type] + Double.parseDouble(weapon[type].getDisplayName(StatsAttribute.DAMAGE));
        } else {
            finalAttack[type] = Double.parseDouble(weapon[type].getDisplayName(StatsAttribute.DAMAGE));
        }
        precision[type] = rand.nextInt(20, 81);
        finalPrecision[type] = precision[type] + Integer.parseInt(armor[type].getDisplayName(StatsAttribute.PRECISION));
        luck[type] = rand.nextInt(1, 41);
        rations[type] = rand.nextInt(1, 4);
    }

    private void getRandomItems(int type) {
        int rndChoiceArmor = rand.nextInt(10);
        int rndChoiceWeapon = rand.nextInt(10);
        inventoryWeapons = new ArrayList<>();
        inventoryArmors = new ArrayList<>();
        inventoryArmors.add(Armor.getRandomArmor(rndChoiceArmor));
        inventoryWeapons.add(Weapon.getRandomWeapon(rndChoiceWeapon));
        weapon[type] = inventoryWeapons.getFirst();
        armor[type] = inventoryArmors.getFirst();
    }

    public boolean isDead(int type) {
        return health[type] == 0;
    }

    public int isStart(int index, int type) {
        return luck[type] - index;
    }

    public void getDamage(double attack, int type) {
        System.out.println((attack - finalDefense[type]) + " dégats");
        health[type] -= (attack - finalDefense[type]);
        if (health[type] < 0) {
            health[type] = 0;
        }
    }

    public boolean attackPass(int type) {
        int randomNumber = rand.nextInt(1,101);
        boolean pass = false;
        if (randomNumber <= finalPrecision[type]) {
            pass = true;
        } else {
            System.out.println("L'enemie a echouer lattaque");
        }
        return pass;
    }
}
