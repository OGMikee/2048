# 2048 Game

A JavaFX implementation of the classic 2048 puzzle game with cross-platform support.

## Download & Play

Download the latest version for your operating system from the [Releases](https://github.com/OGMikee/2048/releases) page.

### Mac
1. Download `Game2048-Mac.zip`
2. Unzip the file
3. Double-click `Game2048.app` to play

### Windows
1. Download `Game2048-Windows.zip`
2. Unzip the file
3. Double-click `Game2048.exe` to play

### Linux
1. Download `Game2048-Linux.zip`
2. Unzip the file
3. Run `./Game2048/bin/Game2048`

## How to Play

Use the **arrow keys** to slide tiles in any direction. When two tiles with the same number touch, they merge into one with their sum. Your goal is to create a tile with the number **2048**!

## Game Rules

- Use arrow keys (↑ ↓ ← →) to move tiles
- Tiles with the same number merge when they collide
- After each move, a new tile (2 or 4) appears randomly
- The game ends when no more moves are possible
- Try to reach 2048 or go even higher!

## Development

### Prerequisites
- Java 21 or higher
- Maven

### Building from Source
```bash
git clone https://github.com/OGMikee/2048.git
cd 2048
mvn clean package
```

### Running Locally
```bash
mvn javafx:run
```

Or with the built JAR:
```bash
java --module-path target/libs --add-modules javafx.controls,javafx.graphics -jar target/game2048-1.0.jar
```

### Project Structure
```
2048/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── game2024/
│                   ├── Main.java
│                   ├── controller/
│                   ├── model/
│                   └── view/
├── .github/
│   └── workflows/
│       └── release.yml
├── pom.xml
└── README.md
```

## Technologies Used

- Java 21
- JavaFX 21
- Maven
- GitHub Actions (CI/CD)

## CI/CD

This project uses GitHub Actions to automatically build and release the game for Mac, Windows, and Linux on every push to the main branch.

## License

MIT License

Copyright (c) 2025 OGMikee

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

## Acknowledgments

Original 2048 game created by Gabriele Cirulli.