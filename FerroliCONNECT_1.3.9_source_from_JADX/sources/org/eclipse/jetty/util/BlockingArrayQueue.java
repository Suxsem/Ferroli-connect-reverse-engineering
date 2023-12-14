package org.eclipse.jetty.util;

import java.util.AbstractList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingArrayQueue<E> extends AbstractList<E> implements BlockingQueue<E> {
    public final int DEFAULT_CAPACITY;
    public final int DEFAULT_GROWTH;
    private volatile int _capacity;
    private Object[] _elements;
    private final int _growCapacity;
    private int _head;
    private final ReentrantLock _headLock;
    private final int _limit;
    private final Condition _notEmpty;
    private final AtomicInteger _size;
    private long _space0;
    private long _space1;
    private long _space2;
    private long _space3;
    private long _space4;
    private long _space5;
    private long _space6;
    private long _space7;
    private int _tail;
    private final ReentrantLock _tailLock;

    public BlockingArrayQueue() {
        this.DEFAULT_CAPACITY = 128;
        this.DEFAULT_GROWTH = 64;
        this._size = new AtomicInteger();
        this._headLock = new ReentrantLock();
        this._notEmpty = this._headLock.newCondition();
        this._tailLock = new ReentrantLock();
        this._elements = new Object[128];
        this._growCapacity = 64;
        this._capacity = this._elements.length;
        this._limit = Integer.MAX_VALUE;
    }

    public BlockingArrayQueue(int i) {
        this.DEFAULT_CAPACITY = 128;
        this.DEFAULT_GROWTH = 64;
        this._size = new AtomicInteger();
        this._headLock = new ReentrantLock();
        this._notEmpty = this._headLock.newCondition();
        this._tailLock = new ReentrantLock();
        this._elements = new Object[i];
        this._capacity = this._elements.length;
        this._growCapacity = -1;
        this._limit = i;
    }

    public BlockingArrayQueue(int i, int i2) {
        this.DEFAULT_CAPACITY = 128;
        this.DEFAULT_GROWTH = 64;
        this._size = new AtomicInteger();
        this._headLock = new ReentrantLock();
        this._notEmpty = this._headLock.newCondition();
        this._tailLock = new ReentrantLock();
        this._elements = new Object[i];
        this._capacity = this._elements.length;
        this._growCapacity = i2;
        this._limit = Integer.MAX_VALUE;
    }

    public BlockingArrayQueue(int i, int i2, int i3) {
        this.DEFAULT_CAPACITY = 128;
        this.DEFAULT_GROWTH = 64;
        this._size = new AtomicInteger();
        this._headLock = new ReentrantLock();
        this._notEmpty = this._headLock.newCondition();
        this._tailLock = new ReentrantLock();
        if (i <= i3) {
            this._elements = new Object[i];
            this._capacity = this._elements.length;
            this._growCapacity = i2;
            this._limit = i3;
            return;
        }
        throw new IllegalArgumentException();
    }

    public int getCapacity() {
        return this._capacity;
    }

    public int getLimit() {
        return this._limit;
    }

    public boolean add(E e) {
        return offer(e);
    }

    public E element() {
        E peek = peek();
        if (peek != null) {
            return peek;
        }
        throw new NoSuchElementException();
    }

    public E peek() {
        E e = null;
        if (this._size.get() == 0) {
            return null;
        }
        this._headLock.lock();
        try {
            if (this._size.get() > 0) {
                e = this._elements[this._head];
            }
            return e;
        } finally {
            this._headLock.unlock();
        }
    }

    public boolean offer(E e) {
        if (e != null) {
            this._tailLock.lock();
            try {
                boolean z = false;
                if (this._size.get() < this._limit) {
                    if (this._size.get() == this._capacity) {
                        this._headLock.lock();
                        if (!grow()) {
                            this._headLock.unlock();
                        } else {
                            this._headLock.unlock();
                        }
                    }
                    this._elements[this._tail] = e;
                    this._tail = (this._tail + 1) % this._capacity;
                    if (this._size.getAndIncrement() == 0) {
                        z = true;
                    }
                    this._tailLock.unlock();
                    if (z) {
                        this._headLock.lock();
                        try {
                            this._notEmpty.signal();
                        } finally {
                            this._headLock.unlock();
                        }
                    }
                    return true;
                }
                this._tailLock.unlock();
                return false;
            } catch (Throwable th) {
                this._tailLock.unlock();
                throw th;
            }
        } else {
            throw new NullPointerException();
        }
    }

    public E poll() {
        E e = null;
        if (this._size.get() == 0) {
            return null;
        }
        this._headLock.lock();
        try {
            if (this._size.get() > 0) {
                int i = this._head;
                E e2 = this._elements[i];
                this._elements[i] = null;
                this._head = (i + 1) % this._capacity;
                if (this._size.decrementAndGet() > 0) {
                    this._notEmpty.signal();
                }
                e = e2;
            }
            return e;
        } finally {
            this._headLock.unlock();
        }
    }

    public E take() throws InterruptedException {
        this._headLock.lockInterruptibly();
        while (this._size.get() == 0) {
            try {
                this._notEmpty.await();
            } catch (InterruptedException e) {
                this._notEmpty.signal();
                throw e;
            } catch (Throwable th) {
                this._headLock.unlock();
                throw th;
            }
        }
        int i = this._head;
        E e2 = this._elements[i];
        this._elements[i] = null;
        this._head = (i + 1) % this._capacity;
        if (this._size.decrementAndGet() > 0) {
            this._notEmpty.signal();
        }
        this._headLock.unlock();
        return e2;
    }

    public E poll(long j, TimeUnit timeUnit) throws InterruptedException {
        long nanos = timeUnit.toNanos(j);
        this._headLock.lockInterruptibly();
        while (this._size.get() == 0) {
            try {
                if (nanos <= 0) {
                    this._headLock.unlock();
                    return null;
                }
                nanos = this._notEmpty.awaitNanos(nanos);
            } catch (InterruptedException e) {
                this._notEmpty.signal();
                throw e;
            } catch (Throwable th) {
                this._headLock.unlock();
                throw th;
            }
        }
        E e2 = this._elements[this._head];
        this._elements[this._head] = null;
        this._head = (this._head + 1) % this._capacity;
        if (this._size.decrementAndGet() > 0) {
            this._notEmpty.signal();
        }
        this._headLock.unlock();
        return e2;
    }

    public E remove() {
        E poll = poll();
        if (poll != null) {
            return poll;
        }
        throw new NoSuchElementException();
    }

    public void clear() {
        this._tailLock.lock();
        try {
            this._headLock.lock();
            this._head = 0;
            this._tail = 0;
            this._size.set(0);
            this._headLock.unlock();
            this._tailLock.unlock();
        } catch (Throwable th) {
            this._tailLock.unlock();
            throw th;
        }
    }

    public boolean isEmpty() {
        return this._size.get() == 0;
    }

    public int size() {
        return this._size.get();
    }

    public E get(int i) {
        this._tailLock.lock();
        try {
            this._headLock.lock();
            if (i >= 0) {
                if (i < this._size.get()) {
                    int i2 = this._head + i;
                    if (i2 >= this._capacity) {
                        i2 -= this._capacity;
                    }
                    E e = this._elements[i2];
                    this._headLock.unlock();
                    this._tailLock.unlock();
                    return e;
                }
            }
            throw new IndexOutOfBoundsException("!(0<" + i + "<=" + this._size + ")");
        } catch (Throwable th) {
            this._tailLock.unlock();
            throw th;
        }
    }

    public E remove(int i) {
        this._tailLock.lock();
        try {
            this._headLock.lock();
            if (i >= 0) {
                if (i < this._size.get()) {
                    int i2 = this._head + i;
                    if (i2 >= this._capacity) {
                        i2 -= this._capacity;
                    }
                    E e = this._elements[i2];
                    if (i2 < this._tail) {
                        System.arraycopy(this._elements, i2 + 1, this._elements, i2, this._tail - i2);
                        this._tail--;
                        this._size.decrementAndGet();
                    } else {
                        System.arraycopy(this._elements, i2 + 1, this._elements, i2, (this._capacity - i2) - 1);
                        if (this._tail > 0) {
                            this._elements[this._capacity] = this._elements[0];
                            System.arraycopy(this._elements, 1, this._elements, 0, this._tail - 1);
                            this._tail--;
                        } else {
                            this._tail = this._capacity - 1;
                        }
                        this._size.decrementAndGet();
                    }
                    this._headLock.unlock();
                    this._tailLock.unlock();
                    return e;
                }
            }
            throw new IndexOutOfBoundsException("!(0<" + i + "<=" + this._size + ")");
        } catch (Throwable th) {
            this._tailLock.unlock();
            throw th;
        }
    }

    public E set(int i, E e) {
        if (e != null) {
            this._tailLock.lock();
            try {
                this._headLock.lock();
                if (i >= 0) {
                    if (i < this._size.get()) {
                        int i2 = this._head + i;
                        if (i2 >= this._capacity) {
                            i2 -= this._capacity;
                        }
                        E e2 = this._elements[i2];
                        this._elements[i2] = e;
                        this._headLock.unlock();
                        this._tailLock.unlock();
                        return e2;
                    }
                }
                throw new IndexOutOfBoundsException("!(0<" + i + "<=" + this._size + ")");
            } catch (Throwable th) {
                this._tailLock.unlock();
                throw th;
            }
        } else {
            throw new NullPointerException();
        }
    }

    public void add(int i, E e) {
        if (e != null) {
            this._tailLock.lock();
            try {
                this._headLock.lock();
                if (i >= 0) {
                    if (i <= this._size.get()) {
                        if (i == this._size.get()) {
                            add(e);
                        } else {
                            if (this._tail == this._head) {
                                if (!grow()) {
                                    throw new IllegalStateException("full");
                                }
                            }
                            int i2 = this._head + i;
                            if (i2 >= this._capacity) {
                                i2 -= this._capacity;
                            }
                            this._size.incrementAndGet();
                            this._tail = (this._tail + 1) % this._capacity;
                            if (i2 < this._tail) {
                                System.arraycopy(this._elements, i2, this._elements, i2 + 1, this._tail - i2);
                                this._elements[i2] = e;
                            } else {
                                if (this._tail > 0) {
                                    System.arraycopy(this._elements, 0, this._elements, 1, this._tail);
                                    this._elements[0] = this._elements[this._capacity - 1];
                                }
                                System.arraycopy(this._elements, i2, this._elements, i2 + 1, (this._capacity - i2) - 1);
                                this._elements[i2] = e;
                            }
                        }
                        this._headLock.unlock();
                        this._tailLock.unlock();
                        return;
                    }
                }
                throw new IndexOutOfBoundsException("!(0<" + i + "<=" + this._size + ")");
            } catch (Throwable th) {
                this._tailLock.unlock();
                throw th;
            }
        } else {
            throw new NullPointerException();
        }
    }

    private boolean grow() {
        int i;
        if (this._growCapacity <= 0) {
            return false;
        }
        this._tailLock.lock();
        try {
            this._headLock.lock();
            int i2 = this._head;
            int i3 = this._tail;
            Object[] objArr = new Object[(this._capacity + this._growCapacity)];
            if (i2 < i3) {
                i = i3 - i2;
                System.arraycopy(this._elements, i2, objArr, 0, i);
            } else {
                if (i2 <= i3) {
                    if (this._size.get() <= 0) {
                        i = 0;
                    }
                }
                int i4 = (this._capacity + i3) - i2;
                int i5 = this._capacity - i2;
                System.arraycopy(this._elements, i2, objArr, 0, i5);
                System.arraycopy(this._elements, 0, objArr, i5, i3);
                i = i4;
            }
            this._elements = objArr;
            this._capacity = this._elements.length;
            this._head = 0;
            this._tail = i;
            this._headLock.unlock();
            this._tailLock.unlock();
            return true;
        } catch (Throwable th) {
            this._tailLock.unlock();
            throw th;
        }
    }

    public int drainTo(Collection<? super E> collection) {
        throw new UnsupportedOperationException();
    }

    public int drainTo(Collection<? super E> collection, int i) {
        throw new UnsupportedOperationException();
    }

    public boolean offer(E e, long j, TimeUnit timeUnit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    public void put(E e) throws InterruptedException {
        if (!add(e)) {
            throw new IllegalStateException("full");
        }
    }

    public int remainingCapacity() {
        this._tailLock.lock();
        try {
            this._headLock.lock();
            int capacity = getCapacity() - size();
            this._headLock.unlock();
            this._tailLock.unlock();
            return capacity;
        } catch (Throwable th) {
            this._tailLock.unlock();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public long sumOfSpace() {
        long j = this._space0;
        this._space0 = j + 1;
        long j2 = this._space1;
        this._space1 = j2 + 1;
        long j3 = j + j2;
        long j4 = this._space2;
        this._space2 = j4 + 1;
        long j5 = j3 + j4;
        long j6 = this._space3;
        this._space3 = j6 + 1;
        long j7 = j5 + j6;
        long j8 = this._space4;
        this._space4 = j8 + 1;
        long j9 = j7 + j8;
        long j10 = this._space5;
        this._space5 = j10 + 1;
        long j11 = j9 + j10;
        long j12 = this._space6;
        this._space6 = j12 + 1;
        long j13 = j11 + j12;
        long j14 = this._space7;
        this._space7 = 1 + j14;
        return j13 + j14;
    }
}
