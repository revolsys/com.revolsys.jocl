package com.revolsys.jocl.core;

import java.util.List;

import org.jocl.CL;
import org.jocl.NativePointerObject;
import org.jocl.Pointer;
import org.jocl.Sizeof;
import org.jocl.cl_kernel;

public class OpenClKernel implements AutoCloseable {

  private final cl_kernel kernel;

  private int argCount = 0;

  private final OpenClContextForDevice context;

  public OpenClKernel(final OpenClContextForDevice context, final cl_kernel kernel) {
    this.context = context;
    this.kernel = kernel;
  }

  public OpenClKernel addArg(final double value) {
    final Pointer pointer = Pointer.to(new double[] {
      value
    });
    return addArg(Sizeof.cl_double, pointer);
  }

  public OpenClKernel addArg(final float value) {
    final Pointer pointer = Pointer.to(new float[] {
      value
    });
    return addArg(Sizeof.cl_double, pointer);
  }

  public OpenClKernel addArg(final int value) {
    final Pointer pointer = Pointer.to(new int[] {
      value
    });
    return addArg(Sizeof.cl_uint, pointer);
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
    return addArg((float)value);
  }

  public OpenClKernel addArgInt(final int value) {
    return addArg(value);
  }

  public OpenClMemory addArgMemory(final List<OpenClMemory> memories, final double... values) {
    final Pointer pointer = Pointer.to(values);
    final int size = values.length * Sizeof.cl_double;
    final long flags = CL.CL_MEM_READ_ONLY | CL.CL_MEM_USE_HOST_PTR;
    final OpenClMemory memory = this.context.addNewMemory(memories, flags, size, pointer);
    addArg(memory);
    return memory;
  }

  public OpenClMemory addArgMemory(final List<OpenClMemory> memories, final float... values) {
    final Pointer pointer = Pointer.to(values);
    final int size = values.length * Sizeof.cl_float;
    final long flags = CL.CL_MEM_READ_ONLY | CL.CL_MEM_USE_HOST_PTR;
    final OpenClMemory memory = this.context.addNewMemory(memories, flags, size, pointer);
    addArg(memory);
    return memory;
  }

  public OpenClMemory addArgMemory(final List<OpenClMemory> memories, final int... values) {
    final Pointer pointer = Pointer.to(values);
    final int size = values.length * Sizeof.cl_int;
    final long flags = CL.CL_MEM_READ_ONLY | CL.CL_MEM_USE_HOST_PTR;
    final OpenClMemory memory = this.context.addNewMemory(memories, flags, size, pointer);
    addArg(memory);
    return memory;
  }

  public OpenClMemory addArgNewMemory(final List<OpenClMemory> memories, final int size) {
    final OpenClMemory memory = this.context.addNewMemory(memories, CL.CL_MEM_WRITE_ONLY, size);
    addArg(memory);
    return memory;
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
