

import java.util.List;

import org.jocl.CL;

import com.revolsys.jocl.core.OpenClDevice;
import com.revolsys.jocl.core.OpenClInfo;
import com.revolsys.jocl.core.OpenClPlatform;
import com.revolsys.jocl.core.OpenClUtil;

public class JoclInfo {
  public static void main(final String[] args) {
    final List<OpenClPlatform> platforms = OpenClUtil.getPlatforms();
    final OpenClPlatform platform = platforms.get(0);

    final List<OpenClDevice> devices = platform.getDevices(CL.CL_DEVICE_TYPE_GPU);
    for (final OpenClInfo device : devices) {
      // CL.CL_DEVICE_NAME
      final String deviceName = device.getInfoString(CL.CL_DEVICE_NAME);
      System.out.println("--- Info for device " + deviceName + ": ---");
      System.out.printf("CL_DEVICE_NAME: \t\t\t%s\n", deviceName);

      // CL.CL_DEVICE_VENDOR
      final String deviceVendor = device.getInfoString(CL.CL_DEVICE_VENDOR);
      System.out.printf("CL_DEVICE_VENDOR: \t\t\t%s\n", deviceVendor);

      // CL.CL_DRIVER_VERSION
      final String driverVersion = device.getInfoString(CL.CL_DRIVER_VERSION);
      System.out.printf("CL_DRIVER_VERSION: \t\t\t%s\n", driverVersion);

      // CL.CL_DEVICE_TYPE
      final long deviceType = device.getInfoLong(CL.CL_DEVICE_TYPE);
      if ((deviceType & CL.CL_DEVICE_TYPE_CPU) != 0) {
        System.out.printf("CL_DEVICE_TYPE:\t\t\t\t%s\n", "CL_DEVICE_TYPE_CPU");
      }
      if ((deviceType & CL.CL_DEVICE_TYPE_GPU) != 0) {
        System.out.printf("CL_DEVICE_TYPE:\t\t\t\t%s\n", "CL_DEVICE_TYPE_GPU");
      }
      if ((deviceType & CL.CL_DEVICE_TYPE_ACCELERATOR) != 0) {
        System.out.printf("CL_DEVICE_TYPE:\t\t\t\t%s\n", "CL_DEVICE_TYPE_ACCELERATOR");
      }
      if ((deviceType & CL.CL_DEVICE_TYPE_DEFAULT) != 0) {
        System.out.printf("CL_DEVICE_TYPE:\t\t\t\t%s\n", "CL_DEVICE_TYPE_DEFAULT");
      }

      // CL.CL_DEVICE_MAX_COMPUTE_UNITS
      final int maxComputeUnits = device.getInfoInt(CL.CL_DEVICE_MAX_COMPUTE_UNITS);
      System.out.printf("CL_DEVICE_MAX_COMPUTE_UNITS:\t\t%d\n", maxComputeUnits);

      // CL.CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS
      final long maxWorkItemDimensions = device.getInfoLong(CL.CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS);
      System.out.printf("CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS:\t%d\n", maxWorkItemDimensions);

      // CL.CL_DEVICE_MAX_WORK_ITEM_SIZES
      final long maxWorkItemSizes[] = device.getInfoSizes(CL.CL_DEVICE_MAX_WORK_ITEM_SIZES, 3);
      System.out.printf("CL_DEVICE_MAX_WORK_ITEM_SIZES:\t\t%d / %d / %d \n", maxWorkItemSizes[0],
        maxWorkItemSizes[1], maxWorkItemSizes[2]);

      // CL.CL_DEVICE_MAX_WORK_GROUP_SIZE
      final long maxWorkGroupSize = device.getInfoSize(CL.CL_DEVICE_MAX_WORK_GROUP_SIZE);
      System.out.printf("CL_DEVICE_MAX_WORK_GROUP_SIZE:\t\t%d\n", maxWorkGroupSize);

      // CL.CL_DEVICE_MAX_CLOCK_FREQUENCY
      final long maxClockFrequency = device.getInfoLong(CL.CL_DEVICE_MAX_CLOCK_FREQUENCY);
      System.out.printf("CL_DEVICE_MAX_CLOCK_FREQUENCY:\t\t%d MHz\n", maxClockFrequency);

      // CL.CL_DEVICE_ADDRESS_BITS
      final int addressBits = device.getInfoInt(CL.CL_DEVICE_ADDRESS_BITS);
      System.out.printf("CL_DEVICE_ADDRESS_BITS:\t\t\t%d\n", addressBits);

      // CL.CL_DEVICE_MAX_MEM_ALLOC_SIZE
      final long maxMemAllocSize = device.getInfoLong(CL.CL_DEVICE_MAX_MEM_ALLOC_SIZE);
      System.out.printf("CL_DEVICE_MAX_MEM_ALLOC_SIZE:\t\t%d MByte\n",
        (int)(maxMemAllocSize / (1024 * 1024)));

      // CL.CL_DEVICE_GLOBAL_MEM_SIZE
      final long globalMemSize = device.getInfoLong(CL.CL_DEVICE_GLOBAL_MEM_SIZE);
      System.out.printf("CL_DEVICE_GLOBAL_MEM_SIZE:\t\t%d MByte\n",
        (int)(globalMemSize / (1024 * 1024)));

      // CL.CL_DEVICE_ERROR_CORRECTION_SUPPORT
      final int errorCorrectionSupport = device.getInfoInt(CL.CL_DEVICE_ERROR_CORRECTION_SUPPORT);
      System.out.printf("CL_DEVICE_ERROR_CORRECTION_SUPPORT:\t%s\n",
        errorCorrectionSupport != 0 ? "yes" : "no");

      // CL.CL_DEVICE_LOCAL_MEM_TYPE
      final int localMemType = device.getInfoInt(CL.CL_DEVICE_LOCAL_MEM_TYPE);
      System.out.printf("CL_DEVICE_LOCAL_MEM_TYPE:\t\t%s\n",
        localMemType == 1 ? "local" : "global");

      // CL.CL_DEVICE_LOCAL_MEM_SIZE
      final long localMemSize = device.getInfoLong(CL.CL_DEVICE_LOCAL_MEM_SIZE);
      System.out.printf("CL_DEVICE_LOCAL_MEM_SIZE:\t\t%d KByte\n", (int)(localMemSize / 1024));

      // CL.CL_DEVICE_MAX_CONSTANT_BUFFER_SIZE
      final long maxConstantBufferSize = device.getInfoLong(CL.CL_DEVICE_MAX_CONSTANT_BUFFER_SIZE);
      System.out.printf("CL_DEVICE_MAX_CONSTANT_BUFFER_SIZE:\t%d KByte\n",
        (int)(maxConstantBufferSize / 1024));

      // CL.CL_DEVICE_QUEUE_PROPERTIES
      final long queueProperties = device.getInfoLong(CL.CL_DEVICE_QUEUE_PROPERTIES);
      if ((queueProperties & CL.CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE) != 0) {
        System.out.printf("CL_DEVICE_QUEUE_PROPERTIES:\t\t%s\n",
          "CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE");
      }
      if ((queueProperties & CL.CL_QUEUE_PROFILING_ENABLE) != 0) {
        System.out.printf("CL_DEVICE_QUEUE_PROPERTIES:\t\t%s\n", "CL_QUEUE_PROFILING_ENABLE");
      }

      // CL.CL_DEVICE_IMAGE_SUPPORT
      final int imageSupport = device.getInfoInt(CL.CL_DEVICE_IMAGE_SUPPORT);
      System.out.printf("CL_DEVICE_IMAGE_SUPPORT:\t\t%d\n", imageSupport);

      // CL.CL_DEVICE_MAX_READ_IMAGE_ARGS
      final int maxReadImageArgs = device.getInfoInt(CL.CL_DEVICE_MAX_READ_IMAGE_ARGS);
      System.out.printf("CL_DEVICE_MAX_READ_IMAGE_ARGS:\t\t%d\n", maxReadImageArgs);

      // CL.CL_DEVICE_MAX_WRITE_IMAGE_ARGS
      final int maxWriteImageArgs = device.getInfoInt(CL.CL_DEVICE_MAX_WRITE_IMAGE_ARGS);
      System.out.printf("CL_DEVICE_MAX_WRITE_IMAGE_ARGS:\t\t%d\n", maxWriteImageArgs);

      // CL.CL_DEVICE_SINGLE_FP_CONFIG
      final long singleFpConfig = device.getInfoLong(CL.CL_DEVICE_SINGLE_FP_CONFIG);
      System.out.printf("CL_DEVICE_SINGLE_FP_CONFIG:\t\t%s\n",
        CL.stringFor_cl_device_fp_config(singleFpConfig));

      // CL.CL_DEVICE_IMAGE2D_MAX_WIDTH
      final long image2dMaxWidth = device.getInfoSize(CL.CL_DEVICE_IMAGE2D_MAX_WIDTH);
      System.out.printf("CL_DEVICE_2D_MAX_WIDTH\t\t\t%d\n", image2dMaxWidth);

      // CL.CL_DEVICE_IMAGE2D_MAX_HEIGHT
      final long image2dMaxHeight = device.getInfoSize(CL.CL_DEVICE_IMAGE2D_MAX_HEIGHT);
      System.out.printf("CL_DEVICE_2D_MAX_HEIGHT\t\t\t%d\n", image2dMaxHeight);

      // CL.CL_DEVICE_IMAGE3D_MAX_WIDTH
      final long image3dMaxWidth = device.getInfoSize(CL.CL_DEVICE_IMAGE3D_MAX_WIDTH);
      System.out.printf("CL_DEVICE_3D_MAX_WIDTH\t\t\t%d\n", image3dMaxWidth);

      // CL.CL_DEVICE_IMAGE3D_MAX_HEIGHT
      final long image3dMaxHeight = device.getInfoSize(CL.CL_DEVICE_IMAGE3D_MAX_HEIGHT);
      System.out.printf("CL_DEVICE_3D_MAX_HEIGHT\t\t\t%d\n", image3dMaxHeight);

      // CL.CL_DEVICE_IMAGE3D_MAX_DEPTH
      final long image3dMaxDepth = device.getInfoSize(CL.CL_DEVICE_IMAGE3D_MAX_DEPTH);
      System.out.printf("CL_DEVICE_3D_MAX_DEPTH\t\t\t%d\n", image3dMaxDepth);

      // CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_<type>
      System.out.printf("CL_DEVICE_PREFERRED_VECTOR_WIDTH_<t>\t");
      final int preferredVectorWidthChar = device
        .getInfoInt(CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_CHAR);
      final int preferredVectorWidthShort = device
        .getInfoInt(CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_SHORT);
      final int preferredVectorWidthInt = device
        .getInfoInt(CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_INT);
      final int preferredVectorWidthLong = device
        .getInfoInt(CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_LONG);
      final int preferredVectorWidthFloat = device
        .getInfoInt(CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_FLOAT);
      final int preferredVectorWidthDouble = device
        .getInfoInt(CL.CL_DEVICE_PREFERRED_VECTOR_WIDTH_DOUBLE);
      System.out.printf("CHAR %d, SHORT %d, INT %d, LONG %d, FLOAT %d, DOUBLE %d\n\n\n",
        preferredVectorWidthChar, preferredVectorWidthShort, preferredVectorWidthInt,
        preferredVectorWidthLong, preferredVectorWidthFloat, preferredVectorWidthDouble);
    }

  }
}
