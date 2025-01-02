package game;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

class EnemyBullet {
    private int x, y;

    // Constructeur pour initialiser la balle ennemie
    public EnemyBullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Déplace la balle ennemie vers le bas de l'écran
    public void move() {
        y += 5; // Descend de 5 pixels à chaque appel
    }

    // Retourne la position Y actuelle de la balle
    public int getY() {
        return y;
    }

    // Récupère les limites de la balle sous forme de rectangle (utile pour les collisions)
    public Rectangle getBounds() {
        return new Rectangle(x, y, 5, 10);
    }

    // Dessine la balle ennemie sur l'écran
    public void draw(Graphics g) {
        g.setColor(Color.GREEN); // Définit la couleur de la balle (verte)
        g.fillRect(x, y, 5, 10); // Dessine un rectangle représentant la balle
    }
}
