# Seismic

A Java noise library, primarily for use in [Terra](https://github.com/PolyhedralDev/Terra).

# About

Seismic is a Java noise library, with a wide selection of noise functions. It started out as simply a fork of
[FastNoiseLite](https://github.com/Auburn/FastNoiseLite) that broke up the monolithic class into separate classes for
each noise function, but has since grown to include a much wider selection of noise functions and features.

To-Do:

* Add "combining" noise function implementations, e.g. "add, subtract, multiply, divide" classes that accept 2 samplers
  and apply the corresponding operation.
* Implement a builder pattern for instantiating noise samplers.

# Licensing

Seismic is licensed under the
[GNU Lesser General Public License (LGPL), version 3.0](https://www.gnu.org/licenses/lgpl-3.0.txt). It contains code
from [FastNoiseLite](https://github.com/Auburn/FastNoiseLite), licensed under the
[MIT License](https://github.com/Auburn/FastNoiseLite/blob/master/LICENSE).