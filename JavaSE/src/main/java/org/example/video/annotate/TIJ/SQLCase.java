package org.example.video.annotate.TIJ;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface DBTable {
    String name() default "";
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Constraints {
    boolean primaryKey() default false;

    boolean allowNull() default false;

    boolean unique() default false;
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface SQLString {
    int value() default 0; // String的长度

    String name() default "";

    Constraints constraints() default @Constraints;
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface SQLInteger {
    String name() default "";

    Constraints constraints() default @Constraints;
}

/**
 * 为了代码简洁，就不搞Set Get了
 */
@DBTable(name = "SQLCase")
public class SQLCase {
    @SQLString(30)
    String firstName;

    @SQLString(60)
    String lastName;

    @SQLInteger
    Integer age;

    @SQLString(value = 120, constraints = @Constraints(primaryKey = true))
    String handle;

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("org.example.video.annotate.TIJ.SQLCase");
        // 获得 SQLCase类上，DBTable相关的注解信息
        DBTable annotation = clazz.getAnnotation(DBTable.class);
        if (annotation == null) {
            System.out.println("No DBTable annotation!");
        }
        String tableName = annotation.name();
        if (tableName.length() < 1) {
            // 没有注解 就用类名当表名
            tableName = clazz.getName().toUpperCase();
        }
        ArrayList<String> columnDefs = new ArrayList<String>();
        for (Field f : clazz.getDeclaredFields()) {
            String columnName = null;
            // 字段上只有一个注解，所以可以直接用annotations[0]访问，不用for遍历
            Annotation[] annotations = f.getAnnotations();
            Annotation ann = annotations[0];
            // 拿到 字段
            if (ann instanceof SQLString) {
                SQLString annS = (SQLString) ann;
                if (annS.name().length() < 1) {
                    columnName = f.getName().toUpperCase();
                } else {
                    columnName = annS.name();
                }
                columnDefs.add(columnName + " varchar(" + annS.value() + ")" + getConstraints(annS.constraints()));
            }
            if (annotations[0] instanceof SQLInteger) {
                SQLInteger annS = (SQLInteger) ann;
                if (annS.name().length() < 1) {
                    columnName = f.getName().toUpperCase();
                } else {
                    columnName = annS.name();
                }
                columnDefs.add(columnName + " INT" + getConstraints(annS.constraints()));
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(tableName);
        sb.append("(\n");
        for (String column : columnDefs) {
            sb.append(column + ",\n");
        }
        String substring = sb.substring(0, sb.length() - 2);
        System.out.println(substring + "\n)");
    }

    private static String getConstraints(Constraints con) {
        String constraints = "";
        if (!con.allowNull()) {
            constraints += " NOT NULL";
        }
        if (con.primaryKey()) {
            constraints += " PRIMARY KEY";
        }
        if (con.unique()) {
            constraints += " UNIQUE";
        }
        return constraints;
    }

}

