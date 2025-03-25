import java.util.Objects;
import java.util.Scanner;

public class Player extends Entity {
    private final BattleRoyalGame game;
    private boolean enemyIsEscaping = false;

    public Player(BattleRoyalGame game) {
        this.game = game;
        getYourBag(MOI_INDEX);
    }

    public void play (Enemy enemy){
        Integer choice;
        do {
            displayMenu();
            choice = Terminal.readInt();
            if (choice != null) {
                boolean isEscapeChoice = (choice == 3);
                boolean isStaticChoice = (choice == 4);
                boolean isSacChoice = (choice == 99);
                boolean isAttackChoice =  (choice == 1);
                boolean isEatingChoice = (choice == 2);
                if (!isStaticChoice && !isSacChoice && !isEatingChoice && !isAttackChoice && !isEscapeChoice) {
                    System.out.println("Choix invalide. Veuillez choisir une option du menu.");
                    choice = null;
                } else if (enemyIsEscaping && isEscapeChoice) {
                    System.out.println("Choix invalide. L'ennemie se sauve vous ne pouvez pas vous echapper");
                    choice = null;
                }

                if (isEscapeChoice) {
                    for (int i = 0; i < 2; i++) {
                        enemy.play(this);
                    }
                    if (!isDead(MOI_INDEX)) {
                        game.escapeTheCombat(true);
                    }
                }
                if (isEatingChoice) {
                    if (health[MOI_INDEX] == 100) {
                        System.out.println("Choix invalide vous avez déja 100 de vie");
                    } else if (bonusRations[MOI_INDEX] != 0) {
                        health[MOI_INDEX] += 25;
                        bonusRations[MOI_INDEX]--;
                    } else if (rations[MOI_INDEX] != 0) {
                        health[MOI_INDEX] += 10;
                        rations[MOI_INDEX]--;
                    } else {
                        System.out.println("Choix invalide vous n'avez pue de rations.");
                        choice = null;
                    }
                    if (health[MOI_INDEX] > 100) {
                        health[MOI_INDEX] = 100;
                    }
                }
                if (isSacChoice) {
                    displaySac();
                    choice = null;
                }
                if (isAttackChoice) {
                    if (weapon[MOI_INDEX].canAttack()) {
                        if (attackPass(MOI_INDEX)) {
                            System.out.print("Vous avez reussi a attacker et vous avez infliguer : ");
                            getDamage(finalAttack[MOI_INDEX], ENEMY_INDEX);
                        }
                        weapon[MOI_INDEX].fireAmmo();
                    } else {
                        System.out.println("Choix invalide vous n'avez pue d'ammo.");
                        choice = null;
                    }
                }
            }
        } while (choice == null);
        if  (choice == 4) {
            System.out.println("Vous ne faites rien ... \n" +
                    "Êtes vous suicidaire ?");
        }
    }

    public void askName() {
        String firstName;
        String lastName;
        Scanner sc = new Scanner(System.in);
        System.out.print("Prenom du joueur: ");
        firstName = sc.nextLine();
        System.out.print("Nom du joueur: ");
        lastName = sc.nextLine();
        fullNames[MOI_INDEX] = firstName + " " + lastName;
        if (fullNames[MOI_INDEX].equalsIgnoreCase("Bruce Wayne")) {
            statsForTheKing();
        }
    }

    public void displaySac() {
        System.out.println("Votre sac de combat contient : \n" +
                "- Une arme (" + weapon[MOI_INDEX].getDisplayName(StatsAttribute.FULL_DESCRIPTION) + ") \n" +
                "- Une armure (" + armor[MOI_INDEX].getDisplayName(StatsAttribute.FULL_DESCRIPTION) + ") \n" +
                "- " + rations[MOI_INDEX] + " rations (" + bonusRations[MOI_INDEX] + ") bonus" );
    }

    public void displayStats() {
        System.out.println("Vitalité : " + health[MOI_INDEX]);
        if (Objects.equals(weapon[MOI_INDEX].getDisplayName(StatsAttribute.BLADE), "true")) {
            System.out.println("Attaque : " + attack[MOI_INDEX] +
                    " (+" + weapon[MOI_INDEX].getDisplayName(StatsAttribute.DAMAGE) + ")");
        } else {
            System.out.println("Attaque de l'arme : " + (weapon[MOI_INDEX].getDisplayName(StatsAttribute.DAMAGE))
            + " (" + weapon[MOI_INDEX].getDisplayName(StatsAttribute.AMMO) + ") ammo");
        }
        System.out.println("Defense : " + defense[MOI_INDEX] + " (+" + armor[MOI_INDEX].getDisplayName(StatsAttribute.DEFENSE) + ")");
        System.out.println("Précision : " + precision[MOI_INDEX] + "% (" + armor[MOI_INDEX].getDisplayName(StatsAttribute.PRECISION) + ")");
        System.out.println("Chance : " + luck[MOI_INDEX]);
        System.out.println();
    }

    public void displayDescritpion() {
        System.out.println("Bienvenue, " + fullNames[MOI_INDEX] + " , dans l'arène impitoyable du Battle Royale.\n" +
                "Vous êtes désormais prisonnier d'une île mystérieuse, \n" +
                "où la magie façonne le terrain et le destin. \n" +
                "Ici, l'amitié se transforme en rivalité, \n" +
                "et la survie est la seule loi. \n" +
                "Vous avez trois jours pour éliminer vos adversaires, \n" +
                "car à l'aube du quatrième jour, \n" +
                "il ne pourra en rester qu'un.\n");
    }

    public void displayMenu() {
        Terminal.displayLife(fullNames[MOI_INDEX], health[MOI_INDEX]);
        Terminal.displayLife(fullNames[ENEMY_INDEX], health[ENEMY_INDEX]); // erreur affiche null
        System.out.print("""
                1 - Attaquer\s
                2 - Manger\s
                3 - S'évader\s
                4 - Ne rien faire\s
                99 - inventaire\s
                Votre choix :\s""");
    }

    public void enemyEscaping(boolean escape){
        enemyIsEscaping = escape;
    }

    public void statsForTheKing() {
        defense[MOI_INDEX] = 100000;
        attack[MOI_INDEX] =  100000;
        precision[MOI_INDEX] = 100000;
        luck[MOI_INDEX] = 100000;
        rations[MOI_INDEX] = 100000;
        armor[MOI_INDEX] =  Armor.Builder.theDestroyer();
        weapon[MOI_INDEX] = Weapon.Builder.theDestroyer();
    }

    public void changeWithEnemyStuff() {
        changeArmor();
        changeWeapon();
    }

    public void changeArmor() {
        System.out.println("Voulez vous échanger votre armure : " + armor[MOI_INDEX].getDisplayName(StatsAttribute.FULL_DESCRIPTION) + "\n" +
                "Contre l'armure de votre enemie : " + armor[ENEMY_INDEX].getDisplayName(StatsAttribute.FULL_DESCRIPTION) + "\n" +
                "1. oui\n" +
                "2. non");
        if (decision() == 1) {
            armor[MOI_INDEX] = armor[ENEMY_INDEX];
        }
    }

    public void changeWeapon() {
        System.out.println("Voulez vous échanger votre arme : " + weapon[MOI_INDEX].getDisplayName(StatsAttribute.FULL_DESCRIPTION) + "\n" +
                "Contre l'arme de votre enemie : " + weapon[ENEMY_INDEX].getDisplayName(StatsAttribute.FULL_DESCRIPTION) + "\n" +
                "1. oui\n" +
                "2. non");
        if (decision() == 1) {
            weapon[MOI_INDEX] = weapon[ENEMY_INDEX];
        }
    }

    public void getBonusFood(int bonus) {
        if (bonus < luck[MOI_INDEX] + finalPrecision[MOI_INDEX]) {
            System.out.println("Bravo vous avez réussi a rammaser une rations Bonus");
            bonusRations[MOI_INDEX]++;
        }
    }

    public int decision() {
        Integer choice;
        do {
            choice = Terminal.readInt();
            if (choice != null) {
                boolean isStaticChoice = (choice == 1 || choice == 2);
                if (!isStaticChoice) {
                    System.out.println("Choisissez une option valide !");
                    choice = null;
                }
            }
        } while (choice == null);
        return choice;
    }

}
