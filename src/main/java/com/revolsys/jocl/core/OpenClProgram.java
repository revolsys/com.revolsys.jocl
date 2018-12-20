package com.revolsys.jocl.core;

import org.jocl.CL;
import org.jocl.cl_kernel;
import org.jocl.cl_program;

public class OpenClProgram implements AutoCloseable {

  private final cl_program id;

  private final OpenClContextForDevice context;

  public OpenClProgram(final OpenClContextForDevice context, final cl_program id) {
    this.context = context;
    this.id = id;
  }

  @Override
  public void close() {
    CL.clReleaseProgram(this.id);
  }

  public cl_program getId() {
    return this.id;
  }

  public OpenClKernel newKernel(final String functionName) {
    final cl_kernel kernel_id = CL.clCreateKernel(this.id, functionName, null);
    return new OpenClKernel(this.context, kernel_id);
  }

  @Override
  public String toString() {
    return this.id.toString();
  }
}
