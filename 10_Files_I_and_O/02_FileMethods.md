# FileMethods

file1.exists();Returns true if file/directory exists

file1.delete();Returns true if file/directory deleted

file1.getAbsolutePath();Returns String path of file/directory

file1.getName();Returns string Name of file/directory

file1.getParent();Returns parent folder directory returns null if passed like this "test.txt"

file1.isDirectory();Returns true if it is directory

file1.isFile();Returns true if it is file

file1.lastModified();Returns long timeStamp

file1.length();Return long file size

file1.listFiles();Returns list of files in directory

file1.mkdir();

file1.mkdirs();

File dest = new File("new.txt");

```java
System.out.println(file1.renameTo(dest));

```

It's important to notice from where we are running the java command in case of relative paths. For example if my class is in folder src-->javatest-->Test.java. It also has a file test.txt. Now if I run file.exists() from src as java .\javaTest\Test.java it will return false as it will check for test.txt in src. But if we run it from javatest then it will find the file and return true
