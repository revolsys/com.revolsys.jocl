package com.revolsys.jocl.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jocl.CL;
import org.jocl.cl_platform_id;

public class OpenClUtil {
  private static boolean available = false;
  static {
    try {
      CL.setExceptionsEnabled(true);
      available = true;
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public static List<OpenClPlatform> getPlatforms() {
    final List<OpenClPlatform> platforms = new ArrayList<>();
    if (isAvailable()) {
      final int numPlatformsArray[] = new int[1];
      CL.clGetPlatformIDs(0, null, numPlatformsArray);
      final int numPlatforms = numPlatformsArray[0];

      final cl_platform_id ids[] = new cl_platform_id[numPlatforms];
      CL.clGetPlatformIDs(ids.length, ids, null);

      for (final cl_platform_id id : ids) {
        final OpenClPlatform platform = new OpenClPlatform(id);
        platforms.add(platform);
      }
    }
    return platforms;
  }

  public static boolean isAvailable() {
    return available;
  }

  public static String sourceFromClasspath(final String path) {
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    try (
      InputStream in = classLoader.getResourceAsStream(path)) {
      if (in == null) {
        throw new IllegalArgumentException("Unable to read source from: " + path);
      } else {
        try (
          final BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
          final StringBuffer sb = new StringBuffer();
          String line = null;
          while (true) {
            line = br.readLine();
            if (line == null) {
              break;
            }
            sb.append(line).append("\n");
          }
          return sb.toString();
        } catch (final IOException e) {
          throw new RuntimeException("Error reading source from: " + path);
        }
      }
    } catch (final IOException e) {
      throw new RuntimeException("Error reading source from: " + path);
    }
  }
}
