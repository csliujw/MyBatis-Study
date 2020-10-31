package org.example.annotate.TIJ.sql;

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
}