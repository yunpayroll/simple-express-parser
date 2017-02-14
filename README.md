# simple-express-parser
it's a simple express parse tools,support such as +,-,*,/ functions etc.


useage:
``` java
    String line = "10 * 10 + 1 + 2 * 3 + 5 * 2";
		long s = System.currentTimeMillis();
		MyParser myParser = new MyParser();
		for(int i=0;i<10000;i++) {
			myParser.parse(line);

			Object value = myParser.getValue();
  		System.out.println(value);
		}
```
