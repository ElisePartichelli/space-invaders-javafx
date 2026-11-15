# Space Invaders – JavaFX

![space-invaders-78](https://user-images.githubusercontent.com/91432003/176572263-8f98329a-1d75-408e-817b-147eca162169.jpg)

A remake of the classic 1978 arcade game **Space Invaders**, built in Java with JavaFX. The project applies core object-oriented programming concepts (inheritance, polymorphism, interfaces and design patterns) to structure the game's elements and logic.

Developed as the final project for the Object-Oriented Programming course (2022).

## Features

- **Main menu** with player name input, ranking screen and exit option
- **Progressive levels** with different enemy formations
- **Four enemy types** with different sprites, durability and point values:

  | Enemy | Hits to destroy | Points | Shoots back |
  |---|---|---|---|
  | Enemy 1 | 1 | 10 | No |
  | Enemy 2 | 3 | 50 | No |
  | Enemy 3 | 1 | 70 | Yes |
  | Enemy 4 | 3 | 100 | Yes (double shot) |

- **Increasing difficulty**: enemies move faster and shoot more often as levels go up. After level 4, formations are chosen at random.
- **Weapon upgrade**: from level 4 on, the player's ship is replaced by a cannon that fires two shots at once
- **Lives system**: the player starts with 3 lives and earns an extra life every 1,000 points
- **Persistent ranking**: the top 10 scores are saved to a local file and shown at the end of each game

## Controls

| Key | Action |
|---|---|
| ← / → | Move |
| Space | Shoot |

## OOP Concepts Applied

- **Abstraction & inheritance**: `BasicElement` is an abstract base class for every game object. `Enemies` extends it, and `Enemy1` to `Enemy4` specialize its behavior.
- **Polymorphism**: the game loop updates, draws and checks collisions for all elements through the `Character` interface, without knowing their concrete types.
- **Interfaces**: `Character` defines the contract for game elements, and `KeyboardCtrl` defines the contract for objects that react to keyboard input.
- **Method overriding**: `JavaCannon` extends `Cannon` and overrides its shooting behavior. `ShotEnemy` extends `Shot` and reverses its direction.
- **Singleton pattern**: `Game` and `Params` ensure there is a single instance managing the game state and its parameters.

## Technologies

- Java 14+
- JavaFX 13
- Maven

## How to Run

Requires a JDK (14 or later) and Maven.

```bash
git clone https://github.com/ElisePartichelli/TrabalhoFinalPOO.git
cd TrabalhoFinalPOO/Andre_Bins-Marcos_Ferreira-Yanny_Partichelli/Andre_Bins-Marcos_Ferreira-Yanny_Partichelli
mvn clean javafx:run
```

## Project Structure

```
src/main/java/
├── Main.java          # Window setup, menu, ranking screen and game loop
├── Game.java          # Game state, levels, scoring, lives and ranking persistence
├── Params.java        # Global parameters (window size, random generator)
├── Character.java     # Interface for all game elements
├── KeyboardCtrl.java  # Interface for keyboard-controlled elements
├── BasicElement.java  # Abstract base class with position, movement and collision
├── Cannon.java        # Player ship
├── JavaCannon.java    # Upgraded player cannon (double shot)
├── Enemies.java       # Abstract enemy behavior
├── Enemy1-4.java      # Enemy types
├── Shot.java          # Player projectile
└── ShotEnemy.java     # Enemy projectile
```

## Authors

- **Yanny Elise Partichelli**
- **André Menezes Bins**
- **Marcos do Nascimento Ferreira**
