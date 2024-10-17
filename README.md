# Seismic

A Java noise library, primarily for use in [Terra](https://github.com/PolyhedralDev/Terra).

# About

Seismic is a Java sampler and math library, with a wide selection of functions. It started out as simply a fork of
[FastNoiseLite](https://github.com/Auburn/FastNoiseLite) that broke up the monolithic class into separate classes for
each sampler, but has since grown to include a much wider selection of samplers and features, including math functions useful for worldgen and other game development tasks.

# Features

Seismic contains a wide selection of samplers and math utilities, including:

<details>

<summary>Noise Samplers</summary>

### Gradient:

* OpenSimplex2
* OpenSimplex2S
* Simplex
* Perlin
* Value
* Cubically Interpolated Value Noise
* Gabor Noise

### Fractal

* Brownian Motion
* Ping-Pong
* Ridged Fractal

### Random

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

### Arithmetic

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

# Licensing

Seismic is licensed under the
[GNU Lesser General Public License (LGPL), version 3.0](https://www.gnu.org/licenses/lgpl-3.0.txt). It contains code
from [FastNoiseLite](https://github.com/Auburn/FastNoiseLite), licensed under the
[MIT License](https://github.com/Auburn/FastNoiseLite/blob/master/LICENSE).
