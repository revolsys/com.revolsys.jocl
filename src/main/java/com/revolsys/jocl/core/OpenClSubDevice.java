package com.revolsys.jocl.core;

import org.jocl.CL;
import org.jocl.cl_device_id;

public class OpenClSubDevice extends OpenClDevice implements AutoCloseable {

  public OpenClSubDevice(final OpenClPlatform platform, final cl_device_id id) {
    super(platform, id);
  }

  @Override
  public void close() {
    CL.clReleaseDevice(this.deviceId);
  }
}
