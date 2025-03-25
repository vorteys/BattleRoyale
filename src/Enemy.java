import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Enemy extends Entity {
    private final BattleRoyalGame game;
    private final String[] fullNameDeadIle = new String[41];
    private final String[] fullNameDeadCombat = new String[41];

    private final int nmbEnemy = 41;
    private int nmbVivant = 41;
    private int nombreDeMortIle;
    private int nombreDeMortCombat;

    public Enemy(BattleRoyalGame game) {
        this.game = game;
        getYourBag(ENEMY_INDEX);
    }

    public void play(Player player) {
        if (health[ENEMY_INDEX] > 60 && health[ENEMY_INDEX] < 70 && rations[ENEMY_INDEX] > 0) {
            System.out.println("L'ennemi veut manger");
            health[ENEMY_INDEX] += 15;
            rations[ENEMY_INDEX]--;
        } else if (weapon[ENEMY_INDEX].canAttack() && (health[ENEMY_INDEX] > 60 || finalAttack[ENEMY_INDEX] > 24)) {
            if (attackPass(ENEMY_INDEX)) {
                System.out.print("L'ennemi a réussi à vous attaquer et vous avez infligé : ");
                getDamage(finalAttack[ENEMY_INDEX], MOI_INDEX);
            }
            weapon[ENEMY_INDEX].fireAmmo();
        } else {
            System.out.println("L'ennemi veut s'échapper, vous avez deux tours pour jouer");
            player.enemyEscaping(true);
            for (int i = 0; i < 2; i++) {
                player.play(this); // bug a corriger quand je joue 2 tours ( choix 3) tout fuck
            }
            if (!isDead(ENEMY_INDEX)) {
                System.out.println("L'ennemi s'est échappé");
                game.escapeTheCombat(true);
            }
        }
    }

    public void displayNewEnemy() {
        fullNames[ENEMY_INDEX] = getRandomEnemy();
        System.out.println(
                fullNames[ENEMY_INDEX] + " armé (e) d'un "
                        + weapon[ENEMY_INDEX].getDisplayName(StatsAttribute.NAME) +
                        " vous attaque ! \n" +
                        "Vous débutez donc le combat. \n");
    }

    public String getRandomEnemy() {
        int rnd = rand.nextInt(nmbEnemy);

        boolean pass = false;
        do {
            if (fullNames[rnd].isEmpty()) {
                rnd = rand.nextInt(nmbEnemy);
            } else {
                pass = true;
            }
        } while (!pass);

        return  fullNames[rnd];
    }

    public void createNames() {
        String[] allFirstName = new String[1000];
        String[] allLastName =  new String[1000];

        Random random = new Random();

        Scanner firstNameReader = null;
        Scanner lastNameReader = null;

        try {
            firstNameReader = new Scanner(new File("Enemy/firstName.txt"));
            lastNameReader = new Scanner(new File("Enemy/lastName.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println("Une erreur inconnue est survenue");
            System.exit(1);
        }

        int i = 0;
        while (firstNameReader.hasNextLine()) {
            allFirstName[i] = firstNameReader.nextLine().trim();
            i++;
        }
        firstNameReader.close();

        i = 0;
        while (lastNameReader.hasNextLine()) {
            allLastName[i] = lastNameReader.nextLine().trim();
            i++;
        }
        lastNameReader.close();

        fullNames = new String[nmbEnemy];

        for (int j = 1; j < nmbEnemy; j++) {
            int firstRandom = random.nextInt(i);
            int lastRandom = random.nextInt(i);

            fullNames[j] =  allFirstName[firstRandom] + " " + allLastName[lastRandom];
        }

    }

    public int getNombreEnemy() {
        return nmbVivant;
    }

    public void showEnemyDead(int nombreDeJour) {
        System.out.println("Bilan des transformations pour le jour " + nombreDeJour + "\n");
        System.out.println(nombreDeMortIle + " sont mort sur cette île");
        for (int i = 0; i < nombreDeMortIle; i++) {
            System.out.println(fullNameDeadIle[i]);
            fullNameDeadIle[i] = "";
        }
        nombreDeMortIle = 0;
        System.out.println(nombreDeMortCombat + " sont mort pendant un combat contre toi");
        for (int i = 0; i < nombreDeMortCombat; i++) {
            System.out.println(fullNameDeadCombat[i]);
            fullNameDeadCombat[i] = "";
        }
        nombreDeMortCombat = 0;
        System.out.println();
    }

    public void killEnemy(int index) {
        int rnd;
        for (int i = 1; i < nmbEnemy; i++) {
            rnd = rand.nextInt(100) + 1;
            if (!fullNames[i].isEmpty() && rnd < index) {
                fullNameDeadIle[nombreDeMortIle] = fullNames[i];
                fullNames[i] = "";
                nombreDeMortIle++;
                nmbVivant--;
            }
        }
    }

    public void displayEnemyDead() {
        System.out.println("Vous avez gagné le combat ! " + fullNames[ENEMY_INDEX] + " est maintenant un lapin.");
        fullNames[ENEMY_INDEX] = "";
        nombreDeMortCombat++;
        nmbVivant--;
    }

    public boolean areAllDead() {
        return nmbVivant == 0;
    }
}
