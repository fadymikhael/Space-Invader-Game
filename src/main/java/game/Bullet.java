package game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Bullet {
    private int x, y;

    // Constructeur pour initialiser la balle
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Déplace la balle vers le haut de l'écran
    public void move() {
        y -= 10; // Monte de 10 pixels à chaque appel
    }

    // Retourne la position Y actuelle de la balle
    public int getY() {
        return y;
    }

    // Récupère les limites de la balle sous forme de rectangle (utile pour les collisions)
    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 10); // Taille de la balle : 5x10 pixels
    }

    // Dessine la balle sur l'écran
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW); // Définit la couleur de la balle (jaune)
        g.fillRect(x, y, 5, 10); // Dessine un rectangle représentant la balle
    }
}
