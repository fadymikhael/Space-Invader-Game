
# Space Invader Game 🎮

Un jeu de Space Invader développé en **Java** avec l'interface graphique Swing.

## Fonctionnalités
- **Contrôle du joueur** : Déplacez un vaisseau pour détruire les ennemis.
- **Vagues d'ennemis** : Les ennemis descendent et deviennent plus nombreux à chaque niveau avec des tires.
- **Power-Ups** : Collectez des bonus pour gagner des vies supplémentaires ou des points bonus.
- **Scores** :
  - Gestion d'un score en direct.
  - Meilleur score enregistré dans un fichier.

## Pré-requis
- **Java JDK 17 ou supérieur**
- Un IDE comme IntelliJ IDEA ou Eclipse (facultatif)

## Instructions d'installation et d'exécution

### 1. Clonez le dépôt
Exécutez cette commande dans votre terminal :
```bash
git clone https://github.com/fadymikhael/Space-Invader-Game.git
cd Space-Invader-Game
```

### 2. Compilez le projet
#### Avec un IDE :
- Ouvrez le projet dans IntelliJ IDEA ou Eclipse.
- Assurez-vous que les images et les sons dans le dossier `resources` sont bien configurés.
- Exécutez la classe `SpaceInvaderGame`.

#### En ligne de commande :
```bash
javac -d target/classes -sourcepath src/main/java src/main/java/game/*.java
java -classpath target/classes game.SpaceInvaderGame
```

### 3. Profitez du jeu 🎉
- **Déplacez le vaisseau** avec les touches `Flèche gauche` et `Flèche droite`.
- **Tirez** sur les ennemis avec la touche `Espace`.
- **Pause** : Appuyez sur `P`.
- **Recommencez** après avoir perdu avec la touche `R`.

## Captures d'écran
*(Ajoutez des captures d'écran du jeu ici)*

## Structure du projet
- `src/main/java/game` : Code source Java du jeu.
- `src/main/resources` : Ressources (images, sons, fichiers).
- `target` : Dossier de compilation (non inclus dans le dépôt).
- `README.md` : Fichier actuel.

## Contribuer
Les contributions sont les bienvenues ! Si vous souhaitez améliorer le jeu ou proposer de nouvelles fonctionnalités :
1. Forkez le projet.
2. Créez une branche pour vos modifications :
   ```bash
   git checkout -b nouvelle-fonctionnalite
   ```
3. Faites un pull request.

## Auteur
- **Fady Mikhael**  
  [GitHub Profile](https://github.com/fadymikhael)

## Licence
Ce projet est sous licence MIT. Vous pouvez l'utiliser librement pour vos propres projets.

---

Amusez-vous bien ! 🚀
