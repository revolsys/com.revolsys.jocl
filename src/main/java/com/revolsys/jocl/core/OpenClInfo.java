package com.revolsys.jocl.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.jocl.Pointer;
import org.jocl.Sizeof;

public interface OpenClInfo {

  void getInfo(final int paramName, final int size, final Pointer pointer);

  long getInfoBufferSize(final int paramName);

  default int getInfoInt(final int paramName) {
    return getInfoInts(paramName, 1)[0];
  }

  default int[] getInfoInts(final int paramName, final int numValues) {
    final int values[] = new int[numValues];
    getInfo(paramName, Sizeof.cl_int * numValues, Pointer.to(values));
    return values;
  }

  default long getInfoLong(final int paramName) {
    return getInfoLongs(paramName, 1)[0];
  }

  default long[] getInfoLongs(final int paramName, final int numValues) {
    final long values[] = new long[numValues];
    getInfo(paramName, Sizeof.cl_long * numValues, Pointer.to(values));
    return values;
  }

  default long getInfoSize(final int paramName) {
    return getInfoSizes(paramName, 1).get(0);
  }

  default List<Long> getInfoSizes(final int paramName, final int numValues) {
    final ByteBuffer buffer = ByteBuffer.allocate(numValues * Sizeof.size_t)
      .order(ByteOrder.nativeOrder());
    getInfo(paramName, Sizeof.size_t * numValues, Pointer.to(buffer));
    final List<Long> values = new ArrayList<>(numValues);
    if (Sizeof.size_t == 4) {
      for (int i = 0; i < numValues; i++) {
        values.add((long)buffer.getInt(i * Sizeof.size_t));
      }
    } else {
      for (int i = 0; i < numValues; i++) {
        values.add(buffer.getLong(i * Sizeof.size_t));
      }
    }
    return values;
  }

  default String getInfoString(final int paramName) {
    final long size = getInfoBufferSize(paramName);
    final byte buffer[] = new byte[(int)size];
    getInfo(paramName, buffer.length, Pointer.to(buffer));

    return new String(buffer, 0, buffer.length - 1);
  }

}
