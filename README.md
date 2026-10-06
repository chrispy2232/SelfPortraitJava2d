# Java 2D Self Portrait

A starter portrait made with Java 2D shapes, with a Swing desktop window and PNG export for cloud environments. Customize the colors and shapes in `src/SelfPortrait.java` to draw yourself.

## Requirements

Java Development Kit (JDK) 17 or newer. No external dependencies or build tool required.

## Build

Run from the repository root:

```sh
mkdir -p build/classes
javac -d build/classes src/SelfPortrait.java
```

## Run on a desktop

```sh
java -cp build/classes SelfPortrait
```

## Render in the cloud

Cloud machines generally have no graphical display. Export the same drawing to a PNG instead:

```sh
java -Djava.awt.headless=true -cp build/classes SelfPortrait --render build/portrait.png
```

The generated image is 600 × 600 pixels. Build outputs are ignored by Git.
