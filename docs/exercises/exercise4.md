---
layout: default
---

## Exercise 4: Page Rank Algorithm

### Introduction
One of the most vital concepts behind how Google ranks all the indexed websites is the so called «Page Rank Algorithm». 
First introduced and patented by Larry Page in 1998 [^1] this algorithm describes a method to assign a weight to every
page in the index depending on how many other pages link to that page and on the weight these pages have themselves.
In this article we'll cover the mechanics of the algorithm itself and some mathematical basics on which the algorithm relies on, such as the Markov-Chain and the Eigenvalue-problem.

### Eigenvalue and Eigenvectors
In order to better understand the mathematics behind, we need to cover a topic called «Eigenvalue/Eigenvector» first. 
Let's assume we have the following Matrix:

$$ 

A=\begin{bmatrix}1 & 2 \\ 3 & 4\end{bmatrix}

$$

Now, if we have a vector $$\vec{x}$$ which is not the null vector, then the following equation with the Eigenvalue 
represented by $$\lambda$$ must be valid:
  
$$

A\vec{x}=\lambda\vec{x}

$$

What is this good for? Well, imagine you have a square matrix of a million columns by a million rows – have fun 
multiplying this monster with a vector. The neat thing about Eigenvalues is that you can represent the whole Matrix with
just one scalar. And then do the multiplication with its corresponding Eigenvector instead. 

### The Markov-Chain
Equally essential to the understanding of the Page Rank Algorithm is the so-called «Markov-Chain». It describes a 
state-transformation matrix which holds the weights of an adjacency graph. And every weight represents the probability
with which the described system will change its current state to another. Let's have a look at the following image to
get a clearer picture:

![State Changes](images/ex4_state_changes.png "State diagram"){:width="80%"}


### Basic principle of the Page Rank Algorithm

### Summary


### References
[^1]: https://www.google.com/patents/US6285999


<script type="text/javascript" async
  src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-MML-AM_CHTML">
</script>