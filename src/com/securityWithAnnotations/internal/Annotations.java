package com.securityWithAnnotations.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Annotations {
  
  @Target(ElementType.TYPE)
  @Repeatable(PermissionsContainer.class)
  public @interface Permissions {
    Role role();
    OperationType[] allowed();
  }

  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface PermissionsContainer {
    Permissions[] value();
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  public @interface MethodOperations {
    OperationType[] value();
  }
}
