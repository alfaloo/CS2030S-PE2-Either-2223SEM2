/**
 * CS2030S PE1 Question 1
 * AY21/22 Semester 2
 *
 * @author A0000000X
 */

package cs2030s.fp;

import java.util.NoSuchElementException;

public abstract class Either<L, R> {
  private static final class Left<L, R> extends Either<L, R> {
    private L item;

    private Left(L item) {
      this.item = item;
    }

    @Override
    public boolean isLeft() {
      return true;
    }

    @Override
    public boolean isRight() {
      return false;
    }

    @Override
    public L getLeft() {
      return this.item;
    }

    @Override
    public R getRight() throws NoSuchElementException {
      throw new NoSuchElementException();
    }

    @Override
    public <T, U> Either<T, U> map(Transformer<? super L, ? extends T> lt, Transformer<? super R, ? extends U> rt) {
      return Either.left(lt.transform(this.item));
    }

    @Override
    public <T, U> Either<T, U> flatMap(Transformer<? super L, ? extends Either<? extends T, ? extends U>> lt, Transformer<? super R, ? extends Either<? extends T, ? extends U>> rt) {
      return Either.left(lt.transform(this.item).getLeft());
    }

    @Override
    public <T> T fold(Transformer<? super L, ? extends T> lt, Transformer<? super R, ? extends T> rt) {
      return lt.transform(this.item);
    }

    @Override
    public Either<L, R> filterOrElse(BooleanCondition<? super R> bc, Transformer<? super R, ? extends L> transformer) {
      return Either.left(this.item);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;

      if (obj instanceof Left) {
        Left<?, ?> left = (Left<?, ?>) obj;
	return this.item == left.item
	  ? true
	  : this.item == null || left.item == null
	  ? false
	  : this.item.equals(left.item);
      } else return false;
    }

    @Override
    public String toString() {
      return "Left[" + this.item.toString() + "]";
    }
  }

  private static final class Right<L, R> extends Either<L, R> {
    private R item;

    private Right(R item) {
      this.item = item;
    }

    @Override
    public boolean isLeft() {
      return false;
    }

    @Override
    public boolean isRight() {
      return true;
    }

    @Override
    public L getLeft() throws NoSuchElementException {
      throw new NoSuchElementException();
    }

    @Override
    public R getRight() {
      return this.item;
    }

    @Override
    public <T, U> Either<T, U> map(Transformer<? super L, ? extends T> lt, Transformer<? super R, ? extends U> rt) {
      return Either.right(rt.transform(this.item));
    }

    @Override
    public <T, U> Either<T, U> flatMap(Transformer<? super L, ? extends Either<? extends T, ? extends U>> lt, Transformer<? super R, ? extends Either<? extends T, ? extends U>> rt) {
      return Either.right(rt.transform(this.item).getRight());
    }

    @Override
    public <T> T fold(Transformer<? super L, ? extends T> lt, Transformer<? super R, ? extends T> rt) {
      return rt.transform(this.item);
    }

    @Override
    public Either<L, R> filterOrElse(BooleanCondition<? super R> bc, Transformer<? super R, ? extends L> transformer) {
      if (bc.test(this.item)) {
        return Either.right(this.item);
      } else {
        return Either.left(transformer.transform(this.item));
      }
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;

      if (obj instanceof Right) {
        Right<?, ?> right = (Right<?, ?>) obj;
	return this.item == right.item
	  ? true
	  : this.item == null || right.item == null
	  ? false
	  : this.item.equals(right.item);
      } else return false;
    }

    @Override
    public String toString() {
      return "Right[" + this.item.toString() + "]";
    }
  }
  
  public static <T, U> Either<T, U> left(T t) {
    return new Left<T, U>(t);
  }

  public static <T, U> Either<T, U> right(U u) {
    return new Right<T, U>(u);
  }

  public abstract boolean isLeft();

  public abstract boolean isRight();

  public abstract L getLeft();

  public abstract R getRight();

  public abstract <T, U> Either<T, U> map(Transformer<? super L, ? extends T> lt, Transformer<? super R, ? extends U> rt);

  public abstract <T, U> Either<T, U> flatMap(Transformer<? super L, ? extends Either<? extends T, ? extends U>> lt, Transformer<? super R, ? extends Either<? extends T, ? extends U>> rt);

  public abstract <T> T fold(Transformer<? super L, ? extends T> lt, Transformer<? super R, ? extends T> rt);

  public abstract Either<L, R> filterOrElse(BooleanCondition<? super R> bc, Transformer<? super R, ? extends L> transformer);
}

