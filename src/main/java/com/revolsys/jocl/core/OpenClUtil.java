package com.revolsys.jocl.core;

import java.util.ArrayList;
import java.util.List;

import org.jocl.CL;
import org.jocl.cl_platform_id;

public class OpenClUtil {
  static {
    CL.setExceptionsEnabled(true);
  }

  public static List<OpenClPlatform> getPlatforms() {
    final int numPlatformsArray[] = new int[1];
    CL.clGetPlatformIDs(0, null, numPlatformsArray);
    final int numPlatforms = numPlatformsArray[0];

    final cl_platform_id ids[] = new cl_platform_id[numPlatforms];
    CL.clGetPlatformIDs(ids.length, ids, null);

    final List<OpenClPlatform> platforms = new ArrayList<>();
    for (final cl_platform_id id : ids) {
      final OpenClPlatform platform = new OpenClPlatform(id);
      platforms.add(platform);
    }
    return platforms;
  }
}
