package com.revolsys.jocl.core;

import static org.jocl.CL.clCreateBuffer;

import org.jocl.CL;
import org.jocl.Pointer;
import org.jocl.cl_command_queue;
import org.jocl.cl_context;
import org.jocl.cl_context_properties;
import org.jocl.cl_device_id;
import org.jocl.cl_mem;
import org.jocl.cl_platform_id;
import org.jocl.cl_program;

public class OpenClContextForDevice implements AutoCloseable {

  private final cl_context context;

  private final OpenClDevice device;

  public OpenClContextForDevice(final OpenClDevice device) {
    this.device = device;
    final cl_context_properties contextProperties = new cl_context_properties();
    final cl_platform_id platformId = device.getPlatformId();
    contextProperties.addProperty(CL.CL_CONTEXT_PLATFORM, platformId);
    final cl_device_id deviceId = device.getId();
    this.context = CL.clCreateContext(contextProperties, 1, new cl_device_id[] {
      deviceId
    }, null, null, null);
  }

  @Override
  public void close() {
    CL.clReleaseContext(this.context);
  }

  public OpenClCommandQueue newCommandQueue() {
    final cl_device_id deviceId = this.device.getId();
    final cl_command_queue commandQueue = CL.clCreateCommandQueue(this.context, deviceId, 0, null);
    return new OpenClCommandQueue(commandQueue);
  }

  public OpenClMemory newMemory(final long flags, final long size) {
    final cl_mem memory = clCreateBuffer(this.context, flags, size, null, null);
    return new OpenClMemory(memory);
  }

  public OpenClMemory newMemory(final long flags, final long size, final Pointer pointer) {
    final cl_mem memory = clCreateBuffer(this.context, flags, size, pointer, null);
    return new OpenClMemory(memory);
  }

  public OpenClProgram newProgram(final String source) {
    final cl_program id = CL.clCreateProgramWithSource(this.context, 1, new String[] {
      source
    }, null, null);
    final String compileOptions = "-cl-mad-enable";
    CL.clBuildProgram(id, 0, null, compileOptions, null, null);

    return new OpenClProgram(id);
  }

  @Override
  public String toString() {
    return this.context.toString();
  }
}
