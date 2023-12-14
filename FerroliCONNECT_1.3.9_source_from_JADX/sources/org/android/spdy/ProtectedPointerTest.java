package org.android.spdy;

import org.android.spdy.ProtectedPointer;

public class ProtectedPointerTest {

    static class Data {

        /* renamed from: i */
        private int f4102i = 0;

        Data() {
        }

        public void work() {
            System.out.println("work");
            if (this.f4102i == 1) {
                System.exit(-1);
            }
        }

        public void destroy() {
            System.out.println("destroy");
            this.f4102i = 1;
        }
    }

    public static void test_sequece(ProtectedPointer protectedPointer) {
        protectedPointer.release();
    }

    public static void test_close_with_work(final ProtectedPointer protectedPointer) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    if (protectedPointer.enter()) {
                        ((Data) protectedPointer.getData()).work();
                        protectedPointer.exit();
                    } else {
                        System.out.println("the data has been destroy");
                    }
                }
            }
        });
        new Thread(new Runnable() {
            public void run() {
                protectedPointer.release();
            }
        }).run();
        thread.run();
    }

    public static void test_close_anywhere1(ProtectedPointer protectedPointer) {
        if (protectedPointer.enter()) {
            protectedPointer.release();
            ((Data) protectedPointer.getData()).work();
            protectedPointer.exit();
        }
    }

    public static void main(String[] strArr) {
        for (int i = 0; i < 1; i++) {
            ProtectedPointer protectedPointer = new ProtectedPointer(new Data());
            protectedPointer.setHow2close(new ProtectedPointer.ProtectedPointerOnClose() {
                public void close(Object obj) {
                    ((Data) obj).destroy();
                }
            });
            test_close_with_work(protectedPointer);
        }
    }
}
