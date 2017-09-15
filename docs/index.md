---
layout: default
---

# LinAlg Exercises

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

Since many of the exercises are going to use common components, it is a good idea to only implement them once and make them available to the exercises. Here's a short
description on the common parts:

#### ifCanvasDrawable interface

This interface must be implemented by all objects that need to be drawn on a JavaFX Canvas. Drawables will be iteratively called by the main render routine using the
_draw_ method and by passing a GraphicsContext reference. Thus, the actual process of drawing is delegated to the individual drawables themselves since they know
best how to do it. The only thing that is decided by the class rendering the scene is _where (which GraphicsContext)_ and _when_ the objects are drawn.

#### AbstractExercise class

Implements an exercise container in its very basic functionalities. All it does is storing a reference to the main exercise container (i.e. the Tab's contents) and
calling the _onExerciseInitialized_ hook.

#### AbstractCanvasExercise class

Basic implementation of a JavaFX-Canvas based exercise. This class extends the _AbstractExercise class_ and implements all steps required to set up a
JavaFX-Canvas element. A reference to the GraphicsContext of the Canvas is stored as _protected_ so that it is available to all subclasses.

#### Matrix class
This class represents a model of a matrix and provides methods to get and set values and to perform simple mathematical operations.
The dimensions of the matrix are set on initialization and are immutable. 

#### Vector2D class
As vectors are a specialized form of matrices, the Vector2D class inherits from the Matrix class. It implements methods for vector-specific operations
such as calculating its length (i.e. magnitude) and scalar product for example.

## Exercise 1: "Wohin klickt die Maus?"
The basic goal behind this exercise is to determine whether a given set of coordinates lie on a specific straight line segment or not, using linear algebra.

So, let's define the requirements for our program:
1. Provide a canvas for the user to draw straight line segments by dragging the mouse.
2. Listen to double-clicks and determine if the click was performed on a line segment. If this is the case, remove the line from the canvas.
3. This is an extra: Provide some UI-controls to set the line width and click tolerance (i.e. the size of the surrounding space to a line in which a click is still accepted as "being on the line").

Let's walk through some of the most important parts of the application:

### Setting up the exercise
Since this exercise is using JavaFX-Canvas, it inherits from the _AbstractCanvasExercise_ class. The exercise basically initializes the following way:

1. When the _onExerciseInitialized_ hook is called, build the additional GUI elements relevant to this exercise (req. 3). 
2. Then, create all necessary event listeners to provide the desired functionalities.

#### Listen for mouse movements
The _establishEventListeners()_ method creates all listeners that are required to register mouse clicks, drags and releases. Let's start with the dragging:

```java
container.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent event) {
        if (isDragging) {
            currentLine.setEndCoordinates(event.getX(), event.getY());
            render();
        } else {
            isDragging = true;
            double x = event.getX();
            double y = event.getY();

            currentLine = new Line(x, y, x, y);
            currentLine.setThickness(thickness);
            drawables.add(currentLine);
        }
    }
});
```

    
