package com.revolsys.jocl.core;

import org.jocl.CL;
import org.jocl.Pointer;
import org.jocl.cl_command_queue;
import org.jocl.cl_mem;

public class OpenClCommandQueue implements AutoCloseable {

  private final cl_command_queue commandQueue;

  public OpenClCommandQueue(final cl_command_queue commandQueue) {
    this.commandQueue = commandQueue;
  }

  @Override
  public void close() {
    CL.clReleaseCommandQueue(this.commandQueue);
  }

  public OpenClCommandQueue enqueueNDRangeKernel(final OpenClKernel kernel,
    final long... globalWorkSize) {
    CL.clEnqueueNDRangeKernel(this.commandQueue, kernel.getKernel(), globalWorkSize.length, null,
      globalWorkSize, null, 0, null, null);
    return this;
  }

  public cl_command_queue getCommandQueue() {
    return this.commandQueue;
  }

  public OpenClCommandQueue readBuffer(final OpenClMemory memory, final long length,
    final Pointer pointer) {
    final cl_mem mem = memory.getMemory();
    CL.clEnqueueReadBuffer(this.commandQueue, mem, CL.CL_TRUE, 0, length, pointer, 0, null, null);
    return this;
  }

  @Override
  public String toString() {
    return this.commandQueue.toString();
  }
}
