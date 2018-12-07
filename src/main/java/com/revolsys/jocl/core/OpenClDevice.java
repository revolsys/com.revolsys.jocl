package com.revolsys.jocl.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.jocl.CL;
import org.jocl.Pointer;
import org.jocl.Sizeof;
import org.jocl.cl_device_id;
import org.jocl.cl_platform_id;

public class OpenClDevice implements AutoCloseable {

  private final cl_platform_id platformId;

  private final OpenClPlatform platform;

  private final cl_device_id deviceId;

  public OpenClDevice(final OpenClPlatform platform, final cl_device_id id) {
    this.platform = platform;
    this.platformId = platform.getId();
    this.deviceId = id;
  }

  @Override
  public void close() {
    CL.clReleaseDevice(this.deviceId);
  }

  public cl_device_id getId() {
    return this.deviceId;
  }

  public int getInfoInt(final int paramName) {
    return getInfoInts(paramName, 1)[0];
  }

  public int[] getInfoInts(final int paramName, final int numValues) {
    final int values[] = new int[numValues];
    CL.clGetDeviceInfo(this.deviceId, paramName, Sizeof.cl_int * numValues, Pointer.to(values),
      null);
    return values;
  }

  public long getInfoLong(final int paramName) {
    return getInfoLongs(paramName, 1)[0];
  }

  public long[] getInfoLongs(final int paramName, final int numValues) {
    final long values[] = new long[numValues];
    CL.clGetDeviceInfo(this.deviceId, paramName, Sizeof.cl_long * numValues, Pointer.to(values),
      null);
    return values;
  }

  public long getInfoSize(final int paramName) {
    return getInfoSizes(paramName, 1)[0];
  }

  public long[] getInfoSizes(final int paramName, final int numValues) {
    final ByteBuffer buffer = ByteBuffer.allocate(numValues * Sizeof.size_t)
      .order(ByteOrder.nativeOrder());
    CL.clGetDeviceInfo(this.deviceId, paramName, Sizeof.size_t * numValues, Pointer.to(buffer),
      null);
    final long values[] = new long[numValues];
    if (Sizeof.size_t == 4) {
      for (int i = 0; i < numValues; i++) {
        values[i] = buffer.getInt(i * Sizeof.size_t);
      }
    } else {
      for (int i = 0; i < numValues; i++) {
        values[i] = buffer.getLong(i * Sizeof.size_t);
      }
    }
    return values;
  }

  public String getInfoString(final int paramName) {
    final long size[] = new long[1];
    CL.clGetDeviceInfo(this.deviceId, paramName, 0, null, size);

    final byte buffer[] = new byte[(int)size[0]];
    CL.clGetDeviceInfo(this.deviceId, paramName, buffer.length, Pointer.to(buffer), null);

    return new String(buffer, 0, buffer.length - 1);
  }

  public OpenClPlatform getPlatform() {
    return this.platform;
  }

  public cl_platform_id getPlatformId() {
    return this.platformId;
  }

  public OpenClContextForDevice newContext() {
    return new OpenClContextForDevice(this);
  }

  @Override
  public String toString() {
    return this.deviceId.toString();
  }
}
