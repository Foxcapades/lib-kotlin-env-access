package io.foxcapades.lib.env

/**
 * Non-Blank Environment Access
 *
 * Provides a set of utilities for retrieving values from the environment.
 *
 * Differs from [Env] in that blank/empty environment variables are treated as
 * if they are not set.
 *
 * @see Env
 */
object NBEnv {

  /**
   * Looks up the value with the given [name] from the current process'
   * environment.
   *
   * If the value is not set, `null` is returned.
   *
   * If the value is set but is blank or empty, `null` will be returned.
   *
   * @param name Name of the environment variable to look up.
   *
   * @return The value of the environment variable [name] if it is set and
   * non-blank, otherwise `null`.
   *
   * @see [Env.get]
   */
  inline operator fun get(name: String) =
    System.getenv(name)?.ifBlank { return null }


  /**
   * Looks up the value with the given [name] from the current process'
   * environment, returning that value or [default] if no such environment
   * variable is set.
   *
   * If the value is set but is blank or empty, [default] will be returned.
   *
   * @param name Name of the environment variable to look up.
   *
   * @param default Default value to return if no such environment variable was
   * set.
   *
   * @return The value of the environment variable [name], or [default] if no
   * such variable is set.
   *
   * @see [Env.get]
   */
  inline fun get(name: String, default: String) = this[name] ?: default

  /**
   * Looks up the value with the given [name] from the current process'
   * environment, returning that value or the result of [default] if no such
   * environment variable is set.
   *
   * If the value is set but is blank or empty, the value provided by [default]
   * will be returned.
   *
   * @param name Name of the environment variable.
   *
   * @param default Default value provider that will be called if no environment
   * variable with the given [name] is set.
   *
   * @return The value of the environment variable [name], or the value returned
   * by [default] if no such variable is set.
   *
   * @see [Env.get]
   */
  inline fun get(name: String, default: () -> String) = this[name] ?: default()

  /**
   * Looks up the value with the given [name] from the current process'
   * environment, returning that value mapped to type [T] if present, otherwise
   * returning `null`.
   *
   * If the value is set but is blank or empty, `null` will be returned.
   *
   * @param name Name of the environment variable.
   *
   * @param fn Mapping function that will be called on the environment variable
   * at the given [name] only if that variable is set.
   *
   * @return The return value of [fn] after mapping the raw environment variable
   * with the given [name], otherwise, if no such environment variable is set,
   * returns `null`.
   *
   * @see [Env.get]
   */
  inline fun <T> get(name: String, fn: (String) -> T) = this[name]?.let(fn)

  /**
   * Looks up the value with the given [name] from the current process'
   * environment, returning that value mapped to type [T] if present, otherwise
   * returning the given [default] value.
   *
   * If the value is set but is blank or empty, [default] will be returned.
   *
   * @param name Name of the environment variable.
   *
   * @param default Default value to return if there is no environment variable
   * set with the given [name].
   *
   * @param fn Mapping function that will be called on the environment variable
   * at the given [name] only if that variable is set.
   *
   * @return The return value of [fn] after mapping the raw environment variable
   * with the given [name], otherwise, if no such environment variable is set,
   * returns [default].
   *
   * @see [Env.get]
   */
  inline fun <T> get(name: String, default: T, fn: (String) -> T) =
    get(name, fn) ?: default

  /**
   * Looks up the value with the given [name] from the current process'
   * environment, returning that value mapped to type [T] if present, otherwise
   * returning the value returned by the given [default] provider.
   *
   * If the value is set but is blank or empty, the value provided by [default]
   * will be returned.
   *
   * @param name Name of the environment variable.
   *
   * @param default Default value provider to that will be called to provide a
   * fallback value only if no environment variable is set with the given
   * [name].
   *
   * @param fn Mapping function that will be called on the environment variable
   * at the given [name] only if that variable is set.
   *
   * @return The return value of [fn] after mapping the raw environment variable
   * with the given [name], otherwise, if no such environment variable is set,
   * returns the value provided by [default].
   *
   * @see [NBEnv.get]
   */
  inline fun <T> get(name: String, default: () -> T, fn: (String) -> T) =
    get(name, fn) ?: default()

  /**
   * Looks up the value with the given [name] from the current process'
   * environment, returning that value or throwing an exception if no such
   * environment variable is set.
   *
   * If the value is set but is blank or empty, an exception will be thrown.
   *
   * @param name Name of the environment variable to look up.
   *
   * @return The value of the environment variable [name].
   *
   * @throws IllegalStateException If no environment variable is set with the
   * given [name].
   *
   * @see [Env.require]
   */
  inline fun require(name: String) =
    this[name] ?: throw IllegalStateException("Missing required environment variable \$$name")

  /**
   * Looks up the value with the given [name] from the current process'
   * environment, returning that value mapped to type [T] by the given mapping
   * function ([fn]).
   *
   * If the value is set but is blank or empty, an exception will be thrown.
   *
   * @param name Name of the environment variable to look up.
   *
   * @param fn Value mapping function.
   *
   * @return The value of the environment variable [name] mapped to type [T].
   *
   * @throws IllegalStateException If no environment variable is set with the
   * given [name].
   *
   * @see [Env.require]
   */
  inline fun <T> require(name: String, fn: (String) -> T) =
    require(name).let(fn)
}