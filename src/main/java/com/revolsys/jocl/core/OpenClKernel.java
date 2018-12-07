package com.revolsys.jocl.core;

import org.jocl.CL;
import org.jocl.NativePointerObject;
import org.jocl.Pointer;
import org.jocl.Sizeof;
import org.jocl.cl_kernel;

public class OpenClKernel implements AutoCloseable {

  private final cl_kernel kernel;

  private int argCount = 0;

  public OpenClKernel(final cl_kernel kernel) {
    this.kernel = kernel;
  }

  public OpenClKernel addArg(final double... values) {
    final Pointer pointer = Pointer.to(values);
    return addArg(Sizeof.cl_double * values.length, pointer);
  }

  public OpenClKernel addArg(final float... values) {
    final Pointer pointer = Pointer.to(values);
    return addArg(Sizeof.cl_double * values.length, pointer);
  }

  public OpenClKernel addArg(final int... values) {
    final Pointer pointer = Pointer.to(values);
    return addArg(Sizeof.cl_int * values.length, pointer);
  }

  public OpenClKernel addArg(final long argSize, final NativePointerObject value) {
    final Pointer pointer = Pointer.to(value);
    return addArg(argSize, pointer);
  }

  public OpenClKernel addArg(final long argSize, final Pointer pointer) {
    CL.clSetKernelArg(this.kernel, this.argCount++, argSize, pointer);
    return this;
  }

  public OpenClKernel addArg(final NativePointerObject value) {
    final Pointer pointer = Pointer.to(value);
    return addArg(Sizeof.cl_mem, pointer);
  }

  public OpenClKernel addArg(final OpenClMemory memory) {
    final Pointer pointer = memory.newPointer();
    return addArg(Sizeof.cl_mem, pointer);
  }

  public OpenClKernel addArgFloat(final double value) {
    return addArg(value);
  }

  @Override
  public void close() {
    CL.clReleaseKernel(this.kernel);
  }

  public cl_kernel getKernel() {
    return this.kernel;
  }

  @Override
  public String toString() {
    return this.kernel.toString();
  }
}
