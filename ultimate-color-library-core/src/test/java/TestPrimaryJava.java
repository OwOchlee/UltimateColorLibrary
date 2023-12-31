import app.moreo.ucl.Color;
import app.moreo.ucl.colors.HSLColor;
import app.moreo.ucl.colors.SRGBColor;
import app.moreo.ucl.enums.ColorType;
import kotlin.test.junit5.JUnit5Asserter;
import org.junit.jupiter.api.Test;

public class TestPrimaryJava {

    private final SRGBColor testingColor = new SRGBColor((short) 150, (short) 192, (short) 98);
    private final SRGBColor shortHexColor = new SRGBColor((short) 153, (short) 204, (short) 0, .8f);

    @Test
    public void primaryConversionTest() {
        HSLColor color = testingColor.toSpace(ColorType.HSL);
        JUnit5Asserter.INSTANCE.assertEquals("HSL conversion wrong", color.getDegreesHue(), 87);
    }

    @Test
    public void longHexParsingTest() {
        SRGBColor color = Color.fromString("#96C062", ColorType.SRGB);
        JUnit5Asserter.INSTANCE.assertEquals("Long hex parsing wrong", testingColor, color);
    }

    @Test
    public void shortHexParsingTest() {
        SRGBColor color = Color.fromString("#9C0C", ColorType.SRGB);
        JUnit5Asserter.INSTANCE.assertEquals("Short hex parsing wrong", shortHexColor, color);
    }

    @Test
    public void rgbParsingTest() {
        SRGBColor color = Color.fromString("rgb(150, 192, 98)", ColorType.SRGB);
        JUnit5Asserter.INSTANCE.assertEquals("RGB parsing wrong", testingColor, color);

        color = Color.fromString("rgb(153 204 0 / .8)", ColorType.SRGB);
        JUnit5Asserter.INSTANCE.assertEquals("RGB parsing wrong", shortHexColor, color);
    }
}
