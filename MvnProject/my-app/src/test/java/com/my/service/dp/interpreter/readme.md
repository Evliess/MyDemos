## Example

java.util.regex.Pattern

```java
Pattern pattern = pattern.compile("ADMIN", Patter.CASE_INSENSITIVE);
Matcher matcher = Pattern.matcher("admin, USER);
while (matcher.find()) {
  System.out.println("permission accept" + matcher.group());
}
```