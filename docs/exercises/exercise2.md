---
layout: default
---

## Exercise 2: "Affine Transformationen"
In this exercise we will cover how to perform 2D-translation of geometric objects. For this exercise we will create
some geometric objects in two dimensions and allow the user to transform them by specifying a transformation matrix. 
The central aspect of this exercise will be the use of 3D-transformation techniques in order to perform 2D-translations.

### The basic problem
Using matrices for calculating transformations is very handy and thus is used whenever it comes to programming 
applications that focus on graphics (such as games, CAD-programs, etc.). This basically works well for all
transformations "around the origin". However, when it comes to translation, we wouldn't be able to combine multiple
transformation matrices with multiplication, i.e. linear transformation. We would need to add the translation
parameters manually. 

### Using 3D-transformations for 2D-translation
We can use a little trick to perform 2D-translations with linear transformations. For this purpose, we imagine
the transformation to take place on a surface in 3D space. We can use the shear transformation matrix to perform a translation in the surface.
Thus, if we extend our 2D-Vector to a 3D-Vector that constantly lies on a surface with z-coordinate 1, we can use 
3D-transformations to "simulate" a 2D-translation.

### Adapting the Vector-Model
Since we'll be using 2D and 3D Vectors, we introduce an abstract base class for vectors:

```java
public abstract class AbstractVector extends Matrix {

    private double length = 0;

    public AbstractVector(int numDimensions, double ... coordinates) {
        super(1, numDimensions);
        for (int c = 0; c < coordinates.length; c++) {
            this.setValue(0, c, coordinates[c]);
        }
    }
    
    // ...
 }
```
As you can see, the *AbstractVector* Model takes a number that indicates how many dimensions this vector should have and
then accepts a random number of *double* arguments, defining the individual coordinates.

Then, we adapt our Vector2D-Model from the previous exercise such that it consists of three instead of only two coordinates:

```java
public class Vector2D extends AbstractVector {
    // ...
    public Vector2D(double dx, double dy) {
        super(3, dx, dy, 1);
    }
    // ...
}
```

So, our 2D-Vector is actually a 3D-Vector with a constant z-Coordinate of 1. The Vector2D constructor calls the *AbstractVector* constructor and tells it to create a vector with three dimensions, whereas
the z-coordinate is set to a constant 1. 

For Vector3D, of course, we need to enhance them with a fourth dimension, accordingly.
 
### Adapting the Matrix-Model
Since we now need to be able to multiply matrices with each other, we have to enhance the Matrix-Model with a 
corresponding method:

```java

public class Matrix {

    //...
    
    public Matrix multiply(Matrix otherMatrix, Matrix resultMatrix) {

        if (resultMatrix == null) {
            resultMatrix = new Matrix(this.getNumCols(), this.getNumRows());
        }

        if (otherMatrix.getNumRows() == getNumCols()) {
            for (int c = 0; c < otherMatrix.getNumCols(); c++) {
                for (int r = 0; r < this.getNumRows(); r++) {

                    double multResult = 0;

                    for (int cM = 0; cM < cells.length; cM++) {
                        multResult += cells[cM][r] * otherMatrix.getValue(c, cM);
                    }

                    resultMatrix.setValue(c, r, multResult);
                }
            }
        }

        return resultMatrix;
    }
    // ...
}
```

This method can be passed a *resultMatrix* if the return type needs to be specific. If this argument is null, the method
will create a new *Matrix* instance as result container. The method returns a *Matrix* instance (or any Subclass) and thus
can also be used for chaning calls such as: *matrix.multiply(matrix2).add(matrix3). ...*

### AbstractGeometry
To better deal with all the transformation stuff for our geometric objects, let's introduce the *AbstractGeometry* class. Every
object (LineSegment, Circle, etc.) will inherit from this class. Instead of declaring a specific number of Vector2D or Vector3D instances
for every specific object (like we did in the first exercise), we instead provide a *List* that can hold a random amount of
Vector-instances. This generic approach allows us to implement the transformation logic (multiplying transformation matrices with vectors)
already in this *AbstractGeometry* class and can omit these details in the specific classes.

Furthermore, every geometric object holds a *TransformationMatrix* instance, that stores the object's current transformation. So, whenever
a transformation needs to be performed, the *AbstractGeometry* class only needs to know the transformation matrix and multiply
 it with all vertices in the *List*. It doesn't need to know, what these vertices are used for by the specific object.


### Introducing: The World
As we will be using 3D-Space in later exercises, let me introduce the concept of "the World" already: The World model holds 
information about the world all the geometric objects live in. It also knows how to project three-dimensional coordinates onto
a surface such as our screen. So, the world is like a global container for our objects and it also can be transformed itself.

This gives us the advantage that we can scale or mirror the world using transformation matrices, too. So, in this example, 
if we create geometric objects with a width or height of 1, we wouldn't be able to see it on the screen because 1 corresponds
to 1 Pixel. Now, we can easly just scale the world by doing:

```
this.world.transformWorld(
    TransformationMatrix3D.createUniformScalingMatrix(100)
);
```

This assings our world a uniform scaling matrix with scaling factor 100. This matrix will be applied to every coordinate of every
object living in that world, when their absolute coordinates are calculated. That way, if we set an object to have a width of 1, it
will result in an actual width of 100 Pixel.

Let's continue by thinking about how the coordinate system of a computer screen corresponds to our natural real-world system.
While the latter maps the y-Coordinate "bottom-up", in the computer, it is mapped "top-down". To address this, we can simply
apply a mirror transformation matrix to our world so that it mirrors on the XZ-plane (and actually inverts the Y-axis):

```
this.world.transformWorld(
    TransformationMatrix3D.createMirrorXZMatrix()
);
```

In order to combine these two world-transformations, we can simply do:

```
this.world.transformWorld(
    (TransformationMatrix3D) TransformationMatrix3D.createMirrorXZMatrix().multiply(
                TransformationMatrix3D.createUniformScalingMatrix(100), TransformationMatrix3D.createIdentityMatrix()
    )
);
```

This multiplies the mirror matrix with the scaling matrix and creates a combined transformation matrix for our world.

In order for our geometric objects to make use of the world's global transformation, we need to assign them to the world
 when creating them:

```java
// ...
Circle unitCircle = new Circle(0, 0, 1, this.world);
// ...
```

When an object needs to be projected to the screen, it can get its individual screen coordinates by using
the method of the *AbstractGeometry* class, from which it inherits:

```
public Vector2D getScreenVertex(int vertexIndex) {
    return this.world.getScreenCoordinates(this.getTransformedVertex(vertexIndex));
}
```

As you can see, this method simply performs the object's local transformations first (*getTransformedVertex()*) and then
calls its assigned world to calculate the screen coordinates.

### Allowing the user to edit the transformation matrix
For the current exercise we need the user to be able to edit a transformation matrix. The **MatrixTableViewController** class takes
a *TableView* instance and a *Matrix* instance and combines them to an editable table. Changes in the matrix done by the user
are directly applied to the actual Matrix instance. 

### Putting it all together
Just like for the first exercise, this exercise uses an own class *Exercise2* that inherits from *AbstractCanvasExercise*. 
When loaded, it first initializes the additional GUI (matrix table and buttons) and then initializes the World instance
and all the relevant geometric objects.

Whenever the user hits the "Transform" button, the current user transformation matrix will be applied to all objects, i.e. their
local transformation matrices.

Whenever the objects are rendered to the canvas, they have "their" World calculate their absolute world coordinates and their
screen coordinates for them.

