package com.revolsys.jocl.core;

import org.jocl.CL;
import org.jocl.Pointer;
import org.jocl.cl_mem;

public class OpenClMemory implements AutoCloseable {
  private final cl_mem memory;

  public OpenClMemory(final cl_mem memory) {
    this.memory = memory;
  }

  @Override
  public void close() {
    CL.clReleaseMemObject(this.memory);
  }

  public cl_mem getMemory() {
    return this.memory;
  }

  public Pointer newPointer() {
    return Pointer.to(this.memory);
  }

  @Override
  public String toString() {
    return this.memory.toString();
  }
}
