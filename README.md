# Seismic

A Java sampler, math, and type library, primarily for use in [Polyhedral Development](https://github.com/PolyhedralDev) projects.

# About

Seismic is a Java sampler, math, and type library, with a wide selection of functions and types. It started out as simply a fork of
[FastNoiseLite](https://github.com/Auburn/FastNoiseLite) that broke up the monolithic class into separate classes for
each sampler, but has since grown to include a much wider selection of samplers and features, including math functions and types. Where there is overlap with Java's Math class it is assumed that Seismic's functions are faster, sometimes trading a reasonable amount of accuracy or handling of special floating point values.

# Features

Seismic contains a wide selection of samplers, math utilities, and types including:

<details>

<summary>Samplers</summary>

### Gradient:

* OpenSimplex2
* OpenSimplex2S
* Simplex
* Perlin
* Value
* Cubically Interpolated Value Noise
* Gabor Noise

### Fractal:

* Brownian Motion
* Ping-Pong
* Ridged Fractal

### Random:

* Gaussian Noise
* White Noise
* Positive White Noise

### Other Noise:

* Cellular (Voronoi/Worley)

### Mutator:

* Domain Warp
* Cubic Spline
* Translate
* Linear Heightmap
* Fractal Gavoro Pseudoerosion

### Arithmetic:

* Addition
* Subtraction
* Multiplication
* Division
* Max
* Min

### Normalizer:

* Linear
* Linear Map
* Clamp
* Posterization
* Probability
* Scale
* Normal

### Exotic Sampler:

* Image
* Kernel

</details>

<details>

<summary>Math Functions</summary>

### Algebra:

* Inverse Square Root

### Arithmetic:

* Fused Multiply Add

### Exponential:

* Exponential

### Floating Point:

* Epsilon Equals
* Round
* Floor
* Ceiling
* Fraction

### Geometry:

* Sphere
* Cube

### Integer:

* Squash
* Integer Power of Ten
* Integer Logarithm Base Two Ceiling
* Integer Logarithm Base Two Floor
* Integer Logarithm Base Ten Ceiling
* Integer Logarithm Base Ten Floor

### Numerical Analysis:

#### Interpolation:

* Linear
* Bilinear
* Trilinear
* Cubic
* Bicubic
* Tricubic

#### Smoothstep:

* Cubic Polynomial
* Quadratic Polynomial
* Quintic Polynomial
* Cubic Rational
* Quartic Rational

### Statistic:

* Standard Deviation
* Normal Inverse

### Trigonometry:

* Sine
* Cosine
* Tangent
* Secant
* Cosecant
* Cotangent

</details>

<details>

<summary>Types</summary>

### Sampler:

* Sampler
* Derivative Sampler

### Vector:

* Vector2
* Vector2Int
* Vector3
* Vector3Int


### Enum:

* Rotation
* Distance Function

### Other:

* Cubic Spline

</details>

# Licensing

Seismic is licensed under the
[GNU Lesser General Public License (LGPL), version 3.0](https://www.gnu.org/licenses/lgpl-3.0.txt). It contains code
from [FastNoiseLite](https://github.com/Auburn/FastNoiseLite), licensed under the
[MIT License](https://github.com/Auburn/FastNoiseLite/blob/master/LICENSE), code
from [Lithium](https://github.com/CaffeineMC/lithium-fabric)
under [GNU Lesser General Public License (LGPL), version 3.0](https://github.com/CaffeineMC/lithium-fabric/blob/develop/LICENSE.txt), and
code from [Apache Lucene](https://github.com/apache/lucene) under
the [Apache License 2.0](https://github.com/apache/lucene/blob/main/LICENSE.txt); along with trivial and or CC0 code from various other
sources.
