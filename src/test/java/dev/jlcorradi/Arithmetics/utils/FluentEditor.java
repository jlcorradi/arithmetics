package dev.jlcorradi.Arithmetics.utils;

@FunctionalInterface
public interface FluentEditor<T> {
  T edit(T builder);
}