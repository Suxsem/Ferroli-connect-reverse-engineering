package org.eclipse.jetty.websocket;

import java.util.Random;

public class RandomMaskGen implements MaskGen {
    private final Random _random;

    public RandomMaskGen() {
        this(new Random());
    }

    public RandomMaskGen(Random random) {
        this._random = random;
    }

    public void genMask(byte[] bArr) {
        this._random.nextBytes(bArr);
    }
}
