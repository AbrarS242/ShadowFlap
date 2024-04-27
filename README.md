## Project Overview

ShadowFlap is a fully functional game in Java based on Flappy Bird. The project involves designing and implementing game mechanics, graphics rendering, and user interaction features.

## Gameplay

**Level 0 Gameplay**:

![Level 0 Gameplay Video](https://github.com/AbrarS242/ShadowFlap/blob/main/assets/level_0_gamplay.gif)

## Project Structure

The project follows a standard Java project structure:

- **src**: Contains the Java source code files.
- **res**: Contains graphics resources used in the game.
  - **level-0**: Graphics resources specific to Level 0.
  - **level-1**: Graphics resources specific to Level 1.
  - **font**: Fonts used for rendering text in the game.

## Getting Started

To get started with the project:

1. Clone the repository to your local machine.
2. Open the project in your preferred Java IDE.
3. Add the `pom.xml` file as a Maven project or reload the dependencies within it.
4. Run `ShadowFlap.java` in order to play the game.

## Dependencies

The game relies on the Bagel library, a basic academic game engine library, for handling graphics and game logic.

## Game Features

ShadowFlap includes a variety of game elements and features, such as:

- Background rendering
- Bird mechanics including spawning, flying, falling, and collision detection
- Pipe mechanics with continuous spawning, different types, and collision detection
- Life bar for tracking player lives
- Weapons in Level 1 for destroying pipes
- Timescale adjustment for changing game speed
- Score counter with level-specific scoring logic
- Level transition screens for Level 0 and Level 1
- Game over and win screens

## Note ##
This program was written as an assignment for the subject SWEN20003 Object Oriented Software Development at the University of Melbourne. 
