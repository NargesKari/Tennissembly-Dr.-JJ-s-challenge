# 🎾 Assembly-Powered Tennis Simulation Game

A high-performance simulation game built using **Java**, **C**, and **x86 Assembly**, where a striped colorful ball is launched toward a wall or goal using selectable projectile paths. The time-consuming trajectory computations are fully optimized with assembly code for maximum performance.

---

## 🧠 Project Overview

This project simulates a simple 2D ball-launching game inspired by tennis or badminton mechanics. The user can choose between three types of projectile trajectories, and the ball is launched accordingly from one side of the screen to the other.

Unlike typical games, this one replaces the core computational logic (i.e., the motion equations and path calculations) with optimized **x86 Assembly code** using **FPU** (Floating-Point Unit) and optionally **SIMD** (Single Instruction, Multiple Data) instructions for significant performance gains.

---

## 🧭 Selectable Trajectories

- 🎯 **Linear Path**  
- 🏹 **Convex / Parabolic Path**  
- 🌊 **Sinusoidal Path**  

Each path has its own physical model and is computed using optimized Assembly routines.

---

## ⚙️ Technologies Used

| Language | Purpose |
|---------|---------|
| **Java** | GUI, event handling, game logic |
| **C**    | JNI (Java Native Interface) bridge between Java and Assembly |
| **x86 Assembly** | High-performance trajectory and physics calculations |

---

## 🚀 Key Features

- ⚡ **Optimized trajectory calculations using x86 Assembly (FPU/SIMD)**
- 🖱️ **Mouse-based input to control player responses and hit timing**
- 🎨 **Rotating, colorful ball visuals with dynamic color changes during motion**
- 🤖 **Optional AI opponent to simulate full matches**
- 📊 **Performance comparison between high-level (Java/C) and low-level (Assembly) implementations**

---

## 📈 Performance Analysis

To evaluate the effect of low-level optimization, the game was benchmarked in two modes:
- **High-level only (Java/C)**
- **Assembly-optimized mode**

In all cases — especially with sinusoidal motion — the Assembly version showed **much higher frame rates** and **faster response times**, making it ideal for real-time gameplay.

---

## Report

You can find the detailed LaTeX report [here](https://latex.sharif.edu/read/vtfsgnvjhypd).
