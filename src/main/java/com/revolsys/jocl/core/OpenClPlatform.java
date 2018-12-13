package com.revolsys.jocl.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jocl.CL;
import org.jocl.Pointer;
import org.jocl.cl_device_id;
import org.jocl.cl_platform_id;

public class OpenClPlatform implements OpenClInfo {

  private final cl_platform_id platformId;

  public OpenClPlatform(final cl_platform_id id) {
    this.platformId = id;
  }

  public List<OpenClDevice> getDevices() {
    final long deviceType = CL.CL_DEVICE_TYPE_ALL;
    return getDevices(deviceType);
  }

  public List<OpenClDevice> getDevices(final long deviceType) {
    final int deviceCountArray[] = new int[1];
    CL.clGetDeviceIDs(this.platformId, deviceType, 0, null, deviceCountArray);
    final int deviceCount = deviceCountArray[0];

    final cl_device_id ids[] = new cl_device_id[deviceCount];
    CL.clGetDeviceIDs(this.platformId, deviceType, deviceCount, ids, null);
    final List<OpenClDevice> devices = new ArrayList<>();
    for (final cl_device_id deviceId : ids) {
      final OpenClDevice device = new OpenClDevice(this, deviceId);
      devices.add(device);
    }
    return devices;
  }

  public List<String> getExtensions() {
    return Arrays.asList(getInfoString(CL.CL_PLATFORM_EXTENSIONS).split(" "));
  }

  public Map<String, Object> getInfo() {
    final Map<String, Object> info = new LinkedHashMap<>();
    info.put("CL_PLATFORM_PROFILE", getProfile());
    info.put("CL_PLATFORM_VERSION", getVersion());
    info.put("CL_PLATFORM_NAME", getName());
    info.put("CL_PLATFORM_VENDOR", getVendor());
    info.put("CL_PLATFORM_EXTENSIONS", getExtensions());
    return info;
  }

  @Override
  public void getInfo(final int paramName, final int size, final Pointer pointer) {
    CL.clGetPlatformInfo(this.platformId, paramName, size, pointer, null);
  }

  @Override
  public long getInfoBufferSize(final int paramName) {
    final long sizes[] = new long[1];
    CL.clGetPlatformInfo(this.platformId, paramName, 0, null, sizes);
    return sizes[0];
  }

  public String getName() {
    return getInfoString(CL.CL_PLATFORM_NAME);
  }

  public cl_platform_id getPlatformId() {
    return this.platformId;
  }

  public String getProfile() {
    return getInfoString(CL.CL_PLATFORM_PROFILE);
  }

  public String getVendor() {
    return getInfoString(CL.CL_PLATFORM_VENDOR);
  }

  public String getVersion() {
    return getInfoString(CL.CL_PLATFORM_VERSION);
  }

  @Override
  public String toString() {
    return this.platformId.toString();
  }
}
