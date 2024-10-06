package com.dfsek.seismic.math.trigonometry;

class TrigonometryUtils {
    private static final int lookupBits = 14;

    static final int lookupTableSize = 1 << lookupBits;

    private static final int lookupTableSizeWithMargin = lookupTableSize + 1;
    private static final double tauOverLookupSize = TrigonometryConstants.TAU / lookupTableSize;
    static final double radianToIndex = (~(-1 << lookupBits) + 1) / TrigonometryConstants.TAU;
    private static final long[] sinTable;

    static {
        sinTable = new long[lookupTableSizeWithMargin];
        for(int i = 0; i < lookupTableSizeWithMargin; i++) {
            double d = i * tauOverLookupSize;
            sinTable[i] = Double.doubleToRawLongBits(StrictMath.sin(d));
        }
    }

    static double sinLookup(long index) {
        // Trigonometric identity: sin(-x) = -sin(x)
        // Given a domain of 0 <= x <= 2*pi, just negate the value if x > pi.
        // This allows the sin table size to be halved.
        long neg = (index & 0x8000000000000000L) << 32;

        // All bits set if (pi/2 <= x), none set otherwise
        // Extracts the 31st bit from 'half'
        long mask = (index << 33) >> 63;

        // Trigonometric identity: sin(x) = sin(pi/2 - x)
        long pos = (0x8000000000000001L & mask) + (index ^ mask);

        // Wrap the position in the table. Moving this down to immediately before the array access
        // seems to help the Hotspot compiler optimize the bit math better.
        pos &= 0x7fffffffffffffffL;

        // Fetch the corresponding value from the LUT and invert the sign bit as needed
        // This directly manipulate the sign bit on the double bits to simplify logic
        return Double.longBitsToDouble(sinTable[(int) pos] ^ neg);
    }
}
