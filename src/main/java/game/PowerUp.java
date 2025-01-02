package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;
import javax.swing.ImageIcon;

public class PowerUp {
    // Types de PowerUp possibles : vie supplémentaire ou score supplémentaire
    public enum Type {
        EXTRA_LIFE, EXTRA_SCORE
    }

    private int x, y; // Position du PowerUp
    private Type type; // Type du PowerUp (EXTRA_LIFE ou EXTRA_SCORE)
    private static Image heartIcon;
    private static Image scoreIcon;

    // Constructeur pour initialiser un PowerUp
    public PowerUp(int x, int y) {
        this.x = x;
        this.y = y;
        // Détermine aléatoirement le type du PowerUp
        this.type = new Random().nextBoolean() ? Type.EXTRA_LIFE : Type.EXTRA_SCORE;

        // Charge l'image de cœur si elle n'est pas déjà chargée
        if (heartIcon == null) {
            heartIcon = new ImageIcon(getClass().getResource("/images/Heart.png")).getImage();
        }
        // Charge l'image de score si elle n'est pas déjà chargée
        if (scoreIcon == null) {
            scoreIcon = new ImageIcon(getClass().getResource("/images/score.png")).getImage();
        }
    }

    // Retourne le type du PowerUp
    public Type getType() {
        return type;
    }

    // Déplace le PowerUp vers le bas de l'écran
    public void move() {
        y += 2; // Descend de 2 pixels
    }

    // Récupère les limites du PowerUp sous forme de rectangle (utile pour les collisions)
    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20);
    }

    // Dessine le PowerUp à l'écran
    public void draw(Graphics g) {
        if (type == Type.EXTRA_LIFE) {
            g.drawImage(heartIcon, x, y, 20, 20, null);
        } else if (type == Type.EXTRA_SCORE) {
            g.drawImage(scoreIcon, x, y, 20, 20, null);
        }
    }

    // Retourne la position Y actuelle
    public int getY() {
        return y;
    }
}
