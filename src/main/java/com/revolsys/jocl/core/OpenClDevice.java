package com.revolsys.jocl.core;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jocl.CL;
import org.jocl.Pointer;
import org.jocl.cl_device_id;
import org.jocl.cl_platform_id;

public class OpenClDevice implements OpenClInfo {

  private final cl_platform_id platformId;

  private final OpenClPlatform platform;

  final cl_device_id deviceId;

  public OpenClDevice(final OpenClPlatform platform, final cl_device_id id) {
    this.platform = platform;
    this.platformId = platform.getPlatformId();
    this.deviceId = id;
  }

  public cl_device_id getId() {
    return this.deviceId;
  }

  public Map<String, Object> getInfo() {
    final Map<String, Object> info = new LinkedHashMap<>();
    final String deviceName = getInfoString(CL.CL_DEVICE_NAME);
    info.put("CL_DEVICE_NAME", deviceName);

    final String deviceVendor = getInfoString(CL.CL_DEVICE_VENDOR);
    info.put("CL_DEVICE_VENDOR", deviceVendor);

    final String driverVersion = getInfoString(CL.CL_DRIVER_VERSION);
    info.put("CL_DRIVER_VERSION", driverVersion);

    final long deviceType = getInfoLong(CL.CL_DEVICE_TYPE);
    if ((deviceType & CL.CL_DEVICE_TYPE_CPU) != 0) {
      info.put("CL_DEVICE_TYPE", "CL_DEVICE_TYPE_CPU");
    }
    if ((deviceType & CL.CL_DEVICE_TYPE_GPU) != 0) {
      info.put("CL_DEVICE_TYPE", "CL_DEVICE_TYPE_GPU");
    }
    if ((deviceType & CL.CL_DEVICE_TYPE_ACCELERATOR) != 0) {
      info.put("CL_DEVICE_TYPE", "CL_DEVICE_TYPE_ACCELERATOR");
    }
    if ((deviceType & CL.CL_DEVICE_TYPE_DEFAULT) != 0) {
      info.put("CL_DEVICE_TYPE", "CL_DEVICE_TYPE_DEFAULT");
    }

    final int maxComputeUnits = getInfoInt(CL.CL_DEVICE_MAX_COMPUTE_UNITS);
    info.put("CL_DEVICE_MAX_COMPUTE_UNITS", maxComputeUnits);

    final long maxWorkItemDimensions = getInfoLong(CL.CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS);
    info.put("CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS", maxWorkItemDimensions);

    final List<Long> maxWorkItemSizes = getMaxWorkItemSizes();
    info.put("CL_DEVICE_MAX_WORK_ITEM_SIZES", maxWorkItemSizes);

    final long maxWorkGroupSize = getInfoSize(CL.CL_DEVICE_MAX_WORK_GROUP_SIZE);
    info.put("CL_DEVICE_MAX_WORK_GROUP_SIZE", maxWorkGroupSize);

    final long maxClockFrequency = getInfoLong(CL.CL_DEVICE_MAX_CLOCK_FREQUENCY);
    info.put("CL_DEVICE_MAX_CLOCK_FREQUENCY", maxClockFrequency);

    final int addressBits = getInfoInt(CL.CL_DEVICE_ADDRESS_BITS);
    info.put("CL_DEVICE_ADDRESS_BITS", addressBits);

    final long maxMemAllocSize = getInfoLong(CL.CL_DEVICE_MAX_MEM_ALLOC_SIZE);
    info.put("CL_DEVICE_MAX_MEM_ALLOC_SIZE", maxMemAllocSize);

    final long globalMemSize = getInfoLong(CL.CL_DEVICE_GLOBAL_MEM_SIZE);
    info.put("CL_DEVICE_GLOBAL_MEM_SIZE", globalMemSize);

    final int errorCorrectionSupport = getInfoInt(CL.CL_DEVICE_ERROR_CORRECTION_SUPPORT);
    info.put("CL_DEVICE_ERROR_CORRECTION_SUPPORT", errorCorrectionSupport != 0 ? "yes" : "no");

    final int localMemType = getInfoInt(CL.CL_DEVICE_LOCAL_MEM_TYPE);
    info.put("CL_DEVICE_LOCAL_MEM_TYPE", localMemType == 1 ? "local" : "global");

    final long localMemSize = getInfoLong(CL.CL_DEVICE_LOCAL_MEM_SIZE);
    info.put("CL_DEVICE_LOCAL_MEM_SIZE", localMemSize);

    final long maxConstantBufferSize = getInfoLong(CL.CL_DEVICE_MAX_CONSTANT_BUFFER_SIZE);
    info.put("CL_DEVICE_MAX_CONSTANT_BUFFER_SIZE", maxConstantBufferSize);

    final long queueProperties = getInfoLong(CL.CL_DEVICE_QUEUE_PROPERTIES);
    if ((queueProperties & CL.CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE) != 0) {
      info.put("CL_DEVICE_QUEUE_PROPERTIES", "CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE");
    }
    if ((queueProperties & CL.CL_QUEUE_PROFILING_ENABLE) != 0) {
      info.put("CL_DEVICE_QUEUE_PROPERTIES", "CL_QUEUE_PROFILING_ENABLE");
    }

    final int imageSupport = getInfoInt(CL.CL_DEVICE_IMAGE_SUPPORT);
    info.put("CL_DEVICE_IMAGE_SUPPORT", imageSupport);

    final int maxReadImageArgs = getInfoInt(CL.CL_DEVICE_MAX_READ_IMAGE_ARGS);
    info.put("CL_DEVICE_MAX_READ_IMAGE_ARGS", maxReadImageArgs);

    final int maxWriteImageArgs = getInfoInt(CL.CL_DEVICE_MAX_WRITE_IMAGE_ARGS);
    info.put("CL_DEVICE_MAX_WRITE_IMAGE_ARGS", maxWriteImageArgs);

    final long singleFpConfig = getInfoLong(CL.CL_DEVICE_SINGLE_FP_CONFIG);
    info.put("CL_DEVICE_SINGLE_FP_CONFIG",
      Arrays.asList(CL.stringFor_cl_device_fp_config(singleFpConfig).split(" ")));

    final long image2dMaxWidth = getInfoSize(CL.CL_DEVICE_IMAGE2D_MAX_WIDTH);
    info.put("CL_DEVICE_2D_MAX_WIDTH", image2dMaxWidth);

    final long image2dMaxHeight = getInfoSize(CL.CL_DEVICE_IMAGE2D_MAX_HEIGHT);
    info.put("CL_DEVICE_2D_MAX_HEIGHT", image2dMaxHeight);

    final long image3dMaxWidth = getInfoSize(CL.CL_DEVICE_IMAGE3D_MAX_WIDTH);
    info.put("CL_DEVICE_3D_MAX_WIDTH", image3dMaxWidth);

    final long image3dMaxHeight = getInfoSize(CL.CL_DEVICE_IMAGE3D_MAX_HEIGHT);
    info.put("CL_DEVICE_3D_MAX_HEIGHT", image3dMaxHeight);

    final long image3dMaxDepth = getInfoSize(CL.CL_DEVICE_IMAGE3D_MAX_DEPTH);
    info.put("CL_DEVICE_3D_MAX_DEPTH", image3dMaxDepth);

    final int preferredVectorWidthChar = getInfoInt(CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_CHAR);
    info.put("CL_DEVICE_PREFERRED_VECTOR_WIDTH_CHAR", preferredVectorWidthChar);

    final int preferredVectorWidthShort = getInfoInt(CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_SHORT);
    info.put("CL_DEVICE_PREFERRED_VECTOR_WIDTH_SHORT", preferredVectorWidthShort);

    final int preferredVectorWidthInt = getInfoInt(CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_INT);
    info.put("CL_DEVICE_PREFERRED_VECTOR_WIDTH_INT", preferredVectorWidthInt);

    final int preferredVectorWidthLong = getInfoInt(CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_LONG);
    info.put("CL_DEVICE_PREFERRED_VECTOR_WIDTH_LONG", preferredVectorWidthLong);

    final int preferredVectorWidthFloat = getInfoInt(CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_FLOAT);
    info.put("CL_DEVICE_PREFERRED_VECTOR_WIDTH_FLOAT", preferredVectorWidthFloat);

    final int preferredVectorWidthDouble = getInfoInt(CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_DOUBLE);
    info.put("CL_DEVICE_PREFERRED_VECTOR_WIDTH_DOUBLE", preferredVectorWidthDouble);

    return info;
  }

  @Override
  public void getInfo(final int paramName, final int size, final Pointer pointer) {
    CL.clGetDeviceInfo(this.deviceId, paramName, size, pointer, null);
  }

  @Override
  public long getInfoBufferSize(final int paramName) {
    final long sizes[] = new long[1];
    CL.clGetDeviceInfo(this.deviceId, paramName, 0, null, sizes);
    return sizes[0];
  }

  public List<Long> getMaxWorkItemSizes() {
    final List<Long> maxWorkItemSizes = getInfoSizes(CL.CL_DEVICE_MAX_WORK_ITEM_SIZES, 3);
    return maxWorkItemSizes;
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
    return this.deviceId.toString();
  }
}
