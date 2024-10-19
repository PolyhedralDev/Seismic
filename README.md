# Seismic

[![Build Status](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci.solo-studios.ca%2Fjob%2FPolyhedralDev%2Fjob%2FSeismic%2Fjob%2Fmaster%2F&style=for-the-badge&link=https%3A%2F%2Fci.solo-studios.ca%2Fjob%2FPolyhedralDev%2Fjob%2FSeismic%2Fjob%2Fmaster%2F)](https://ci.solo-studios.ca/job/PolyhedralDev/job/Seismic/job/master/)
[![GitHub Tag](https://img.shields.io/github/v/tag/PolyhedralDev/Seismic?sort=semver&style=for-the-badge)](https://github.com/PolyhedralDev/Seismic/tags)
[![Chat](https://img.shields.io/discord/715448651786485780?style=for-the-badge&color=7389D8)](https://terra.polydev.org/contact.html)

---

<big><b>A Java sampler, math, and type library, primarily for use in [Polyhedral Development](https://github.com/PolyhedralDev)
projects.</b></big>

# About

Seismic is a Java sampler, math, and type library, with a wide selection of functions and types. It started out as simply a fork of
[FastNoiseLite](https://github.com/Auburn/FastNoiseLite) that broke up the monolithic class into separate classes for
each sampler, but has since grown to include a much wider selection of samplers and features, including math functions and types. Where
there is overlap with Java's Math class it is assumed that Seismic's functions are faster, sometimes trading a reasonable amount of accuracy
or handling of special floating point values.

Seismic is lightweight and only depends on JetBrains' [Annotations](https://github.com/JetBrains/java-annotations)
and [SLF4J](http://www.slf4j.org/). However, it is recommended to use Seismic with a HotSpot based JDK as Seismic hooks into HotSpot's
internals for optimal performance.

# Features

Seismic contains a wide selection of samplers, math utilities, and types including:

<details>

<summary><big><b>Samplers</b></big></summary>

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

<summary><big><b>Math Functions</b></big></summary>

### Algebra:

* Inverse Square Root

### Arithmetic:

* Fused Multiply Add

### Floating Point:

* Epsilon Equals
* Round
* Floor
* Ceiling
* Fraction

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
* Quartic Polynomial
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

<summary><big><b>Types</b></big></summary>

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

## Licensing

Seismic is licensed under the [GNU Lesser General Public License (LGPL), version 3.0](https://www.gnu.org/licenses/lgpl-3.0.txt). It
includes code from:

- [FastNoiseLite](https://github.com/Auburn/FastNoiseLite) (MIT License)
- [Lithium](https://github.com/CaffeineMC/lithium-fabric) (LGPL 3.0)
- [Apache Lucene](https://github.com/apache/lucene) (Apache License 2.0)

Additionally, it contains trivial and/or CC0 code from various other sources.
