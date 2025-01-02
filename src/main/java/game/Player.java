package game;

import java.awt.*;
import java.awt.Graphics;

public class Player {
    private int x, y;
    private int direction;
    private Image sprite;

    // Constructeur pour initialiser le joueur

    public Player(int x, int y, Image sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.direction = 0;
    }
    // Définit la direction du mouvement du joueur
    public void setDirection(int direction) {
        this.direction = direction; // -1 pour gauche, 1 pour droite
    }

    // Déplace le joueur en fonction de la direction
    public void move() {
        x += direction * 5; // Change la position X selon la direction et la vitesse
        x = Math.max(0, Math.min(800 - 50, x)); // Limite le joueur dans la fenêtre de jeu (800px de large)
    }

    // Permet au joueur de tirer une balle
    public Bullet shoot() {
        return new Bullet(x + 20, y); // Crée une balle à la position du joueur
    }

    // Récupère les limites du joueur sous forme de rectangle (utile pour les collisions)
    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 20); // Dimensions du joueur : 50x20 pixels
    }

    // Dessine le joueur sur l'écran
    public void draw(Graphics g) {
        g.drawImage(sprite, x, y, 50, 20, null); // Dessine le sprite à la position actuelle
    }
}
