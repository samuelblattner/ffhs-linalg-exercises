---
layout: default
---

# Linear Algebra Exercises

## General notes on architecture

This is a JavaFX application which is split up into two major packages: The <strong>Application-Package</strong> and the <strong>Exercises-Package</strong>.

### Application-Package

This package contains the main entry point of the application (_class Main_). The _Main_ class loads the main window that will contain all exercises
in a TabPane so that each Tab represents and loads one exercise. The _ApplicationController_ class is assigned as controller to the application via the
_application.fxml_ user interface.

The _ApplicationController_ is responsible for loading the individual exercises dynamically into the tabs of the TabPane. After the main window has successfully
loaded, the _Main_ class calls the controller to initialize the tabs and load the first exercise.

All exercises must implement the _ifExercise_ interface.

### Exercises-Package

Since many of the exercises are going to use common components, it is a good idea to only implement them once and make them available to the individual exercises. Here's a short
description on the common parts:

#### ifCanvasDrawable interface

This interface must be implemented by all objects that need to be drawn onto a JavaFX Canvas. Drawables will be iteratively called by the main _render_ routine using the
_draw_ method which is passed a _GraphicsContext_ reference. Thus, the actual process of drawing is delegated to the individual drawables themselves since they know
best how to do it. The only thing that is decided by the class rendering the scene is _where (which GraphicsContext)_ and _when_ the objects are drawn.

#### AbstractExercise class

Implements an exercise container in its very basic functionalities. All it does is store a reference to the main exercise container (i.e. the Tab's contents) and
calling the _onExerciseInitialized_ hook.

#### AbstractCanvasExercise class

Basic implementation of a JavaFX-Canvas-based exercise. This class extends the _AbstractExercise class_ and implements all steps required to set up a
JavaFX-Canvas element. A reference to the GraphicsContext of the Canvas is stored as _protected_ so that it is available to all sub-classes.

#### Matrix class
This class represents a model of a matrix and provides methods to get and set values and to perform simple mathematical operations.
The dimensions of the matrix are set on initialization and are immutable. 

#### Vector2D class
As vectors are a specialized form of matrices, the Vector2D class inherits from the Matrix class. It implements methods for vector-specific operations
such as calculating its length (i.e. magnitude) or its scalar product, for example.

## Exercises

### Exercise 1: «Wohin klickt die Maus?»
Using linear algebra to determine if a given set of coordinates lies on a specific line segment or not.
[Goto Exercise 1 Docs](./exercises/exercise1.html)

### Exercise 2: «Affine Transformationen»
Transforming geometry using matrices.
[Goto Exercise 2 Docs](./exercises/exercise2.html)

### Exercise 3: «Rotation 3D-Cube»
Rotating a 3D cube.
[Goto Exercise 3 Docs](./exercises/exercise3.html)

### Exercise 4: «Page Rank Algorithm»
A brief overview over Google's Page Rank Algorithm.
[Goto Exercise 4 Docs](./exercises/exercise4.html)
