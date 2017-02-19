# Smart Color Strip
Make your color strips smart by extending it with metadata. Smart Color Strips are particularily in Industry 4.0 environments increasingly important in order to tag color measurment series automatically with metadata like the job id etc. Using a Smart Color Stip, the color measurement plus the metadata can be catched on one shot as the metadata is encoded using additinal color fields. In the following a sample of such an TVI 10 Smart Color Strip. The same concept can be used for all other color strips and targets as well:

![A smart tvi10 color strip.](https://github.com/ricebean-net/SmartColorStrip/blob/master/docs/smart-color-strip.png?raw=true "A smart tvi10 color strip.")

## Functional Description
The Smart Color Strip uses the panels of the orignal color strip in order to encode the metadata as color fields. In the sample above, the basis for the metadata encoding was the TVI 10 Color Strip. A TVI 10 Color Strip consists of 45 individual fields. Each color field in the original color strip has a specific value. Here, the CMY Field on the left has the value "0" whereas the C100 Field on the right has the value "44". This means, a TVI 10 Color Strip provides 45 states we can use for encoding metadata. So, the usage of 6 additional color fields enables us to encode 45 to the power of 6 individual states (= 8,303,765,625 states).

This method of encoding is also robust against color variation during print production as the reference values of the metadata are also printed on the same sheet. Further, most standard color measurement devices can be used in order to catch the information as the metadata are just color fields.

Many thanks to Jan-Peter Homann, who was a great discussion partner for the development of the concept of Smart Color Strips.


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
