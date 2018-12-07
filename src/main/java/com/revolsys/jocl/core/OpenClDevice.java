package com.revolsys.jocl.core;

import org.jocl.CL;
import org.jocl.cl_device_id;
import org.jocl.cl_platform_id;

public class OpenClDevice implements AutoCloseable {

  private final cl_platform_id platformId;

  private final OpenClPlatform platform;

  private final cl_device_id id;

  public OpenClDevice(final OpenClPlatform platform, final cl_device_id id) {
    this.platform = platform;
    this.platformId = platform.getId();
    this.id = id;
  }

  @Override
  public void close() {
    CL.clReleaseDevice(this.id);
  }

  public cl_device_id getId() {
    return this.id;
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
    return this.id.toString();
  }
}
