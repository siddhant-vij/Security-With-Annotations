package com.securityWithAnnotations;

import java.util.*;
import java.lang.reflect.*;

import com.securityWithAnnotations.internal.*;
import com.securityWithAnnotations.internal.Annotations.MethodOperations;
import com.securityWithAnnotations.internal.Annotations.Permissions;

public class PermissionsChecker {
  public static void checkPermissions(Object callerObject, String callerMethodName)
      throws Throwable {
    User user = getLoggedInUser(callerObject);
    Method callingMethod = getCallingMethod(callerObject, callerMethodName);
    Permissions[] allPermissions = getClassAnnotatedPermissions(callerObject);
    MethodOperations methodOperations = getCallerMethodOperations(callingMethod);
    OperationType[] methodOperationTypes = methodOperations.value();
    List<OperationType> userAllowedOperations = findUserAllowedOperations(allPermissions, user);
    for (OperationType methodOperationsTypes : methodOperationTypes) {
      if (!userAllowedOperations.contains(methodOperationsTypes)) {
        throw new PermissionException();
      }
    }
  }

  static List<OperationType> findUserAllowedOperations(Permissions[] allPermissions,
      User user) {
    for (Permissions permission : allPermissions) {
      if (user.getRole().equals(permission.role())) {
        return Arrays.asList(permission.allowed());
      }
    }
    return Collections.emptyList();
  }

  static Permissions[] getClassAnnotatedPermissions(Object callerObject) {
    return callerObject.getClass().getAnnotationsByType(Permissions.class);
  }

  static MethodOperations getCallerMethodOperations(Method callerMethod) {
    return callerMethod.getAnnotation(MethodOperations.class);
  }

  private static User getLoggedInUser(Object callerObject)
      throws NoSuchFieldException, IllegalAccessException {
    Class<?> callerClass = callerObject.getClass();
    Field userField = callerClass.getDeclaredField("user");
    userField.setAccessible(true);
    if (!userField.getType().equals(User.class)) {
      throw new IllegalStateException("The caller object must have a user field of type User");
    }
    return (User) userField.get(callerObject);
  }

  private static Method getCallingMethod(Object callerObject, String methodName) {
    return Arrays.stream(callerObject.getClass().getDeclaredMethods())
        .filter(method -> method.getName().equals(methodName))
        .findFirst()
        .orElseThrow(
            () -> new IllegalStateException(String.format("The passed method name :%s does not exist", methodName)));
  }
}
