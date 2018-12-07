package com.revolsys.jocl.core;

import java.util.ArrayList;
import java.util.List;

import org.jocl.CL;
import org.jocl.cl_device_id;
import org.jocl.cl_platform_id;

public class OpenClPlatform {

  private final cl_platform_id id;

  public OpenClPlatform(final cl_platform_id id) {
    this.id = id;
  }

  public List<OpenClDevice> getDevices() {
    final long deviceType = CL.CL_DEVICE_TYPE_ALL;
    return getDevices(deviceType);
  }

  public List<OpenClDevice> getDevices(final long deviceType) {
    final int deviceCountArray[] = new int[1];
    CL.clGetDeviceIDs(this.id, deviceType, 0, null, deviceCountArray);
    final int deviceCount = deviceCountArray[0];

    final cl_device_id ids[] = new cl_device_id[deviceCount];
    CL.clGetDeviceIDs(this.id, deviceType, deviceCount, ids, null);
    final List<OpenClDevice> devices = new ArrayList<>();
    for (final cl_device_id deviceId : ids) {
      final OpenClDevice device = new OpenClDevice(this, deviceId);
      devices.add(device);
    }
    return devices;
  }

  public cl_platform_id getId() {
    return this.id;
  }

  @Override
  public String toString() {
    return this.id.toString();
  }
}
