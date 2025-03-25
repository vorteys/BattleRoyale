
import java.util.Random;

public class BattleRoyalGame {
    private final Random rand = new Random();
    private final Player player;
    private Enemy enemy;

    private int jour;
    private int tour;
    private int start;

    private boolean escape = false;

    public BattleRoyalGame() {
        this.player = new Player(this);
        this.enemy = new Enemy(this);
        enemy.createNames();
    }

    public void start() {
        beforeGame();
        do {
            beforeStartDay();
            do {
                beforeStartRound();
                do {
                    whoStart();
                } while (enemy.isDead(Entity.ENEMY_INDEX) && !enemy.isDead(Entity.ENEMY_INDEX) && !escape);
                afterRound();
            } while (tour > 0 && !player.isDead(Entity.MOI_INDEX) && !enemy.areAllDead());
            afterDay();
        } while (jour <= 3 && !player.isDead(Entity.MOI_INDEX));
        afterGame();
    }

    public void beforeGame() {
        jour = 1;
        displayHud();
    }

    public void afterGame() {
        if (player.isDead(Entity.MOI_INDEX)) {
            displayGameOver();
            return;
        }
        if (enemy.areAllDead()) {
            displayWinner();
        }
    }

    public void afterDay() {
        enemy.showEnemyDead(jour);
        jour++;
    }

    public void afterRound() {
        if (enemy.isDead(Entity.ENEMY_INDEX) || escape) {
            if (!escape) {
                int getFood = rand.nextInt(100) + 1;
                player.getBonusFood(getFood);
                enemy.displayEnemyDead();
                player.changeWithEnemyStuff();
            }
            enemy = new Enemy(this);
        }
    }

    public void beforeStartRound() {
        displayDay();
        enemy.displayNewEnemy();
        start = rand.nextInt(1, 41);
    }

    public void beforeStartDay() {
        enemy.killEnemy(dayNmbDead());
        generateNumberWaves();
        displayWaiting();
    }

    public void whoStart() {
        boolean playerStarts = player.isStart(start, Entity.MOI_INDEX) < enemy.isStart(start, Entity.ENEMY_INDEX);

        if (playerStarts) {
            player.play(enemy);
        } else {
            enemy.play(player);
        }

        if (!escape) {
            if (playerStarts) {
                enemy.play(player);
            } else {
                player.play(enemy);
            }
        }
    }

    public void escapeTheCombat(boolean choice) {
        escape = choice;
    }

    public int dayNmbDead() {
        int luckOfDead;

        luckOfDead = (jour == 1) ? 45 : (jour == 2) ? 30 : 20;

        return luckOfDead;
    }

    public void displayWaiting() {
        for (int i = 5; i >= 0; i--) {
            System.out.println("Prochain jour: " + i);
            Terminal.pause(1000);
        }
    }

    public void displayHud() {
        //displayStart();
        player.askName();
        System.out.println();
        player.displayDescritpion();
        System.out.println();
        displayStartGame();
        System.out.println();
        player.displaySac();
        System.out.println();
        player.displayStats();
    }

    public void displayWinner() {
        System.out.println("Vous avez gagner");
    }

    public void displayGameOver() {
        System.out.println("Game Over");
    }

    public void displayChoice() {
        System.out.println("""
                Récuperer votre sac de combat et démarrer la partie\s
                1. oui
                2. non""");
    }

    public void displayStartGame() {
        Integer choice;
        do {
            displayChoice();
            choice = Terminal.readInt();
            if (choice != null) {
                boolean isStaticChoice = (choice == 1 || choice == 2);
                if (!isStaticChoice) {
                    System.out.println("Choix invalide");
                    choice = null;
                }
            }

        } while (choice == null);
        if (choice == 2) {
            System.out.println("Tu est un peureux !");
            displayGameOver();
            System.exit(1);
        }
    }

    public void displayStart() {
        System.out.println("""
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                      ██                           \s
                                                                                     ▓█                            \s
                                     ░░░░░░░░▒     ░░░░░ ░░░░░░░░░░▒░░░░░░░░░░     ███▒░░░░                        \s
                                      ░░░░░░░░░   ░░░░░░ ░░░░░░░░░░▒░░░░░░░░░░▒  ▒▒▓▒░░░░░░▒                       \s
                                      ▒░░░ ░░░░   ░░░░░░▒   ▒░░░       ░░░░     ██▒▒░░░                            \s
                                      ▒░░░ ░░░░  ▒░░░░░░░   ▒░░░       ░░░░    ███ ░░▒░░░░░                        \s
                                      ▒░░░░░░░▒  ░░░░ ░░░▒  ▒░░░       ░░░░  ░▓█▓▓▓▓▓█ ░░░░                        \s
                                      ▒░░░  ░░░░░░░░░░░░░░  ▒░░░       ░░░░  ▓▓██  ▒▒▒░                            \s
                                      ▒░░░░░░░░░░░░░░░░░░░  ░░░░       ░░░░ ▒▓█▒█  ░░░░░░░░▒                       \s
                                      ░░░░░░░░░ ░░░░  ▒░░░░ ░░░░░      ░░░░▒▒█  ▒  ░░░░░░░░                        \s
                                                                          ▒▒▓▒                                     \s
                               ▓▓▓▓▓▓▓▓▓▓     ▒▓▓▓▓▓▓▓▓▓ ▓▓▓▓▓▓  ▓▓▓▓▓ ▓▓▓▒██    ▓▓▓▓▓     ▓▓▓▓▓▓▓▓▓▓              \s
                               ▓▓▓▓▓▓▓▓▓▓▓▒ ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▒▓▓▓▓▒▓▓▓▓▓ ▓▓▓▓▓▓▓▓   ▓▓▓▓▓     ▓▓▓▓▓▓▓▓▓▓              \s
                               ▓▓▓▓▓  ▓▓▓▓▓▓▓▓▓▓▓▒  ▓▓▓▓▓▓ ▓▓▓▓▓▒▓▓▓▓ ▓▓▓▓▓▓▓▓   ▓▓▓▓▓     ▓▓▓▓▓▓▓▓▓▓              \s
                                ▓▓▓▓▒  ▓▓▓▓▓▓▓▓▓      ▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ░▓▓▓▓▓▓▓▓▓  ▓▓▓▓▓     ▓▓▓▓▓▓▓▓▓               \s
                                ▒▒▒▒▒▒▒▒▒▒ ▒▒▒▒       ▒▒▒▒▒  ▒▒▒▒▒▒  ▒▒▒▒▒░▒▒▒▒  ▒▒▒▒▒     ▒▒▒▒▒▒▒▒▒               \s
                                ▒▒▒▒▒▒▒▒▒  ▒▒▒▒▒     ▒▒▒▒▒▓  ▓▒▒▒▒   ▒▒▒▒  ▒▒▒▒  ▒▒▒▒▒     ▒▒▒▒▒▒▒▒▒               \s
                                ▒▒▒▒▒▒▒▒▒▒ ▓▒▒▒▒▒▒▒▒▒▒▒▒▒▒   ▓▒▒▒▒  ▓▒▒▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒▒▓▒▒▒▒▒▒▒▒▒▒              \s
                                ▒▒▒▒▒▓▒▒▒▒▓ ▓▒▒▒▒▒▒▒▒▒▒▒▒    ▓▒▒▒▒  ▒▒▒▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒▒▓▒▒▒▒▒▒▒▒▒▒              \s
                                ▒▒▒▒▒ ▒▒▒▒▒   ▓▒▒▒▒▒▒▒▓      ▓▒▒▒▒▓▒▒▒▒▒▒  ▒▒▒▒▒▓▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒▒▓              \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                   \s
                                                                                                                    \
                """);
    }

    public void displayDay() {
        System.out.println();
        System.out.println("Jour #" + jour);
    }

    public void generateNumberWaves() {
        if (jour <= 2) {
            tour = rand.nextInt(4) + 1;
            if (tour > enemy.getNombreEnemy()) {
                tour = enemy.getNombreEnemy();
            }
        } else {
            tour = enemy.getNombreEnemy();
        }
    }
}
