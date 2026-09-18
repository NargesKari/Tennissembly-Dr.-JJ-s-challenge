# Tennissembly

A JavaFX tennis/badminton simulation where the ball's trajectory math runs as hand-written x86 assembly, called from Java through a JNI native library.

## What it does

- Simulates a ball launched across the screen along one of three trajectories (linear, parabolic, sinusoidal), each with its own physics model.
- The trajectory, rounding, clamping, and distance math (`calculateParabola`, `sin`, `myHypot`, `makeInBound`, `isBetween`, `divRoundAwayFromZero`) is implemented directly in x86 assembly inside `MyNative.c` and exposed to Java via JNI — `calculateParabola` in particular uses AVX (`vbroadcastsd`/`vmulpd` on YMM registers) to compute four trajectory samples in parallel instead of one call per frame.
- JavaFX handles the GUI, animation loop, input, and an optional AI opponent; the assembly layer only ever does the numeric core.

## Tech stack

Java (JavaFX for UI/animation), C (JNI bridge), x86 inline assembly (FPU and AVX/SIMD instructions), Maven.

## Getting started

The native library (`MyNative.dll`) is already built for Windows and checked into `src/main/java/model/library/`, so no separate C build step is needed to run the game. Open the project in an IDE with JavaFX support (or run via Maven with the JavaFX SDK on the module path) and run `view.Main`.

If you change `MyNative.c`, recompile it into `MyNative.dll` (see `windowsCommands.txt` for the build commands used) before rerunning.

## Report

Full write-up: [LaTeX report](https://latex.sharif.edu/read/vtfsgnvjhypd)
