package org.example.video.annotate.TIJ;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Annotation01 {
    // 空注解
}
