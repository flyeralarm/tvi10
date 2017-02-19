# Smart Color Strip
Make your color strips smart by extending it with metadata. Smart color strips are particularily in Industry 4.0 environments increasingly important in order to tag automatically color measurment series with metadata like the job id etc. Using the Smart Color Stip, the color measurment plus the metadata can be catched on one shot with all default measurement devices as the metadata encoded as color panels. In the following a sample of such an TVI 10 Smart Color Strip. The same concept can be used for all other color strips and targets as well:

![A smart tvi10 color strip.](https://github.com/ricebean-net/SmartColorStrip/blob/master/docs/smart-color-strip.png?raw=true "A smart tvi10 color strip.")

## Functional Description
Fist of all many thanks to Jan-Peter Homann, who was a great discussion partner for the development of the concept of smart color strips.
Standard measurement devices.


## Maven Dependencies
The SmartColorStrip library is also available in the Central Maven Repository:

```xml
<dependency>
    <groupId>net.ricebean.tools.colorstrip</groupId>
    <artifactId>SmartColorStrip</artifactId>
    <version>1.0</version>
</dependency>
```

## Groovy Code Sample
The usage of the SmartColorStrip library is very straight forward. Currently, only the creation of tvi10 strips is supported. If there is some interest, i will extend the library by more default color strips or even a more generic interface in order to build custom strips.

```groovy
import net.ricebean.tools.colorstrip.ColorStripFactory
import java.nio.file.Paths

@Grapes(
        @Grab(group='net.ricebean.tools.colorstrip', module='SmartColorStrip', version='1.0')
)

File targetFile = Paths.get("/Users/stefan/desktop/tvi10smart.pdf").toFile()
ColorStripFactory.createTvi10Strip(463746374, targetFile)
```
