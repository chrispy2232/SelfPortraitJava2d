# Self-Portrait in Java 2D

A programmed portrait based on the supplied graduation photograph. The drawing uses Java 2D vector shapes rather than displaying or tracing a raster image at runtime. It preserves the reference composition, dark background, short black hair, smiling face, white gown, blue stole, gold tassel, circular university seal, and wristwatch. Fine cloth texture and seal lettering are simplified.

## Requirements and execution

Use JDK 17 or newer. No external libraries are required. From the repository root:

```sh
mkdir -p build/classes
javac -Xlint:all -Werror -d build/classes src/SelfPortrait.java
java -cp build/classes SelfPortrait
```

The last command opens the desktop window. On a cloud machine without a display, export instead:

```sh
java -Djava.awt.headless=true -cp build/classes SelfPortrait --render submission/java-portrait.png
```

The image is 660 × 1000 pixels. The window scales the drawing uniformly to retain its proportions. The desktop window requires a graphical display; compilation and PNG export were validated in the cloud.

In this prepared cloud environment, use `/workspace/java-toolchain/runtime/bin/javac` if `javac` is not on PATH. If Fontconfig cannot write its cache, run with `XDG_CACHE_HOME=/workspace/java-toolchain/cache` after creating that directory.

## Required techniques: 10 out of 10

| Technique | Meaningful use | Source location (method) |
| --- | --- | --- |
| Line | Tooth divisions, tassel strands, watch hands, stole seams | `drawHead`, `drawCollarAndStole`, `drawHandsAndWatch` |
| Quadratic curve | Eyelids, under-eye contours, facial creases, gown folds | `drawEyes`, `drawHead`, `drawGown` |
| Cubic curve | Face and hair silhouettes, smile, neck, gown outline | `drawHead`, `drawHair`, `drawGown` |
| Arc | Curved inner-ear details | `drawHead` |
| Ellipse | Ears, irises, cheek shading, watch face | `drawHead`, `drawEyes`, `drawHandsAndWatch` |
| Circle | Concentric university seal rings | `drawBadge` |
| Rectangle | Tassel suspension cord and wristwatch strap | `drawCollarAndStole`, `drawHandsAndWatch` |
| Square | Small equal-sided white eye catchlights | `drawEyes` |
| General path | Irregular gown, face, hairstyle, collar, tie, stole | `polygon`, `drawGown`, `drawHead`, `drawHair` |
| Area / transformation | Iris intersected with eyelid opening; rotated seal and watch; scaled panel drawing | `drawEyes`, `drawBadge`, `drawHandsAndWatch`, `paintComponent` |

## Organization

`SelfPortrait.java` is the complete standalone source file. Separate drawing methods follow the back-to-front layering order. Graphics contexts created for clipping and transformations are disposed afterward. Geometry uses a consistent 660 × 1000 reference coordinate system.

## Submission checklist

- Original photograph: save the graduation photo provided in the chat as `original-portrait.jpg` and include it with your upload. It is not included in this repository or archive.
- Java-generated portrait: `submission/java-portrait.png`.
- Complete source and required `.java` file: `src/SelfPortrait.java`.
- Documentation: this README, including the technique mapping above.
- Downloadable bundle: `submission/self-portrait-submission.zip` contains source, generated portrait, and documentation. Add the original photograph before submitting.

Check the course portal for any prescribed filename or student-name convention. The assignment is due October 8, 2026 at 11:59 PM as stated in the activity instructions.
