package game;

import java.awt.*;
import java.awt.Graphics;

public class Enemy {
    private int x, y;
    private int direction;
    private Image sprite;

    // Constructeur pour initialiser les propriétés de l'ennemi
    public Enemy(int x, int y, Image sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.direction = 1;
    }
    // Méthode pour déplacer l'ennemi horizontalement
    public void move() {
        x += direction * 2; // Déplace l'ennemi dans la direction actuelle
    }

    // Méthode pour inverser la direction du mouvement
    public void reverseDirection() {
        direction = -direction; // Change la direction (de droite à gauche ou inversement)
    }

    // Méthode pour faire descendre l'ennemi d'un certain nombre de pixels
    public void moveDown() {
        y += 20; // Descend de 20 pixels
    }

    // Obtenir la position X de l'ennemi
    public int getX() {
        return x;
    }

    // Obtenir la position Y de l'ennemi
    public int getY() {
        return y;
    }

    // Récupérer les limites de l'ennemi sous forme de rectangle (utile pour la collision)
    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 40);
    }

    // Méthode pour dessiner l'ennemi sur l'écran
    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, 40, 40, null); // Dessine l'image à la position (x, y)
    }
}