package game;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import javax.sound.sampled.*;
import java.net.URL;


public class SpaceInvaderGame extends JPanel implements ActionListener, KeyListener {
    // Timer pour l'actualisation du jeu
    private Timer timer;

    // Objets principaux du jeu
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<EnemyBullet> enemyBullets;

    // États du jeu
    private boolean gameRunning;
    private boolean gameStarted;
    private boolean paused;

    // Variables du jeu
    private int highScore;
    private int lives;
    private int score;
    private int level;
    private int enemyShootCooldown;
    private Image background;
    private Image heartIcon;
    private Image enemyImage;
    private Image playerImage;
    private Image alienKilledAnim;

    // Animation pour un ennemi détruit
    private Point alienAnimationPosition = null;
    private long alienAnimationStartTime;

    // Chemin du fichier pour stocker le meilleur score
    private final String HIGH_SCORE_FILE = "highscore.txt";

    // Constructeur : Initialise le jeu
    public SpaceInvaderGame() {
        setFocusable(true);
        setPreferredSize(new Dimension(800, 600));
        addKeyListener(this);
        loadImages();
        loadHighScore();
        initGame();
    }
    /**
     * Chargement des images pour le jeu.
     */
    private void loadImages() {
        background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/space-bk.jpg"))).getImage();
        heartIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/Heart.png"))).getImage();
        enemyImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/enemy.gif"))).getImage();
        playerImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/player.png"))).getImage();
        alienKilledAnim = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/alienkilledanim.gif"))).getImage();
    }

    /**
     * Chargement du meilleur score depuis le fichier.
     */
    private void loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORE_FILE))) {
            highScore = Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            highScore = 0;
        }
    }
    /**
     * Sauvegarde du meilleur score dans un fichier.
     */
    private void saveHighScore() {
        if (score > highScore) {
            highScore = score;
            try (FileWriter writer = new FileWriter(HIGH_SCORE_FILE)) {
                writer.write(String.valueOf(highScore));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void playSound(String soundFile) {
        try {
            URL soundURL = getClass().getClassLoader().getResource("sounds/" + soundFile);
            if (soundURL != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            } else {
                System.err.println("Sound file not found: " + soundFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialisation du jeu (reset des valeurs initiales).
     */
    private void initGame() {
        resetGame();
        gameStarted = false;
        paused = false;
        timer = new Timer(20, this);
        timer.start();
    }
    /**
     * Réinitialisation des variables du jeu.
     */
    private void resetGame() {
        player = new Player(400, 550, playerImage);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        powerUps = new ArrayList<>();
        enemyBullets = new ArrayList<>();
        gameRunning = true;
        lives = 3;
        score = 0;
        level = 1;
        enemyShootCooldown = 0;
        createEnemies();
    }
    /**
     * Création d'une nouvelle vague d'ennemis.
     */
    private void createEnemies() {
        enemies.clear();
        for (int i = 0; i < level + 3; i++) {// Nombre de lignes basé sur le niveau
            for (int j = 0; j < 10; j++) { // 10 ennemis par ligne
                enemies.add(new Enemy(50 + j * 60, 50 + i * 40, enemyImage));
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);

        if (!gameStarted) {
            drawWelcomeScreen(g);
        } else if (paused) {
            drawPauseScreen(g);
        } else if (gameRunning) {
            player.draw(g);
            for (Enemy enemy : enemies) {
                enemy.draw(g);
            }
            for (Bullet bullet : bullets) {
                bullet.draw(g);
            }
            for (EnemyBullet enemyBullet : enemyBullets) {
                enemyBullet.draw(g);
            }
            for (PowerUp powerUp : powerUps) {
                powerUp.draw(g);
            }
            drawLives(g);
            drawScore(g);
            if (alienAnimationPosition != null) {
                long elapsedTime = System.currentTimeMillis() - alienAnimationStartTime;
                if (elapsedTime < 500) { // Affiche l'animation de destruction
                    g.drawImage(alienKilledAnim, alienAnimationPosition.x, alienAnimationPosition.y, 40, 40, this);
                } else {
                    alienAnimationPosition = null; // End animation
                }
            }
        } else {
            saveHighScore();// Sauvegarde le score à la fin de la partie
            drawGameOver(g);        }
    }

    // Dessine l'image de fond couvrant toute la fenêtre de jeu
    private void drawBackground(Graphics g) {
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    // Dessine l'écran de bienvenue avant le démarrage du jeu
    private void drawWelcomeScreen(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Welcome to Space Invader !", 100, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press ENTER to Start", 300, 300);
        drawHighScore(g);
    }

    // Dessine l'écran de pause lorsque le jeu est en pause
    private void drawPauseScreen(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Game Paused", 250, 300);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press P to Resume", 300, 350);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Game Over !", 300, 300);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Final Score: " + score, 350, 350);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.setColor(Color.WHITE);
        g.drawString("Press R to Restart", 330, 400);
        drawHighScore(g);
    }

    // Dessine les icônes représentant les vies restantes
    private void drawLives(Graphics g) {
        for (int i = 0; i < lives; i++) {
            g.drawImage(heartIcon, 10 + i * 30, 10, 20, 20, this);
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + score, 680, 30);
        g.drawString("Level: " + level, 680, 60);
    }
    private void drawHighScore(Graphics g) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("High Score: " + highScore, 600, 90);
    }

    // Vérifie si un ennemi atteint le joueur ou le bas de l'écran
    private void checkEnemiesReachPlayerOrBottom() {
        for (Enemy enemy : enemies) {
            if (enemy.getY() >= 550 || enemy.getBounds().intersects(player.getBounds())) {
                gameRunning = false;
                return;
            }
        }
    }
    private void applyPowerUp(PowerUp powerUp) {
        if (powerUp.getType() == PowerUp.Type.EXTRA_LIFE) {
            lives++;
            playSound("/power-up.wav");
        } else if (powerUp.getType() == PowerUp.Type.EXTRA_SCORE) {
            score += 50;
            playSound("/power-up.wav");
        }
    }
    private void moveEnemies() {
        for (Enemy enemy : enemies) {
            enemy.move();
        }

        for (Enemy enemy : enemies) {
            if (enemy.getX() <= 0 || enemy.getX() >= 760) {
                for (Enemy e : enemies) {
                    e.reverseDirection();
                    e.moveDown();
                }
                break;
            }
        }
    }

    private void checkCollisions() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            for (int j = 0; j < enemies.size(); j++) {
                Enemy enemy = enemies.get(j);
                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    bullets.remove(i);
                    alienAnimationPosition = new Point(enemy.getX(), enemy.getY());
                    alienAnimationStartTime = System.currentTimeMillis();
                    enemies.remove(j);
                    score += 10;
                    i--;
                    break;
                }
            }
        }
    }
    // Vérifie si le joueur est touché par une balle ennemie

    private boolean playerIsHit() {
        for (EnemyBullet enemyBullet : enemyBullets) {
            if (enemyBullet.getBounds().intersects(player.getBounds())) {
                enemyBullets.remove(enemyBullet);
                playSound("/space-impact.wav");
                return true;// Le joueur est touché
            }
        }
        return false;// Le joueur n'est pas touché
    }

    private void spawnPowerUps() {
        Random rand = new Random();  // Génère des power-ups de manière aléatoire

        if (rand.nextInt(500) < 1) {
            powerUps.add(new PowerUp(rand.nextInt(800), 0));
        }
    }

    private void enemyShoot() {
        Random rand = new Random();  // Permet aux ennemis de tirer des balles de manière aléatoire
        if (enemyShootCooldown > 0) {
            enemyShootCooldown--;
            return;
        }

        for (Enemy enemy : enemies) {
            if (rand.nextInt(100) < 2) {  // 2% de chance qu'un ennemi tire
                enemyBullets.add(new EnemyBullet(enemy.getX() + 20, enemy.getY() + 20));
            }
        }
        enemyShootCooldown = 15; // Temps de recharge des tirs ennemis
    }
    /**
     * Mise à jour des objets de jeu à chaque "tick" du Timer.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted && gameRunning && !paused) {
            player.move();

            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                bullet.move();
                if (bullet.getY() < 0) {
                    bullets.remove(i);
                    i--;
                }
            }

            for (int i = 0; i < enemyBullets.size(); i++) {
                EnemyBullet enemyBullet = enemyBullets.get(i);
                enemyBullet.move();
                if (enemyBullet.getY() > 600) {
                    enemyBullets.remove(i);
                    i--;
                }
            }

            for (int i = 0; i < powerUps.size(); i++) {
                PowerUp powerUp = powerUps.get(i);
                powerUp.move();
                if (powerUp.getY() > 600) {
                    powerUps.remove(i);
                    i--;
                } else if (powerUp.getBounds().intersects(player.getBounds())) {
                    applyPowerUp(powerUp);
                    powerUps.remove(i);
                    i--;
                }
            }

            moveEnemies();
            checkEnemiesReachPlayerOrBottom();
            checkCollisions();

            if (enemies.isEmpty()) {
                level++;
                createEnemies();
            }

            if (playerIsHit()) {
                lives--;
                if (lives <= 0) {
                    gameRunning = false;
                    playSound("/game-over.wav");
                }
            }

            spawnPowerUps();
            enemyShoot();
        }
        repaint();
    }


    /**
     * Détection des entrées clavier.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameStarted && e.getKeyCode() == KeyEvent.VK_ENTER) {
            gameStarted = true;
            resetGame();
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
            repaint();
        } else if (!gameRunning && e.getKeyCode() == KeyEvent.VK_R) {
            resetGame();
        } else if (gameRunning && !paused) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) player.setDirection(-1);
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.setDirection(1);
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                bullets.add(player.shoot());
                playSound("/short-laser.wav");
            }

        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setDirection(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    /**
     * Méthode principale pour lancer le jeu.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Invader");
        SpaceInvaderGame game = new SpaceInvaderGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(SpaceInvaderGame.class.getResource("/images/iconjeu.png"));
        frame.setIconImage(icon.getImage());
        frame.setVisible(true);
    }
}
